package com.paladin.credit.controller.home;

import com.paladin.credit.model.publicity.PublicityMessage;
import com.paladin.credit.service.publicity.PublicityMessageService;
import com.paladin.credit.service.publicity.vo.PublicityMessageVO;
import com.paladin.framework.core.ControllerSupport;
import com.paladin.framework.core.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/index")
    public String index(Model model) {
        List<PublicityMessage> notices = publicityMessageService.findAll();
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
}

