package com.adanac.demo.restful.service.coupon;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adanac.demo.restful.constant.Constants;
import com.adanac.demo.restful.constant.CouponRuleStatusEnum;
import com.adanac.demo.restful.dao.common.BaseDao;
import com.adanac.demo.restful.entity.coupon.CouponRuleDto;
import com.adanac.demo.restful.utils.Cacheable;
import com.adanac.demo.restful.utils.RedisUtil;
import com.adanac.demo.restful.utils.uuid.DefaultSequenceGenerator;
import com.adanac.framework.cache.redis.client.ShardedJedisPipelineAction;
import com.adanac.framework.cache.redis.client.impl.MyShardedClient;
import com.adanac.framework.exception.SysException;
import com.adanac.framework.log.MyLogger;
import com.adanac.framework.log.MyLoggerFactory;
import com.adanac.framework.page.Pager;
import com.adanac.framework.page.PagerParam;
import com.adanac.framework.utils.DateUtils;
import com.adanac.framework.utils.TimeUtil;

import redis.clients.jedis.ShardedJedisPipeline;

/**
 * 券规则业务实现类
 * 
 * @author zhangs
 *
 */
@Service("couponRuleService")
@com.alibaba.dubbo.config.annotation.Service(protocol = { "dubbo" })
public class CouponRuleServiceImpl implements CouponRuleService {

	/**
	 * redis
	 */
	@Autowired
	private MyShardedClient redisClient;

	@Autowired
	private BaseDao baseDao;

	private MyLogger log = MyLoggerFactory.getLogger(CouponRuleServiceImpl.class);

	@Override
	public String addCouponRule(CouponRuleDto dto) throws SysException {

		log.info("addCouponRule dto=" + dto.toString());
		try {
			dto.setStatus(CouponRuleStatusEnum.SAVED.getValue());
			dto.setCreateTime(TimeUtil.getCurrentTimestamp());
			dto.setUpdateTime(TimeUtil.getCurrentTimestamp());
			dto.setId(DefaultSequenceGenerator.getInstance().uuid());
			if (dto.getSurplusNum() == null) {
				dto.setSurplusNum(dto.getIssuedNum());
			}
			baseDao.execute("couponrule.insert", dto);

			// new RedisUtil(redisClient).set(Constants.RULE_PRE+dto.getId(),
			// JsonUtils.bean2json(dto));
		} catch (Exception e) {
			log.error("addCouponRule fail", e);
			throw new SysException("增加券规则失败");
		}
		return dto.getId();
	}

	@Override
	public Boolean modCouponRule(CouponRuleDto dto) throws SysException {
		log.info("modCouponRule dto=" + dto.toString());
		try {
			dto.setUpdateTime(TimeUtil.getCurrentTimestamp());
			baseDao.execute("couponrule.update", dto);

			// new RedisUtil(redisClient).set(Constants.RULE_PRE+dto.getId(),
			// JsonUtils.bean2json(dto));
			new RedisUtil(redisClient).del(Constants.RULE_PRE + dto.getId()); // 删除redis
		} catch (Exception e) {
			log.error("modCouponRule fail", e);
			throw new SysException("修改券规则失败");
		}
		return true;
	}

