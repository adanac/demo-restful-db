package com.adanac.demo.restful.service.xs;

import java.util.List;

import com.adanac.demo.restful.entity.xs.XSActivityDto;
import com.adanac.framework.exception.SysException;
import com.adanac.framework.page.Pager;
import com.adanac.framework.page.PagerParam;

/**
 * 提供限时促销业务接口
 * 
 */
public interface XSActivityService {

	/**
	 * 新增限时促销活动信息
	 * 
	 * @param suppActivityDto
	 *            限时促销活动
	 * @return 活动主键
	 */
	public String addActivity(XSActivityDto suppActivityDto) throws SysException;

	/**
	 * 修改限时促销信息
	 * 
	 * @param platActivity
	 *            限时促销活动
	 * @return 是否成功 0-成功 1-失败
	 */
	public Boolean modActivity(XSActivityDto suppActivityDto) throws SysException;

	/**
	 * 终止促销活动
	 * 
	 * @param suppActivityId
	 *            活动Id
	 * @param status
	 *            状态值
	 * @param userId
	 *            更新用户ID
	 * @return 是否成功 0-成功 1-失败
	 */
	public Boolean stopActivity(String suppActivityId, String userId) throws SysException;

	/**
	 * 根据限时促销活动Id查询活动信息
	 * 
	 * @param suppActivityId
	 *            限时促销活动Id
	 * @return 平台活动dto
	 */
	public XSActivityDto getActivity(String suppActivityId) throws SysException;

	/**
	 * 根据分页对象查询分页平台活动列表
	 * 
	 * @param suppId
	 *            供应商Id
	 * @param name
	 *            活动名称
	 * @param statusDis
	 *            活动状态0已开始|1未开始|2已结束
	 * @param startTime
	 *            开始时间 格式为: yyyy-MM-dd HH:MM:SS
	 * @param endTime
	 *            结束时间 格式为: yyyy-MM-dd HH:MM:SS
	 * @param pageParam
	 *            分页对象
	 * @return 限时促销活动对象
	 */
	public Pager<XSActivityDto> queryActivityList(String suppId, String name, String status, String startTime,
			String endTime, PagerParam pageParam) throws SysException;

	/**
	 * 根据供应商Id，活动Id，查询出与该活动有时间重叠的活动列表
	 * 
	 * @param suppId
	 *            供应商Id
	 * @param activityId
	 *            限时促销活动Id
	 * @return 平台活动dto
	 */
	public List<XSActivityDto> queryRepeatActivityList(String suppId, String activityId) throws SysException;

}
