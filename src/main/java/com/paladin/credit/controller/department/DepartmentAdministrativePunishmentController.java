package com.paladin.credit.controller.department;

import com.paladin.common.core.export.ExportUtil;
import com.paladin.credit.controller.department.dto.DepartmentAdministrativePunishmentExportCondition;
import com.paladin.credit.model.department.DepartmentAdministrativePunishment;
import com.paladin.credit.service.department.DepartmentAdministrativePunishmentService;
import com.paladin.credit.service.department.dto.DepartmentAdministrativePunishmentDTO;
import com.paladin.credit.service.department.dto.DepartmentAdministrativePunishmentOrgUploadDTO;
import com.paladin.credit.service.department.dto.DepartmentAdministrativePunishmentPeopleUploadDTO;
import com.paladin.credit.service.department.dto.DepartmentAdministrativePunishmentQuery;
import com.paladin.credit.service.department.vo.DepartmentAdministrativePunishmentVO;
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
@RequestMapping("/credit/department/administrative/punishment")
public class DepartmentAdministrativePunishmentController extends ControllerSupport {

    @Autowired
    private DepartmentAdministrativePunishmentService departmentAdministrativePunishmentService;

    @GetMapping("/index/{type}")
    @QueryInputMethod(queryClass = DepartmentAdministrativePunishmentQuery.class)
    public String index(@PathVariable String type,Model model) {
		model.addAttribute("type",type);
        return "/credit/department/department_administrative_punishment_index";
    }

    @RequestMapping(value = "/find/page", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    @QueryOutputMethod(queryClass = DepartmentAdministrativePunishmentQuery.class, paramIndex = 0)
    public Object findPage(DepartmentAdministrativePunishmentQuery query) {
        return CommonResponse.getSuccessResponse(departmentAdministrativePunishmentService.searchPage(query));
    }
    
    @GetMapping("/get")
    @ResponseBody
    public Object getDetail(@RequestParam String id, Model model) {
        return CommonResponse.getSuccessResponse(beanCopy(departmentAdministrativePunishmentService.get(id), new DepartmentAdministrativePunishmentVO()));
    }
    

    @GetMapping("/detail")
    public String detailInput(@RequestParam String id,@RequestParam String type, Model model) {
    	model.addAttribute("id", id);
		model.addAttribute("type", type);
        return "/credit/department/department_administrative_punishment_detail";
    }
    

    @PostMapping("/update")
	@ResponseBody
    public Object update(@Valid DepartmentAdministrativePunishmentDTO departmentAdministrativePunishmentDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return validErrorHandler(bindingResult);
		}
		String id = departmentAdministrativePunishmentDTO.getId();
		DepartmentAdministrativePunishment model = beanCopy(departmentAdministrativePunishmentDTO, departmentAdministrativePunishmentService.get(id));
		if (departmentAdministrativePunishmentService.update(model) > 0) {
			return CommonResponse.getSuccessResponse(beanCopy(departmentAdministrativePunishmentService.get(id), new DepartmentAdministrativePunishmentVO()));
		}
		return CommonResponse.getFailResponse();
	}

    @RequestMapping(value = "/delete", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Object delete(@RequestParam String id) {
        return CommonResponse.getResponse(departmentAdministrativePunishmentService.removeByPrimaryKey(id));
    }

	@RequestMapping(value = "/report", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Object report(@RequestParam String id, @RequestParam String type) {
		if (Integer.valueOf(type) == BaseModel.TARGET_TYPE_ORG) {
			return CommonResponse.getResponse(departmentAdministrativePunishmentService.reportOrgPunishment(departmentAdministrativePunishmentService.get(id)));
		}else if (Integer.valueOf(type) == BaseModel.TARGET_TYPE_PEOPELE){
			return CommonResponse.getResponse(departmentAdministrativePunishmentService.reportPeoplePunishment(departmentAdministrativePunishmentService.get(id)));
		}
		return CommonResponse.getFailResponse("上报行政处罚信息失败");
	}

	@RequestMapping(value = "/cancel", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Object cancel(@RequestParam String id, @RequestParam String type) {
		return CommonResponse.getResponse(departmentAdministrativePunishmentService.cancelPunishment(id,type));
	}
    
    @PostMapping(value = "/export")
	@ResponseBody
	public Object export(@RequestBody DepartmentAdministrativePunishmentExportCondition condition) {
		if (condition == null) {
			return CommonResponse.getFailResponse("导出失败：请求参数异常");
		}
		condition.sortCellIndex();
		DepartmentAdministrativePunishmentQuery query = condition.getQuery();
		try {
			if (query != null) {
				if (condition.isExportAll()) {
					return CommonResponse.getSuccessResponse("success", ExportUtil.export(condition, departmentAdministrativePunishmentService.searchAll(query), DepartmentAdministrativePunishment.class));
				} else if (condition.isExportPage()) {
					return CommonResponse.getSuccessResponse("success",
							ExportUtil.export(condition, departmentAdministrativePunishmentService.searchPage(query).getData(), DepartmentAdministrativePunishment.class));
				}
			}
			return CommonResponse.getFailResponse("导出数据失败：请求参数错误");
		} catch (IOException | ExcelWriteException e) {
			return CommonResponse.getFailResponse("导出数据失败：" + e.getMessage());
		}
	}
	private static final List<ReadColumn> DepartmentAdministrativePunishmentPeopleUploadColumns = DefaultReadColumn.createReadColumn(DepartmentAdministrativePunishmentPeopleUploadDTO.class);
	private static final List<ReadColumn> DepartmentAdministrativePunishmentOrgUploadColumns = DefaultReadColumn.createReadColumn(DepartmentAdministrativePunishmentOrgUploadDTO.class);

	@RequestMapping("/upload/people")
	@ResponseBody
	public Object uploadPeople(@RequestParam(value = "file",required = false) MultipartFile file) {
		try {
			if (file == null) {
				throw new BusinessException("请选择文件");
			}
			@SuppressWarnings("resource")
			XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
			ExcelReader<DepartmentAdministrativePunishmentPeopleUploadDTO> reader = new ExcelReader<>(DepartmentAdministrativePunishmentPeopleUploadDTO.class, DepartmentAdministrativePunishmentPeopleUploadColumns,
					new DefaultSheet(workbook.getSheetAt(0)), 1);

			int maxImportCount = 1000;

			List<ExcelImportResult.ExcelImportError> importErrors = new ArrayList<>();
			int index = 1;
			while (reader.hasNext()) {
				try {
					index++;
					DepartmentAdministrativePunishmentPeopleUploadDTO dto = reader.readRow();
					String errorMsg;
					if (dto != null) {
						errorMsg = departmentAdministrativePunishmentService.importPeople(dto);
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
			ExcelReader<DepartmentAdministrativePunishmentOrgUploadDTO> reader = new ExcelReader<>(DepartmentAdministrativePunishmentOrgUploadDTO.class, DepartmentAdministrativePunishmentOrgUploadColumns,
					new DefaultSheet(workbook.getSheetAt(0)), 1);

			int maxImportCount = 1000;

			List<ExcelImportResult.ExcelImportError> importErrors = new ArrayList<>();
			int index = 1;
			while (reader.hasNext()) {
				try {
					index++;
					DepartmentAdministrativePunishmentOrgUploadDTO dto = reader.readRow();
					String errorMsg;
					if (dto != null) {
						errorMsg = departmentAdministrativePunishmentService.importOrg(dto);
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