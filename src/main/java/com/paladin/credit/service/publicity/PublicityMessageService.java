package com.paladin.credit.service.publicity;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.paladin.credit.mapper.publicity.PublicityMessageMapper;
import com.paladin.credit.service.publicity.dto.PublicityMessageQuery;
import com.paladin.credit.service.publicity.vo.PublicityMessageVO;
import com.paladin.framework.common.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paladin.credit.model.publicity.PublicityMessage;
import com.paladin.framework.core.ServiceSupport;

import java.util.List;

@Service
public class PublicityMessageService extends ServiceSupport<PublicityMessage> {

    @Autowired
    PublicityMessageMapper publicityMessageMapper;

    public List<PublicityMessage> searchHomePagNotices() {
        return publicityMessageMapper.searchHomePagNotices();
    }

    public PageResult<PublicityMessageVO> searchPageNotices(PublicityMessageQuery query) {
        Page<PublicityMessageVO> page = PageHelper.offsetPage(query.getOffset(), query.getLimit());
        publicityMessageMapper.searchPageNotices(query);
        return new PageResult<>(page);
    }
}