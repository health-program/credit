package com.paladin.credit.controller.department;

import com.paladin.common.core.export.ExportUtil;
import com.paladin.credit.controller.department.dto.DepartmentPersonCreditExportCondition;
import com.paladin.credit.model.department.DepartmentPersonCredit;
import com.paladin.credit.service.department.DepartmentPersonCreditService;
import com.paladin.credit.service.department.dto.DepartmentPersonBlackUploadDTO;
import com.paladin.credit.service.department.dto.DepartmentPersonCreditDTO;
import com.paladin.credit.service.department.dto.DepartmentPersonCreditQuery;
import com.paladin.credit.service.department.dto.DepartmentPersonRedUploadDTO;
import com.paladin.credit.service.department.vo.DepartmentPersonCreditVO;
import com.paladin.framework.common.ExcelImportResult;
import com.paladin.framework.core.ControllerSupport;
import com.paladin.framework.core.query.QueryInputMethod;
import com.paladin.framework.core.query.QueryOutputMethod;
import com.paladin.framework.excel.DefaultSheet;
import com.paladin.framework.excel.read.DefaultReadColumn;
import com.paladin.framework.excel.read.ExcelReader;
import com.paladin.framework.excel.read.ReadColumn;
import com.paladin.framework.excel.write.ExcelWriteException;
import com.paladin.framework.web.response.CommonResponse;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/credit/department/person/credit")
public class DepartmentPersonCreditController extends ControllerSupport {

    @Autowired
    private DepartmentPersonCreditService departmentPersonCreditService;

    @GetMapping("/index/{type}")
    @QueryInputMethod(queryClass = DepartmentPersonCreditQuery.class)
    public String index(@PathVariable String type,Model model) {
    	model.addAttribute("type",type);
        return "/credit/department/department_person_credit_index";
    }

    @RequestMapping(value = "/find/page", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    @QueryOutputMethod(queryClass = DepartmentPersonCreditQuery.class, paramIndex = 0)
    public Object findPage(DepartmentPersonCreditQuery query) {
        return CommonResponse.getSuccessResponse(departmentPersonCreditService.searchPage(query));
    }
    
    @GetMapping("/get")
    @ResponseBody
    public Object getDetail(@RequestParam String id, Model model) {   	
        return CommonResponse.getSuccessResponse(beanCopy(departmentPersonCreditService.get(id), new DepartmentPersonCreditVO()));
    }

    @GetMapping("/detail")
    public String detailInput(@RequestParam String id,@RequestParam String type, Model model) {
    	model.addAttribute("id", id);
    	model.addAttribute("type", type);
        return "/credit/department/department_person_credit_detail";
    }
    

    @PostMapping("/update")
	@ResponseBody
    public Object update(@Valid DepartmentPersonCreditDTO departmentPersonCreditDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return validErrorHandler(bindingResult);
		}
		String id = departmentPersonCreditDTO.getId();
		DepartmentPersonCredit model = beanCopy(departmentPersonCreditDTO, departmentPersonCreditService.get(id));
		if (departmentPersonCreditService.update(model) > 0) {
			return CommonResponse.getSuccessResponse(beanCopy(departmentPersonCreditService.get(id), new DepartmentPersonCreditVO()));
		}
		return CommonResponse.getFailResponse();
	}

