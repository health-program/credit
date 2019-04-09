package com.paladin.credit.service.org;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paladin.common.core.permission.PermissionContainer;
import com.paladin.common.core.permission.Role;
import com.paladin.common.model.syst.SysUser;
import com.paladin.common.service.syst.SysUserService;
import com.paladin.credit.core.CreditAgencyContainer;
import com.paladin.credit.core.CreditUserSession;
import com.paladin.credit.core.CreditAgencyContainer.Agency;
import com.paladin.credit.model.org.OrgAdmin;
import com.paladin.credit.service.org.dto.OrgAdminDTO;
import com.paladin.framework.core.ServiceSupport;
import com.paladin.framework.core.exception.BusinessException;
import com.paladin.framework.utils.uuid.UUIDUtil;

@Service
public class OrgAdminService extends ServiceSupport<OrgAdmin> {

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private PermissionContainer permissionContainer;

	@Transactional
	public boolean saveAdmin(OrgAdminDTO orgAdminDTO) {
		String id = orgAdminDTO.getId();
		if (id == null || id.length() == 0) {
			id = UUIDUtil.createUUID();
		}

		CreditUserSession session = CreditUserSession.getCurrentUserSession();
		if (!session.isAdminRoleLevel()) {
			throw new BusinessException("没有权限新增机构管理账号");
		}

		String agencyIds = checkAgency(orgAdminDTO.getManageAgency());
		String roleIds = checkRole(orgAdminDTO.getRole());
		String account = orgAdminDTO.getAccount();

		if (sysUserService.validateAccount(account)) {
			sysUserService.createUserAccount(account, id, SysUser.TYPE_AGENCY_ADMIN);
		} else {
			throw new BusinessException("账号不可用");
		}

		OrgAdmin admin = new OrgAdmin();

		admin.setId(id);
		admin.setManageAgency(agencyIds);
		admin.setRole(roleIds);
		admin.setName(orgAdminDTO.getName());
		admin.setAccount(account);

		return save(admin) > 0;
	}

	@Transactional
	public boolean updateAdmin(OrgAdminDTO orgAdminDTO) {
		String id = orgAdminDTO.getId();
		if (id == null || id.length() == 0) {
			throw new BusinessException("没有需要修改的对象");
		}

		OrgAdmin admin = get(id);
		if (admin == null) {
			throw new BusinessException("没有需要修改的对象");
		}

		CreditUserSession session = CreditUserSession.getCurrentUserSession();
		if (!session.isAdminRoleLevel()) {
			throw new BusinessException("没有权限修改机构管理账号");
		}

		String agencyIds = checkAgency(orgAdminDTO.getManageAgency());
		String roleIds = checkRole(orgAdminDTO.getRole());
		String account = orgAdminDTO.getAccount();
		String originAccount = admin.getAccount();

		if (!originAccount.equals(account)) {
			if (sysUserService.validateAccount(account)) {
				sysUserService.updateAccount(id, originAccount, account);
			} else {
				throw new BusinessException("账号不可用");
			}
		}

		admin.setId(id);
		admin.setManageAgency(agencyIds);
		admin.setRole(roleIds);
		admin.setName(orgAdminDTO.getName());
		admin.setAccount(account);

		return update(admin) > 0;
	}

	private String checkAgency(String agencyIdString) {
		if (agencyIdString == null || agencyIdString.length() == 0) {
			throw new BusinessException("机构不能为空");
		}
		String[] aids = agencyIdString.split(",");
		List<Agency> agencies = CreditAgencyContainer.getAgencies(aids);
		if (agencies == null || agencies.size() == 0) {
			throw new BusinessException("机构不能为空");
		}

		agencyIdString = "";
		for (Agency agency : agencies) {
			agencyIdString += agency.getId() + ",";
		}
		return agencyIdString;
	}

	private String checkRole(String roleIdString) {
		if (roleIdString == null || roleIdString.length() == 0) {
			throw new BusinessException("角色不能为空");
		}

		String[] rids = roleIdString.split(",");
		roleIdString = "";
		for (String rid : rids) {
			Role role = permissionContainer.getRole(rid);
			if (role != null) {
				roleIdString += rid + ",";
			}
		}

		if (roleIdString.length() == 0) {
			throw new BusinessException("角色不能为空");
		}

		return roleIdString;
	}

}