package com.paladin.credit.service.supervise.vo;

public class SuperviseRecordOrgMapVO {

    private String agencyId;
    //机构名称
    private String agencyName;
    //结果名称
    private String resultName;
    //结果等级
    private int resultGrade;
    //机构坐标
    private String agencyCoordinate;
    //左坐标
    private String leftAgencyCoordinate;
    //右坐标
    private String rightAgencyCoordinate;

    public String getLeftAgencyCoordinate() {
        return leftAgencyCoordinate;
    }

    public void setLeftAgencyCoordinate(String leftAgencyCoordinate) {
        this.leftAgencyCoordinate = leftAgencyCoordinate;
    }

    public String getRightAgencyCoordinate() {
        return rightAgencyCoordinate;
    }

    public void setRightAgencyCoordinate(String rightAgencyCoordinate) {
        this.rightAgencyCoordinate = rightAgencyCoordinate;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getResultName() {
        return resultName;
    }

    public void setResultName(String resultName) {
        this.resultName = resultName;
    }

    public int getResultGrade() {
        return resultGrade;
    }

    public void setResultGrade(int resultGrade) {
        this.resultGrade = resultGrade;
    }

    public String getAgencyCoordinate() {
        return agencyCoordinate;
    }

    public void setAgencyCoordinate(String agencyCoordinate) {
        this.agencyCoordinate = agencyCoordinate;
    }
}
