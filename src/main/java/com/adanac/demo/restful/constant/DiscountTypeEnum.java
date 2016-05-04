package com.adanac.demo.restful.constant;

/**
 * 
 * 优惠方式枚举值
 * @author zhangs
 *
 */
public enum DiscountTypeEnum {

	/**
	 * 1统一价格(一口价)
	 */
	PRICE(1),
	/**
	 * 2统一折扣(一口折)
	 */
	DISCOUNT(2),
	/**
	 * 3阶梯价格(阶梯价)
	 */
	STEP(3);
	
	private int value;
	
	private DiscountTypeEnum(int value)
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
