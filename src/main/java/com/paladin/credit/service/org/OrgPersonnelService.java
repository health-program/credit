package com.paladin.credit.service.org;

import com.paladin.credit.mapper.org.OrgPersonnelMapper;
import com.paladin.credit.service.org.vo.OrgPersonnelVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paladin.credit.model.org.OrgPersonnel;
import com.paladin.framework.core.ServiceSupport;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrgPersonnelService extends ServiceSupport<OrgPersonnel> {

    @Autowired
    private OrgPersonnelMapper personnelMapper;


    public List<OrgPersonnelVO> searchName() {
        ArrayList<OrgPersonnelVO> list=personnelMapper.searchName();


        return list;
    }
}