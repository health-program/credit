package com.paladin.credit.service.org;

import com.paladin.credit.mapper.org.OrgPunishmentDiscretionMapper;
import com.paladin.credit.model.org.OrgPunishmentDiscretion;
import com.paladin.credit.service.org.vo.OrgPunishmentDiscretionSimpleVO;
import com.paladin.framework.common.Condition;
import com.paladin.framework.common.QueryType;
import com.paladin.framework.core.ServiceSupport;
import com.paladin.framework.core.copy.SimpleBeanCopier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrgPunishmentDiscretionService extends ServiceSupport<OrgPunishmentDiscretion> {

    @Autowired
    private OrgPunishmentDiscretionMapper orgPunishmentDiscretionMapper;

    public List<OrgPunishmentDiscretionSimpleVO> findDiscretionById(String id) {
        List<OrgPunishmentDiscretion> lists = orgPunishmentDiscretionMapper.findDiscretionById(id);
        List<OrgPunishmentDiscretionSimpleVO> discretions = new ArrayList<>(lists.size());
        OrgPunishmentDiscretionSimpleVO discretionVO;
        for (OrgPunishmentDiscretion list : lists) {
            discretionVO = new OrgPunishmentDiscretionSimpleVO();
            SimpleBeanCopier.SimpleBeanCopyUtil.simpleCopy(list,discretionVO);
            discretions.add(discretionVO);
        }
        return discretions;
    }

    public int removeDiscretionById(String ruleId) {
        return removeByExample(new Condition(OrgPunishmentDiscretion.COLUMN_FIELD_RULE_ID, QueryType.EQUAL,ruleId));
    }
}