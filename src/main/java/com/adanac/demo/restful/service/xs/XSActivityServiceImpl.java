package com.adanac.demo.restful.service.xs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adanac.demo.restful.constant.XSActivityStatusEnum;
import com.adanac.demo.restful.dao.common.BaseDao;
import com.adanac.demo.restful.dao.xs.LimitedBaseService;
import com.adanac.demo.restful.entity.xs.XSActivityDto;
import com.adanac.framework.exception.SysException;
import com.adanac.framework.log.MyLogger;
import com.adanac.framework.log.MyLoggerFactory;
import com.adanac.framework.page.Pager;
import com.adanac.framework.page.PagerParam;
import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;

/**
 * 限时促销活动中台业务实现
 * 
 */
@Service("xSActivityService")
@Path("xs")
@com.alibaba.dubbo.config.annotation.Service(protocol = { "dubbo" })
public class XSActivityServiceImpl implements XSActivityService {

	private MyLogger log = MyLoggerFactory.getLogger(XSActivityServiceImpl.class);

	@Autowired
	private BaseDao baseDao;

	@Autowired
	private LimitedBaseService limitedBaseService;

	@Override
	@POST
	@Path("add")
	@Consumes({ MediaType.APPLICATION_JSON, ContentType.APPLICATION_JSON_UTF_8 })
	public String addActivity(XSActivityDto suppActivityDto) {
		log.info("XSActivityServiceImpl-->addActivity-->suppActivityDto:" + suppActivityDto.toString());
		String uuid = null;
		try {
			uuid = limitedBaseService.addActivity(suppActivityDto);
		} catch (Exception e) {
			log.error("新增限时促销活动失败", e);
			throw new SysException("新增限时促销活动失败");
		}
		return uuid;
	}

	@Override
	@PUT
	@Path("mod")
	@Consumes({ MediaType.APPLICATION_JSON, ContentType.APPLICATION_JSON_UTF_8 })
	public Boolean modActivity(XSActivityDto suppActivityDto) {
		log.info("XSActivityServiceImpl-->modActivity-->suppActivityDto:" + suppActivityDto.toString());
		boolean flag = false;
		try {
			flag = limitedBaseService.modActivity(suppActivityDto);
		} catch (Exception e) {
			log.error("修改限时促销活动失败", e);
			throw new SysException("修改限时促销活动失败");
		}
		return flag;
	}

	@Override
	@GET
	@Path("stopActivity")
	public Boolean stopActivity(String suppActivityId, String userId) {
		log.info("XSActivityServiceImpl-->stopActivity-->suppActivityId:" + suppActivityId + "userId" + userId);
		Boolean flag = false;
		try {
			XSActivityDto tempXSAct = new XSActivityDto();
			tempXSAct.setId(suppActivityId);
			tempXSAct.setLastPer(userId);
			tempXSAct.setStatus(XSActivityStatusEnum.STATUS_END.getValue());

			int count = baseDao.execute("limited.stopById", tempXSAct);
			if (count > 0) {
				flag = true;
			}
		} catch (Exception e) {
			log.error("终止促销活动失败", e);
			throw new SysException("终止促销活动失败");
		}
		return flag;
	}

	@Override
	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, ContentType.APPLICATION_JSON_UTF_8 })
	public XSActivityDto getActivity(String suppActivityId) {
		XSActivityDto activityParam = new XSActivityDto();
		activityParam.setId(suppActivityId);
		return baseDao.queryForObject("limited.select", activityParam, XSActivityDto.class);
	}

	@Override
	@GET
	@Path("queryActivityList")
	@Produces({ MediaType.APPLICATION_JSON, ContentType.APPLICATION_JSON_UTF_8 })
	public Pager<XSActivityDto> queryActivityList(@QueryParam("suppId") String suppId, @QueryParam("name") String name,
			@QueryParam("status") String status, @QueryParam("startTime") String startTime,
			@QueryParam("endTime") String endTime, PagerParam pageParam) {
		Map<String, Object> paramMap = new HashMap<String, Object>();

		// 由于状态值只有已结束有效，其他2个值都要根据时间来进行区分的，故要根据当前时间来进行获取相关的数据
		// if(!StringUtils.isEmpty(status))
		// {
		// if(XSActivityStatusEnum.STATUS_NOT_START.getValue()==Integer.parseInt(status))
		// //未开始
		// {
		// paramMap.put("startT", TimeUtil.getCurrentTimestamp());
		// }
		// if(XSActivityStatusEnum.STATUS_HAS_START.getValue()==Integer.parseInt(status))
		// //已开始
		// {
		// paramMap.put("benT", TimeUtil.getCurrentTimestamp());
		// }
		// if(XSActivityStatusEnum.STATUS_END.getValue()==Integer.parseInt(status))
		// {
		// paramMap.put("status", status);
		// paramMap.put("endT", TimeUtil.getCurrentTimestamp());
		// }
		// }
		paramMap.put("status", status);
		paramMap.put("suppId", suppId);
		paramMap.put("name", name);

		paramMap.put("startDttm", startTime);
		paramMap.put("endDttm", endTime);
		// 注意时间范围
		log.info("XSActivityServiceImpl-->queryActivityList:" + paramMap);
		Pager<XSActivityDto> result = new Pager<XSActivityDto>();
		try {
			result = baseDao.queryForPage("limited.select", paramMap, XSActivityDto.class, pageParam.getPageSize(),
					pageParam.getPageNumber());
		} catch (Exception e) {
			log.error("分页查询限时促销列表失败", e);
			throw new SysException("分页查询限时促销列表失败");
		}
		return result;
	}

	@Override
	@GET
	@Path("queryRepeatActivityList")
	@Produces({ MediaType.APPLICATION_JSON, ContentType.APPLICATION_JSON_UTF_8 })
	public List<XSActivityDto> queryRepeatActivityList(@QueryParam("suppId") String suppId,
			@QueryParam("activityId") String activityId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("suppId", suppId);
		paramMap.put("activityId", activityId);
		return baseDao.queryForList("limited.queryRepeatActivityList", paramMap, XSActivityDto.class);
	}

}