	@Override
	public Boolean stopCounponRule(String id, String userId) throws SysException {
		log.info("stopCounponRule id=" + id);
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("id", id);
			paramMap.put("status", CouponRuleStatusEnum.STOPED.getValue());
			paramMap.put("updateTime", TimeUtil.getCurrentTimestamp());
			paramMap.put("updateUserId", userId);
			baseDao.execute("couponrule.updateStatus", paramMap);

			// 需要清除redis中未领的券信息
			new RedisUtil(redisClient).del(Constants.RULE_PRE + id);
			new RedisUtil(redisClient).del(id);

		} catch (Exception e) {
			log.error("stopCounponRule fail", e);
		}
		return true;
	}

	@Override
	public Boolean publishCouponRule(String id, String userId) throws SysException {
		log.info("publishCouponRule id=" + id);
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("id", id);
			paramMap.put("status", CouponRuleStatusEnum.PUBLISH.getValue());
			paramMap.put("updateTime", TimeUtil.getCurrentTimestamp());
			paramMap.put("updateUserId", userId);
			baseDao.execute("couponrule.updateStatus", paramMap);

			// 更新redis
			// String json = new
			// RedisUtil(redisClient).get(Constants.RULE_PRE+id);
			//
			// if(!StringUtils.isEmpty(json))
			// {
			// CouponRuleDto dto = JsonUtils.json2bean(json,
			// CouponRuleDto.class);
			// dto.setStatus(CouponRuleStatusEnum.PUBLISH.getValue());
			// dto.setUpdateTime(TimeUtil.getCurrentTimestamp());
			// dto.setUpdateUserId(userId);
			// new RedisUtil(redisClient).set(Constants.RULE_PRE+id,
			// JsonUtils.bean2json(dto));
			// }
			new RedisUtil(redisClient).del(Constants.RULE_PRE + id); // 删除redis

			// 更新完券后，需要实时生成券,
			long start = System.currentTimeMillis();
			toGenNoSingle(id);
			long end = System.currentTimeMillis();
			log.info("--------------------put redis success cost time=" + (end - start));

		} catch (Exception e) {
			log.error("publishCouponRule fail", e);
			throw new SysException("发布券规则失败");
		}
		return true;
	}

	/**
	 * 
	 * 生成券号
	 * 
	 * @param id
	 */
	public void toGenNoSingle(String id) {
		CouponRuleDto rule = getCouponRule(id);
		String ruleId = rule.getId();

		int num = rule.getSurplusNum(); // 已剩余张数为准
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < num; i++) {
			// 生成券号
			String date = DateUtils.format(new Date(), Constants.PATTREN_1);
			// 生成序列号
			String no = date + genSeq(i);
			list.add(no);
		}

		//
		int count = 1;

		List<String> clist = new ArrayList<String>();
		for (String s : list) {
			clist.add(s);
			if (count % 1000 == 0 || count == list.size()) {
				redisClient.execute(clist, new ShardedJedisPipelineAction<Object>() {
					public List<Object> doAction(ShardedJedisPipeline pipeline, Object inParam) {
						List<String> nlist = (ArrayList<String>) inParam;
						for (String s : nlist) {
							pipeline.rpush(ruleId, s);
						}
						return pipeline.syncAndReturnAll();
					}
				});
				clist = new ArrayList<String>();
			}
			count++;
		}

		// 设置规则在redis中的过期时间
		Date endDate = DateUtils.parse(rule.getEndTime(), Constants.PATTREN_2);
		Date now = new Date();
		long cha = (endDate.getTime() - now.getTime()) / 1000;
		redisClient.expire(ruleId, (int) cha);
	}

	static int CRE_SIZE = 1000;

	/**
	 * 
	 * 生成券号
	 * 
	 * @param id
	 */
	public void toGenNo(String id) {
		CouponRuleDto rule = getCouponRule(id);
		String ruleId = rule.getId();

		int num = rule.getIssuedNum();
		// 条数很少，不需要创建线程即可
		if (num < CRE_SIZE) {
			List<String> list = new ArrayList<String>();
			for (int i = 0; i < num; i++) {
				// 生成券号
				String date = DateUtils.format(new Date(), Constants.PATTREN_1);
				// 生成序列号
				String no = date + genSeq(i);
				list.add(no);
			}

			redisClient.execute(list, new ShardedJedisPipelineAction<Object>() {
				public List<Object> doAction(ShardedJedisPipeline pipeline, Object inParam) {
					List<String> nlist = (ArrayList<String>) inParam;
					for (String s : nlist) {
						pipeline.rpush(ruleId, s);
					}
					return pipeline.syncAndReturnAll();
				}
			});
			return;
		}

		// 创建多线程进行券号的生成
		int totalPage = 0;
		if (num % CRE_SIZE == 0) {
			totalPage = num / CRE_SIZE;
		} else {
			totalPage = (num / CRE_SIZE) + 1;
		}
		log.info("totalPage=" + totalPage);

		BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(totalPage);
		ExecutorService service = new ThreadPoolExecutor(10, 10, 0L, TimeUnit.MILLISECONDS, queue);

		for (int i = 1; i <= totalPage; i++) {

			final int startS = (i - 1) * CRE_SIZE;
			int end = i * CRE_SIZE;
			// 判定是否最后一页
			if (i == totalPage) {
				end = num;
			}
			final int endS = end;

			Runnable run = new Runnable() {
				@Override
				public void run() {
					System.out.println("-----------------startS=" + startS + ",endS=" + endS);
					List<String> list = new ArrayList<String>();
					for (int i = startS; i < endS; i++) {
						// 生成券号
						String date = DateUtils.format(new Date(), Constants.PATTREN_1);
						// 生成序列号
						String no = date + genSeq(i);
						System.out.println("[" + i + "]---------------------" + no);
						list.add(no);
					}
					setRedis(ruleId, list);
				}

				private void setRedis(String ruleId, List<String> list) {
					// 存入redis中去
					redisClient.execute(list, new ShardedJedisPipelineAction<Object>() {
						public List<Object> doAction(ShardedJedisPipeline pipeline, Object inParam) {
							List<String> nlist = (ArrayList<String>) inParam;

							for (String s : nlist) {
								pipeline.rpush(ruleId, s);
							}
							return pipeline.syncAndReturnAll();
						}
					});
				}
			};
			service.execute(run);
		}
		service.shutdown();
	}

	/**
	 * 生成序列号
	 * 
	 * @param num
	 * @return
	 */
	private String genSeq(int num) {
		String pre = "";
		int len = Constants.PRE_ZERO_LEN - (num + "").length();
		for (int i = 0; i < len; i++) {
			pre += "0";
		}
		return pre + num;
	}

	@Override
	public CouponRuleDto getCouponRule(String id) throws SysException {
		log.info("getCouponRule id=" + id);
		CouponRuleDto dto = getDetail(id);

		if (null == dto) {
			log.info("getCouponRule is null");
			return null;
		}
		// 需要比对失效时间，失效的话则返回空
		Date endDate = DateUtils.parse(dto.getEndTime(), Constants.PATTREN_2);
		Date now = new Date();
		// 该券已过期了
		if (endDate.before(now)) {
			log.info("getCouponRule is expire {}", id);
			return null;
		}
		return dto;
	}

	@Override
	public Pager<CouponRuleDto> queryList(String suppId, String name, Integer status, String startTime, String endTime,
			Integer type, PagerParam pageParam) throws SysException {
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("name", name);
			paramMap.put("startTime", startTime);
			paramMap.put("endTime", endTime);
			paramMap.put("status", status);
			paramMap.put("type", type);
			paramMap.put("suppId", suppId);

			// if(status!=null&&status.intValue()==CouponRuleStatusEnum.STOPED.getValue())
			// {
			// if(StringUtils.isEmpty(startTime))
			// {
			// paramMap.put("startTime", TimeUtil.getCurrentTimestamp());
			// }
			// if(StringUtils.isEmpty(endTime))
			// {
			// paramMap.put("endTime", TimeUtil.getCurrentTimestamp());
			// }
			// }
			log.info("queryList param = " + paramMap);
			Pager<CouponRuleDto> page = baseDao.queryForPage("couponrule.select", paramMap, CouponRuleDto.class,
					pageParam.getPageSize(), pageParam.getPageNumber());
			List<CouponRuleDto> list = page.getDatas();
			if (list == null || list.isEmpty()) {
				return page;
			}

			// for(CouponRuleDto dto:list)
			// {
			// //判断结束时间是否已到档期时间
			// //需要比对失效时间，失效的话则返回空
			// Date endDate = DateUtils.parse(dto.getEndTime(),
			// Constants.PATTREN_2);
			// Date now = new Date();
			// //该券已过期了
			// if(endDate.before(now))
			// {
			// dto.setStatus(CouponRuleStatusEnum.STOPED.getValue());
			// }
			// }
			return page;
		} catch (Exception e) {
			log.error("queryList fail", e);
			throw new SysException("查询券规则列表失败");
		}
	}

	@Override
	public void generateNo(String ruleId) {
		log.info("generateNo ruleId {}", ruleId);
		this.toGenNoSingle(ruleId);
	}

	@Override
	public CouponRuleDto getDetail(String id) throws SysException {
		log.info("getDetail id {}", id);
		CouponRuleDto dto = null;
		try {
			dto = new RedisUtil(redisClient).get(Constants.RULE_PRE + id, new Cacheable<CouponRuleDto>() {
				@Override
				public CouponRuleDto call() {
					Map<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put("id", id);
					return baseDao.queryForObject("couponrule.select", paramMap, CouponRuleDto.class);
				}
			});
			// log.info("dto {}",dto.toString());
		} catch (Exception e) {
			log.error("getCouponRule fail", e);
			throw new SysException("查询券规则详情失败");
		}
		return dto;
	}

}
