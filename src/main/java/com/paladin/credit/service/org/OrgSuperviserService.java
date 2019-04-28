package com.paladin.credit.service.org;

import com.paladin.common.core.permission.PermissionContainer;
import com.paladin.common.core.permission.Role;
import com.paladin.common.model.syst.SysUser;
import com.paladin.common.service.syst.SysUserService;
import com.paladin.credit.core.CreditAgencyContainer;
import com.paladin.credit.core.CreditAgencyContainer.Agency;
import com.paladin.credit.core.CreditUserSession;
import com.paladin.credit.core.DataPermissionUtil;
import com.paladin.credit.model.org.OrgSuperviser;
import com.paladin.credit.service.org.dto.OrgSuperviserDTO;
import com.paladin.framework.core.ServiceSupport;
import com.paladin.framework.core.exception.BusinessException;
import com.paladin.framework.utils.uuid.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrgSuperviserService extends ServiceSupport<OrgSuperviser> {

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private PermissionContainer permissionContainer;

	@Transactional
	public boolean saveAdmin(OrgSuperviserDTO orgSuperviserDTO) {
		String id = orgSuperviserDTO.getId();
		if (id == null || id.length() == 0) {
			id = UUIDUtil.createUUID();
		}

		CreditUserSession session = CreditUserSession.getCurrentUserSession();
		if (!session.isAdminRoleLevel()) {
			throw new BusinessException("没有权限新增机构管理账号");
		}

		String scope = checkScope(orgSuperviserDTO.getSuperviseScope());
		String roleIds = checkRole(orgSuperviserDTO.getRole());
		String account = orgSuperviserDTO.getAccount();

		if (sysUserService.validateAccount(account)) {
			sysUserService.createUserAccount(account, id, SysUser.TYPE_SUPERVISE);
		} else {
			throw new BusinessException("账号不可用");
		}

		OrgSuperviser superviser = new OrgSuperviser();

		superviser.setId(id);
		superviser.setSuperviseScope(scope);
		superviser.setRole(roleIds);
		superviser.setName(orgSuperviserDTO.getName());
		superviser.setAccount(account);

		return save(superviser) > 0;
	}

	@Transactional
	public boolean updateAdmin(OrgSuperviserDTO orgSuperviserDTO) {
		String id = orgSuperviserDTO.getId();
		if (id == null || id.length() == 0) {
			throw new BusinessException("没有需要修改的对象");
		}

		OrgSuperviser superviser = get(id);
		if (superviser == null) {
			throw new BusinessException("没有需要修改的对象");
		}

		CreditUserSession session = CreditUserSession.getCurrentUserSession();
		if (!session.isAdminRoleLevel()) {
			throw new BusinessException("没有权限修改机构管理账号");
		}

		String scope = checkScope(orgSuperviserDTO.getSuperviseScope());
		String roleIds = checkRole(orgSuperviserDTO.getRole());
		String account = orgSuperviserDTO.getAccount();
		String originAccount = superviser.getAccount();

		if (!originAccount.equals(account)) {
			if (sysUserService.validateAccount(account)) {
				sysUserService.updateAccount(id, originAccount, account);
			} else {
				throw new BusinessException("账号不可用");
			}
		}

		superviser.setId(id);
		superviser.setSuperviseScope(scope);
		superviser.setRole(roleIds);
		superviser.setName(orgSuperviserDTO.getName());
		superviser.setAccount(account);

		return update(superviser) > 0;
	}

	@SuppressWarnings("unused")
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
		int roleLevel = -1;
		for (String rid : rids) {
			Role role = permissionContainer.getRole(rid);
			if (role != null) {
				roleIdString += rid + ",";
			}
			roleLevel = Math.max(role.getRoleLevel(), roleLevel);
		}

		if (roleIdString.length() == 0) {
			throw new BusinessException("角色不能为空");
		}

		if (roleLevel < CreditUserSession.ROLE_LEVEL_SUPERVISE) {
			throw new BusinessException("角色等级不能低于监督人员");
		}

		return roleIdString;
	}

	private String checkScope(String superviseScope) {
		String result = "";
		if (superviseScope != null && superviseScope.length() > 0) {

			String[] scopes = superviseScope.split(",");

			for (String scope : scopes) {
				if (DataPermissionUtil.isSuperviseScope(scope)) {
					result += scope + ",";
				}
			}
		}

		return result;
	}
}