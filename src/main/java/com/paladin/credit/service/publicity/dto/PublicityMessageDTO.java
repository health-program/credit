package com.paladin.credit.service.publicity.dto;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

public class PublicityMessageDTO {

	// 
	private String id;

	// 信息类型
	private Integer type;

	@NotEmpty(message = "标题不能为空")
	private String title;

	// 副标题
	private String subtitle;

	@NotEmpty(message = "内容不能为空")
	private String content;

	// 0：暂存1：提交待审核2：驳回待审核3:发布9：审核通过10：待发送
	private Integer status;

	// 发布时间，可以为空
	private Date publishTime;

	// 发布对象
	private String publishTarget;

	private String attachments;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public String getPublishTarget() {
		return publishTarget;
	}

	public void setPublishTarget(String publishTarget) {
		this.publishTarget = publishTarget;
	}

	public String getAttachments() {
		return attachments;
	}

	public void setAttachments(String attachments) {
		this.attachments = attachments;
	}

}