package com.paladin.credit.service.org;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.paladin.credit.mapper.org.OrgPersonnelMapper;
import com.paladin.credit.model.org.OrgPersonnel;
import com.paladin.credit.service.org.dto.OrgPersonnelQuery;
import com.paladin.credit.service.org.vo.OrgPersonnelSimpleVO;
import com.paladin.credit.service.org.vo.OrgPersonnelVO;
import com.paladin.framework.common.PageResult;
import com.paladin.framework.core.ServiceSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrgPersonnelService extends ServiceSupport<OrgPersonnel> {

    @Autowired
    private OrgPersonnelMapper personnelMapper;

    public PageResult<OrgPersonnelVO> searchPeoplePage(OrgPersonnelQuery query) {
        Page<OrgPersonnelVO> page = PageHelper.offsetPage(query.getOffset(), query.getLimit());
        personnelMapper.searchPeoplePage(query);
        return new PageResult<>(page);
    }

    public List<OrgPersonnelSimpleVO> searchMangePeoples(List<String> codes) {
        return personnelMapper.searchMangePeoples(codes);
    }
}