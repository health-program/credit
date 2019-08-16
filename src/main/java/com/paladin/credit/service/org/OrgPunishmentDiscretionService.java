package com.paladin.credit.service.org;

import com.paladin.credit.mapper.org.OrgPunishmentDiscretionMapper;
import com.paladin.credit.model.org.OrgPunishmentDiscretion;
import com.paladin.framework.common.Condition;
import com.paladin.framework.common.QueryType;
import com.paladin.framework.core.ServiceSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrgPunishmentDiscretionService extends ServiceSupport<OrgPunishmentDiscretion> {

    @Autowired
    private OrgPunishmentDiscretionMapper orgPunishmentDiscretionMapper;

    public List<OrgPunishmentDiscretion> findDiscretionById(String id) {

        return orgPunishmentDiscretionMapper.findDiscretionById(id);
    }

    public int removeDiscretionById(String ruleId) {
        return removeByExample(new Condition(OrgPunishmentDiscretion.COLUMN_FIELD_RULE_ID, QueryType.EQUAL,ruleId));
    }
}