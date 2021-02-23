package com.demo.controller;

import com.demo.dto.LoginDto;
import com.demo.service.IAccountService;
import com.demo.service.IResourceService;
import com.demo.vo.ResourceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequestMapping("auth")
public class LoginController {

	@Autowired
	private IAccountService accountServiceImpl;
	@Autowired
	private IResourceService resourceService;

	@PostMapping("login")
	public String login(String username, String password, HttpSession session,
						RedirectAttributes attributes, Model model) {
		//测试
		//测试2
		//测试3
		//测试4
		LoginDto login = accountServiceImpl.login(username, password);
		String error = login.getError();
		if (error == null) {
			session.setAttribute("account", login.getAccount());
			List<ResourceVo> resourceVos = resourceService.listResourceByRoleId(login.getAccount().getRoleId());
			model.addAttribute("resources", resourceVos);
		} else {
			attributes.addFlashAttribute("error", error);
		}
		return login.getPath();
	}

	@GetMapping("logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}



	@GetMapping("lzy")
	@ResponseBody
	//如果不加这个注解 在浏览器端是访问不到的  前提是你的控制器上面使用了 @Controller注解 而不是 @RestController注解
	//不加这个注解  可以用ajax访问
	public String lzy(){
		return "lzy";
	}

}
