package com.paladin.credit.core;

import java.util.ArrayList;
import java.util.List;

import com.paladin.common.core.ConstantsContainer;
import com.paladin.credit.core.CreditAgencyContainer.Agency;

public class DataPermissionUtil {

	/**
	 * 获取可管理的机构
	 * 
	 * @return
	 */
	public static List<Agency> getManageAgency() {
		CreditUserSession userSession = CreditUserSession.getCurrentUserSession();
		int roleLevel = userSession.getRoleLevel();
		if (roleLevel > CreditUserSession.ROLE_LEVEL_AGENCY) {
			return CreditAgencyContainer.getAgencys();
		} else if (roleLevel == CreditUserSession.ROLE_LEVEL_AGENCY) {
			String[] agencyIds = userSession.getAgencyIds();
			return CreditAgencyContainer.getAgencies(agencyIds);
		} else {
			return new ArrayList<>();
		}
	}

	/**
	 * 判断是否拥有管理机构的权利
	 * 
	 * @param agencyIds
	 * @return
	 */
	public static boolean ownManageAgency(String... agencyIds) {
		CreditUserSession userSession = CreditUserSession.getCurrentUserSession();
		int roleLevel = userSession.getRoleLevel();
		if (roleLevel > CreditUserSession.ROLE_LEVEL_AGENCY) {
			return true;
		} else if (roleLevel == CreditUserSession.ROLE_LEVEL_AGENCY) {
			if (agencyIds == null || agencyIds.length == 0) {
				return true;
			}
			String[] aids = userSession.getAgencyIds();
			for (String agencyId : agencyIds) {
				boolean b = false;
				for (String aid : aids) {
					if (aid.equals(agencyId)) {
						b = true;
					}
				}
				if (!b) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	private static final String TYPE_SUPERVISE_SCOPE = "supervise-scope";

	/**
	 * 判断是否有这个监察范围
	 * @param scopeCode
	 * @return
	 */
	public static boolean isSuperviseScope(String scopeCode) {
		return ConstantsContainer.getTypeValue(TYPE_SUPERVISE_SCOPE, scopeCode) != null;
	}

}
