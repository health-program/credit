package com.paladin.credit.service.org;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.base.Strings;
import com.paladin.common.core.permission.PermissionContainer;
import com.paladin.common.core.permission.Role;
import com.paladin.common.model.org.OrgRole;
import com.paladin.common.model.syst.SysUser;
import com.paladin.common.service.org.OrgRoleService;
import com.paladin.common.service.syst.SysUserService;
import com.paladin.credit.core.CreditAgencyContainer;
import com.paladin.credit.core.CreditUserSession;
import com.paladin.credit.mapper.org.OrgPersonnelAgencyMapper;
import com.paladin.credit.model.org.OrgPersonnelAgency;
import com.paladin.credit.service.org.dto.OrgPersonnelAgencyDTO;
import com.paladin.credit.service.org.dto.OrgPersonnelAgencyQuery;
import com.paladin.credit.service.org.vo.OrgPersonnelAgencyVO;
import com.paladin.framework.common.PageResult;
import com.paladin.framework.core.ServiceSupport;
import com.paladin.framework.core.copy.SimpleBeanCopier;
import com.paladin.framework.core.exception.BusinessException;
import com.paladin.framework.utils.uuid.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrgPersonnelAgencyService extends ServiceSupport<OrgPersonnelAgency> {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private PermissionContainer permissionContainer;
    @Autowired
    private OrgPersonnelAgencyMapper personnelAgencyMapper;
    @Autowired
    private OrgRoleService orgRoleService;



    /**
     * 功能描述: <新增机构用户>
     * @param dto
     * @return  int
     */
    public int saveAgenecyPeople(OrgPersonnelAgencyDTO dto) {
        int roleLevel = CreditUserSession.getCurrentUserSession().getRoleLevel();
        if (!(roleLevel == CreditUserSession.ROLE_LEVEL_ADMIN || roleLevel == CreditUserSession.ROLE_LEVEL_AGENCY)) {
            throw  new BusinessException("您无权添加机构人员账号");
        }
        String id = dto.getId();
        if (Strings.isNullOrEmpty(id)) {
          id = UUIDUtil.createUUID();
        }
        String newAccount = dto.getAccount();
        boolean createAccountSuccess = checkAccount(null, newAccount, id);
        if (!createAccountSuccess) {
            throw  new BusinessException("创建账号失败");
        }
        String roleId = dto.getRole();
        String role = checkRole(roleId);
        String agencyId = dto.getAgencyId();
        OrgRole orgRole = orgRoleService.get(role);
        if ( orgRole.getRoleLevel() == CreditUserSession.ROLE_LEVEL_AGENCY ){
            agencyId = checkAgency(agencyId);
        }
        OrgPersonnelAgency orgPersonnelAgency = new OrgPersonnelAgency();
        SimpleBeanCopier.SimpleBeanCopyUtil.simpleCopy(dto,orgPersonnelAgency);
        orgPersonnelAgency.setId(id);
        orgPersonnelAgency.setRole(role);
        orgPersonnelAgency.setAgencyId(agencyId);
        return save(orgPersonnelAgency);
    }
  /**
   * 功能描述: <更新机构用户>
   *
   * @param agency
   * @return int
   */
    public int updateAgenecyPeople(OrgPersonnelAgency agency) {
        int i;
        int roleLevel = CreditUserSession.getCurrentUserSession().getRoleLevel();
        if (!(roleLevel == CreditUserSession.ROLE_LEVEL_ADMIN || roleLevel == CreditUserSession.ROLE_LEVEL_AGENCY)) {
            throw  new BusinessException("您无权添加机构人员账号");
        }
        String id = agency.getId();
        if (Strings.isNullOrEmpty(id)) {
          throw new BusinessException("修改的账号不存在");
        }
        OrgPersonnelAgency personnelAgency = this.get(id);
        String newAgencyId = agency.getAgencyId();
        String newRoleId = agency.getRole();
        if (newRoleId.equals(personnelAgency.getRole())) {
            newRoleId = checkRole(newRoleId);
            agency.setRole(newRoleId);
        }
        if ( newAgencyId.equals(personnelAgency.getAgencyId())){
            OrgRole orgRole = orgRoleService.get(newRoleId);
            if ( orgRole.getRoleLevel() == CreditUserSession.ROLE_LEVEL_AGENCY ){
                newAgencyId = checkAgency(newAgencyId);
                agency.setAgencyId(newAgencyId);
            }
        }
        String accountOld = personnelAgency.getAccount();
        String accountNew = agency.getAccount();
        if (!accountNew.equals(accountOld)) {
            boolean updateAccountSuccess = checkAccount(accountOld, accountNew, id);
            if ( !updateAccountSuccess){
                throw  new BusinessException("更新账号失败");
            }
            i = update(agency);
        } else {
          i = update(agency);
        }
        return i;
    }

    public String checkRole(String roleIdString) {
        if (Strings.isNullOrEmpty(roleIdString)) {
            throw new BusinessException("角色不能为空");
        }
        Role role = permissionContainer.getRole(roleIdString);
        Optional<Role> optionalRole = Optional.ofNullable(role);
        return optionalRole.map(Role::getId).orElseThrow(() -> new BusinessException("角色不能为空"));
    }

    private String checkAgency(String agencyIdString) {
        if (Strings.isNullOrEmpty(agencyIdString)) {
          throw new BusinessException("机构不能为空");
        }
        String[] aids = agencyIdString.split(",");
        List<CreditAgencyContainer.Agency> agencies = CreditAgencyContainer.getAgencies(aids);
        if (agencies == null || agencies.size() == 0) {
          throw new BusinessException("机构不能为空");
        }
        return agencies.stream()
            .map(CreditAgencyContainer.Agency::getId)
            .collect(Collectors.joining(","));
    }

    private boolean checkAccount(String oldAccount, String newAccount, String userId) {
        if (Strings.isNullOrEmpty(oldAccount)) {
            Optional<String> stringOptional = Optional.ofNullable(newAccount);
            return stringOptional
                    .filter(s -> sysUserService.validateAccount(s))
                    .map(s -> sysUserService.createUserAccount(s, userId, SysUser.TYPE_AGENCY))
                    .orElseThrow(() -> new BusinessException("账号不可用"))
                    > 0;
        } else {
            SysUser user = sysUserService.getUserByAccount(oldAccount);
            if (user == null) {
                throw new BusinessException("修改的账号不存在");
            }
            if (sysUserService.validateAccount(newAccount)) {
                return sysUserService.updateAccount(userId, oldAccount, newAccount) > 0;
            } else {
                throw new BusinessException("账号不可用,或已存在该账号");
            }
        }
    }

    public PageResult<OrgPersonnelAgencyVO> findPageList(OrgPersonnelAgencyQuery query) {
        Page<OrgPersonnelAgencyVO> page = PageHelper.offsetPage(query.getOffset(), query.getLimit());
        personnelAgencyMapper.findPageList(query);
        return new PageResult<>(page);
    }

    /**
     * 功能描述: <通过Id和账号删除机构人员账号>
     * @param id
     * @return  int
     */
    public int removeAgencyPeopleById(String id) {
        OrgPersonnelAgency orgPersonnelAgency = get(id);
        Optional<OrgPersonnelAgency> agencyOptional = Optional.ofNullable(orgPersonnelAgency);
        return  agencyOptional.map(a -> sysUserService.getUserByAccount(a.getAccount()))
                .filter(Objects::nonNull)
                .map(sysUser -> sysUserService.removeByPrimaryKey(sysUser.getId()))
                .filter(integer -> integer >0)
                .map( state -> removeByPrimaryKey(id))
                .orElseThrow(() -> new BusinessException("无相关人员账号"));
    }
}