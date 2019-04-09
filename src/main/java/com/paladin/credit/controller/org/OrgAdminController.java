package com.paladin.credit.controller.org;

import com.paladin.credit.controller.org.dto.OrgAdminExportCondition;
import com.paladin.credit.core.CreditAgencyContainer;
import com.paladin.credit.model.org.OrgAdmin;
import com.paladin.credit.service.org.OrgAdminService;
import com.paladin.credit.service.org.dto.OrgAdminQuery;
import com.paladin.credit.service.org.dto.OrgAdminDTO;
import com.paladin.credit.service.org.vo.OrgAdminVO;

import com.paladin.common.core.export.ExportUtil;
import com.paladin.common.core.permission.PermissionContainer;
import com.paladin.common.core.permission.Role;
import com.paladin.framework.core.ControllerSupport;
import com.paladin.framework.core.query.QueryInputMethod;
import com.paladin.framework.core.query.QueryOutputMethod;
import com.paladin.framework.excel.write.ExcelWriteException;
import com.paladin.framework.excel.write.ValueFormator;
import com.paladin.framework.web.response.CommonResponse;
import com.paladin.framework.utils.uuid.UUIDUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

@Controller
@RequestMapping("/credit/org/admin")
public class OrgAdminController extends ControllerSupport {

	@Autowired
	private OrgAdminService orgAdminService;

	@GetMapping("/index")
	@QueryInputMethod(queryClass = OrgAdminQuery.class)
	public String index() {
		return "/credit/org/org_admin_index";
	}

	@RequestMapping(value = "/find/page", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	@QueryOutputMethod(queryClass = OrgAdminQuery.class, paramIndex = 0)
	public Object findPage(OrgAdminQuery query) {
		return CommonResponse.getSuccessResponse(orgAdminService.searchPage(query).convert(OrgAdminVO.class));
	}

	@GetMapping("/get")
	@ResponseBody
	public Object getDetail(@RequestParam String id, Model model) {
		return CommonResponse.getSuccessResponse(beanCopy(orgAdminService.get(id), new OrgAdminVO()));
	}

	@GetMapping("/add")
	public String addInput() {
		return "/credit/org/org_admin_add";
	}

	@GetMapping("/detail")
	public String detailInput(@RequestParam String id, Model model) {
		model.addAttribute("id", id);
		return "/credit/org/org_admin_detail";
	}

	@PostMapping("/save")
	@ResponseBody
	public Object save(@Valid OrgAdminDTO orgAdminDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return validErrorHandler(bindingResult);
		}
		String id = UUIDUtil.createUUID();
		orgAdminDTO.setId(id);
		if (orgAdminService.saveAdmin(orgAdminDTO)) {
			return CommonResponse.getSuccessResponse(beanCopy(orgAdminService.get(id), new OrgAdminVO()));
		}
		return CommonResponse.getFailResponse();
	}

	@PostMapping("/update")
	@ResponseBody
	public Object update(@Valid OrgAdminDTO orgAdminDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return validErrorHandler(bindingResult);
		}
		String id = orgAdminDTO.getId();
		if (orgAdminService.updateAdmin(orgAdminDTO)) {
			return CommonResponse.getSuccessResponse(beanCopy(orgAdminService.get(id), new OrgAdminVO()));
		}
		return CommonResponse.getFailResponse();
	}

	@RequestMapping(value = "/delete", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Object delete(@RequestParam String id) {
		return CommonResponse.getResponse(orgAdminService.removeByPrimaryKey(id));
	}

	private static Map<String, ValueFormator> excelValueFormatMap;

	static {

		excelValueFormatMap = new HashMap<>();
		excelValueFormatMap.put(OrgAdmin.COLUMN_FIELD_MANAGE_AGENCY, new ValueFormator() {
			@Override
			public String format(Object obj) {
				if (obj != null) {
					String manageAgency = (String) obj;
					if (manageAgency.length() > 0) {
						List<String> names = CreditAgencyContainer.getAgencyNames(manageAgency.split(","));
						if (names != null && names.size() > 0) {
							StringBuilder sb = new StringBuilder();
							for (String name : names) {
								sb.append(name).append("，");
							}

							sb.deleteCharAt(sb.length() - 1);
							return sb.toString();
						}
					}
				}
				return "";
			}
		});

		
		excelValueFormatMap.put(OrgAdmin.COLUMN_FIELD_ROLE, new ValueFormator() {
			@Override
			public String format(Object obj) {
				if (obj != null) {
					String roleString = (String) obj;
					if (roleString.length() > 0) {
						PermissionContainer container = PermissionContainer.getInstance();
						String[] roleIds = roleString.split(",");

						String result = "";
						for(String roleId: roleIds) {
							Role role = container.getRole(roleId);
							if(role != null) {
								result+= role.getRoleName() +"，";
							}
						}
						
						if(result.length() >0) {
							result = result.substring(0, result.length() - 1);
						}
						
						return result;
					}
				}
				return "";
			}
		});
	}

	@PostMapping(value = "/export")
	@ResponseBody
	public Object export(@RequestBody OrgAdminExportCondition condition) {
		if (condition == null) {
			return CommonResponse.getFailResponse("导出失败：请求参数异常");
		}
		condition.setExcelValueFormat(excelValueFormatMap);
		condition.sortCellIndex();
		OrgAdminQuery query = condition.getQuery();
		try {
			if (query != null) {
				if (condition.isExportAll()) {
					return CommonResponse.getSuccessResponse("success", ExportUtil.export(condition, orgAdminService.searchAll(query), OrgAdmin.class));
				} else if (condition.isExportPage()) {
					return CommonResponse.getSuccessResponse("success",
							ExportUtil.export(condition, orgAdminService.searchPage(query).getData(), OrgAdmin.class));
				}
			}
			return CommonResponse.getFailResponse("导出数据失败：请求参数错误");
		} catch (IOException | ExcelWriteException e) {
			return CommonResponse.getFailResponse("导出数据失败：" + e.getMessage());
		}
	}
}