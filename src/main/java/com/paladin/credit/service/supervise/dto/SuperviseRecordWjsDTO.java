package com.paladin.credit.service.supervise.dto;

import java.util.Date;

/**

 * @author Huangguochen
 * @create 2019/5/13 13:31
 */
public class SuperviseRecordWjsDTO {

    private String item;

    private String agencyId;

    private Integer targetType;

    private Integer score;

    private String scoreNo;

    private String scoreAttachment;

    private Date scoreTime;

    private String punishNo;

    private String punishAttachment;

    private Date punishTime;

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Integer getTargetType() {
        return targetType;
    }

    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
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

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }
}
