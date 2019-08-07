package com.paladin.credit.service.department;

import com.paladin.credit.mapper.department.DepartmentCreditMapper;
import com.paladin.credit.model.department.DepartmentCredit;
import com.paladin.credit.service.department.dto.DepartmentCreditBlackUploadDTO;
import com.paladin.credit.service.department.dto.DepartmentCreditRedUploadDTO;
import com.paladin.credit.service.department.dto.DepartmentEnterpriseUploadDTO;
import com.paladin.credit.service.xyb.request.XYBReqCondition;
import com.paladin.framework.common.BaseModel;
import com.paladin.framework.core.ServiceSupport;
import com.paladin.framework.core.copy.SimpleBeanCopier;
import com.paladin.framework.core.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DepartmentCreditService extends ServiceSupport<DepartmentCredit> {

    @Autowired
    private DepartmentCreditMapper departmentCreditMapper;

    @Autowired
    private WJWDepartmentCreditUploadService WJWDepartmentCreditUploadService;

    @Autowired
    private com.paladin.credit.service.xyb.XYBDepartmentCreditSystemService XYBDepartmentCreditSystemService;

    /**信用办请求账号*/
    @Value("${xyb.req.acctount}")
    private String acctount;

    /**信用办请求密码*/
    @Value("${xyb.req.pwd}")
    private String pwd;

    @Transactional
    public String importRed(DepartmentCreditRedUploadDTO dto) {
        DepartmentCredit departmentCredit = new DepartmentCredit();
        SimpleBeanCopier.SimpleBeanCopyUtil.simpleCopy(dto,departmentCredit);
        departmentCredit.setType(1);
        if ( save(departmentCredit) > 0){
            return  null;
        }
        throw new BusinessException("上传失败");
    }

    @Transactional
    public String importBlack(DepartmentCreditBlackUploadDTO dto) {
        DepartmentCredit departmentCredit = new DepartmentCredit();
        SimpleBeanCopier.SimpleBeanCopyUtil.simpleCopy(dto,departmentCredit);
        departmentCredit.setType(2);
        if ( save(departmentCredit) > 0){
            return  null;
        }
        throw new BusinessException("上传失败");
    }


    @Transactional
    public String importBusiness(DepartmentEnterpriseUploadDTO dto) {
        DepartmentCredit departmentCredit = new DepartmentCredit();
        SimpleBeanCopier.SimpleBeanCopyUtil.simpleCopy(dto,departmentCredit);
        departmentCredit.setType(3);
        if ( save(departmentCredit) > 0){
            return  null;
        }
        throw new BusinessException("上传失败");
    }

    public int reportOrgRed(DepartmentCredit departmentCredit) {
        int i = WJWDepartmentCreditUploadService.executeOrgRedInsert(departmentCredit);
        if (i > 0) {
          return   updateNoReportStatusById(departmentCredit.getId());
        }
        return 0;
    }

    public int reportOrgBlack(DepartmentCredit departmentCredit) {
        int i = WJWDepartmentCreditUploadService.executeOrgBlackInsert(departmentCredit);
        if (i > 0) {
            return   updateNoReportStatusById(departmentCredit.getId());
        }
        return 0;
    }

    public int reportOrgHybd(DepartmentCredit departmentCredit) {
        int i = WJWDepartmentCreditUploadService.executeHypdInsert(departmentCredit);
        if (i > 0) {
            return   updateNoReportStatusById(departmentCredit.getId());
        }
        return 0;
    }

    public int cancelOrgRed(String id) {
        DepartmentCredit departmentCredit = get(id);
        if (departmentCredit == null) {
            throw new BusinessException("撤销的信息不存在");
        }
        String name = departmentCredit.getName();
        String affirmWrit = departmentCredit.getAffirmWrit();
        Date affirmTime = departmentCredit.getAffirmTime();
        StringBuilder sql = new StringBuilder();
        sql.append(" FROM DBO.frred  WHERE 1 = 1 ");
        if (name != null && name.length() > 0) {
            sql.append(" AND JGQC = ? ");
        }
        if (affirmWrit != null) {
            sql.append(" AND  RDWH = ? ");
        }
        if (affirmTime != null) {
            sql.append(" AND  RDRQ = ? ");
        }
        sql.append(BaseModel.REPLY_TIME_RANGE);
        String cancelId = WJWDepartmentCreditUploadService.searchRemoteInfo(sql.toString(), name, affirmWrit, affirmTime == null ? null : new java.sql.Date(affirmTime.getTime()));
        int i = WJWDepartmentCreditUploadService.deleteRemoteInfoById(cancelId,"frred ");
        if (i > 0) {
            return   updateHaveReportedStatusById(id);
        }
        return 0;
    }

    public int cancelOrgBlack(String id) {
        DepartmentCredit departmentCredit = get(id);
        if (departmentCredit == null) {
            throw new BusinessException("撤销的信息不存在");
        }
        String name = departmentCredit.getName();
        String affirmWrit = departmentCredit.getAffirmWrit();
        Date losePromiseTime = departmentCredit.getLosePromiseTime();
        StringBuilder sql = new StringBuilder();
        sql.append(" FROM DBO.frblack WHERE 1 = 1 ");
        if (name != null && name.length() > 0) {
            sql.append(" AND JGQC = ? ");
        }
        if (affirmWrit != null) {
            sql.append(" AND  RDWH = ? ");
        }
        if (losePromiseTime != null) {
            sql.append(" AND  QRYZSXSJ = ? ");
        }
        sql.append(BaseModel.REPLY_TIME_RANGE);
        String cancelId = WJWDepartmentCreditUploadService.searchRemoteInfo(sql.toString(), name, affirmWrit, losePromiseTime == null ? null : new java.sql.Date(losePromiseTime.getTime()));
        int i = WJWDepartmentCreditUploadService.deleteRemoteInfoById(cancelId,"frblack");
        if (i > 0) {
            return   updateHaveReportedStatusById(id);
        }
        return 0;
    }

    public int cancelOrgHybd(String id) {
        DepartmentCredit departmentCredit = get(id);
        if (departmentCredit == null) {
            throw new BusinessException("撤销的信息不存在");
        }
        String name = departmentCredit.getName();
        Date affirmTime = departmentCredit.getAffirmTime();
        StringBuilder sql = new StringBuilder();
        sql.append(" FROM DBO.hypd WHERE 1 = 1 ");
        if (name != null && name.length() > 0) {
            sql.append(" AND JGQC = ? ");
        }
        if (affirmTime != null) {
            sql.append(" AND  PDRQ = ? ");
        }
        sql.append(" AND  CREATE_TIME <= DATEADD(mi, -10, DATEDIFF(DAY, 0,GETDATE() + 1 )) AND  CREATE_TIME >=  DATEADD(hh, 8, DATEDIFF(DAY, 0,GETDATE()))  ");
        String cancelId = WJWDepartmentCreditUploadService.searchRemoteInfo(sql.toString(), name, affirmTime == null ? null : new java.sql.Date(affirmTime.getTime()));
        int i = WJWDepartmentCreditUploadService.deleteRemoteInfoById(cancelId,"hypd");
        if (i > 0) {
            return   updateHaveReportedStatusById(id);
        }
        return 0;
    }


    public int updateNoReportStatusById(String id) {
        return  departmentCreditMapper.updateNoReportStatusById(id);
    }

    public int updateHaveReportedStatusById(String id) {
        return  departmentCreditMapper.updateHaveReportedStatusById(id);
    }

    public List getXybInfo(XYBReqCondition condition, String type) {
        condition.setAcctount(acctount);
        condition.setPwd(pwd);
        Map info = new HashMap(3);
        int tType = Integer.parseInt(type);
        if (tType == BaseModel.CREDIT_TYPE_RED) {
            info = XYBDepartmentCreditSystemService.getRedInfo(condition);
        }else if (tType == BaseModel.CREDIT_TYPE_BLACK){
            info = XYBDepartmentCreditSystemService.getBlackInfo(condition);

        }else if (tType == BaseModel.CREDIT_TYPE_HYPD){
            info = XYBDepartmentCreditSystemService.getHypdInfo(condition);
        }

        int status = (int) info.get("status");
        if (status == 0) {
            throw new BusinessException((String) info.get("message"));
        }
        return  tType == BaseModel.CREDIT_TYPE_RED ? (List) info.get("redlist") : ( tType == BaseModel.CREDIT_TYPE_BLACK ? (List)info.get("blacklist") : (List)info.get("hypdlist"));
    }
}
