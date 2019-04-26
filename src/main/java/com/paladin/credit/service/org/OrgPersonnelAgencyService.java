package com.paladin.credit.service.org;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.paladin.common.core.permission.PermissionContainer;
import com.paladin.common.core.permission.Role;
import com.paladin.common.model.syst.SysUser;
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
import com.paladin.framework.core.exception.BusinessException;
import com.paladin.framework.utils.uuid.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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


    /**
     * 功能描述: <新增机构用户>
     * @param dto
     * @return  int
     */
    public int saveAgenecyPeople(OrgPersonnelAgencyDTO dto) {
        int roleLevel = CreditUserSession.getCurrentUserSession().getRoleLevel();
        Preconditions.checkState( roleLevel == CreditUserSession.ROLE_LEVEL_ADMIN || roleLevel == CreditUserSession.ROLE_LEVEL_AGENCY,"您无权添加机构人员账号");
        String id = dto.getId();
        if (Strings.isNullOrEmpty(id)) {
            id = UUIDUtil.createUUID();
        }
        String account = dto.getAccount();
        Optional<String> stringOptional = Optional.ofNullable(account);
        String userId = id;
        stringOptional.filter(s ->sysUserService.validateAccount(s))
                .map(s -> sysUserService.createUserAccount(s, userId, SysUser.TYPE_AGENCY))
                .orElseThrow(() -> new BusinessException("账号不可用"));
        String roleId = dto.getRole();
        String role = checkRole(roleId);
        String agencyId = dto.getAgencyId();
        String agencyIds = checkAgency(agencyId);
        OrgPersonnelAgency orgPersonnelAgency = new OrgPersonnelAgency();
        orgPersonnelAgency.setId(id);
        orgPersonnelAgency.setName(dto.getName());
        orgPersonnelAgency.setAgencyId(agencyIds);
        orgPersonnelAgency.setAccount(account);
        orgPersonnelAgency.setRole(role);

        return save(orgPersonnelAgency);
    }
    /**
     * 功能描述: <更新机构用户>
     * @param agency
     * @return  int
     */
    public int updateAgenecyPeople(OrgPersonnelAgency agency) {
        int i = 0;
        int roleLevel = CreditUserSession.getCurrentUserSession().getRoleLevel();
        Preconditions.checkState(roleLevel == CreditUserSession.ROLE_LEVEL_ADMIN || roleLevel == CreditUserSession.ROLE_LEVEL_AGENCY, "您无权添加机构人员账号");
        String id = agency.getId();
        if (Strings.isNullOrEmpty(id)) {
            throw new BusinessException("修改的账号不存在");
        }
        OrgPersonnelAgency personnelAgency = this.get(id);
        String accountOld = personnelAgency.getAccount();
        SysUser user = sysUserService.getUserByAccount(accountOld);
        if (user == null) {
            throw new BusinessException("修改的账号不存在");
        }
        String accountNew = agency.getAccount();
        if (sysUserService.validateAccount(accountNew)) {
            if (sysUserService.updateAccount(id, accountOld, accountNew) > 0) {
                i =  update(agency);
            }
        } else {
            throw new BusinessException("账号不可用,或已存在该账号");
        }
        return i;
    }

    private String checkRole(String roleIdString) {
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
        return agencies.stream().map(CreditAgencyContainer.Agency::getId).collect(Collectors.joining(",")) ;
    }

    public PageResult<OrgPersonnelAgencyVO> findPageList(OrgPersonnelAgencyQuery query) {
        Page<OrgPersonnelAgencyVO> page = PageHelper.offsetPage(query.getOffset(), query.getLimit());
        personnelAgencyMapper.findPageList(query);
        return new PageResult<>(page);
    }

    /**
     * 功能描述: <通过Id和账号删除机构人员账号>
     * @param account
     * @param id
     * @return  int
     */
    public int removeAgencyPeopleById(String id, String account) {
        int i = 0;
        SysUser user = sysUserService.getUserByAccount(account);
        Optional<SysUser> userOptional = Optional.ofNullable(user);
        Integer state = userOptional.map(u -> sysUserService.removeByPrimaryKey(u.getId()))
                .orElseThrow(() -> new BusinessException("无相关人员账号"));
        if (state > 0) {
             i = removeByPrimaryKey(id);
        }
    return i;
    }
}