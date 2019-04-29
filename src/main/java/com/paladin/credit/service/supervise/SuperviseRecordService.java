package com.paladin.credit.service.supervise;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.paladin.credit.mapper.supervise.SuperviseRecordMapper;
import com.paladin.credit.model.supervise.SuperviseRecord;
import com.paladin.credit.model.template.TemplateItem;
import com.paladin.credit.model.template.TemplateItemSelection;
import com.paladin.credit.service.supervise.dto.SuperviseRecordDTO;
import com.paladin.credit.service.supervise.dto.SuperviseRecordPersonnelDTO;
import com.paladin.credit.service.supervise.dto.SuperviseRecordQuery;
import com.paladin.credit.service.supervise.vo.SuperviseRecordReportVO;
import com.paladin.credit.service.template.TemplateItemSelectionService;
import com.paladin.credit.service.template.TemplateItemService;
import com.paladin.framework.common.PageResult;
import com.paladin.framework.core.ServiceSupport;
import com.paladin.framework.core.exception.BusinessException;
import com.paladin.framework.utils.StringUtil;
import com.paladin.framework.utils.uuid.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SuperviseRecordService extends ServiceSupport<SuperviseRecord> {

    @Autowired
    private TemplateItemService templateItemService;

    @Autowired
    private TemplateItemSelectionService templateItemSelectionService;

    @Autowired
    private SuperviseRecordMapper superviseRecordMapper;

    @Transactional
    public int saveRecords(SuperviseRecordDTO superviseRecordDTO) {
    int i = 0;
    String itemId = superviseRecordDTO.getItemId();
    if (StringUtil.isEmpty(itemId)) {
      throw new BusinessException("找不到对应模板项目");
    }
    TemplateItem templateItem = templateItemService.get(itemId);
    if (templateItem == null) {
      throw new BusinessException("找不到对应模板项目");
    }
    String[] selections = superviseRecordDTO.getSelections();
    if (selections == null || selections.length == 0) {
      throw new BusinessException("项目选项不能为空");
    }
    SuperviseRecord record = new SuperviseRecord();
    Integer itemTargetType = templateItem.getItemTargetType();
    record.setItem(templateItem.getItemName());
    record.setExplainText(superviseRecordDTO.getExplain());
    record.setExplainAttachment(superviseRecordDTO.getExplainAttachment());
    record.setTargetType(itemTargetType);
    Integer isMultiple = templateItem.getIsMultiple();
    TemplateItemSelection templateItemSelection;
    if (isMultiple == 1) {
      for (String selection : selections) {
        templateItemSelection = templateItemSelectionService.get(selection);
        if (templateItemSelection == null) {
          throw new BusinessException("找不到对应项目选项");
        }
        i = getSaveResult(record, itemTargetType, superviseRecordDTO, templateItemSelection);
      }

    } else {
      templateItemSelection = templateItemSelectionService.get(selections[0]);
      if (templateItemSelection == null) {
        throw new BusinessException("找不到对应项目选项");
      }
      i = getSaveResult(record, itemTargetType, superviseRecordDTO, templateItemSelection);
    }
    return i;
    }

    public int getSaveResult(SuperviseRecord record, Integer itemTargetType, SuperviseRecordDTO superviseRecordDTO, TemplateItemSelection templateItemSelection) {
        int i = 0;
        record.setResultGrade(templateItemSelection.getSelectionGrade());
        record.setResultName(templateItemSelection.getSelectionName());
        if (itemTargetType == TemplateItem.ITEM_TARGET_TYPE_AGENCY ){
            String[] agencyId = superviseRecordDTO.getAgencyId();
            if ( agencyId != null && agencyId.length>0) {
                for (String id :agencyId ) {
                    record.setAgencyId(id);
                    record.setId(UUIDUtil.createUUID());
                    i += save(record);
                }
            }
        }else if ( itemTargetType == TemplateItem.ITEM_TARGET_TYPE_PERSONNEL){
            String[] personnelId = superviseRecordDTO.getPersonnelId();
            if ( personnelId != null && personnelId.length > 0) {
                for (String id : personnelId) {
                    record.setPersonnelId(id);
                    record.setId(UUIDUtil.createUUID());
                    i += save(record);
                }
            }
        } else {
            List<SuperviseRecordPersonnelDTO> personnels = superviseRecordDTO.getPersonnels();
            if ( personnels != null && personnels.size() > 0) {
                for (SuperviseRecordPersonnelDTO personnel : personnels) {
                    record.setPersonnelAddress(personnel.getPersonnelAddress());
                    record.setPersonnelIdentification(personnel.getPersonnelIdentification());
                    record.setPersonnelName(personnel.getPersonnelName());
                    record.setPersonnelSex(personnel.getPersonnelSex());
                    record.setId(UUIDUtil.createUUID());
                    i += save(record);
                }
            }
        }
        return i;
    }

    /**
     * 功能描述: <查询所有机构报表>
     * @param query
     * @return  com.paladin.framework.common.PageResult<com.paladin.credit.service.supervise.vo.SuperviseRecordReportVO>
     * @date  2019/4/29
     */
    public PageResult<SuperviseRecordReportVO> searchAgencyReportsByQuery(SuperviseRecordQuery query) {
        Page<SuperviseRecordReportVO> page = PageHelper.offsetPage(query.getOffset(), query.getLimit());
        superviseRecordMapper.searchAgencyReportsByQuery(query);
        return  new PageResult<>(page);
    }
}