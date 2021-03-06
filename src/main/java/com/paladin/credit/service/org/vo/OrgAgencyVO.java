package com.paladin.credit.service.org.vo;

import com.paladin.common.core.AttachmentContainer;
import com.paladin.common.model.syst.SysAttachment;

import java.util.Date;
import java.util.List;

public class OrgAgencyVO {

	// 主键
	private String id;

	// 执业许可证
	private String licenseNo;

	// 执业许可证照片
	private String license;

	// 机构名称
	private String name;

	// 机构类型
	private String agencyType;

	// 地址
	private String address;

	// 成立日期
	private Date registerTime;

	// 统一社会信用代码
	private String socialCreditCode;

	// 经营范围
	private String businessScope;

	// 联系方式
	private String contactWay;

	// 法定代表人或负责人
	private String chargePerson;

	// 
	private String chargePersonId;

	// 人事信息
	private String personnelInformation;

	private  String agencyCoordinate;

	// 获取附件文件
	public List<SysAttachment> getLicenseFile() {
		if (license != null && license.length() != 0) {
			return AttachmentContainer.getAttachments(license.split(","));
		}
		return null;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAgencyType() {
		return agencyType;
	}

	public void setAgencyType(String agencyType) {
		this.agencyType = agencyType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public String getSocialCreditCode() {
		return socialCreditCode;
	}

	public void setSocialCreditCode(String socialCreditCode) {
		this.socialCreditCode = socialCreditCode;
	}

	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}

	public String getContactWay() {
		return contactWay;
	}

	public void setContactWay(String contactWay) {
		this.contactWay = contactWay;
	}

	public String getChargePerson() {
		return chargePerson;
	}

	public void setChargePerson(String chargePerson) {
		this.chargePerson = chargePerson;
	}

	public String getChargePersonId() {
		return chargePersonId;
	}

	public void setChargePersonId(String chargePersonId) {
		this.chargePersonId = chargePersonId;
	}

	public String getPersonnelInformation() {
		return personnelInformation;
	}

	public void setPersonnelInformation(String personnelInformation) {
		this.personnelInformation = personnelInformation;
	}

	public String getAgencyCoordinate() {
		return agencyCoordinate;
	}

	public void setAgencyCoordinate(String agencyCoordinate) {
		this.agencyCoordinate = agencyCoordinate;
	}
}