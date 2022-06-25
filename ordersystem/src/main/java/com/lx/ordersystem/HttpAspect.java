package com.lx.ordersystem;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author:XinLiu
 * @Date:Created in 9:52 2019/4/29
 */
@Aspect
@Component
public class HttpAspect {

	private final static Logger logger = LoggerFactory.getLogger(HttpAspect.class);

	@Pointcut("execution(public * com.lx.ordersystem.User.controller.UserController.*(..))|| execution(public * com.lx.ordersystem.Ordermenu.controller.OrdermenuController.*(..))|| execution(public * com.lx.ordersystem.Menu.controller.MenuController.*(..))|| execution(public * com.lx.ordersystem.Discount.controller.DiscountController.*(..))")
	public void log(){

	}

	@Before("log()")
	public void doBefore(JoinPoint joinPoint){

		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();

		//url
		logger.info("url={}",request.getRequestURL());

		//method
		logger.info("method={}",request.getMethod());

		//ip
		logger.info("ip={}",request.getRemoteAddr());

		//类方法
		logger.info("class_method={}",joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName());
	}

	@After("log()")
	public void doAfter() {
		logger.info("结束");
	}
}
