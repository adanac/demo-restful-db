package com.adanac.demo.restful.constant;

/**
 * 
 * 券状态枚举值
 * @author zhangs
 *
 */
public enum CouponRuleStatusEnum {

	// 1:已发布2:已保存 3:已终止
	/**
	 * 1:已发布
	 */
	PUBLISH(1),
	/**
	 * 2:已保存
	 */
	SAVED(2),
	
	/**
	 *  3:已终止
	 */
	STOPED(3);
		
	
	private int value;
	
	private CouponRuleStatusEnum(int value)
	{
		this.value=value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
}
