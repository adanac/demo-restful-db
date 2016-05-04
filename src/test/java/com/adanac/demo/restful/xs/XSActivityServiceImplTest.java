package com.adanac.demo.restful.xs;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.adanac.demo.restful.BaseTest;
import com.adanac.demo.restful.constant.DiscountTypeEnum;
import com.adanac.demo.restful.constant.XSActivityStatusEnum;
import com.adanac.demo.restful.entity.xs.XSActivityDto;
import com.adanac.demo.restful.service.xs.XSActivityService;
import com.adanac.demo.restful.utils.uuid.DefaultSequenceGenerator;
import com.adanac.framework.log.MyLogger;
import com.adanac.framework.log.MyLoggerFactory;
import com.adanac.framework.page.Pager;
import com.adanac.framework.page.PagerParam;
import com.adanac.framework.utils.TimeUtil;

public class XSActivityServiceImplTest extends BaseTest {

	private MyLogger log = MyLoggerFactory.getLogger(XSActivityServiceImplTest.class);

	@Autowired
	private XSActivityService xSActivityService;

	@Test
	public void addActivity() {

		XSActivityDto dto = new XSActivityDto();
		dto.setId(DefaultSequenceGenerator.getInstance().uuid());
		dto.setSuppId("100000");
		dto.setName("xs-dazhe");
		dto.setStartDttm("2016-03-02 10:00:00");
		dto.setEndDttm("2016-03-12 20:00:00");
		dto.setDiscountType(DiscountTypeEnum.DISCOUNT.getValue());
		dto.setStatus(XSActivityStatusEnum.STATUS_HAS_START.getValue());
		dto.setCreateDttm(TimeUtil.getCurrentTimestamp());
		dto.setLastPer("admin");
		dto.setLastDttm(TimeUtil.getCurrentTimestamp());
		dto.setUrl("");
		dto.setDescripe("阿达达到撒旦撒旦实打实");
		dto.setImg("http://oa.bizcent.com:5003/group1/M00/00/54/wKgBylbVLMzdd2y_AAFCqBRLelA160.jpg");
		dto.setInfo("");
		String uid = xSActivityService.addActivity(dto);
		log.info("success,uid=" + uid);

	}

	@Test
	public void modActivity() {

		String id = "ea33afcfe60b482da89c5364948dacf3";
		XSActivityDto dto = new XSActivityDto();
		dto.setId(id);
		dto.setName("xs-new-dazhe");
		dto.setImg("http://oa.bizcent.com:5003/group1/M00/00/54/wKgBylbVLMzdd2y_AAFCqBRLelA160.jpg");
		Boolean res = xSActivityService.modActivity(dto);
		log.info("success,res=" + res);
	}

	@Test
	public void stopActivity() {

		String suppActivityId = "ea33afcfe60b482da89c5364948dacf3";
		String userId = "22";
		xSActivityService.stopActivity(suppActivityId, userId);
	}

	@Test
	public void getActivity() {
		String id = "ea33afcfe60b482da89c5364948dacf3";
		XSActivityDto dto = xSActivityService.getActivity(id);
		log.info(dto.toString());
	}

	@Test
	public void queryActivityList() {

		String suppId = "1042";
		String name = "";
		String status = "0";
		String startTime = "";
		String endTime = "";
		PagerParam pageParam = new PagerParam();
		pageParam.setPageSize(10);
		pageParam.setPageNumber(1);

		Pager<XSActivityDto> list = xSActivityService.queryActivityList(suppId, name, status, startTime, endTime,
				pageParam);
		log.info("-----------------" + list.getDatas().size());

	}

	@Test
	public void queryRepeatActivityList() {

		String suppId = "";
		String activityId = "";
		List<XSActivityDto> list = xSActivityService.queryRepeatActivityList(suppId, activityId);
		log.info("list size=" + list.size());
	}

}
