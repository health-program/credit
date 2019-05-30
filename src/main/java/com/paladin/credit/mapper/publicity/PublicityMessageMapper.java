package com.paladin.credit.mapper.publicity;

import com.paladin.credit.model.publicity.PublicityMessage;
import com.paladin.credit.service.publicity.dto.PublicityMessageQuery;
import com.paladin.credit.service.publicity.vo.PublicityMessageVO;
import com.paladin.framework.core.configuration.mybatis.CustomMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PublicityMessageMapper extends CustomMapper<PublicityMessage> {

    List<PublicityMessage> searchHomePagNotices();

    List<PublicityMessageVO> searchPageNotices(@Param("query") PublicityMessageQuery query);
}