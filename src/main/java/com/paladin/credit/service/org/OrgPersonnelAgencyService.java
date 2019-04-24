package com.paladin.credit.service.org;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.paladin.common.core.permission.PermissionContainer;
import com.paladin.common.core.permission.Role;
import com.paladin.common.model.syst.SysUser;
import com.paladin.common.service.syst.SysUserService;
import com.paladin.credit.core.CreditUserSession;
import com.paladin.credit.model.org.OrgPersonnelAgency;
import com.paladin.credit.service.org.dto.OrgPersonnelAgencyDTO;
import com.paladin.framework.core.ServiceSupport;
import com.paladin.framework.core.exception.BusinessException;
import com.paladin.framework.utils.uuid.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrgPersonnelAgencyService extends ServiceSupport<OrgPersonnelAgency> {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private OrgSuperviserService orgSuperviserService;
    @Autowired
    private PermissionContainer permissionContainer;


    /**
     * 功能描述: <新增机构用户>
     * @param dto
     * @return  int
     */
    public int saveAgenecy(OrgPersonnelAgencyDTO dto) {
        int roleLevel = CreditUserSession.getCurrentUserSession().getRoleLevel();
        Preconditions.checkArgument(!(roleLevel == 2 || roleLevel == 9),"您无权添加机构人员账号");
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
        String agencyIds = orgSuperviserService.checkAgency(agencyId);
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
        roleIdString = "";
        Role role = permissionContainer.getRole(roleIdString);
        roleIdString = role.getId();
        if (roleIdString.length() == 0) {
            throw new BusinessException("角色不能为空");
        }

        return roleIdString;
    }
}