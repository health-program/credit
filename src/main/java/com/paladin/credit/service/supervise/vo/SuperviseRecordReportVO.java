package com.paladin.credit.service.supervise.vo;

import java.util.Date;

/**
 * <机构报表管理前台展示>
 *
 * @author Huangguochen
 * @create 2019/4/29 9:50
 */
public class SuperviseRecordReportVO {

    private  String agencyName;

    private  String agencyId;

    private  Integer grade1;

    private  Integer grade2;

    private  Integer grade3;

    private  Integer grade4;

    private  Integer grade5;

    private  Integer mgrade;

    private Date bgtime;

    private Date endtime;

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public Integer getGrade1() {
        return grade1;
    }

    public void setGrade1(Integer grade1) {
        this.grade1 = grade1;
    }

    public Integer getGrade2() {
        return grade2;
    }

    public void setGrade2(Integer grade2) {
        this.grade2 = grade2;
    }

    public Integer getGrade3() {
        return grade3;
    }

    public void setGrade3(Integer grade3) {
        this.grade3 = grade3;
    }

    public Integer getGrade4() {
        return grade4;
    }

    public void setGrade4(Integer grade4) {
        this.grade4 = grade4;
    }

    public Integer getGrade5() {
        return grade5;
    }

    public void setGrade5(Integer grade5) {
        this.grade5 = grade5;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public Integer getMgrade() {
        return mgrade;
    }

    public void setMgrade(Integer mgrade) {
        this.mgrade = mgrade;
    }

    public Date getBgtime() {
        return bgtime;
    }

    public void setBgtime(Date bgtime) {
        this.bgtime = bgtime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }
}
