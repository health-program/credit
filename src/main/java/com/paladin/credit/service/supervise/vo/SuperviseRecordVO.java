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

	private String scoreAttachment;

	private String punishAttachment;

	private Integer infoEntryType;

	private Integer identificationType;

	// 证件号码
	private String identificationNo;

	// 处罚名称
	private String punishmentName;

	// 统一社会信用代码
	private String socialCreditCode;

	// 法定代表人或负责人
	private String chargePerson;

	// 文书号
	private String punishmentDocumentNumber;

	// 处罚日期
	private Date punishmentDecisionTime;

	// 处罚机构
	private String punishmentOrganization;

	// 处罚类型1
	private Integer punishmentTypeOne;

	// 处罚类型2
	private Integer punishmentTypeTwo;

	// 处罚事由
	private String punishmentCase;

	private String punishmentReason;

	// 处罚依据
	private String punishmentBasis;

	// 处罚结果
	private String punishmentResult;

	// 信息使用范围：0.公示；1.仅政府部门内部共享；2.仅可授权查询；
	private Integer informationUsageScope;

	// 失信严重程度：0.未定；1.一般；2.较重；3.严重
	private Integer dishonestyDegree;

	// 状态：0=正常；1=撤销；2=异议；3=其他（备注说明）
	private Integer punishmentStatus;


	private Date scoreTime;

	private Integer score;

	private String scoreCase;

	private String scoreNotificationNumber;

	private String scoreBasis;

	private String scoreResult;

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


	public Integer getIsWjs() {
		return isWjs;
	}

	public void setIsWjs(Integer isWjs) {
		this.isWjs = isWjs;
	}

	public String getPunishAttachment() {
		return punishAttachment;
	}

	public void setPunishAttachment(String punishAttachment) {
		this.punishAttachment = punishAttachment;
	}

	public Integer getInfoEntryType() {
		return infoEntryType;
	}

	public void setInfoEntryType(Integer infoEntryType) {
		this.infoEntryType = infoEntryType;
	}

	public Integer getIdentificationType() {
		return identificationType;
	}

	public void setIdentificationType(Integer identificationType) {
		this.identificationType = identificationType;
	}

	public String getIdentificationNo() {
		return identificationNo;
	}

	public void setIdentificationNo(String identificationNo) {
		this.identificationNo = identificationNo;
	}

	public String getPunishmentName() {
		return punishmentName;
	}

	public void setPunishmentName(String punishmentName) {
		this.punishmentName = punishmentName;
	}

	public String getSocialCreditCode() {
		return socialCreditCode;
	}

	public void setSocialCreditCode(String socialCreditCode) {
		this.socialCreditCode = socialCreditCode;
	}

	public String getChargePerson() {
		return chargePerson;
	}

	public void setChargePerson(String chargePerson) {
		this.chargePerson = chargePerson;
	}

	public String getPunishmentDocumentNumber() {
		return punishmentDocumentNumber;
	}

	public void setPunishmentDocumentNumber(String punishmentDocumentNumber) {
		this.punishmentDocumentNumber = punishmentDocumentNumber;
	}

	public Date getPunishmentDecisionTime() {
		return punishmentDecisionTime;
	}

	public void setPunishmentDecisionTime(Date punishmentDecisionTime) {
		this.punishmentDecisionTime = punishmentDecisionTime;
	}

	public String getPunishmentOrganization() {
		return punishmentOrganization;
	}

	public void setPunishmentOrganization(String punishmentOrganization) {
		this.punishmentOrganization = punishmentOrganization;
	}

	public Integer getPunishmentTypeOne() {
		return punishmentTypeOne;
	}

	public void setPunishmentTypeOne(Integer punishmentTypeOne) {
		this.punishmentTypeOne = punishmentTypeOne;
	}

	public Integer getPunishmentTypeTwo() {
		return punishmentTypeTwo;
	}

	public void setPunishmentTypeTwo(Integer punishmentTypeTwo) {
		this.punishmentTypeTwo = punishmentTypeTwo;
	}

	public String getPunishmentCase() {
		return punishmentCase;
	}

	public void setPunishmentCase(String punishmentCase) {
		this.punishmentCase = punishmentCase;
	}

	public String getPunishmentReason() {
		return punishmentReason;
	}

	public void setPunishmentReason(String punishmentReason) {
		this.punishmentReason = punishmentReason;
	}

	public String getPunishmentBasis() {
		return punishmentBasis;
	}

	public void setPunishmentBasis(String punishmentBasis) {
		this.punishmentBasis = punishmentBasis;
	}

	public String getPunishmentResult() {
		return punishmentResult;
	}

	public void setPunishmentResult(String punishmentResult) {
		this.punishmentResult = punishmentResult;
	}

	public Integer getInformationUsageScope() {
		return informationUsageScope;
	}

	public void setInformationUsageScope(Integer informationUsageScope) {
		this.informationUsageScope = informationUsageScope;
	}

	public Integer getDishonestyDegree() {
		return dishonestyDegree;
	}

	public void setDishonestyDegree(Integer dishonestyDegree) {
		this.dishonestyDegree = dishonestyDegree;
	}

	public Integer getPunishmentStatus() {
		return punishmentStatus;
	}

	public void setPunishmentStatus(Integer punishmentStatus) {
		this.punishmentStatus = punishmentStatus;
	}

	public String getScoreCase() {
		return scoreCase;
	}

	public void setScoreCase(String scoreCase) {
		this.scoreCase = scoreCase;
	}

	public String getScoreNotificationNumber() {
		return scoreNotificationNumber;
	}

	public void setScoreNotificationNumber(String scoreNotificationNumber) {
		this.scoreNotificationNumber = scoreNotificationNumber;
	}

	public String getScoreBasis() {
		return scoreBasis;
	}

	public void setScoreBasis(String scoreBasis) {
		this.scoreBasis = scoreBasis;
	}

	public String getScoreResult() {
		return scoreResult;
	}

	public void setScoreResult(String scoreResult) {
		this.scoreResult = scoreResult;
	}
}