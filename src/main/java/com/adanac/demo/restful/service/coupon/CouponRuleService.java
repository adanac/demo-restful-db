package com.adanac.demo.restful.service.coupon;

import com.adanac.demo.restful.entity.coupon.CouponRuleDto;
import com.adanac.framework.exception.SysException;
import com.adanac.framework.page.Pager;
import com.adanac.framework.page.PagerParam;

/**
 * 券规则业务接口
 * 
 * @author zhangs
 *
 */
public interface CouponRuleService {

	/**
	 * 
	 * 添加优惠券规则
	 * 
	 * @param dto
	 *            优惠券规则
	 * @return 主键
	 * @throws SysException
	 */
	public String addCouponRule(CouponRuleDto dto) throws SysException;

	/**
	 * 修改券规则
	 * 
	 * @param dto
	 *            优惠券规则
	 * @return true 成功 false-失败
	 * @throws SysException
	 */
	public Boolean modCouponRule(CouponRuleDto dto) throws SysException;

	/**
	 * 终止券规则
	 * 
	 * @param id
	 *            券规则id
	 * @param userId
	 *            userId
	 * @return true 成功 false-失败
	 * @throws SysException
	 */
	public Boolean stopCounponRule(String id, String userId) throws SysException;

	/**
	 * 发布券规则
	 * 
	 * @param id
	 *            券规则id
	 * @param userId
	 *            userid
	 * @return true 成功 false-失败
	 * @throws SysException
	 */
	public Boolean publishCouponRule(String id, String userId) throws SysException;

	/**
	 * 根据id查询券规则 ---如果规则已过期则返回null
	 * 
	 * @param id
	 *            券规则id
	 * @return 券规则对象
	 * @throws SysException
	 */
	public CouponRuleDto getCouponRule(String id) throws SysException;

	/**
	 * 根据id查询券规则
	 * 
	 * @param id
	 *            券规则id
	 * @return 券规则对象
	 * @throws SysException
	 */
	public CouponRuleDto getDetail(String id) throws SysException;

	/**
	 * 
	 * 查询券规则分页列表
	 * 
	 * @param suppId
	 *            商家Id
	 * @param name
	 * @param status
	 *            状态
	 * @param startTime
	 * @param endTime
	 * @param type
	 * @param pageParam
	 * @return
	 * @throws SysException
	 */
	public Pager<CouponRuleDto> queryList(String suppId, String name, Integer status, String startTime, String endTime,
			Integer type, PagerParam pageParam) throws SysException;

	/**
	 * 
	 * 根据规则id生成券-主要用于缓存中没有，但是还未领完的券
	 * 
	 * @param ruleId
	 */
	public void generateNo(String ruleId);
}
