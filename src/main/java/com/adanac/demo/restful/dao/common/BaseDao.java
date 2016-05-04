package com.adanac.demo.restful.dao.common;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.adanac.framework.page.Pager;

public interface BaseDao {

	public Number persist(Object entity);

	public <T> T persist(Object entity, Class<T> requiredType);

	public int merge(Object entity);

	public int dynamicMerge(Object entity);

	public int remove(Object entity);

	public <T> T find(Class<T> entityClass, Object entity);

	public <T> T queryForObject(String sqlId, Map<String, Object> paramMap, Class<T> requiredType);

	public <T> T queryForObject(String sqlId, Object param, Class<T> requiredType);

	public Map<String, Object> queryForMap(String sqlId, Map<String, Object> paramMap);

	public Map<String, Object> queryForMap(String sqlId, Object param);

	public <T> List<T> queryForList(String sqlId, Map<String, Object> paramMap, Class<T> elementType);

	public <T> List<T> queryForList(String sqlId, Object param, Class<T> requiredType);

	public List<Map<String, Object>> queryForList(String sqlId, Map<String, Object> paramMap);

	public List<Map<String, Object>> queryForList(String sqlId, Object param);

	public int execute(String sqlId, Map<String, Object> paramMap);

	public int execute(String sqlId, Object param);

	public int[] batchUpdate(String sqlId, Map<String, Object>[] batchValues);

	public <T> Pager<T> queryForPage(String sqlId, Map<String, Object> paramMap, Class<T> elementType, int pageSize,
			int pageNumber);

	public <T> Pager<T> queryForPage(String sqlId, Map<String, Object> paramMap, RowMapper<T> rowMapper, int offset,
			int limit);

	/** 根据sqlId执行，返回主键值 */
	Number execute4PrimaryKey(String sqlId, Map<String, Object> paramMap);
}
