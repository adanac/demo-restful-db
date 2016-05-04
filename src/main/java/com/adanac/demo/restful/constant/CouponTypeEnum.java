package com.adanac.demo.restful.constant;

/**
 * 
 * 券类型枚举值
 * @author zhangs
 *
 */
public enum CouponTypeEnum {

	//1:通用券 2:条码券
	/**
	 * 1:通用券
	 */
	GENERAL(1),
	/**
	 * 2:条码券
	 */
	BARCODE(2);
	
	private int value;
	
	private CouponTypeEnum(int value)
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
