package com.paladin.credit.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.paladin.credit.model.org.OrgAgency;
import com.paladin.credit.service.org.OrgAgencyService;
import com.paladin.framework.core.VersionContainer;
import com.paladin.framework.core.VersionContainerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CreditAgencyContainer implements VersionContainer {

	@Autowired
	private OrgAgencyService agencyService;

	private static Map<String, Agency> agencyMap;
	private static List<Agency> agencyList;

	private void initAgency() {

		List<OrgAgency> agencies = agencyService.findAll();
		List<Agency> agencyList = new ArrayList<>(agencies.size());
		Map<String, Agency> agencyMap = new HashMap<>();
		for (OrgAgency agency : agencies) {
			Agency a = new Agency(agency);
			agencyList.add(a);
			agencyMap.put(a.id, a);
		}

		CreditAgencyContainer.agencyMap = Collections.unmodifiableMap(agencyMap);
		CreditAgencyContainer.agencyList = Collections.unmodifiableList(agencyList);
	}

	public static class Agency {

		private String id;
		private String name;

		@JsonIgnore
		private OrgAgency source;

		private Agency(OrgAgency agency) {
			this.id = agency.getId();
			this.name = agency.getName();
			this.source = agency;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public OrgAgency getSource() {
			return source;
		}

		public void setSource(OrgAgency source) {
			this.source = source;
		}

	}

	public static List<Agency> getAgencys() {
		return agencyList;
	}

	public static List<Agency> getAgencies(String[] agencyIds) {
		if (agencyIds == null || agencyIds.length == 0) {
			return null;
		}

		List<Agency> agencies = new ArrayList<>(agencyIds.length);
		for (String id : agencyIds) {
			Agency a = agencyMap.get(id);
			if (a != null) {
				agencies.add(a);
			}
		}

		return agencies;
	}

	public static List<String> getAgencyNames(String[] agencyIds) {
		if (agencyIds == null || agencyIds.length == 0) {
			return null;
		}

		List<String> names = new ArrayList<>(agencyIds.length);
		for (String id : agencyIds) {
			Agency a = agencyMap.get(id);
			if (a != null) {
				names.add(a.name);
			}
		}

		return names;
	}

	public static String getAgencyName(String agencyId) {
		if (agencyId == null || agencyId.length() == 0) {
			return null;
		}

		Agency a = agencyMap.get(agencyId);
		if (a != null) {
			return a.name;
		}

		return null;
	}

	@Override
	public String getId() {
		return "agency_container";
	}

	@Override
	public boolean versionChangedHandle(long version) {
		initAgency();
		return true;
	}

	public void updateData() {
		VersionContainerManager.versionChanged(getId());
	}
}
