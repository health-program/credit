package com.paladin.credit.controller.publicity;

import com.paladin.common.core.export.ExportUtil;
import com.paladin.common.model.syst.SysAttachment;
import com.paladin.common.service.syst.SysAttachmentService;
import com.paladin.credit.controller.publicity.dto.PublicityMessageExportCondition;
import com.paladin.credit.model.publicity.PublicityMessage;
import com.paladin.credit.service.publicity.PublicityMessageService;
import com.paladin.credit.service.publicity.dto.PublicityMessageDTO;
import com.paladin.credit.service.publicity.dto.PublicityMessageQuery;
import com.paladin.credit.service.publicity.vo.PublicityMessageVO;
import com.paladin.framework.core.ControllerSupport;
import com.paladin.framework.core.query.QueryInputMethod;
import com.paladin.framework.core.query.QueryOutputMethod;
import com.paladin.framework.excel.write.ExcelWriteException;
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
@RequestMapping("/credit/publicity/message")
public class PublicityMessageController extends ControllerSupport {

    @Autowired
    private PublicityMessageService publicityMessageService;

	@Autowired
	private SysAttachmentService sysAttachmentService;


	@GetMapping("/index")
    @QueryInputMethod(queryClass = PublicityMessageQuery.class)
    public String index() {
        return "/credit/publicity/publicity_message_index";
    }

    @RequestMapping(value = "/find/page", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    @QueryOutputMethod(queryClass = PublicityMessageQuery.class, paramIndex = 0)
    public Object findPage(PublicityMessageQuery query) {
        return CommonResponse.getSuccessResponse(publicityMessageService.searchPage(query));
    }
    
    @GetMapping("/get")
    @ResponseBody
    public Object getDetail(@RequestParam String id, Model model) {   	
        return CommonResponse.getSuccessResponse(beanCopy(publicityMessageService.get(id), new PublicityMessageVO()));
    }
    
    @GetMapping("/add")
    public String addInput() {
        return "/credit/publicity/publicity_message_add";
    }

    @GetMapping("/detail")
    public String detailInput(@RequestParam String id, Model model) {
    	model.addAttribute("id", id);
        return "/credit/publicity/publicity_message_detail";
    }
    
    @PostMapping("/save")
	@ResponseBody
    public Object save(@Valid PublicityMessageDTO publicityMessageDTO, BindingResult bindingResult,@RequestParam(required = false) MultipartFile[] attachmentFiles) {
		if (bindingResult.hasErrors()) {
			return validErrorHandler(bindingResult);
		}
		List<SysAttachment> attachments = sysAttachmentService.checkOrCreateAttachment(publicityMessageDTO.getAttachments(), attachmentFiles);
		if (attachments != null && attachments.size() > 4) {
			return CommonResponse.getErrorResponse("附件数量不能超过4张");
		}
        PublicityMessage model = beanCopy(publicityMessageDTO, new PublicityMessage());
		model.setAttachments(sysAttachmentService.splicingAttachmentId(attachments));
		return CommonResponse.getResponse(publicityMessageService.save(model));
	}

    @PostMapping("/update")
	@ResponseBody
    public Object update(@Valid PublicityMessageDTO publicityMessageDTO, BindingResult bindingResult,@RequestParam(required = false) MultipartFile[] attachmentFiles) {
		if (bindingResult.hasErrors()) {
			return validErrorHandler(bindingResult);
		}
		String id = publicityMessageDTO.getId();
		PublicityMessage model = beanCopy(publicityMessageDTO, publicityMessageService.get(id));
		List<SysAttachment> attachments = sysAttachmentService.checkOrCreateAttachment(publicityMessageDTO.getAttachments(), attachmentFiles);
		if (attachments != null && attachments.size() > 4) {
			return CommonResponse.getErrorResponse("附件数量不能超过4张");
		}
		model.setAttachments(sysAttachmentService.splicingAttachmentId(attachments));
		if (publicityMessageService.update(model) > 0) {
			return CommonResponse.getSuccessResponse(beanCopy(publicityMessageService.get(id), new PublicityMessageVO()));
		}
		return CommonResponse.getFailResponse();
	}

    @RequestMapping(value = "/delete", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Object delete(@RequestParam String id) {
        return CommonResponse.getResponse(publicityMessageService.removeByPrimaryKey(id));
    }

    
    @PostMapping(value = "/export")
	@ResponseBody
	public Object export(@RequestBody PublicityMessageExportCondition condition) {
		if (condition == null) {
			return CommonResponse.getFailResponse("导出失败：请求参数异常");
		}
		condition.sortCellIndex();
		PublicityMessageQuery query = condition.getQuery();
		try {
			if (query != null) {
				if (condition.isExportAll()) {
					return CommonResponse.getSuccessResponse("success", ExportUtil.export(condition, publicityMessageService.searchAll(query), PublicityMessage.class));
				} else if (condition.isExportPage()) {
					return CommonResponse.getSuccessResponse("success",
							ExportUtil.export(condition, publicityMessageService.searchPage(query).getData(), PublicityMessage.class));
				}
			}
			return CommonResponse.getFailResponse("导出数据失败：请求参数错误");
		} catch (IOException | ExcelWriteException e) {
			return CommonResponse.getFailResponse("导出数据失败：" + e.getMessage());
		}
	}
}