package com.adanac.demo.restful.coupon;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.adanac.demo.restful.BaseTest;
import com.adanac.demo.restful.constant.CouponModeEnum;
import com.adanac.demo.restful.constant.CouponTypeEnum;
import com.adanac.demo.restful.entity.coupon.CouponRuleDto;
import com.adanac.demo.restful.service.coupon.CouponRuleService;
import com.adanac.framework.page.Pager;
import com.adanac.framework.page.PagerParam;
import com.adanac.framework.utils.DateUtils;
import com.adanac.framework.utils.JsonUtils;

public class CouponRuleServiceImplTest extends BaseTest {

	@Autowired
	private CouponRuleService couponRuleService;

	@Test
	public void test_addCouponRule() {
		CouponRuleDto dto = new CouponRuleDto();
		dto.setSuppId("18");
		dto.setName("HTC手机促销");
		dto.setType(CouponTypeEnum.GENERAL.getValue());
		dto.setFaceValue(1d);

		dto.setStartTime(DateUtils.format(new Date(), "yyyy-MM-dd"));
		dto.setEndTime(DateUtils.format(new Date(), "yyyy-MM-dd"));
		dto.setIssuedMode(CouponModeEnum.SELF_COLL.getValue());
		dto.setIssuedNum(12);
		// dto.setSurplusNum(15);
		dto.setTakeMaxNum(1);
		dto.setSatisfyMoney(1000d);
		dto.setCreateUserId("20");
		dto.setUpdateUserId("20");

		couponRuleService.addCouponRule(dto);
	}

	@Test
	public void test_modCouponRule() {
		CouponRuleDto dto = new CouponRuleDto();
		dto.setName("new 发券");
		dto.setIssuedNum(20);
		dto.setSurplusNum(20);
		dto.setId("a634999b3bf6475e9570445f06634c83");
		couponRuleService.modCouponRule(dto);
	}

	@Test
	public void test_stopCounponRule() {
		String id = "a634999b3bf6475e9570445f06634c83";
		String userId = "11";
		couponRuleService.stopCounponRule(id, userId);
	}

	@Test
	public void test_publishCouponRule() {
		String id = "24d8fac81a8148c7aade84ec16fdb414";
		String userId = "11";
		couponRuleService.publishCouponRule(id, userId);
	}

	@Test
	public void test_getCouponRule() {
		String id = "1088a3f6e4254404977e13d327dfbdcb";
		CouponRuleDto dto = couponRuleService.getCouponRule(id);
		System.out.println("-----" + dto.toString());
	}

	@Test
	public void test_queryList() {
		String name = "";
		String startTime = "";
		String endTime = "";
		Integer type = null;
		Integer status = 1;
		PagerParam page = new PagerParam();
		page.setPageNumber(1);
		page.setPageSize(10);
		String suppId = "";
		Pager<CouponRuleDto> list = couponRuleService.queryList(suppId, name, status, startTime, endTime, type, page);
		System.out.println("--------" + JsonUtils.bean2json(list));
	}

}
