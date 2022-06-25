package com.lx.ordersystem.Menu.controller;

import com.lx.ordersystem.Menu.entity.Menu;
import com.lx.ordersystem.Menu.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lx
 * @since 2019-03-03
 */
@RestController
@RequestMapping("/menu")
//@CrossOrigin(origins = "*",maxAge = 3600)
public class MenuController {

	@Autowired
	private IMenuService menuService;

	//查询所有菜单
	@PostMapping( value = "/check_all")
	public List checkAll(){
		return menuService.checkMenu();
	}

	//添加新菜
	@PostMapping(value = "/insertMenu")
	public boolean insertMenu(@RequestBody Menu menu){
		return menuService.insertMenu(menu);
	}

	//删除菜单
	@PostMapping(value = "/deleteMenu")
	public boolean deleteMenu(@RequestBody Menu menu){
		return menuService.deleteMenu(menu);
	}

	//删除全部菜单
	@PostMapping(value = "/deleteAll")
	public boolean deleteAll(){
		return menuService.deleteAll();
	}

	//上传图片
	@PostMapping("/upload")
	@ResponseBody
	public boolean upload(MultipartFile file, HttpServletRequest request) throws IOException {
		String path=this.getClass().getClassLoader().getResource("./static").getPath();
		path = java.net.URLDecoder.decode(path,"utf-8");
		path = path.replaceAll("/","\\\\");
		System.out.println("路径："+path);

		//获取上传文件的名字
		String fileName = file.getOriginalFilename();
		System.out.println("fileName:"+fileName);
		File picture = new File(path,fileName);
		System.out.println(file.getSize());
		if (!picture.getParentFile().exists()){
			picture.getParentFile().mkdirs();
			picture.createNewFile();
		}
		//MultipartFile自带的解析方法,把内存中图片写入磁盘
		file.transferTo(picture);
		return true;
	}
}
