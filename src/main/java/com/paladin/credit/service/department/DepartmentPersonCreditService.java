package com.paladin.credit.service.department;

import com.paladin.credit.mapper.department.DepartmentPersonCreditMapper;
import com.paladin.credit.model.department.DepartmentPersonCredit;
import com.paladin.credit.service.department.dto.DepartmentPersonBlackUploadDTO;
import com.paladin.credit.service.department.dto.DepartmentPersonRedUploadDTO;
import com.paladin.framework.common.BaseModel;
import com.paladin.framework.core.ServiceSupport;
import com.paladin.framework.core.copy.SimpleBeanCopier;
import com.paladin.framework.core.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class DepartmentPersonCreditService extends ServiceSupport<DepartmentPersonCredit> {

    @Autowired
    private WJWDepartmentCreditUploadService WJWDepartmentCreditUploadService;

    @Autowired
    private DepartmentPersonCreditMapper departmentPersonCreditMapper;

    @Transactional(rollbackFor = Exception.class)
    public String importRed(DepartmentPersonRedUploadDTO dto) {
        DepartmentPersonCredit departmentPersonCredit = new DepartmentPersonCredit();
        SimpleBeanCopier.SimpleBeanCopyUtil.simpleCopy(dto,departmentPersonCredit);
        departmentPersonCredit.setType(1);
        if ( save(departmentPersonCredit) > 0){
            return  null;
        }
        throw new BusinessException("上传失败");
    }

    @Transactional(rollbackFor = Exception.class)
    public String importBlack(DepartmentPersonBlackUploadDTO dto) {
        DepartmentPersonCredit departmentPersonCredit = new DepartmentPersonCredit();
        SimpleBeanCopier.SimpleBeanCopyUtil.simpleCopy(dto,departmentPersonCredit);
        departmentPersonCredit.setType(2);
        if ( save(departmentPersonCredit) > 0){
            return  null;
        }
        throw new BusinessException("上传失败");
    }

    @Transactional(rollbackFor = Exception.class)
    public int reportPeopleRed(DepartmentPersonCredit departmentPersonCredit) {
        int i = WJWDepartmentCreditUploadService.executePeopleRedInsert(departmentPersonCredit);
        if (i > 0) {
            return   updateNoReportStatusById(departmentPersonCredit.getId());
        }
        return 0;
    }

    @Transactional(rollbackFor = Exception.class)
    public int reportPeopleBlack(DepartmentPersonCredit departmentPersonCredit) {
        int i = WJWDepartmentCreditUploadService.executePeopleBlackInsert(departmentPersonCredit);
        if (i > 0) {
            return   updateNoReportStatusById(departmentPersonCredit.getId());
        }
        return 0;
    }

    @Transactional(rollbackFor = Exception.class)
    public int cancelPeopleRed(String id) {
        DepartmentPersonCredit departmentPersonCredit = get(id);
        if (departmentPersonCredit == null) {
            throw new BusinessException("撤销的信息不存在");
        }
        String name = departmentPersonCredit.getName();
        String affirmWrit = departmentPersonCredit.getAffirmWrit();
        Date affirmTime = departmentPersonCredit.getAffirmTime();
        StringBuilder sql = new StringBuilder();
        sql.append(" FROM DBO.zrrred  WHERE 1 = 1 ");
        if (name != null && name.length() > 0) {
            sql.append(" AND XM = ? ");
        }
        if (affirmWrit != null) {
            sql.append(" AND  RDWH = ? ");
        }
        if (affirmTime != null) {
            sql.append(" AND  RDRQ = ? ");
        }
        sql.append(BaseModel.REPLY_TIME_RANGE);
        String cancelId = WJWDepartmentCreditUploadService.searchRemoteInfo(sql.toString(), name, affirmWrit, affirmTime == null ? null : new java.sql.Date(affirmTime.getTime()));
        int i = WJWDepartmentCreditUploadService.deleteRemoteInfoById(cancelId,"zrrred");
        if (i > 0) {
            return   updateHaveReportedStatusById(id);
        }
        return 0;
    }

    @Transactional(rollbackFor = Exception.class)
    public int cancelPeopleBlack(String id) {
        DepartmentPersonCredit departmentPersonCredit = get(id);
        if (departmentPersonCredit == null) {
            throw new BusinessException("撤销的信息不存在");
        }
        String chargePersonName = departmentPersonCredit.getChargePersonName();
        String affirmWrit = departmentPersonCredit.getAffirmWrit();
        Date losePromiseTime = departmentPersonCredit.getLosePromiseTime();
        StringBuilder sql = new StringBuilder();
        sql.append(" FROM DBO.zrrblack  WHERE 1 = 1 ");
        if (chargePersonName != null && chargePersonName.length() > 0) {
            sql.append(" AND FZRXM = ? ");
        }
        if (affirmWrit != null) {
            sql.append(" AND  RDWH = ? ");
        }
        if (losePromiseTime != null) {
            sql.append(" AND  QRYZSXSJ = ? ");
        }
        sql.append(BaseModel.REPLY_TIME_RANGE);
        String cancelId = WJWDepartmentCreditUploadService.searchRemoteInfo(sql.toString(), chargePersonName, affirmWrit, losePromiseTime == null ? null : new java.sql.Date(losePromiseTime.getTime()));
        int i = WJWDepartmentCreditUploadService.deleteRemoteInfoById(cancelId,"zrrblack");
        if (i > 0) {
            return   updateHaveReportedStatusById(id);
        }
        return 0;
    }


    public int updateNoReportStatusById(String id) {
        return  departmentPersonCreditMapper.updateNoReportStatusById(id);
    }

    public int updateHaveReportedStatusById(String id) {
        return  departmentPersonCreditMapper.updateHaveReportedStatusById(id);
    }
}