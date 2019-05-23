package com.paladin.credit.service.department;

import com.paladin.credit.model.department.DepartmentPersonCredit;
import com.paladin.credit.service.department.dto.DepartmentPersonBlackUploadDTO;
import com.paladin.credit.service.department.dto.DepartmentPersonRedUploadDTO;
import com.paladin.framework.core.ServiceSupport;
import com.paladin.framework.core.copy.SimpleBeanCopier;
import com.paladin.framework.core.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepartmentPersonCreditService extends ServiceSupport<DepartmentPersonCredit> {

    @Transactional
    public String importRed(DepartmentPersonRedUploadDTO dto) {
        DepartmentPersonCredit departmentPersonCredit = new DepartmentPersonCredit();
        SimpleBeanCopier.SimpleBeanCopyUtil.simpleCopy(dto,departmentPersonCredit);
        departmentPersonCredit.setType(1);
        if ( save(departmentPersonCredit) > 0){
            return  null;
        }
        throw new BusinessException("上传失败");
    }

    @Transactional
    public String importBlack(DepartmentPersonBlackUploadDTO dto) {
        DepartmentPersonCredit departmentPersonCredit = new DepartmentPersonCredit();
        SimpleBeanCopier.SimpleBeanCopyUtil.simpleCopy(dto,departmentPersonCredit);
        departmentPersonCredit.setType(2);
        if ( save(departmentPersonCredit) > 0){
            return  null;
        }
        throw new BusinessException("上传失败");
    }
}