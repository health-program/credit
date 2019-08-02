package com.paladin.credit.controller.department;

import com.paladin.common.core.export.ExportUtil;
import com.paladin.credit.controller.department.dto.DepartmentAdministrativeLicenseExportCondition;
import com.paladin.credit.model.department.DepartmentAdministrativeLicense;
import com.paladin.credit.service.department.DepartmentAdministrativeLicenseService;
import com.paladin.credit.service.department.dto.DepartmentAdministrativeLicenseDTO;
import com.paladin.credit.service.department.dto.DepartmentAdministrativeLicenseOrgUploadDTO;
import com.paladin.credit.service.department.dto.DepartmentAdministrativeLicensePeopleUploadDTO;
import com.paladin.credit.service.department.dto.DepartmentAdministrativeLicenseQuery;
import com.paladin.credit.service.department.vo.DepartmentAdministrativeLicenseVO;
import com.paladin.framework.common.BaseModel;
import com.paladin.framework.common.ExcelImportResult;
import com.paladin.framework.core.ControllerSupport;
import com.paladin.framework.core.exception.BusinessException;
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
@RequestMapping("/credit/department/administrative/license")
public class DepartmentAdministrativeLicenseController extends ControllerSupport {

    @Autowired
    private DepartmentAdministrativeLicenseService departmentAdministrativeLicenseService;

    @GetMapping("/index/{type}")
    @QueryInputMethod(queryClass = DepartmentAdministrativeLicenseQuery.class)
    public String index(@PathVariable String type,Model model) {
		model.addAttribute("type",type);
        return "/credit/department/department_administrative_license_index";
    }

    @RequestMapping(value = "/find/page", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    @QueryOutputMethod(queryClass = DepartmentAdministrativeLicenseQuery.class, paramIndex = 0)
    public Object findPage(DepartmentAdministrativeLicenseQuery query) {
        return CommonResponse.getSuccessResponse(departmentAdministrativeLicenseService.searchPage(query));
    }
    
    @GetMapping("/get")
    @ResponseBody
    public Object getDetail(@RequestParam String id, Model model) {   	
        return CommonResponse.getSuccessResponse(beanCopy(departmentAdministrativeLicenseService.get(id), new DepartmentAdministrativeLicenseVO()));
    }

    @GetMapping("/detail")
    public String detailInput(@RequestParam String id, @RequestParam String type,Model model) {
    	model.addAttribute("id", id);
    	model.addAttribute("type", type);
        return "/credit/department/department_administrative_license_detail";
    }

    @PostMapping("/update")
	@ResponseBody
    public Object update(@Valid DepartmentAdministrativeLicenseDTO departmentAdministrativeLicenseDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return validErrorHandler(bindingResult);
		}
		String id = departmentAdministrativeLicenseDTO.getId();
		DepartmentAdministrativeLicense model = beanCopy(departmentAdministrativeLicenseDTO, departmentAdministrativeLicenseService.get(id));
		if (departmentAdministrativeLicenseService.update(model) > 0) {
			return CommonResponse.getSuccessResponse(beanCopy(departmentAdministrativeLicenseService.get(id), new DepartmentAdministrativeLicenseVO()));
		}
		return CommonResponse.getFailResponse();
	}

