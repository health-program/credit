package com.paladin.credit.service.org;

import com.paladin.credit.model.org.OrgRuleManagement;
import com.paladin.framework.common.Condition;
import com.paladin.framework.common.QueryType;
import com.paladin.framework.core.ServiceSupport;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrgRuleManagementService extends ServiceSupport<OrgRuleManagement> {

    public List<Map<String, Object>> findRuleLowerByType(String type) {
    List<Map<String, Object>> result = null;
    List<OrgRuleManagement> orgRuleManagements = searchAll(new Condition(OrgRuleManagement.COLUMN_FIELD_TYPE, QueryType.EQUAL, type));
    if (orgRuleManagements != null) {
      result =
          orgRuleManagements.stream()
              .collect(
                  ArrayList::new,
                  (lists, orgRuleManagement) -> {
                    HashMap<String, Object> map = new HashMap<>(2);
                    map.put("id", orgRuleManagement.getScore());
                    map.put("name", orgRuleManagement.getName());
                    lists.add(map);
                  },
                  List::addAll);
    }
    return result;
  }
}