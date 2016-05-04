package com.adanac.demo.restful.entity.coupon;

import java.io.Serializable;

/**
 * 券规则
 */
public class CouponRuleDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private String id;
	/**
	 * 商家ID
	 */
	private String suppId;
	/**
	 * 券名称
	 */
	private String name;

	/**
	 * 券类型 1通用券 2:条码券
	 */
	private Integer type;

	/**
	 * 面值
	 */
	private Double faceValue;
	/**
	 * 开始时间
	 */
	private String startTime;
	/**
	 * 失效时间
	 */
	private String endTime;

	/**
	 * 发行方式 1:自助领取 2:定向发放
	 */
	private Integer issuedMode;

	/**
	 * 发行数量
	 */
	private Integer issuedNum;
	/**
	 * 剩余数量
	 */
	private Integer surplusNum;
	/**
	 * 最大领取数量
	 */
	private Integer takeMaxNum;

	/**
	 * 满足金额
	 */
	private Double satisfyMoney;

	/**
	 * 状态 1:已保存 2:已发布 3:已终止
	 */
	private Integer status;
	/**
	 * 更新时间
	 */
	private String updateTime;
	/**
	 * 更新用户id
	 */
	private String updateUserId;
	/**
	 * 创建时间
	 */
	private String createTime;
	/**
	 * 创建用户id
	 */
	private String createUserId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSuppId() {
		return suppId;
	}

	public void setSuppId(String suppId) {
		this.suppId = suppId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Double getFaceValue() {
		return faceValue;
	}

	public void setFaceValue(Double faceValue) {
		this.faceValue = faceValue;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getIssuedMode() {
		return issuedMode;
	}

	public void setIssuedMode(Integer issuedMode) {
		this.issuedMode = issuedMode;
	}

	public Integer getIssuedNum() {
		return issuedNum;
	}

	public void setIssuedNum(Integer issuedNum) {
		this.issuedNum = issuedNum;
	}

	public Integer getTakeMaxNum() {
		return takeMaxNum;
	}

	public void setTakeMaxNum(Integer takeMaxNum) {
		this.takeMaxNum = takeMaxNum;
	}

	public Double getSatisfyMoney() {
		return satisfyMoney;
	}

	public void setSatisfyMoney(Double satisfyMoney) {
		this.satisfyMoney = satisfyMoney;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public Integer getSurplusNum() {
		return surplusNum;
	}

	public void setSurplusNum(Integer surplusNum) {
		this.surplusNum = surplusNum;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CouponRuleDto [id=");
		builder.append(id);
		builder.append(", suppId=");
		builder.append(suppId);
		builder.append(", name=");
		builder.append(name);
		builder.append(", type=");
		builder.append(type);
		builder.append(", faceValue=");
		builder.append(faceValue);
		builder.append(", startTime=");
		builder.append(startTime);
		builder.append(", endTime=");
		builder.append(endTime);
		builder.append(", issuedMode=");
		builder.append(issuedMode);
		builder.append(", issuedNum=");
		builder.append(issuedNum);
		builder.append(", surplusNum=");
		builder.append(surplusNum);
		builder.append(", takeMaxNum=");
		builder.append(takeMaxNum);
		builder.append(", satisfyMoney=");
		builder.append(satisfyMoney);
		builder.append(", status=");
		builder.append(status);
		builder.append(", updateTime=");
		builder.append(updateTime);
		builder.append(", updateUserId=");
		builder.append(updateUserId);
		builder.append(", createTime=");
		builder.append(createTime);
		builder.append(", createUserId=");
		builder.append(createUserId);
		builder.append("]");
		return builder.toString();
	}

}
