package com.paladin.credit.controller.home;

import com.paladin.credit.model.publicity.PublicityMessage;
import com.paladin.credit.service.publicity.PublicityMessageService;
import com.paladin.credit.service.publicity.vo.PublicityMessageVO;
import com.paladin.credit.service.supervise.SuperviseRecordService;
import com.paladin.framework.core.ControllerSupport;
import com.paladin.framework.core.exception.BusinessException;
import com.paladin.framework.web.response.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * <首页>
 *
 * @author Huangguochen
 * @create 2019/4/12 15:05
 */
@Controller
@RequestMapping("/credit/home/page")
public class HomePageController extends ControllerSupport {

    @Autowired
    private PublicityMessageService publicityMessageService;
    @Autowired
    private SuperviseRecordService superviseRecordService;


    @GetMapping("/index")
    public String index(Model model) {
        List<PublicityMessage> notices = publicityMessageService.findAll();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String bgTimeStr = now.minusYears(1).format(formatter);
        model.addAttribute("time",bgTimeStr+'~'+"今天");
        model.addAttribute("notices",notices);
        return "/credit/index_content";
    }

    @GetMapping("/notice/view")
    public String view(@RequestParam String id, Model model) {
        PublicityMessage message = publicityMessageService.get(id);
        if (message == null) {
            throw new BusinessException("查看的内容不存在");
        }
        PublicityMessageVO publicityMessageVO = beanCopy(message, new PublicityMessageVO());
        model.addAttribute("object",publicityMessageVO);
        return "/credit/publicity/publicity_message_home_view";
    }

    @PostMapping("/count/event")
    @ResponseBody
    public Object countEventGrade(@RequestParam(required = false)Date searchTime,Model model) {
        return CommonResponse.getSuccessResponse( superviseRecordService.countRecordEventGradeByDate(searchTime)) ;
    }

    @PostMapping("/count/org")
    @ResponseBody
    public Object countOrgCredit(@RequestParam(required = false)Date searchTime) {
        return CommonResponse.getSuccessResponse( superviseRecordService.countRecordOrgCreditByDate(searchTime)) ;
    }

    @PostMapping("/map/org")
    @ResponseBody
    public Object findOrgMap() {
        return CommonResponse.getSuccessResponse(superviseRecordService.findAllOrgMap());
    }


    @PostMapping("/map/org/info")
    @ResponseBody
    public Object findNewOrgMap(String agencyId) {
        return CommonResponse.getSuccessResponse(superviseRecordService.findMapOrgInfoById(agencyId)) ;
    }
}

