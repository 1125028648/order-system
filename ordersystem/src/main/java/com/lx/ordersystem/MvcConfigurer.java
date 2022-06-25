package com.lx.ordersystem;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author:XinLiu
 * @Date:Created in 20:55 2019/4/25
 */
@Configuration
public class MvcConfigurer implements WebMvcConfigurer {

	//跨域设置
	public void addCorsMappings(CorsRegistry registry){
		registry.addMapping("/**")
				.allowedOrigins("*")
				.allowedMethods("GET","POST")
				.allowCredentials(true); //允许携带cookie信息
	}
}
