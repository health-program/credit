package com.paladin.credit.controller.org;

import com.paladin.common.core.export.ExportUtil;
import com.paladin.credit.controller.org.dto.OrgPersonnelExportCondition;
import com.paladin.credit.model.org.OrgAgency;
import com.paladin.credit.model.org.OrgPersonnel;
import com.paladin.credit.service.org.OrgAgencyService;
import com.paladin.credit.service.org.OrgPersonnelService;
import com.paladin.credit.service.org.dto.OrgPersonnelDTO;
import com.paladin.credit.service.org.dto.OrgPersonnelQuery;
import com.paladin.credit.service.org.vo.OrgPersonnelVO;
import com.paladin.framework.core.ControllerSupport;
import com.paladin.framework.excel.write.ExcelWriteException;
import com.paladin.framework.utils.uuid.UUIDUtil;
import com.paladin.framework.web.response.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/credit/org/personnel")
public class OrgPersonnelController extends ControllerSupport {

	@Autowired
	private OrgPersonnelService orgPersonnelService;
	@Autowired
	private OrgAgencyService orgAgencyService;


	@GetMapping("/index")
	public String index() {
		return "/credit/org/org_personnel_index";
	}

	@RequestMapping(value = "/find/page", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Object findPage(OrgPersonnelQuery query) {
		return CommonResponse.getSuccessResponse(orgPersonnelService.searchPeoplePage(query));
	}

	@GetMapping("/get")
	@ResponseBody
	public Object getDetail(@RequestParam String id, Model model) {
		return CommonResponse.getSuccessResponse(beanCopy(orgPersonnelService.get(id), new OrgPersonnelVO()));
	}

	@GetMapping("/add")
	public String addInput() {
		return "/credit/org/org_personnel_add";
	}

	@GetMapping("/detail")
	public String detailInput(@RequestParam String id, Model model) {
		model.addAttribute("id", id);
		return "/credit/org/org_personnel_detail";
	}

	@PostMapping("/save")
	@ResponseBody
	public Object save(@Valid OrgPersonnelDTO orgPersonnelDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return validErrorHandler(bindingResult);
		}
		String agencyId = orgPersonnelDTO.getAgencyId();
		OrgAgency orgAgency = orgAgencyService.get(agencyId);
		String licenseNo = orgAgency.getLicenseNo();
		String name = orgAgency.getName();
		if (licenseNo != null &&  licenseNo.length() > 0) {
			orgPersonnelDTO.setAgencyId(licenseNo);
		}
		if (name != null &&  name.length() > 0) {
			orgPersonnelDTO.setAgencyName(name);
		}
		OrgPersonnel model = beanCopy(orgPersonnelDTO, new OrgPersonnel());
		String id = UUIDUtil.createUUID();
		model.setId(id);
		if (orgPersonnelService.save(model) > 0) {
			return CommonResponse.getSuccessResponse(beanCopy(orgPersonnelService.get(id), new OrgPersonnelVO()));
		}
		return CommonResponse.getFailResponse();
	}

	@PostMapping("/update")
	@ResponseBody
	public Object update(@Valid OrgPersonnelDTO orgPersonnelDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return validErrorHandler(bindingResult);
		}
		String agencyId = orgPersonnelDTO.getAgencyId();
		OrgAgency orgAgency = orgAgencyService.get(agencyId);
		String licenseNo = orgAgency.getLicenseNo();
		String name = orgAgency.getName();
		if (licenseNo != null &&  licenseNo.length() > 0) {
			orgPersonnelDTO.setAgencyId(licenseNo);
		}
		if (name != null &&  name.length() > 0) {
			orgPersonnelDTO.setAgencyName(name);
		}
		String id = orgPersonnelDTO.getId();
		OrgPersonnel model = beanCopy(orgPersonnelDTO, orgPersonnelService.get(id));
		if (orgPersonnelService.update(model) > 0) {
			return CommonResponse.getSuccessResponse(beanCopy(orgPersonnelService.get(id), new OrgPersonnelVO()));
		}
		return CommonResponse.getFailResponse();
	}

	@RequestMapping(value = "/delete", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Object delete(@RequestParam String id) {
		return CommonResponse.getResponse(orgPersonnelService.removeByPrimaryKey(id));
	}

	@PostMapping(value = "/export")
	@ResponseBody
	public Object export(@RequestBody OrgPersonnelExportCondition condition) {
		if (condition == null) {
			return CommonResponse.getFailResponse("导出失败：请求参数异常");
		}
		condition.sortCellIndex();
		OrgPersonnelQuery query = condition.getQuery();
		try {
			if (query != null) {
				if (condition.isExportAll()) {
					return CommonResponse.getSuccessResponse("success", ExportUtil.export(condition, orgPersonnelService.searchAll(query), OrgPersonnel.class));
				} else if (condition.isExportPage()) {
					return CommonResponse.getSuccessResponse("success",
							ExportUtil.export(condition, orgPersonnelService.searchPage(query).getData(), OrgPersonnel.class));
				}
			}
			return CommonResponse.getFailResponse("导出数据失败：请求参数错误");
		} catch (IOException | ExcelWriteException e) {
			return CommonResponse.getFailResponse("导出数据失败：" + e.getMessage());
		}
	}
}