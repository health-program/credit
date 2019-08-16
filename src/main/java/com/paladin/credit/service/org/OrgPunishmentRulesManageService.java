package com.paladin.credit.service.org;

import com.google.common.base.Strings;
import com.paladin.credit.model.org.OrgPunishmentDiscretion;
import com.paladin.credit.model.org.OrgPunishmentRulesManage;
import com.paladin.credit.service.org.dto.OrgPunishmentRulesManageDTO;
import com.paladin.credit.service.org.vo.OrgPunishmentRulesManageVO;
import com.paladin.framework.common.Condition;
import com.paladin.framework.common.QueryType;
import com.paladin.framework.core.ServiceSupport;
import com.paladin.framework.core.copy.SimpleBeanCopier;
import com.paladin.framework.core.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrgPunishmentRulesManageService extends ServiceSupport<OrgPunishmentRulesManage> {

    @Autowired
    private OrgPunishmentDiscretionService orgPunishmentDiscretionService;

    @Transactional(rollbackFor = Exception.class)
    public boolean saveRule(OrgPunishmentRulesManageDTO orgPunishmentRulesManageDTO) {
        String ruleId = orgPunishmentRulesManageDTO.getId();
        Integer serialNumber = orgPunishmentRulesManageDTO.getSerialNumber();
        int i = searchAllCount(new Condition(OrgPunishmentRulesManage.COLUMN_FIELD_SERIAL_NUMBER, QueryType.EQUAL, serialNumber));
        if (i > 0) {
            throw new BusinessException("该序号已存在，请重新输入");
        }
        OrgPunishmentRulesManage rules = new OrgPunishmentRulesManage();
        SimpleBeanCopier.SimpleBeanCopyUtil.simpleCopy(orgPunishmentRulesManageDTO,rules);
        List<OrgPunishmentDiscretion> selections = orgPunishmentRulesManageDTO.getSelections();
        if (selections == null || selections.size() == 0 ) {
            throw new BusinessException("裁量基准不能为空");
        }
        OrgPunishmentDiscretion discretion;
        int count = 0;
        for (OrgPunishmentDiscretion selection : selections) {
            discretion = new OrgPunishmentDiscretion();
            SimpleBeanCopier.SimpleBeanCopyUtil.simpleCopy(selection,discretion);
            discretion.setRuleId(ruleId);
            count += orgPunishmentDiscretionService.save(discretion);
        }
        if (count == selections.size()) {
            return save(rules) > 0;
        } else {
            throw new BusinessException("保存裁量基准出错");
        }

    }

    public OrgPunishmentRulesManageVO getRule(String id) {
        if (Strings.isNullOrEmpty(id)) {
            throw new BusinessException("找不到对应的处罚条例");
        }
        OrgPunishmentRulesManage rulesManage = get(id);
        if (rulesManage == null) {
            throw new BusinessException("找不到对应的处罚条例");
        }
        OrgPunishmentRulesManageVO rulesManageVO = new OrgPunishmentRulesManageVO();
        SimpleBeanCopier.SimpleBeanCopyUtil.simpleCopy(rulesManage,rulesManageVO);
        rulesManageVO.setSelections(orgPunishmentDiscretionService.findDiscretionById(id));
        return rulesManageVO;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateRule(OrgPunishmentRulesManageDTO orgPunishmentRulesManageDTO) {
        String ruleId = orgPunishmentRulesManageDTO.getId();
        if (Strings.isNullOrEmpty(ruleId)) {
            throw new BusinessException("处罚条例不存在");
        }
        OrgPunishmentRulesManage rulesManage = get(ruleId);
        if (rulesManage == null) {
            throw new BusinessException("处罚条例不存在");
        }
        Integer newSerialNumber = orgPunishmentRulesManageDTO.getSerialNumber();
        Integer oldSerialNumber = rulesManage.getSerialNumber();
        if (!newSerialNumber.equals(oldSerialNumber)) {
            int i = searchAllCount(new Condition(OrgPunishmentRulesManage.COLUMN_FIELD_SERIAL_NUMBER, QueryType.EQUAL, newSerialNumber));
            if (i > 0) {
                throw new BusinessException("该序号已存在，请重新输入");
            }
        }
        SimpleBeanCopier.SimpleBeanCopyUtil.simpleCopy(orgPunishmentRulesManageDTO,rulesManage);
        List<OrgPunishmentDiscretion> selections = orgPunishmentRulesManageDTO.getSelections();
        if (selections == null || selections.size() == 0 ) {
            throw new BusinessException("裁量基准不能为空");
        }
        orgPunishmentDiscretionService.removeDiscretionById(ruleId);
        int count = 0;
        OrgPunishmentDiscretion discretion;
        for (OrgPunishmentDiscretion selection : selections) {
            discretion = new OrgPunishmentDiscretion();
            SimpleBeanCopier.SimpleBeanCopyUtil.simpleCopy(selection,discretion);
            discretion.setRuleId(ruleId);
            count += orgPunishmentDiscretionService.save(discretion);
        }
        if (count == selections.size()) {
            return update(rulesManage) > 0;
        } else {
            throw new BusinessException("修改裁量基准出错");
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public int removeRuleById(String id) {
        int i = orgPunishmentDiscretionService.removeDiscretionById(id);
        if (i == 0) {
            throw new BusinessException("删除裁量基准出错");
        }
        return removeByPrimaryKey(id);
    }
}