    @RequestMapping(value = "/delete", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Object delete(@RequestParam String id) {
        return CommonResponse.getResponse(departmentPersonCreditService.removeByPrimaryKey(id));
    }
    
    @PostMapping(value = "/export")
	@ResponseBody
	public Object export(@RequestBody DepartmentPersonCreditExportCondition condition) {
		if (condition == null) {
			return CommonResponse.getFailResponse("导出失败：请求参数异常");
		}
		condition.sortCellIndex();
		DepartmentPersonCreditQuery query = condition.getQuery();
		try {
			if (query != null) {
				if (condition.isExportAll()) {
					return CommonResponse.getSuccessResponse("success", ExportUtil.export(condition, departmentPersonCreditService.searchAll(query), DepartmentPersonCredit.class));
				} else if (condition.isExportPage()) {
					return CommonResponse.getSuccessResponse("success",
							ExportUtil.export(condition, departmentPersonCreditService.searchPage(query).getData(), DepartmentPersonCredit.class));
				}
			}
			return CommonResponse.getFailResponse("导出数据失败：请求参数错误");
		} catch (IOException | ExcelWriteException e) {
			return CommonResponse.getFailResponse("导出数据失败：" + e.getMessage());
		}
	}

	private static final List<ReadColumn> departmentPersonRedUploadColumns = DefaultReadColumn.createReadColumn(DepartmentPersonRedUploadDTO.class);
	private static final List<ReadColumn> departmentPersonBlackUploadColumns = DefaultReadColumn.createReadColumn(DepartmentPersonBlackUploadDTO.class);

	@RequestMapping("/upload/red")
	@ResponseBody
	public Object uploadBase(@RequestParam("file") MultipartFile file) {
		try {
			@SuppressWarnings("resource")
			XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
			ExcelReader<DepartmentPersonRedUploadDTO> reader = new ExcelReader<>(DepartmentPersonRedUploadDTO.class, departmentPersonRedUploadColumns,
					new DefaultSheet(workbook.getSheetAt(0)), 1);

			int maxImportCount = 1000;

			List<ExcelImportResult.ExcelImportError> importErrors = new ArrayList<>();
			int index = 1;
			while (reader.hasNext()) {
				try {
					index++;
					DepartmentPersonRedUploadDTO dto = reader.readRow();
					String errorMsg;
					if (dto != null) {
						errorMsg = departmentPersonCreditService.importRed(dto);
					} else {
						errorMsg = "空行";
					}

					if (errorMsg != null) {
						importErrors.add(new ExcelImportResult.ExcelImportError(index, errorMsg));
					}
				} catch (Exception e) {
					e.printStackTrace();
					importErrors.add(new ExcelImportResult.ExcelImportError(index, e));
				}

				if (index > maxImportCount) {
					break;
				}
			}

			return CommonResponse.getSuccessResponse(new ExcelImportResult(index - 1, importErrors));
		} catch (IOException e) {
			return CommonResponse.getFailResponse("上传文件异常，请检查是否基于下载的模板编辑！");
		}
	}

	@RequestMapping("/upload/black")
	@ResponseBody
	public Object uploadBlack(@RequestParam("file") MultipartFile file) {
		try {
			@SuppressWarnings("resource")
			XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
			ExcelReader<DepartmentPersonBlackUploadDTO> reader = new ExcelReader<>(DepartmentPersonBlackUploadDTO.class, departmentPersonBlackUploadColumns,
					new DefaultSheet(workbook.getSheetAt(0)), 1);

			int maxImportCount = 1000;

			List<ExcelImportResult.ExcelImportError> importErrors = new ArrayList<>();
			int index = 1;
			while (reader.hasNext()) {
				try {
					index++;
					DepartmentPersonBlackUploadDTO dto = reader.readRow();
					String errorMsg;
					if (dto != null) {
						errorMsg = departmentPersonCreditService.importBlack(dto);
					} else {
						errorMsg = "空行";
					}

					if (errorMsg != null) {
						importErrors.add(new ExcelImportResult.ExcelImportError(index, errorMsg));
					}
				} catch (Exception e) {
					e.printStackTrace();
					importErrors.add(new ExcelImportResult.ExcelImportError(index, e));
				}

				if (index > maxImportCount) {
					break;
				}
			}

			return CommonResponse.getSuccessResponse(new ExcelImportResult(index - 1, importErrors));
		} catch (IOException e) {
			return CommonResponse.getFailResponse("上传文件异常，请检查是否基于下载的模板编辑！");
		}
	}
}