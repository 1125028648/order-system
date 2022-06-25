package com.lx.ordersystem.User.controller;


import com.lx.ordersystem.User.entity.User;
import com.lx.ordersystem.User.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lx
 * @since 2019-03-01
 */
@RestController
@RequestMapping("/user")
//@CrossOrigin(origins = "*",maxAge = 3600)
public class UserController {

	@Autowired
	IUserService userService;

	@PostMapping( value = "/login")
	public User login(@RequestBody User user, HttpServletRequest request, HttpServletResponse response){
		User selectUser = userService.findOne(user);
		if (selectUser != null){
			HttpSession session = request.getSession();
			session.setAttribute("username",selectUser.getUsername());
			session.setAttribute("role",selectUser.getRole());

//			Cookie cookie = new Cookie("JSESSIONID",session.getId());
//			cookie.setMaxAge(60*60);
//			cookie.setPath("/");
//			response.addCookie(cookie);

			User responseUser = new User();
			responseUser.setUsername(session.getAttribute("username").toString());
			responseUser.setRole((Integer) session.getAttribute("role"));
			return responseUser;
		}else{
			return null;
		}
	}

	//登录验证
	@GetMapping(value = "/checkLogin")
	public User ckeckLogin(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		if (session.getAttribute("username") != null){
			User user = new User();
			user.setUsername(session.getAttribute("username").toString());
			user.setRole((Integer) session.getAttribute("role"));
			return user;
		}else{
			return null;
		}

	}

	//注销
	@GetMapping(value = "/exit")
	public void exit(HttpServletRequest request,HttpServletResponse response){
		HttpSession session = request.getSession();
		session.removeAttribute("username");
	}

	//注册
	@PostMapping( value = "/register")
	public boolean register(@RequestBody User user){
		return userService.insert(user);
	}

	@PostMapping(value = "/findAll")
	public List findAll(){
		return userService.findAll();
	}

	@PostMapping(value = "/updateRole")
	public boolean updateRole(@RequestBody User user) {
		return userService.updateRole(user);
	}

	//个人信息查询
	@GetMapping(value = "/information")
	public User information(String username){
		return userService.information(username);
	}

	//修改个人信息
	@PostMapping(value = "/updateInformation")
	public void updateInformation(@RequestBody User user){
		userService.updateInformation(user);
	}

	//密码验证
	@PostMapping(value = "/checkPassword")
	public boolean checkPassword(@RequestBody User user){
		if(userService.findOne(user) != null){
			return true;
		}else{
			return false;
		}
	}

}
