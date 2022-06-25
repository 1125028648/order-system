package com.lx.ordersystem.Menu.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lx.ordersystem.Menu.entity.Menu;
import com.lx.ordersystem.Menu.mapper.MenuMapper;
import com.lx.ordersystem.Menu.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lx
 * @since 2019-03-03
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

	@Autowired
	private MenuMapper menuMapper;

	@Override
	public List<Menu> checkMenu() {
		List<Menu> list = menuMapper.selectList(null);
		return list;
	}

	@Override
	public boolean insertMenu(Menu menu) {
		String picturePath = menu.getMenuName()+".jpg";
		String path=this.getClass().getClassLoader().getResource("./static").getPath();
		try {
			path = java.net.URLDecoder.decode(path,"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		path = path.replaceAll("/","\\\\");

		File file = new File(path+"\\"+picturePath);
		if (file.exists()){
			menu.setPicture(picturePath);
			QueryWrapper<Menu> wrapper = new QueryWrapper<>();
			wrapper.eq("menu_name",menu.getMenuName());
			if (menuMapper.selectOne(wrapper)==null){
				menuMapper.insert(menu);
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}

	@Override
	public boolean deleteMenu(Menu menu) {
		QueryWrapper<Menu> wrapper = new QueryWrapper<>();
		wrapper.eq("menu_name",menu.getMenuName());
		int result = menuMapper.delete(wrapper);
		if(result == 0){
			return false;
		}else{
			return true;
		}
	}

	@Override
	public boolean deleteAll() {
		menuMapper.deleteByMap(null);
		return true;
	}


}
