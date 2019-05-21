package com.paladin.credit.service.supervise;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.base.Strings;
import com.paladin.credit.core.CreditUserSession;
import com.paladin.credit.mapper.supervise.SuperviseRecordMapper;
import com.paladin.credit.model.supervise.SuperviseRecord;
import com.paladin.credit.model.template.TemplateItem;
import com.paladin.credit.model.template.TemplateItemSelection;
import com.paladin.credit.service.supervise.dto.SuperviseRecordDTO;
import com.paladin.credit.service.supervise.dto.SuperviseRecordPersonnelDTO;
import com.paladin.credit.service.supervise.dto.SuperviseRecordQuery;
import com.paladin.credit.service.supervise.vo.*;
import com.paladin.credit.service.template.TemplateItemSelectionService;
import com.paladin.credit.service.template.TemplateItemService;
import com.paladin.framework.common.PageResult;
import com.paladin.framework.core.ServiceSupport;
import com.paladin.framework.core.copy.SimpleBeanCopier;
import com.paladin.framework.core.exception.BusinessException;
import com.paladin.framework.utils.StringUtil;
import com.paladin.framework.utils.uuid.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
    CreditUserSession userSession = CreditUserSession.getCurrentUserSession();
    int roleLevel = userSession.getRoleLevel();
    if ( ! (roleLevel >= CreditUserSession.ROLE_LEVEL_AGENCY)){
        throw new BusinessException("您没有操作该功能权限");
    }
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
    record.setCode(Integer.valueOf(userSession.getCurrentSuperviseScope()));
    record.setExplainText(superviseRecordDTO.getExplain());
    record.setExplainAttachment(superviseRecordDTO.getExplainAttachment());
    record.setTargetType(itemTargetType);
    if (roleLevel == CreditUserSession.ROLE_LEVEL_SUPERVISE || roleLevel == CreditUserSession.ROLE_LEVEL_AGENCY ){
        record.setStatus(0);
    } else if (roleLevel >= CreditUserSession.ROLE_LEVEL_SUPERVISE_ADMIN ) {
        record.setStatus(1);
    }
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
        if (templateItemSelection != null) {
            record.setResultGrade(templateItemSelection.getSelectionGrade());
            record.setResultName(templateItemSelection.getSelectionName());
        }
        if (itemTargetType == TemplateItem.ITEM_TARGET_TYPE_AGENCY ){
            String agencyId = superviseRecordDTO.getAgencyId();
            if (Strings.isNullOrEmpty(agencyId)) {
                throw new BusinessException("机构不能为空");
            }
            record.setAgencyId(agencyId);
            record.setId(UUIDUtil.createUUID());
            i += save(record);
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
    /**
     * 功能描述 :<查询医疗机构信誉等级表>
     * @Date 14:15 2019/4/29
     * @Param  * @param query
     * @return com.paladin.framework.common.PageResult<com.paladin.credit.service.supervise.vo.SuperviseRecordReportVO>
     **/
    public PageResult<SuperviseRecordReportOrgVO> searchAgencyReportsOrgByQuery(SuperviseRecordQuery query) {
        Page<SuperviseRecordReportOrgVO> page = PageHelper.offsetPage(query.getOffset(), query.getLimit());
        superviseRecordMapper.searchAgencyReportsOrgByQuery(query);
        return  new PageResult<>(page);
    }

    /**
     * 功能描述: <查询机构报表详情>
     * @param agencyId
     * @param grade
     * @return  java.util.List<com.paladin.credit.service.supervise.vo.SuperviseRecordVO>
     */
    public List<SuperviseRecordVO> searchReportDetailByQuery(String agencyId,Integer grade) {
        return superviseRecordMapper.searchReportDetailByQuery(agencyId,grade);
    }

    /**
     * 功能描述: <查询监察记录>
     * @param query
     * @return  com.paladin.framework.common.PageResult<com.paladin.credit.service.supervise.vo.SuperviseRecordSimpleVO>
     * @date  2019/5/5
     */
    public PageResult<SuperviseRecordSimpleVO> searchSuperviseRecordsPageByQuery(SuperviseRecordQuery query) {
        Page<SuperviseRecordSimpleVO> page = PageHelper.offsetPage(query.getOffset(), query.getLimit());
        superviseRecordMapper.searchSuperviseRecordsPageByQuery(query);
        return  new PageResult<>(page);
    }

  /**
   * 功能描述: <监察记录审核>
   *
   * @param id
   * @param illustrate
   * @param success
   * @return int
   * @date 2019/5/6
   */
    public int check(String id, String illustrate, boolean success) {
        int i;
        if (Strings.isNullOrEmpty(id)) {
          throw new BusinessException("无审核记录");
        }
        CreditUserSession userSession = CreditUserSession.getCurrentUserSession();
        int roleLevel = userSession.getRoleLevel();
        if (roleLevel < CreditUserSession.ROLE_LEVEL_SUPERVISE_ADMIN) {
            throw  new BusinessException("您没有操作该功能权限");
        }
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 hh:mm a");
        String checkTime = now.format(formatter);
        String checkPeople = userSession.getUserName();
        String newIllustrate;
        if (success) {
          if (Strings.isNullOrEmpty(illustrate)) {
            newIllustrate = checkPeople + "于" + checkTime + " 回复:通过";
          } else {
              if (illustrate.length() >SuperviseRecord.DEFAULT_ILLUSTRATE_SIZE) {
                  throw new BusinessException("审核意见不应大于30个字符");
              }
            newIllustrate = checkPeople + "于" + checkTime + " 回复:" + illustrate;
          }
          i = superviseRecordMapper.updateRecordById(id, newIllustrate, 1);
        } else {
          if (Strings.isNullOrEmpty(illustrate)) {
            throw new BusinessException("请输入审核意见");
          }
          if (illustrate.length() > SuperviseRecord.DEFAULT_ILLUSTRATE_SIZE) {
              throw new BusinessException("审核意见不应大于30个字符");
          }
          newIllustrate = checkPeople + "于" + checkTime + " 回复:" + illustrate;
          i = superviseRecordMapper.updateRecordById(id, newIllustrate, 2);
        }
        return i;
    }


    /**
     * 功能描述: <卫监所监察项目保存>
     * @param superviseRecordDTO
     * @return  int
     * @date  2019/5/13
     */
    public int saveWjsRecords(SuperviseRecordDTO superviseRecordDTO) {
        int i;
        CreditUserSession userSession = CreditUserSession.getCurrentUserSession();
        superviseRecordDTO.setCode(Integer.valueOf(userSession.getCurrentSuperviseScope()));
        int roleLevel = userSession.getRoleLevel();
        if (roleLevel < CreditUserSession.ROLE_LEVEL_SUPERVISE) {
          throw new BusinessException("您没有操作该功能权限");
        }
        SuperviseRecord record = new SuperviseRecord();
        SimpleBeanCopier.SimpleBeanCopyUtil.simpleCopy(superviseRecordDTO,record);
        record.setStatus(0);
        record.setResultGrade(0);
        record.setIsWjs(1);
        i = getSaveResult(record,superviseRecordDTO.getTargetType(),superviseRecordDTO,null);
        return i;
      }

    /**
     * 功能描述: <卫监所保存记录评级>
     * @param id
     * @param grade
     * @return  int
     * @date  2019/5/17
     */
    public int grade(String id, Integer grade) {
        CreditUserSession userSession = CreditUserSession.getCurrentUserSession();
        int roleLevel = userSession.getRoleLevel();
        if (roleLevel < CreditUserSession.ROLE_LEVEL_SUPERVISE_ADMIN) {
            throw new BusinessException("您没有操作该功能权限");
        }
        return superviseRecordMapper.updateGradeById(id,grade);
    }

    /**
     * 功能描述: <撤销已审核记录>
     * @param id
     * @return  int
     * @date  2019/5/17
     */
    public int repealSuperviseRecordById(String id) {
        CreditUserSession userSession = CreditUserSession.getCurrentUserSession();
        int roleLevel = userSession.getRoleLevel();
        if (roleLevel < CreditUserSession.ROLE_LEVEL_SUPERVISE_ADMIN) {
            throw new BusinessException("您没有操作该功能权限");
        }

        SuperviseRecord superviseRecord = superviseRecordMapper.selectByPrimaryKey(id);

        String resultName = superviseRecord.getResultName();

        int i = superviseRecordMapper.updateRecordCheckStatusById(id ,resultName);
        if (i<=0){
            throw new BusinessException("已超过时间无法撤销");
        }
        return i ;
    }

    /**
     * 功能描述: <按事件等级统计监察记录>
     * @param searchTime
     * @return  com.paladin.credit.service.supervise.vo.SuperviseRecordReportVO
     */
  public SuperviseRecordReportVO countRecordEventGradeByDate(Date searchTime) {
    return superviseRecordMapper.countRecordEventGradeByDate(searchTime);
  }

    /**
     * 功能描述: <按机构信用等级统计监察记录>
     * @param searchTime
     * @return  com.paladin.credit.service.supervise.vo.SuperviseRecordReportVO
     */
    public SuperviseRecordReportVO countRecordOrgCreditByDate(Date searchTime) {
        return superviseRecordMapper.countRecordOrgCreditByDate(searchTime);
    }
    /**
     * 功能描述: <查询机构等级>
     * @return  com.paladin.credit.service.supervise.vo.SuperviseRecordReportVO
     */
    public List<SuperviseRecordOrgMapVO> findAllOrgMap() {
        List<SuperviseRecordOrgMapVO> orgMap = superviseRecordMapper.findAllOrgMap();
        for (SuperviseRecordOrgMapVO orgMapVO : orgMap) {
            if (orgMapVO.getAgencyCoordinate()!=null) {
                String[] arr = orgMapVO.getAgencyCoordinate().split(",");
                orgMapVO.setLeftAgencyCoordinate(arr[0]);
                orgMapVO.setRightAgencyCoordinate(arr[1]);
            }
        }
        return orgMap;
    }
    /**
     * 功能描述 :<根据机构id查询该机构最新创建的五条数据>
     * @Date 13:01 2019/5/21
     * @Param  * @param agencyId
     * @return java.util.List<com.paladin.credit.service.supervise.vo.SuperviseRecordOrgMapVO>
     **/
    public List<SuperviseRecordOrgMapVO> findNewOrgMap(String agencyId){

        if (agencyId==null) {
            throw new BusinessException("机构id不能为空");
        }
        return superviseRecordMapper.findNewOrgMap(agencyId);
    }
}