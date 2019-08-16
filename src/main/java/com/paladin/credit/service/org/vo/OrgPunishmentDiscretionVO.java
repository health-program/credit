package com.paladin.credit.service.org.vo;


public class OrgPunishmentDiscretionVO {

	// 
	private String id;

	private String ruleId;

	// 情形类别
	private Integer situation;

	// 情节
	private String plot;

	// 裁量幅度
	private String result;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getSituation() {
		return situation;
	}

	public void setSituation(Integer situation) {
		this.situation = situation;
	}

	public String getPlot() {
		return plot;
	}

	public void setPlot(String plot) {
		this.plot = plot;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
}