	@RequestMapping(value = "/report", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Object report(@RequestParam String id, @RequestParam String type) {
		if (Integer.valueOf(type) == BaseModel.TARGET_TYPE_ORG) {
			return CommonResponse.getResponse(departmentAdministrativeLicenseService.reportOrgPunishment(departmentAdministrativeLicenseService.get(id)));
		}else if (Integer.valueOf(type) == BaseModel.TARGET_TYPE_PEOPELE){
			return CommonResponse.getResponse(departmentAdministrativeLicenseService.reportPeoplePunishment(departmentAdministrativeLicenseService.get(id)));
		}
		return CommonResponse.getFailResponse("上报行政许可信息失败");
	}


	@RequestMapping(value = "/cancel", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Object cancel(@RequestParam String id, @RequestParam String type) {
		return CommonResponse.getResponse(departmentAdministrativeLicenseService.cancelLicense(id,type));
	}

    @RequestMapping(value = "/delete", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Object delete(@RequestParam String id) {
        return CommonResponse.getResponse(departmentAdministrativeLicenseService.removeByPrimaryKey(id));
    }
    
    @PostMapping(value = "/export")
	@ResponseBody
	public Object export(@RequestBody DepartmentAdministrativeLicenseExportCondition condition) {
		if (condition == null) {
			return CommonResponse.getFailResponse("导出失败：请求参数异常");
		}
		condition.sortCellIndex();
		DepartmentAdministrativeLicenseQuery query = condition.getQuery();
		try {
			if (query != null) {
				if (condition.isExportAll()) {
					return CommonResponse.getSuccessResponse("success", ExportUtil.export(condition, departmentAdministrativeLicenseService.searchAll(query), DepartmentAdministrativeLicense.class));
				} else if (condition.isExportPage()) {
					return CommonResponse.getSuccessResponse("success",
							ExportUtil.export(condition, departmentAdministrativeLicenseService.searchPage(query).getData(), DepartmentAdministrativeLicense.class));
				}
			}
			return CommonResponse.getFailResponse("导出数据失败：请求参数错误");
		} catch (IOException | ExcelWriteException e) {
			return CommonResponse.getFailResponse("导出数据失败：" + e.getMessage());
		}
	}

	private static final List<ReadColumn> DepartmentAdministrativeLicensePeopleUploadColumns = DefaultReadColumn.createReadColumn(DepartmentAdministrativeLicensePeopleUploadDTO.class);
	private static final List<ReadColumn> DepartmentAdministrativeLicenseOrgUploadDTOColumns = DefaultReadColumn.createReadColumn(DepartmentAdministrativeLicenseOrgUploadDTO.class);

	@RequestMapping("/upload/people")
	@ResponseBody
	public Object uploadPeople(@RequestParam(value = "file",required = false) MultipartFile file) {
		try {
			if (file == null) {
				throw new BusinessException("请选择文件");
			}
			@SuppressWarnings("resource")
			XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
			ExcelReader<DepartmentAdministrativeLicensePeopleUploadDTO> reader = new ExcelReader<>(DepartmentAdministrativeLicensePeopleUploadDTO.class, DepartmentAdministrativeLicensePeopleUploadColumns,
					new DefaultSheet(workbook.getSheetAt(0)), 1);

			int maxImportCount = 1000;

			List<ExcelImportResult.ExcelImportError> importErrors = new ArrayList<>();
			int index = 1;
			while (reader.hasNext()) {
				try {
					index++;
					DepartmentAdministrativeLicensePeopleUploadDTO dto = reader.readRow();
					String errorMsg;
					if (dto != null) {
						errorMsg = departmentAdministrativeLicenseService.importPeople(dto);
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


	@RequestMapping("/upload/org")
	@ResponseBody
	public Object uploadOrg(@RequestParam(value = "file",required = false) MultipartFile file) {
		try {
			if (file == null) {
				throw new BusinessException("请选择文件");
			}
			@SuppressWarnings("resource")
			XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
			ExcelReader<DepartmentAdministrativeLicenseOrgUploadDTO> reader = new ExcelReader<>(DepartmentAdministrativeLicenseOrgUploadDTO.class, DepartmentAdministrativeLicenseOrgUploadDTOColumns,
					new DefaultSheet(workbook.getSheetAt(0)), 1);

			int maxImportCount = 1000;

			List<ExcelImportResult.ExcelImportError> importErrors = new ArrayList<>();
			int index = 1;
			while (reader.hasNext()) {
				try {
					index++;
					DepartmentAdministrativeLicenseOrgUploadDTO dto = reader.readRow();
					String errorMsg;
					if (dto != null) {
						errorMsg = departmentAdministrativeLicenseService.importOrg(dto);
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