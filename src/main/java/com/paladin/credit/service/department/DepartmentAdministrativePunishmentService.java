package com.paladin.credit.service.department;

import com.paladin.credit.core.CreditAgencyContainer;
import com.paladin.credit.mapper.department.DepartmentAdministrativePunishmentMapper;
import com.paladin.credit.model.department.DepartmentAdministrativePunishment;
import com.paladin.credit.model.org.OrgPunishmentRulesManage;
import com.paladin.credit.service.department.dto.DepartmentAdministrativePunishmentOrgUploadDTO;
import com.paladin.credit.service.department.dto.DepartmentAdministrativePunishmentPeopleUploadDTO;
import com.paladin.credit.service.org.OrgPunishmentRulesManageService;
import com.paladin.credit.service.supervise.dto.SuperviseRecordDTO;
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
public class DepartmentAdministrativePunishmentService extends ServiceSupport<DepartmentAdministrativePunishment> {
    @Autowired
    private WJWDepartmentCreditUploadService WJWDepartmentCreditUploadService;

    @Autowired
    private DepartmentAdministrativePunishmentMapper departmentAdministrativePunishmentMapper;

    @Autowired
    private com.paladin.credit.service.xyb.XYBDepartmentCreditSystemService XYBDepartmentCreditSystemService;

    @Autowired
    private OrgPunishmentRulesManageService orgPunishmentRulesManageService;


    /**信用办请求账号*/
    @Value("${xyb.req.acctount}")
    private String acctount;

    /**信用办请求密码*/
    @Value("${xyb.req.pwd}")
    private String pwd;

    @Transactional(rollbackFor = Exception.class)
    public String importPeople(DepartmentAdministrativePunishmentPeopleUploadDTO dto) {
        DepartmentAdministrativePunishment departmentAdministrativePunishment = new DepartmentAdministrativePunishment();
        SimpleBeanCopier.SimpleBeanCopyUtil.simpleCopy(dto,departmentAdministrativePunishment);
        departmentAdministrativePunishment.setType(1);
        if (save(departmentAdministrativePunishment) > 0 ) {
            return  null;
        }
        throw new BusinessException("上传失败");
    }

    @Transactional(rollbackFor = Exception.class)
    public String importOrg(DepartmentAdministrativePunishmentOrgUploadDTO dto) {
        DepartmentAdministrativePunishment departmentAdministrativePunishment = new DepartmentAdministrativePunishment();
        SimpleBeanCopier.SimpleBeanCopyUtil.simpleCopy(dto,departmentAdministrativePunishment);
        departmentAdministrativePunishment.setType(2);
        if (save(departmentAdministrativePunishment) > 0 ) {
            return  null;
        }
        throw new BusinessException("上传失败");
    }


    public int saveOrgFromWjsDepartment(SuperviseRecordDTO superviseRecordDTO) {
        DepartmentAdministrativePunishment punishment = new DepartmentAdministrativePunishment();
        SimpleBeanCopier.SimpleBeanCopyUtil.simpleCopy(superviseRecordDTO,punishment);
        punishment.setType(2);
        String punishmentCase = superviseRecordDTO.getPunishmentCase();
        OrgPunishmentRulesManage rules = orgPunishmentRulesManageService.get(punishmentCase);
        punishment.setPunishmentCause(rules.getPunishmentCase());
        punishment.setName(CreditAgencyContainer.getAgencyName(superviseRecordDTO.getAgencyId()));
        return  save(punishment);
    }


    @Transactional(rollbackFor = Exception.class)
    public int reportOrgPunishment(DepartmentAdministrativePunishment punishment) {
        if (punishment == null) {
            throw new BusinessException("上报法人行政处罚信息不存在");
        }
        int i = WJWDepartmentCreditUploadService.executeOrgPunishmentInsert(punishment);
        if (i > 0) {
           return  updateNoReportStatusById(punishment.getId());
        }
        return 0;
    }

    @Transactional(rollbackFor = Exception.class)
    public int reportPeoplePunishment(DepartmentAdministrativePunishment punishment) {
        if (punishment == null) {
            throw new BusinessException("上报自然人行政处罚信息不存在");
        }
        int i = WJWDepartmentCreditUploadService.executePeoplePunishmentInsert(punishment);
        if (i > 0) {
            return  updateNoReportStatusById(punishment.getId());
        }
        return 0;
    }

    @Transactional(rollbackFor = Exception.class)
    public int cancelPunishment(String id, String type) {
        DepartmentAdministrativePunishment departmentAdministrativePunishment = get(id);
        if (departmentAdministrativePunishment == null) {
            throw new BusinessException("撤销的信息不存在");
        }
        String name = departmentAdministrativePunishment.getName();
        String punishmentDocumentNumber = departmentAdministrativePunishment.getPunishmentDocumentNumber();
        Date punishmentDecisionTime = departmentAdministrativePunishment.getPunishmentDecisionTime();
        StringBuilder sql = new StringBuilder();
        String tableName = "";
        if (Integer.valueOf(type) == BaseModel.TARGET_TYPE_ORG) {
            sql.append(" FROM DBO.frxzcf  WHERE 1 = 1 ");
            tableName = "frxzcf";
        }else if (Integer.valueOf(type) == BaseModel.TARGET_TYPE_PEOPELE){
            sql.append(" FROM DBO.zrrxzcf  WHERE 1 = 1 ");
            tableName = "zrrxzcf";
        }
        if (name != null && name.length() > 0) {
            sql.append(" AND CF_XDR_MC = ? ");
        }
        if (punishmentDocumentNumber != null) {
            sql.append(" AND  CF_WSH = ? ");
        }
        if (punishmentDecisionTime != null) {
            sql.append(" AND  CF_JDRQ = ? ");
        }
        sql.append(BaseModel.REPLY_TIME_RANGE);
        String cancelId = WJWDepartmentCreditUploadService.searchRemoteInfo(sql.toString(), name, punishmentDocumentNumber, punishmentDecisionTime == null ? null : new java.sql.Date(punishmentDecisionTime.getTime()));
        int i = WJWDepartmentCreditUploadService.deleteRemoteInfoById(cancelId,tableName);
        if (i > 0) {
            return   updateHaveReportedStatusById(id);
        }
        return 0;
    }

    public int updateNoReportStatusById(String id) {
        return  departmentAdministrativePunishmentMapper.updateNoReportStatusById(id);
    }

    public int updateHaveReportedStatusById(String id) {
        return  departmentAdministrativePunishmentMapper.updateHaveReportedStatusById(id);
    }


    public List getXybInfo(XYBReqCondition condition, String type) {
        condition.setAcctount(acctount);
        condition.setPwd(pwd);
        Map info = new HashMap(3);
        int tType = Integer.parseInt(type);
        if (tType == BaseModel.TARGET_TYPE_ORG) {
            info = XYBDepartmentCreditSystemService.getXzcfInfo(condition);
        }
        int status = (int) info.get("status");
        if (status == 0) {
            throw new BusinessException((String) info.get("message"));
        }
        return  (List) info.get("xzcflist");
    }

}