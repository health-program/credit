package com.paladin.credit.controller.org;

import com.google.common.base.Strings;
import com.paladin.common.core.export.ExportUtil;
import com.paladin.common.model.syst.SysAttachment;
import com.paladin.common.service.syst.SysAttachmentService;
import com.paladin.credit.controller.org.dto.OrgAgencyExportCondition;
import com.paladin.credit.core.CreditAgencyContainer;
import com.paladin.credit.model.org.OrgAgency;
import com.paladin.credit.service.org.OrgAgencyService;
import com.paladin.credit.service.org.dto.OrgAgencyDTO;
import com.paladin.credit.service.org.dto.OrgAgencyQuery;
import com.paladin.credit.service.org.vo.OrgAgencyVO;
import com.paladin.framework.core.ControllerSupport;
import com.paladin.framework.core.exception.BusinessException;
import com.paladin.framework.core.query.QueryInputMethod;
import com.paladin.framework.core.query.QueryOutputMethod;
import com.paladin.framework.excel.write.ExcelWriteException;
import com.paladin.framework.utils.uuid.UUIDUtil;
import com.paladin.framework.web.response.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/credit/org/agency")
public class OrgAgencyController extends ControllerSupport {

	@Autowired
	private OrgAgencyService orgAgencyService;
	@Autowired
	private CreditAgencyContainer creditAgencyContainer;

	@Autowired
	private SysAttachmentService sysAttachmentService;

	@GetMapping("/index")
	@QueryInputMethod(queryClass = OrgAgencyQuery.class)
	public String index() {
		return "/credit/org/org_agency_index";
	}

	@RequestMapping(value = "/find/page", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	@QueryOutputMethod(queryClass = OrgAgencyQuery.class, paramIndex = 0)
	public Object findPage(OrgAgencyQuery query) {
		return CommonResponse.getSuccessResponse(orgAgencyService.searchPage(query));
	}

	@GetMapping("/get")
	@ResponseBody
	public Object getDetail(@RequestParam String id, Model model) {
		return CommonResponse.getSuccessResponse(beanCopy(orgAgencyService.get(id), new OrgAgencyVO()));
	}

	@GetMapping("/add")
	public String addInput() {
		return "/credit/org/org_agency_add";
	}

	@GetMapping("/detail")
	public String detailInput(@RequestParam String id, @RequestParam(required = false) Boolean isTemplatePage, Model model) {
		model.addAttribute("id", id);
		if (isTemplatePage) {
			model.addAttribute("callBackUrl", "/credit/template/item/agency/index");
			model.addAttribute("isTemplatePage", 1);
		} else {
			model.addAttribute("callBackUrl", "/credit/org/agency/index");
		}
		return "/credit/org/org_agency_detail";
	}

	@PostMapping("/save")
	@ResponseBody
	public Object save(@Valid OrgAgencyDTO orgAgencyDTO, @RequestParam(required = false) MultipartFile[] licenseFile ,BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return validErrorHandler(bindingResult);
		}
		List<SysAttachment> license = sysAttachmentService.checkOrCreateAttachment(orgAgencyDTO.getLicense(), licenseFile);
		if (license != null && license.size() > 4) {
			return CommonResponse.getErrorResponse("执业许可证照片数量不能超过4张");
		}
		orgAgencyDTO.setLicense(sysAttachmentService.splicingAttachmentId(license));
		OrgAgency model = beanCopy(orgAgencyDTO, new OrgAgency());
		String id = UUIDUtil.createUUID();
		model.setId(id);
		if (orgAgencyService.save(model) > 0) {
			creditAgencyContainer.updateData();
			return CommonResponse.getSuccessResponse(beanCopy(orgAgencyService.get(id), new OrgAgencyVO()));
		}
		return CommonResponse.getFailResponse();
	}

	@PostMapping("/update")
	@ResponseBody
	public Object update(@Valid OrgAgencyDTO orgAgencyDTO, BindingResult bindingResult ) {
		if (bindingResult.hasErrors()) {
			return validErrorHandler(bindingResult);
		}
		String agencyCoordinate = orgAgencyDTO.getAgencyCoordinate();
		if (Strings.isNullOrEmpty(agencyCoordinate)) {
			throw new BusinessException("机构坐标不能为空");
		}
		String id = orgAgencyDTO.getId();
		OrgAgency agency = orgAgencyService.get(id);
		agency.setAgencyCoordinate(agencyCoordinate);
		if (orgAgencyService.update(agency) > 0) {
			creditAgencyContainer.updateData();
			return CommonResponse.getSuccessResponse(beanCopy(orgAgencyService.get(id), new OrgAgencyVO()));
		}
		return CommonResponse.getFailResponse();
	}

	@RequestMapping(value = "/delete", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Object delete(@RequestParam String id) {
		int effect = orgAgencyService.removeByPrimaryKey(id);
		if (effect > 0) {
			creditAgencyContainer.updateData();
		}
		return CommonResponse.getResponse(effect);
	}

	@PostMapping(value = "/export")
	@ResponseBody
	public Object export(@RequestBody OrgAgencyExportCondition condition) {
		if (condition == null) {
			return CommonResponse.getFailResponse("导出失败：请求参数异常");
		}
		condition.sortCellIndex();
		OrgAgencyQuery query = condition.getQuery();
		try {
			if (query != null) {
				if (condition.isExportAll()) {
					return CommonResponse.getSuccessResponse("success", ExportUtil.export(condition, orgAgencyService.searchAll(query), OrgAgency.class));
				} else if (condition.isExportPage()) {
					return CommonResponse.getSuccessResponse("success",
							ExportUtil.export(condition, orgAgencyService.searchPage(query).getData(), OrgAgency.class));
				}
			}
			return CommonResponse.getFailResponse("导出数据失败：请求参数错误");
		} catch (IOException | ExcelWriteException e) {
			return CommonResponse.getFailResponse("导出数据失败：" + e.getMessage());
		}
	}
}