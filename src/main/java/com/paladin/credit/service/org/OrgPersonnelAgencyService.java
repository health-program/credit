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
    private OrgSuperviserService orgSuperviserService;
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
        if (sysUserService.validateAccount(account)) {
            sysUserService.createUserAccount(account, id, SysUser.TYPE_AGENCY);
        } else {
            throw new BusinessException("账号不可用");
        }

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
}