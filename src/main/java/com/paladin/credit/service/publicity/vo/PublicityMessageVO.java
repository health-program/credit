package com.paladin.credit.service.publicity.vo;

import com.paladin.common.core.AttachmentContainer;
import com.paladin.common.model.syst.SysAttachment;

import java.util.Date;
import java.util.List;

public class PublicityMessageVO {

	// 
	private String id;

	// 信息类型
	private Integer type;

	// 标题
	private String title;

	// 副标题
	private String subtitle;

	// 内容
	private String content;

	// 0：暂存1：提交待审核2：驳回待审核3:发布9：审核通过10：待发送
	private Integer status;

	// 发布时间，可以为空
	private Date publishTime;

	// 发布对象
	private String publishTarget;

	// 
	private String attachments;

	// 审核人
	private String examineUserId;

	// 获取附件文件
	public List<SysAttachment> getAttachmentFiles() {
		if (attachments != null && attachments.length() != 0) {
			return AttachmentContainer.getAttachments(attachments.split(","));
		}
		return null;
	}

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

	public String getExamineUserId() {
		return examineUserId;
	}

	public void setExamineUserId(String examineUserId) {
		this.examineUserId = examineUserId;
	}

}