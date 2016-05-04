package com.adanac.demo.restful.constant;

/**
 * 
 * 券状态枚举值
 * @author zhangs
 *
 */
public enum CouponStatusEnum {

	// 1:未使用2:已使用 3:已作废
	/**
	 * 1:未使用
	 */
	NOUSE(1),
	/**
	 * 2:已使用
	 */
	USED(2),
	
	
	/**
	 *  3:已作废
	 */
	OBSOLETE(3);
	
	private int value;
	
	private CouponStatusEnum(int value)
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
