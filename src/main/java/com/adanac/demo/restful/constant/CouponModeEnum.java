package com.adanac.demo.restful.constant;

/**
 * 
 * 券发行方式枚举值
 * @author zhangs
 *
 */
public enum CouponModeEnum {

	//1:自助领取 2:定向发放
	/**
	 * 1:自助领取
	 */
	SELF_COLL(1),
	/**
	 * 2:定向发放
	 */
	TARGET_DIST(2);
	
	private int value;
	
	private CouponModeEnum(int value)
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
