package com.adanac.demo.restful.entity.xs;

import java.io.Serializable;

/**
 * 限时活动Dto
 */
public class XSActivityDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 活动ID
	 */
	private String id;

	/**
	 * 供应商ID
	 */
	private String suppId;

	/**
	 * 活动名称
	 */
	private String name;

	/**
	 * 活动开始时间
	 */
	private String startDttm;

	/**
	 * 活动结束时间
	 */
	private String endDttm;

	/**
	 * 折扣方式(1一口价|2打折|3阶梯价)
	 */
	private Integer discountType;

	/**
	 * 活动状态:目前只用0未开始|1已开始|2已结束
	 * 
	 */
	private Integer status;

	/**
	 * 
	 * 活动显示状态:主要在列表显示排序使用：0已开始|1未开始|2已结束
	 * 
	 */
	private Integer statusDis;

	/**
	 * 创建日期
	 */
	private String createDttm;

	/**
	 * 最后修改人
	 */
	private String lastPer;

	/**
	 * 最后修改日期
	 */
	private String lastDttm;

	/**
	 * 活动URL
	 */
	private String url;

	/**
	 * 活动描述
	 */
	private String descripe;

	/**
	 * 活动图片
	 */
	private String img;

	/**
	 * 活动信息
	 */
	private String info;

	/**
	 * 活动时长（小时）
	 */
	private long timeLast;

	/**
	 * 该活动下的商品数目
	 * 
	 */
	private Integer goodsSum;

	public Integer getStatusDis() {
		return statusDis;
	}

	public void setStatusDis(Integer statusDis) {
		this.statusDis = statusDis;
	}

	public Integer getGoodsSum() {
		return goodsSum;
	}

	public void setGoodsSum(Integer goodsSum) {
		this.goodsSum = goodsSum;
	}

	public long getTimeLast() {
		return timeLast;
	}

	public void setTimeLast(long timeLast) {
		this.timeLast = timeLast;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStartDttm() {
		return startDttm;
	}

	public void setStartDttm(String startDttm) {
		this.startDttm = startDttm;
	}

	public String getEndDttm() {
		return endDttm;
	}

	public void setEndDttm(String endDttm) {
		this.endDttm = endDttm;
	}

	public Integer getDiscountType() {
		return discountType;
	}

	public void setDiscountType(Integer discountType) {
		this.discountType = discountType;
	}

	public String getCreateDttm() {
		return createDttm;
	}

	public void setCreateDttm(String createDttm) {
		this.createDttm = createDttm;
	}

	public String getLastPer() {
		return lastPer;
	}

	public void setLastPer(String lastPer) {
		this.lastPer = lastPer;
	}

	public String getLastDttm() {
		return lastDttm;
	}

	public void setLastDttm(String lastDttm) {
		this.lastDttm = lastDttm;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescripe() {
		return descripe;
	}

	public void setDescripe(String descripe) {
		this.descripe = descripe;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getSuppId() {
		return suppId;
	}

	public void setSuppId(String suppId) {
		this.suppId = suppId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "XSActivityDto [id=" + id + ", suppId=" + suppId + ", name=" + name + ", startDttm=" + startDttm
				+ ", endDttm=" + endDttm + ", discountType=" + discountType + ", status=" + status + ", createDttm="
				+ createDttm + ", lastPer=" + lastPer + ", lastDttm=" + lastDttm + ", url=" + url + ", descripe="
				+ descripe + ", img=" + img + ", info=" + info + ", timeLast=" + timeLast + ", goodsSum=" + goodsSum
				+ "]";
	}

}
