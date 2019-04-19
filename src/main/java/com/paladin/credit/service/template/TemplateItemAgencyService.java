package com.paladin.credit.service.template;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.paladin.credit.mapper.template.TemplateItemAgencyMapper;
import com.paladin.credit.model.template.TemplateItemAgency;
import com.paladin.credit.service.template.dto.TemplateItemAgencyQuery;
import com.paladin.credit.service.template.vo.TemplateItemAgencyVO;
import com.paladin.framework.common.Condition;
import com.paladin.framework.common.PageResult;
import com.paladin.framework.common.QueryType;
import com.paladin.framework.core.ServiceSupport;
import com.paladin.framework.utils.uuid.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TemplateItemAgencyService extends ServiceSupport<TemplateItemAgency> {

    @Autowired
    TemplateItemAgencyMapper templateItemAgencyMapper;

    public PageResult<TemplateItemAgencyVO> searchConfigedTemplatesByQuery(TemplateItemAgencyQuery query) {
        Page<TemplateItemAgencyVO> page = PageHelper.offsetPage(query.getOffset(), query.getLimit());
        templateItemAgencyMapper.searchAgencyTemplatesByQuery(query);
        return new PageResult<>(page);
    }

  public int templateConfigById(String agencyId, String itemId) {
    TemplateItemAgency templateItemAgency = new TemplateItemAgency();
    templateItemAgency.setAgencyId(agencyId);
    templateItemAgency.setItemId(itemId);
    templateItemAgency.setId(UUIDUtil.createUUID());
    return save(templateItemAgency);
  }

    public int templateConfigByIds(String agencyId, List<String> itemIds) {
        int i = 0;
        TemplateItemAgency templateItemAgency=  new TemplateItemAgency();
        for (String itemId : itemIds) {
            templateItemAgency.setAgencyId(agencyId);
            templateItemAgency.setItemId(itemId);
            templateItemAgency.setId(UUIDUtil.createUUID());
            i = save(templateItemAgency);
        }
        return i;
    }

    /**
     * 功能描述: <检查机构是否配置过该模板>
     * @param agencyId
     * @param itemId
     *
     * @return  boolean
     */
  public boolean checkAgencyTemplateConfiged(String agencyId, String itemId) {
      boolean isConfiged = false;
      List<TemplateItemAgency> itemAgencies = searchAll(new Condition(TemplateItemAgency.COLUMN_FIELD_AGENCY_ID, QueryType.EQUAL, agencyId),
              new Condition(TemplateItemAgency.COLUMN_FIELD_ITEM_ID, QueryType.EQUAL, itemId));
      if (itemAgencies != null) {
          isConfiged = true;
      }
      return  isConfiged;
  }

    public PageResult<TemplateItemAgencyVO> searchNoConfigTemplatesByQuery(TemplateItemAgencyQuery query) {
        Page<TemplateItemAgencyVO> page = PageHelper.offsetPage(query.getOffset(), query.getLimit());
        templateItemAgencyMapper.searchNoConfigTemplatesByQuery(query);
        return new PageResult<>(page);
  }

      public PageResult<TemplateItemAgencyVO> searchTemplatesByQuery(TemplateItemAgencyQuery query) {
        Page<TemplateItemAgencyVO> page = PageHelper.offsetPage(query.getOffset(), query.getLimit());
        templateItemAgencyMapper.searchTemplatesByQuery(query);
        return new PageResult<>(page);
      }
}