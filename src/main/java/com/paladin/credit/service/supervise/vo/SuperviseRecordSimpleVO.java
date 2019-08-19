package com.paladin.credit.service.supervise.vo;

/**
 * <监察记录前台简单展示>
 *
 * @author Huangguochen
 * @create 2019/5/5 13:59
 */
public class SuperviseRecordSimpleVO {

    private String id;

    private Integer targetType;

    private String agencyName;

    private String personnelName;

    private String relatedPersonnelName;

    private Integer infoEntryType;

    private String resultName;

    private String resultGrade;

    private String item;

    private Integer status;

    private Integer isWjs;

    private Integer code;

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

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getPersonnelName() {
        return personnelName;
    }

    public void setPersonnelName(String personnelName) {
        this.personnelName = personnelName;
    }

    public String getRelatedPersonnelName() {
        return relatedPersonnelName;
    }

    public void setRelatedPersonnelName(String relatedPersonnelName) {
        this.relatedPersonnelName = relatedPersonnelName;
    }

    public String getResultName() {
        return resultName;
    }

    public void setResultName(String resultName) {
        this.resultName = resultName;
    }

    public String getResultGrade() {
        return resultGrade;
    }

    public void setResultGrade(String resultGrade) {
        this.resultGrade = resultGrade;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsWjs() {
        return isWjs;
    }

    public void setIsWjs(Integer isWjs) {
        this.isWjs = isWjs;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getInfoEntryType() {
        return infoEntryType;
    }

    public void setInfoEntryType(Integer infoEntryType) {
        this.infoEntryType = infoEntryType;
    }
}
