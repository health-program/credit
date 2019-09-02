package com.paladin.credit.service.supervise.dto;


import java.util.Date;
import java.util.List;

public class SuperviseRecordDTO {

	private String itemId;

	// 目标类型
	private Integer targetType;

	// 机构ID
	private String agencyId;

	// 人员ID
	private String personnelId;

	private String[] selections;

	//相关人员
	private List<SuperviseRecordPersonnelDTO> personnels;

	// 监察结果名称
	private String explain;

	// 监察结果名称
	private String explainAttachment;

	private String item;

	private String scoreAttachment;

	private String punishAttachment;

	private Integer code;

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

	private String punishmentCause;

	/*private String punishmentReason;*/

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


	public Integer getTargetType() {
		return targetType;
	}

	public void setTargetType(Integer targetType) {
		this.targetType = targetType;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public String getExplainAttachment() {
		return explainAttachment;
	}

	public void setExplainAttachment(String explainAttachment) {
		this.explainAttachment = explainAttachment;
	}

	public List<SuperviseRecordPersonnelDTO> getPersonnels() {
		return personnels;
	}

	public void setPersonnels(List<SuperviseRecordPersonnelDTO> personnels) {
		this.personnels = personnels;
	}

	public String[] getSelections() {
		return selections;
	}

	public void setSelections(String[] selections) {
		this.selections = selections;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
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

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getPersonnelId() {
		return personnelId;
	}

	public void setPersonnelId(String personnelId) {
		this.personnelId = personnelId;
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

	public String getPunishmentCause() {
		return punishmentCause;
	}

	public void setPunishmentCause(String punishmentCause) {
		this.punishmentCause = punishmentCause;
	}

	public String getScoreCase() {
		return scoreCase;
	}

	public void setScoreCase(String scoreCase) {
		this.scoreCase = scoreCase;
	}

}