package com.paladin.credit.service.department;

import com.paladin.credit.mapper.department.DepartmentAdministrativeLicenseMapper;
import com.paladin.credit.model.department.DepartmentAdministrativeLicense;
import com.paladin.credit.service.department.dto.DepartmentAdministrativeLicenseOrgUploadDTO;
import com.paladin.credit.service.department.dto.DepartmentAdministrativeLicensePeopleUploadDTO;
import com.paladin.framework.common.BaseModel;
import com.paladin.framework.core.ServiceSupport;
import com.paladin.framework.core.copy.SimpleBeanCopier;
import com.paladin.framework.core.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class DepartmentAdministrativeLicenseService extends ServiceSupport<DepartmentAdministrativeLicense> {

    @Autowired
    private WJWDepartmentCreditUploadService WJWDepartmentCreditUploadService;

    @Autowired
    private DepartmentAdministrativeLicenseMapper departmentAdministrativeLicenseMapper;

    @Transactional(rollbackFor = Exception.class)
    public String importPeople(DepartmentAdministrativeLicensePeopleUploadDTO dto) {
        DepartmentAdministrativeLicense departmentAdministrativeLicense = new DepartmentAdministrativeLicense();
        SimpleBeanCopier.SimpleBeanCopyUtil.simpleCopy(dto,departmentAdministrativeLicense);
        departmentAdministrativeLicense.setType(1);
        if (save(departmentAdministrativeLicense) > 0 ) {
            return  null;
        }
        throw new BusinessException("上传失败");
    }

    @Transactional(rollbackFor = Exception.class)
    public String importOrg(DepartmentAdministrativeLicenseOrgUploadDTO dto) {
        DepartmentAdministrativeLicense departmentAdministrativeLicense = new DepartmentAdministrativeLicense();
        SimpleBeanCopier.SimpleBeanCopyUtil.simpleCopy(dto,departmentAdministrativeLicense);
        departmentAdministrativeLicense.setType(2);
        if (save(departmentAdministrativeLicense) > 0 ) {
            return  null;
        }
        throw new BusinessException("上传失败");
    }

    @Transactional(rollbackFor = Exception.class)
    public int reportOrgPunishment(DepartmentAdministrativeLicense license) {
        if (license == null) {
            throw new BusinessException("上报法人行政许可信息不存在");
        }
        int i = WJWDepartmentCreditUploadService.executeOrgLicenseInsert(license);
        if (i > 0) {
            return  updateNoReportStatusById(license.getId());
        }
        return 0;
    }

    @Transactional(rollbackFor = Exception.class)
    public int reportPeoplePunishment(DepartmentAdministrativeLicense license) {
        if (license == null) {
            throw new BusinessException("上报机构行政许可信息不存在");
        }
        int i = WJWDepartmentCreditUploadService.executePeopleLicenseInsert(license);
        if (i > 0) {
            return  updateNoReportStatusById(license.getId());
        }
        return 0;
    }

    @Transactional(rollbackFor = Exception.class)
    public int cancelLicense(String id, String type) {
        DepartmentAdministrativeLicense departmentAdministrativeLicense = get(id);
        if (departmentAdministrativeLicense == null) {
            throw new BusinessException("撤销的信息不存在");
        }
        String name = departmentAdministrativeLicense.getName();
        String licenseDocumentNumber = departmentAdministrativeLicense.getLicenseDocumentNumber();
        Date licenseDecisionTime = departmentAdministrativeLicense.getLicenseDecisionTime();
        StringBuilder sql = new StringBuilder();
        String tableName = "";
        if (Integer.valueOf(type) == BaseModel.TARGET_TYPE_ORG) {
            sql.append(" FROM DBO.frxzxk  WHERE 1 = 1 ");
            tableName = "frxzxk";
        }else if (Integer.valueOf(type) == BaseModel.TARGET_TYPE_PEOPELE){
            sql.append(" FROM DBO.zrrxzxk  WHERE 1 = 1 ");
            tableName = "zrrxzxk";
        }
        if (name != null && name.length() > 0) {
            sql.append(" AND XK_XDR = ? ");
        }
        if (licenseDocumentNumber != null) {
            sql.append(" AND  XK_WSH = ? ");
        }
        if (licenseDecisionTime != null) {
            sql.append(" AND  XK_JDRQ = ? ");
        }
        sql.append(BaseModel.REPLY_TIME_RANGE);
        String cancelId = WJWDepartmentCreditUploadService.searchRemoteInfo(sql.toString(), name, licenseDocumentNumber, licenseDecisionTime == null ? null : new java.sql.Date(licenseDecisionTime.getTime()));
        int i = WJWDepartmentCreditUploadService.deleteRemoteInfoById(cancelId,tableName);
        if (i > 0) {
            return   updateHaveReportedStatusById(id);
        }
        return 0;
    }

    public int updateNoReportStatusById(String id) {
        return  departmentAdministrativeLicenseMapper.updateNoReportStatusById(id);
    }

    public int updateHaveReportedStatusById(String id) {
        return  departmentAdministrativeLicenseMapper.updateHaveReportedStatusById(id);
    }

}