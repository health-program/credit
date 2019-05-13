package com.paladin.credit.service.supervise.vo;


import com.paladin.common.core.AttachmentContainer;
import com.paladin.common.model.syst.SysAttachment;

import java.util.Date;
import java.util.List;

public class SuperviseRecordVO {

	// 
	private String id;

	// 目标类型
	private Integer targetType;

	// 机构ID
	private String agencyId;

	private String agencyName;

	// 人员ID
	private String personnelId;

	// 人员姓名
	private String personnelName;

	// 人员性别
	private Integer personnelSex;

	// 人员身份证
	private String personnelIdentification;

	// 人员地址
	private String personnelAddress;

	// 监察项目
	private String item;

	// 监察结果名称
	private String resultName;

	// 监察结果等级
	private Integer resultGrade;

	// 监察结果名称
	private String explainText;

	// 监察结果名称
	private String explainAttachment;

	private Date createTime;

	private String illustrate;

	private Integer status;

	private Integer isWjs;

	private Integer score;

	private String scoreNo;

	private String scoreAttachment;

	private Date scoreTime;

	private String punishNo;

	private String punishAttachment;

	private Date punishTime;

	// 获取附件文件
	public List<SysAttachment> getExplainAttachmentFile() {
		if (explainAttachment != null && explainAttachment.length() != 0) {
			return AttachmentContainer.getAttachments(explainAttachment.split(","));
		}
		return null;
	}

	public List<SysAttachment> getScoreAttachmentFile() {
		if (scoreAttachment != null && scoreAttachment.length() != 0) {
			return AttachmentContainer.getAttachments(scoreAttachment.split(","));
		}
		return null;
	}

	public List<SysAttachment> getPunishAttachmentFile() {
		if (punishAttachment != null && punishAttachment.length() != 0) {
			return AttachmentContainer.getAttachments(punishAttachment.split(","));
		}
		return null;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getTargetType() {
		return targetType;
	}

	public void setTargetType(Integer targetType) {
		this.targetType = targetType;
	}

	public String getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}

	public String getPersonnelId() {
		return personnelId;
	}

	public void setPersonnelId(String personnelId) {
		this.personnelId = personnelId;
	}

	public String getPersonnelName() {
		return personnelName;
	}

	public void setPersonnelName(String personnelName) {
		this.personnelName = personnelName;
	}

	public Integer getPersonnelSex() {
		return personnelSex;
	}

	public void setPersonnelSex(Integer personnelSex) {
		this.personnelSex = personnelSex;
	}

	public String getPersonnelIdentification() {
		return personnelIdentification;
	}

	public void setPersonnelIdentification(String personnelIdentification) {
		this.personnelIdentification = personnelIdentification;
	}

	public String getPersonnelAddress() {
		return personnelAddress;
	}

	public void setPersonnelAddress(String personnelAddress) {
		this.personnelAddress = personnelAddress;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getResultName() {
		return resultName;
	}

	public void setResultName(String resultName) {
		this.resultName = resultName;
	}

	public Integer getResultGrade() {
		return resultGrade;
	}

	public void setResultGrade(Integer resultGrade) {
		this.resultGrade = resultGrade;
	}

	public String getExplainText() {
		return explainText;
	}

	public void setExplainText(String explainText) {
		this.explainText = explainText;
	}

	public String getExplainAttachment() {
		return explainAttachment;
	}

	public void setExplainAttachment(String explainAttachment) {
		this.explainAttachment = explainAttachment;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getIllustrate() {
		return illustrate;
	}

	public void setIllustrate(String illustrate) {
		this.illustrate = illustrate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getScoreNo() {
		return scoreNo;
	}

	public void setScoreNo(String scoreNo) {
		this.scoreNo = scoreNo;
	}

	public String getScoreAttachment() {
		return scoreAttachment;
	}

	public void setScoreAttachment(String scoreAttachment) {
		this.scoreAttachment = scoreAttachment;
	}

	public Date getScoreTime() {
		return scoreTime;
	}

	public void setScoreTime(Date scoreTime) {
		this.scoreTime = scoreTime;
	}

	public String getPunishNo() {
		return punishNo;
	}

	public void setPunishNo(String punishNo) {
		this.punishNo = punishNo;
	}

	public String getPunishAttachment() {
		return punishAttachment;
	}

	public void setPunishAttachment(String punishAttachment) {
		this.punishAttachment = punishAttachment;
	}

	public Date getPunishTime() {
		return punishTime;
	}

	public void setPunishTime(Date punishTime) {
		this.punishTime = punishTime;
	}

	public Integer getIsWjs() {
		return isWjs;
	}

	public void setIsWjs(Integer isWjs) {
		this.isWjs = isWjs;
	}
}