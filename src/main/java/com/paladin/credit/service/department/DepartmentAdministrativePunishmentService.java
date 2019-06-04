package com.paladin.credit.service.department;

import com.paladin.credit.model.department.DepartmentAdministrativePunishment;
import com.paladin.credit.service.department.dto.DepartmentAdministrativePunishmentOrgUploadDTO;
import com.paladin.credit.service.department.dto.DepartmentAdministrativePunishmentPeopleUploadDTO;
import com.paladin.framework.core.ServiceSupport;
import com.paladin.framework.core.copy.SimpleBeanCopier;
import com.paladin.framework.core.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepartmentAdministrativePunishmentService extends ServiceSupport<DepartmentAdministrativePunishment> {

    @Transactional
    public String importPeople(DepartmentAdministrativePunishmentPeopleUploadDTO dto) {
        DepartmentAdministrativePunishment departmentAdministrativePunishment = new DepartmentAdministrativePunishment();
        SimpleBeanCopier.SimpleBeanCopyUtil.simpleCopy(dto,departmentAdministrativePunishment);
        departmentAdministrativePunishment.setType(1);
        if (save(departmentAdministrativePunishment) > 0 ) {
            return  null;
        }
        throw new BusinessException("上传失败");
    }

    @Transactional
    public String importOrg(DepartmentAdministrativePunishmentOrgUploadDTO dto) {
        DepartmentAdministrativePunishment departmentAdministrativePunishment = new DepartmentAdministrativePunishment();
        SimpleBeanCopier.SimpleBeanCopyUtil.simpleCopy(dto,departmentAdministrativePunishment);
        departmentAdministrativePunishment.setType(2);
        if (save(departmentAdministrativePunishment) > 0 ) {
            return  null;
        }
        throw new BusinessException("上传失败");
    }
}