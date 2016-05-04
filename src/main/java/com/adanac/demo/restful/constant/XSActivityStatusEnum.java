package com.adanac.demo.restful.constant;

public enum XSActivityStatusEnum {
	//活动状态:0已开始|1未开始|2已结束(即已終止状态)
	STATUS_HAS_START(0),
	STATUS_NOT_START(1),
	STATUS_END(2);
	
	private int value;
	
	private XSActivityStatusEnum(int value)
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
