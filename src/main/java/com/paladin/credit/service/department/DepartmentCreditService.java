package com.paladin.credit.service.department;

import com.paladin.credit.model.department.DepartmentCredit;
import com.paladin.credit.service.department.dto.DepartmentCreditBlackUploadDTO;
import com.paladin.credit.service.department.dto.DepartmentCreditRedUploadDTO;
import com.paladin.credit.service.department.dto.DepartmentEnterpriseUploadDTO;
import com.paladin.framework.core.ServiceSupport;
import com.paladin.framework.core.copy.SimpleBeanCopier;
import com.paladin.framework.core.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepartmentCreditService extends ServiceSupport<DepartmentCredit> {

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


    public String importBusiness(DepartmentEnterpriseUploadDTO dto) {
        DepartmentCredit departmentCredit = new DepartmentCredit();
        SimpleBeanCopier.SimpleBeanCopyUtil.simpleCopy(dto,departmentCredit);
        departmentCredit.setType(3);
        if ( save(departmentCredit) > 0){
            return  null;
        }
        throw new BusinessException("上传失败");
    }
}