package com.paladin.credit.service.xyb.request;

/**
 * <调用信用办接口请求条件>
 *
 * @author Huangguochen
 * @create 2019/7/25 10:51
 */
public class XYBReqCondition {

    private String acctount;

    private String pwd;

    private String qymc;

    private String tyshxydm;

    private String gszch;

    private String zzjgdm;

    /** 访问者的系统平台名称（选填）*/
    private String visitpt;

    /** 请求原因（选填）*/
    private String visitreason;

    public String getQymc() {
        return qymc;
    }

    public void setQymc(String qymc) {
        this.qymc = qymc != null && qymc.length() > 0 ? qymc.trim() : null;
    }

    public String getTyshxydm() {
        return tyshxydm;
    }

    public void setTyshxydm(String tyshxydm) {
        this.tyshxydm = tyshxydm != null && tyshxydm.length() > 0 ? tyshxydm.trim() : null;
    }

    public String getGszch() {
        return gszch;
    }

    public void setGszch(String gszch) {
        this.gszch = gszch != null && gszch.length() > 0 ? gszch.trim() : null;
    }

    public String getZzjgdm() {
        return zzjgdm;
    }

    public void setZzjgdm(String zzjgdm) {
        this.zzjgdm = zzjgdm != null && zzjgdm.length() > 0 ? zzjgdm.trim() : null;
    }

    public String getVisitpt() {
        return visitpt;
    }

    public void setVisitpt(String visitpt) {
        this.visitpt = visitpt != null && visitpt.length() > 0 ? visitpt.trim() : null;
    }

    public String getVisitreason() {
        return visitreason;
    }

    public void setVisitreason(String visitreason) {
        this.visitreason = visitreason != null && visitreason.length() > 0 ? visitreason.trim() : null;
    }

    public String getAcctount() {
        return acctount;
    }

    public void setAcctount(String acctount) {
        this.acctount = acctount;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
