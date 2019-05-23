package com.paladin.credit.controller.department;

import com.paladin.common.core.export.ExportUtil;
import com.paladin.credit.controller.department.dto.DepartmentCreditExportCondition;
import com.paladin.credit.model.department.DepartmentCredit;
import com.paladin.credit.service.department.DepartmentCreditService;
import com.paladin.credit.service.department.dto.*;
import com.paladin.credit.service.department.vo.DepartmentCreditVO;
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
@RequestMapping("/credit/department/credit")
public class DepartmentCreditController extends ControllerSupport {

    @Autowired
    private DepartmentCreditService departmentCreditService;

    @GetMapping("/index/{type}")
    @QueryInputMethod(queryClass = DepartmentCreditQuery.class)
    public String index(@PathVariable String type,Model model) {
		model.addAttribute("type",type);
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
    

    @GetMapping("/detail")
    public String detailInput(@RequestParam String id,@RequestParam String type, Model model) {
		model.addAttribute("id", id);
		model.addAttribute("type", type);
        return "/credit/department/department_credit_detail";
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

	private static final List<ReadColumn> departmentCreditRedUploadColumns = DefaultReadColumn.createReadColumn(DepartmentCreditRedUploadDTO.class);
	private static final List<ReadColumn> departmentCreditBlackUploadColumns = DefaultReadColumn.createReadColumn(DepartmentCreditBlackUploadDTO.class);
	private static final List<ReadColumn> departmentEnterpriseUploadColumns = DefaultReadColumn.createReadColumn(DepartmentEnterpriseUploadDTO.class);

	@RequestMapping("/upload/red")
	@ResponseBody
	public Object uploadBase(@RequestParam("file") MultipartFile file) {
		try {
			@SuppressWarnings("resource")
			XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
			ExcelReader<DepartmentCreditRedUploadDTO> reader = new ExcelReader<>(DepartmentCreditRedUploadDTO.class, departmentCreditRedUploadColumns,
					new DefaultSheet(workbook.getSheetAt(0)), 1);

			int maxImportCount = 1000;

			List<ExcelImportResult.ExcelImportError> importErrors = new ArrayList<>();
			int index = 1;
			while (reader.hasNext()) {
				try {
					index++;
					DepartmentCreditRedUploadDTO dto = reader.readRow();
					String errorMsg;
					if (dto != null) {
						errorMsg = departmentCreditService.importRed(dto);
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
			ExcelReader<DepartmentCreditBlackUploadDTO> reader = new ExcelReader<>(DepartmentCreditBlackUploadDTO.class, departmentCreditBlackUploadColumns,
					new DefaultSheet(workbook.getSheetAt(0)), 1);

			int maxImportCount = 1000;

			List<ExcelImportResult.ExcelImportError> importErrors = new ArrayList<>();
			int index = 1;
			while (reader.hasNext()) {
				try {
					index++;
					DepartmentCreditBlackUploadDTO dto = reader.readRow();
					String errorMsg;
					if (dto != null) {
						errorMsg = departmentCreditService.importBlack(dto);
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

	@RequestMapping("/upload/business")
	@ResponseBody
	public Object uploadBusiness(@RequestParam("file") MultipartFile file) {
		try {
			@SuppressWarnings("resource")
			XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
			ExcelReader<DepartmentEnterpriseUploadDTO> reader = new ExcelReader<>(DepartmentEnterpriseUploadDTO.class, departmentEnterpriseUploadColumns,
					new DefaultSheet(workbook.getSheetAt(0)), 1);

			int maxImportCount = 1000;

			List<ExcelImportResult.ExcelImportError> importErrors = new ArrayList<>();
			int index = 1;
			while (reader.hasNext()) {
				try {
					index++;
					DepartmentEnterpriseUploadDTO dto = reader.readRow();
					String errorMsg;
					if (dto != null) {
						errorMsg = departmentCreditService.importBusiness(dto);
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