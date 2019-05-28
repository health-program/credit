package com.paladin.credit.mapper.supervise;

import com.paladin.credit.model.supervise.SuperviseRecord;
import com.paladin.credit.service.supervise.dto.SuperviseRecordQuery;
import com.paladin.credit.service.supervise.vo.*;
import com.paladin.framework.core.configuration.mybatis.CustomMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface SuperviseRecordMapper extends CustomMapper<SuperviseRecord> {

    List<SuperviseRecordReportVO> searchAgencyReportsByQuery(@Param("query") SuperviseRecordQuery query);

    List<SuperviseRecordVO> searchReportDetailByQuery(@Param("agencyId") String agencyId, @Param("grade") Integer grade);

    List<SuperviseRecordReportOrgVO> searchAgencyReportsOrgByQuery(@Param("query") SuperviseRecordQuery query);

    List<SuperviseRecordSimpleVO> searchSuperviseRecordsByQuery(@Param("query") SuperviseRecordQuery query);

    int updateRecordById(@Param("id") String id, @Param("illustrate") String newIllustrate, @Param("status") int i);

    int updateGradeById(@Param("id") String id, @Param("grade") Integer grade);

    SuperviseRecordReportVO countRecordEventGradeByDate(@Param("searchTime") Date searchTime);

    SuperviseRecordReportVO countRecordOrgCreditByDate(@Param("searchTime") Date searchTime);

    int updateRecordCheckStatusById(@Param("id") String id ,@Param("resultName")String resultName);

    List<SuperviseRecordOrgMapVO>  findAllOrgMap();

    List<SuperviseRecordOrgMapVO> findMapOrgInfoById(@Param("agencyId")String agencyId);
}