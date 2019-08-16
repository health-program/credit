package com.paladin.credit.mapper.org;

import com.paladin.credit.model.org.OrgPunishmentDiscretion;
import com.paladin.framework.core.configuration.mybatis.CustomMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrgPunishmentDiscretionMapper extends CustomMapper<OrgPunishmentDiscretion> {

    List<OrgPunishmentDiscretion> findDiscretionById(@Param("id") String id);
}