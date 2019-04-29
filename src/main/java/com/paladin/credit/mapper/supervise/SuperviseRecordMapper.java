package com.paladin.credit.mapper.supervise;

import com.paladin.credit.model.supervise.SuperviseRecord;
import com.paladin.credit.service.supervise.dto.SuperviseRecordQuery;
import com.paladin.credit.service.supervise.vo.SuperviseRecordReportVO;
import com.paladin.framework.core.configuration.mybatis.CustomMapper;import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SuperviseRecordMapper extends CustomMapper<SuperviseRecord>{

    List<SuperviseRecordReportVO> searchAgencyReportsByQuery(@Param("query")SuperviseRecordQuery query);
}