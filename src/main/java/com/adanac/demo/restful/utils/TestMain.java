package com.adanac.demo.restful.utils;

import java.util.Date;

import com.adanac.demo.restful.constant.CouponFaceValueEnum;
import com.adanac.framework.utils.DateUtils;
import com.adanac.framework.utils.TimeUtil;

public class TestMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String beginTime = TimeUtil.getCurrentTimestamp();
		System.out.println("beginTime=" + beginTime);

		Date endDate = DateUtils.parse("2016-03-28 15:30:00", DateUtils.DEFAULT_YEAR_MONTH_DAY_HMS);
		Date now = new Date();
		long cha = (endDate.getTime() - now.getTime()) / 1000;
		System.out.println(cha);

		System.out.println("desc=" + CouponFaceValueEnum.valueOf(10));

		String attr = "111:222|111:333";
		attr = attr.replaceAll(":", "_").replaceAll("\\|", ",");
		System.out.println(attr);

		String name = "[\"母婴\",\"奶粉\",\"牛奶粉\"]";
		System.out.println(name.substring(1, name.length() - 1).replaceAll("\"", ""));
	}

}
