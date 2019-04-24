package com.paladin.credit.service.template;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.paladin.credit.mapper.template.TemplateItemAgencyMapper;
import com.paladin.credit.model.template.TemplateItemAgency;
import com.paladin.credit.service.template.dto.TemplateItemAgencyQuery;
import com.paladin.credit.service.template.vo.TemplateItemAgencyVO;
import com.paladin.framework.common.PageResult;
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

  public int templateConfigById(Integer code, String itemId) {
    TemplateItemAgency templateItemAgency = new TemplateItemAgency();
    templateItemAgency.setCode(code);
    templateItemAgency.setItemId(itemId);
    templateItemAgency.setId(UUIDUtil.createUUID());
    return save(templateItemAgency);
  }

    public int templateConfigByIds(Integer code, List<String> itemIds) {
        int i = 0;
        TemplateItemAgency templateItemAgency=  new TemplateItemAgency();
        templateItemAgency.setCode(code);
        for (String itemId : itemIds) {
            templateItemAgency.setItemId(itemId);
            templateItemAgency.setId(UUIDUtil.createUUID());
            i = save(templateItemAgency);
        }
        return i;
    }

    public PageResult<TemplateItemAgencyVO> searchNoConfigTemplatesByQuery(TemplateItemAgencyQuery query) {
        Page<TemplateItemAgencyVO> page = PageHelper.offsetPage(query.getOffset(), query.getLimit());
        templateItemAgencyMapper.searchNoConfigTemplatesByQuery(query);
        return new PageResult<>(page);
  }

    /**
     * 功能描述: <日常操作功能管理获取对应机构模板>
     * @param query
     * @return  com.paladin.framework.common.PageResult<com.paladin.credit.service.template.vo.TemplateItemAgencyVO>
     */
      public PageResult<TemplateItemAgencyVO> searchTemplatesByQuery(TemplateItemAgencyQuery query) {
        Page<TemplateItemAgencyVO> page = PageHelper.offsetPage(query.getOffset(), query.getLimit());
        templateItemAgencyMapper.searchTemplatesByQuery(query);
        return new PageResult<>(page);
      }
}