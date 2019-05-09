package com.paladin.credit.controller;

import com.paladin.common.core.ConstantsContainer;
import com.paladin.common.core.ConstantsContainer.KeyValue;
import com.paladin.common.core.permission.MenuPermission;
import com.paladin.common.model.org.OrgPermission;
import com.paladin.common.service.syst.SysUserService;
import com.paladin.credit.core.CreditUserSession;
import com.paladin.framework.core.session.UserSession;
import com.paladin.framework.web.response.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;

@Api("用户认证模块")
@Controller
@RequestMapping("/credit")
public class LoginController {

	private final static String CONSTANT_TYPE_SUPERVISE_SCOPE = "supervise-scope";
	
	@Autowired
	private SysUserService sysUserService;

	@ApiOperation(value = "主页面")
	@GetMapping(value = "/index")
	public Object main(HttpServletRequest request) {
		CreditUserSession userSession = CreditUserSession.getCurrentUserSession();
		ModelAndView model = new ModelAndView("/credit/index");
		model.addObject("name", userSession.getUserName());
		
		// 加载监管范围
		boolean isSupervisor = false;
		List<KeyValue> supervise = null;
		String currentSuperviseScope = null;

		if (userSession.isAdminRoleLevel()) {
			isSupervisor = true;
			supervise = ConstantsContainer.getType(CONSTANT_TYPE_SUPERVISE_SCOPE);
			currentSuperviseScope = userSession.getCurrentSuperviseScope();
		} else if (userSession.getRoleLevel() >= CreditUserSession.ROLE_LEVEL_SUPERVISE) {
			isSupervisor = true;
			supervise = ConstantsContainer.getTypes(CONSTANT_TYPE_SUPERVISE_SCOPE, userSession.getSuperviseScopes());
			currentSuperviseScope = userSession.getCurrentSuperviseScope();
		}

		if (isSupervisor) {
			model.addObject("isSupervisor", true);
			model.addObject("supervise", supervise);
			model.addObject("currentSuperviseScope", ConstantsContainer.getTypeValue(CONSTANT_TYPE_SUPERVISE_SCOPE, currentSuperviseScope));
		}

		// 加载菜单
		Collection<MenuPermission> menus = userSession.getMenuResources();
		StringBuilder sb = new StringBuilder("<li class=\"header\">菜单</li>");
		createMenuHtml(menus, sb);
		model.addObject("menuHtml", sb.toString());
		return model;
	}

	private void createMenuHtml(Collection<MenuPermission> menus, StringBuilder sb) {
		for (MenuPermission menu : menus) {
			OrgPermission op = menu.getSource();
			Collection<MenuPermission> children = menu.getChildren();

			String href = menu.isMenu() && menu.isOwned() ? op.getUrl() : null;

			String icon = op.getMenuIcon();
			if (icon != null && icon.length() > 0) {
				icon = "fa iconfont icon" + icon;
			} else {
				icon = "fa fa-circle-o";
			}

			if (children.size() > 0) {
				sb.append("<li class=\"treeview\"><a class=\"nav-link\"");
				if (href != null && href.length() > 0) {
					sb.append(" onclick=\"addTabs({id:'").append(op.getId()).append("',title: '").append(op.getName()).append("',close: true,url: '")
							.append(href).append("',urlType: 'relative'});\"");
				}

				sb.append("><i class=\"").append(icon).append("\"></i><span>").append(op.getName()).append(
						"</span><span class=\"pull-right-container\"><i class=\"fa fa-angle-left pull-right\"></i></span></a><ul class=\"treeview-menu\">");
				createMenuHtml(children, sb);
				sb.append("</ul></li>");
			} else {
				sb.append("<li><a class=\"nav-link\"");
				if (href != null) {
					sb.append(" onclick=\"addTabs({id:'").append(op.getId()).append("',title: '").append(op.getName()).append("',close: true,url: '")
							.append(href).append("',urlType: 'relative'});\"");
				}
				sb.append("><i class=\"").append(icon).append("\"></i> <span>").append(op.getName()).append("</span></a></li>");
			}
		}
	}

	@ApiOperation(value = "修改密码", response = CommonResponse.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "newPassword", value = "新密码", required = true), @ApiImplicitParam(name = "oldPassword", value = "旧密码") })
	@RequestMapping(value = "/update/password", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Object updatePassword(@RequestParam String newPassword, @RequestParam String oldPassword) {
		return CommonResponse.getResponse(sysUserService.updateSelfPassword(newPassword, oldPassword));
	}

	@ApiOperation(value = "登录页面")
	@GetMapping("/login")
	public Object loginInput(HttpServletRequest request, HttpServletResponse response) {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			return main(request);
		}
		return "/credit/login";
	}

	@ApiOperation(value = "用户认证")
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
	public Object login(HttpServletRequest request, HttpServletResponse response, Model model) {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			if (username != null && username.length() != 0 && password != null && password.length() != 0) {
				subject.login(new UsernamePasswordToken(username, password));
			}
		}

		if (subject.isAuthenticated()) {
			return main(request);
		} else {
			model.addAttribute("isError", true);
			return "/credit/login";
		}
	}

	@ApiOperation(value = "用户认证")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Object ajaxLogin(HttpServletRequest request, HttpServletResponse response, Model model) {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			if (username != null && username.length() != 0 && password != null && password.length() != 0) {
				subject.login(new UsernamePasswordToken(username, password));
			}
		}

		if (subject.isAuthenticated()) {
			return CommonResponse.getSuccessResponse("登录成功", UserSession.getCurrentUserSession().getUserForView());
		} else {
			return CommonResponse.getFailResponse("登录失败");
		}
	}

	@RequestMapping(value = "/update/{superviseCode}", method = RequestMethod.GET)
	@ResponseBody
	public Object update(@PathVariable String superviseCode, Model model) {
		CreditUserSession.getCurrentUserSession().setCurrentSuperviseScope(superviseCode);
		return CommonResponse.getSuccessResponse();
	}

}
