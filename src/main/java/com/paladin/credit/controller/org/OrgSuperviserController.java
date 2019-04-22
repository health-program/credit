package com.paladin.credit.controller.org;

import com.paladin.credit.controller.org.dto.OrgSuperviserExportCondition;
import com.paladin.credit.model.org.OrgSuperviser;
import com.paladin.credit.service.org.OrgSuperviserService;
import com.paladin.credit.service.org.dto.OrgSuperviserQuery;
import com.paladin.credit.service.org.dto.OrgSuperviserDTO;
import com.paladin.credit.service.org.vo.OrgSuperviserVO;

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
import java.util.Map;

import javax.validation.Valid;

@Controller
@RequestMapping("/credit/org/superviser")
public class OrgSuperviserController extends ControllerSupport {

	@Autowired
	private OrgSuperviserService orgSuperviserService;

	@GetMapping("/index")
	@QueryInputMethod(queryClass = OrgSuperviserQuery.class)
	public String index() {
		return "/credit/org/org_superviser_index";
	}

	@RequestMapping(value = "/find/page", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	@QueryOutputMethod(queryClass = OrgSuperviserQuery.class, paramIndex = 0)
	public Object findPage(OrgSuperviserQuery query) {
		return CommonResponse.getSuccessResponse(orgSuperviserService.searchPage(query).convert(OrgSuperviserVO.class));
	}

	@GetMapping("/get")
	@ResponseBody
	public Object getDetail(@RequestParam String id, Model model) {
		return CommonResponse.getSuccessResponse(beanCopy(orgSuperviserService.get(id), new OrgSuperviserVO()));
	}

	@GetMapping("/add")
	public String addInput() {
		return "/credit/org/org_superviser_add";
	}

	@GetMapping("/detail")
	public String detailInput(@RequestParam String id, Model model) {
		model.addAttribute("id", id);
		return "/credit/org/org_superviser_detail";
	}

	@PostMapping("/save")
	@ResponseBody
	public Object save(@Valid OrgSuperviserDTO orgSuperviserDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return validErrorHandler(bindingResult);
		}
		String id = UUIDUtil.createUUID();
		orgSuperviserDTO.setId(id);
		if (orgSuperviserService.saveAdmin(orgSuperviserDTO)) {
			return CommonResponse.getSuccessResponse(beanCopy(orgSuperviserService.get(id), new OrgSuperviserVO()));
		}
		return CommonResponse.getFailResponse();
	}

	@PostMapping("/update")
	@ResponseBody
	public Object update(@Valid OrgSuperviserDTO orgSuperviserDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return validErrorHandler(bindingResult);
		}
		String id = orgSuperviserDTO.getId();
		if (orgSuperviserService.updateAdmin(orgSuperviserDTO)) {
			return CommonResponse.getSuccessResponse(beanCopy(orgSuperviserService.get(id), new OrgSuperviserVO()));
		}
		return CommonResponse.getFailResponse();
	}

	@RequestMapping(value = "/delete", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Object delete(@RequestParam String id) {
		return CommonResponse.getResponse(orgSuperviserService.removeByPrimaryKey(id));
	}

	private static Map<String, ValueFormator> excelValueFormatMap;

	static {

		excelValueFormatMap = new HashMap<>();	
		excelValueFormatMap.put(OrgSuperviser.COLUMN_FIELD_ROLE, new ValueFormator() {
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
	public Object export(@RequestBody OrgSuperviserExportCondition condition) {
		if (condition == null) {
			return CommonResponse.getFailResponse("导出失败：请求参数异常");
		}
		condition.setExcelValueFormat(excelValueFormatMap);
		condition.sortCellIndex();
		OrgSuperviserQuery query = condition.getQuery();
		try {
			if (query != null) {
				if (condition.isExportAll()) {
					return CommonResponse.getSuccessResponse("success", ExportUtil.export(condition, orgSuperviserService.searchAll(query), OrgSuperviser.class));
				} else if (condition.isExportPage()) {
					return CommonResponse.getSuccessResponse("success",
							ExportUtil.export(condition, orgSuperviserService.searchPage(query).getData(), OrgSuperviser.class));
				}
			}
			return CommonResponse.getFailResponse("导出数据失败：请求参数错误");
		} catch (IOException | ExcelWriteException e) {
			return CommonResponse.getFailResponse("导出数据失败：" + e.getMessage());
		}
	}
}