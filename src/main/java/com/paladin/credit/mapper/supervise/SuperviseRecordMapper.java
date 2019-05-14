package com.paladin.credit.mapper.supervise;

import com.paladin.credit.model.supervise.SuperviseRecord;
import com.paladin.credit.service.supervise.dto.SuperviseRecordQuery;
import com.paladin.credit.service.supervise.vo.SuperviseRecordReportOrgVO;
import com.paladin.credit.service.supervise.vo.SuperviseRecordReportVO;
import com.paladin.credit.service.supervise.vo.SuperviseRecordSimpleVO;
import com.paladin.credit.service.supervise.vo.SuperviseRecordVO;
import com.paladin.framework.core.configuration.mybatis.CustomMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SuperviseRecordMapper extends CustomMapper<SuperviseRecord> {

    List<SuperviseRecordReportVO> searchAgencyReportsByQuery(@Param("query") SuperviseRecordQuery query);

    List<SuperviseRecordVO> searchReportDetailByQuery(@Param("agencyId") String agencyId, @Param("grade") Integer grade);

    List<SuperviseRecordReportOrgVO> searchAgencyReportsOrgByQuery(@Param("query") SuperviseRecordQuery query);

    List<SuperviseRecordSimpleVO> searchSuperviseRecordsPageByQuery(@Param("query") SuperviseRecordQuery query);

    int updateRecordById(@Param("id") String id, @Param("illustrate") String newIllustrate, @Param("status") int i);

    int updateGradeById(@Param("id") String id, @Param("grade") Integer grade);
}