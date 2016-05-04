package com.adanac.demo.restful.dao.xs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adanac.demo.restful.dao.common.BaseDao;
import com.adanac.demo.restful.entity.xs.XSActivityDto;
import com.adanac.demo.restful.utils.uuid.DefaultSequenceGenerator;
import com.adanac.framework.utils.TimeUtil;

/**
 * 活动基础业务实现类
 */
@Service("limitedBaseService")
public class LimitedBaseServiceImpl implements LimitedBaseService {

	@Autowired
	private BaseDao baseDao;

	@Override
	@Transactional
	public String addActivity(XSActivityDto suppActivityDto) {
		// 给ID赋值
		String id = DefaultSequenceGenerator.getInstance().uuid();
		suppActivityDto.setId(id);
		// 添加制单日期和最后修改日期
		suppActivityDto.setCreateDttm(TimeUtil.getCurrentTimestamp());// 创建时间
		suppActivityDto.setLastDttm(TimeUtil.getCurrentTimestamp());// 最后修改时间
		Integer count = baseDao.execute("limited.insert", suppActivityDto);
		if (count > 0) {
			return id;
		} else {
			return null;
		}
	}

	@Override
	public Boolean modActivity(XSActivityDto suppActivityDto) {
		// 给ID赋值
		suppActivityDto.setCreateDttm(TimeUtil.getCurrentTimestamp());// 创建时间
		suppActivityDto.setLastDttm(TimeUtil.getCurrentTimestamp());// 最后修改时间
		Integer count = baseDao.execute("limited.updateById", suppActivityDto);
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

}
