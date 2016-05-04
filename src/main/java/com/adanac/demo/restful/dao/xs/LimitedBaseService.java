package com.adanac.demo.restful.dao.xs;

import com.adanac.demo.restful.entity.xs.XSActivityDto;

/**
 * 活动基础业务实现接口
 */
public interface LimitedBaseService {
	/**
	 * 新增限时促销活动信息
	 * 
	 * @param suppActivityDto
	 *            限时促销活动
	 * @return 活动主键
	 */
	public String addActivity(XSActivityDto suppActivityDto);

	/**
	 * 修改限时促销信息
	 * 
	 * @param platActivity
	 *            限时促销活动
	 * @return 是否成功 0-成功 1-失败
	 */
	public Boolean modActivity(XSActivityDto suppActivityDto);
}
