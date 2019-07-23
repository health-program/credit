package com.paladin.credit.service.department;

import com.paladin.credit.model.department.DepartmentAdministrativeLicense;
import com.paladin.credit.service.department.dto.DepartmentAdministrativeLicenseOrgUploadDTO;
import com.paladin.credit.service.department.dto.DepartmentAdministrativeLicensePeopleUploadDTO;
import com.paladin.framework.core.ServiceSupport;
import com.paladin.framework.core.copy.SimpleBeanCopier;
import com.paladin.framework.core.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepartmentAdministrativeLicenseService extends ServiceSupport<DepartmentAdministrativeLicense> {

    @Transactional
    public String importPeople(DepartmentAdministrativeLicensePeopleUploadDTO dto) {
        DepartmentAdministrativeLicense departmentAdministrativeLicense = new DepartmentAdministrativeLicense();
        SimpleBeanCopier.SimpleBeanCopyUtil.simpleCopy(dto,departmentAdministrativeLicense);
        departmentAdministrativeLicense.setType(1);
        if (save(departmentAdministrativeLicense) > 0 ) {
            return  null;
        }
        throw new BusinessException("上传失败");
    }

    public String importOrg(DepartmentAdministrativeLicenseOrgUploadDTO dto) {
        DepartmentAdministrativeLicense departmentAdministrativeLicense = new DepartmentAdministrativeLicense();
        SimpleBeanCopier.SimpleBeanCopyUtil.simpleCopy(dto,departmentAdministrativeLicense);
        departmentAdministrativeLicense.setType(2);
        if (save(departmentAdministrativeLicense) > 0 ) {
            return  null;
        }
        throw new BusinessException("上传失败");
    }
}