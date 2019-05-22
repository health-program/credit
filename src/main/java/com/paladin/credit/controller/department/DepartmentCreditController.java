package com.paladin.credit.controller.department;

import com.paladin.common.core.export.ExportUtil;
import com.paladin.credit.controller.department.dto.DepartmentCreditExportCondition;
import com.paladin.credit.model.department.DepartmentCredit;
import com.paladin.credit.service.department.DepartmentCreditService;
import com.paladin.credit.service.department.dto.DepartmentCreditDTO;
import com.paladin.credit.service.department.dto.DepartmentCreditQuery;
import com.paladin.credit.service.department.vo.DepartmentCreditVO;
import com.paladin.framework.core.ControllerSupport;
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

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/credit/department/credit")
public class DepartmentCreditController extends ControllerSupport {

    @Autowired
    private DepartmentCreditService departmentCreditService;

    @GetMapping("/index")
    @QueryInputMethod(queryClass = DepartmentCreditQuery.class)
    public String index() {
        return "/credit/department/department_credit_index";
    }

    @RequestMapping(value = "/find/page", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    @QueryOutputMethod(queryClass = DepartmentCreditQuery.class, paramIndex = 0)
    public Object findPage(DepartmentCreditQuery query) {
        return CommonResponse.getSuccessResponse(departmentCreditService.searchPage(query));
    }
    
    @GetMapping("/get")
    @ResponseBody
    public Object getDetail(@RequestParam String id, Model model) {   	
        return CommonResponse.getSuccessResponse(beanCopy(departmentCreditService.get(id), new DepartmentCreditVO()));
    }
    
    @GetMapping("/add")
    public String addInput() {
        return "/credit/department/department_credit_add";
    }

    @GetMapping("/detail")
    public String detailInput(@RequestParam String id, Model model) {
    	model.addAttribute("id", id);
        return "/credit/department/department_credit_detail";
    }
    
    @PostMapping("/save")
	@ResponseBody
    public Object save(@Valid DepartmentCreditDTO departmentCreditDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return validErrorHandler(bindingResult);
		}
        DepartmentCredit model = beanCopy(departmentCreditDTO, new DepartmentCredit());
		String id = UUIDUtil.createUUID();
		model.setId(id);
		if (departmentCreditService.save(model) > 0) {
			return CommonResponse.getSuccessResponse(beanCopy(departmentCreditService.get(id), new DepartmentCreditVO()));
		}
		return CommonResponse.getFailResponse();
	}

    @PostMapping("/update")
	@ResponseBody
    public Object update(@Valid DepartmentCreditDTO departmentCreditDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return validErrorHandler(bindingResult);
		}
		String id = departmentCreditDTO.getId();
		DepartmentCredit model = beanCopy(departmentCreditDTO, departmentCreditService.get(id));
		if (departmentCreditService.update(model) > 0) {
			return CommonResponse.getSuccessResponse(beanCopy(departmentCreditService.get(id), new DepartmentCreditVO()));
		}
		return CommonResponse.getFailResponse();
	}

    @RequestMapping(value = "/delete", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Object delete(@RequestParam String id) {
        return CommonResponse.getResponse(departmentCreditService.removeByPrimaryKey(id));
    }
    
    @PostMapping(value = "/export")
	@ResponseBody
	public Object export(@RequestBody DepartmentCreditExportCondition condition) {
		if (condition == null) {
			return CommonResponse.getFailResponse("导出失败：请求参数异常");
		}
		condition.sortCellIndex();
		DepartmentCreditQuery query = condition.getQuery();
		try {
			if (query != null) {
				if (condition.isExportAll()) {
					return CommonResponse.getSuccessResponse("success", ExportUtil.export(condition, departmentCreditService.searchAll(query), DepartmentCredit.class));
				} else if (condition.isExportPage()) {
					return CommonResponse.getSuccessResponse("success",
							ExportUtil.export(condition, departmentCreditService.searchPage(query).getData(), DepartmentCredit.class));
				}
			}
			return CommonResponse.getFailResponse("导出数据失败：请求参数错误");
		} catch (IOException | ExcelWriteException e) {
			return CommonResponse.getFailResponse("导出数据失败：" + e.getMessage());
		}
	}
}