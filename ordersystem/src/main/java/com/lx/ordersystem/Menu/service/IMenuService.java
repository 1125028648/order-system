package com.lx.ordersystem.Menu.service;

import com.lx.ordersystem.Menu.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lx
 * @since 2019-03-03
 */
public interface IMenuService extends IService<Menu> {
	//查看所有菜单
	public List<Menu> checkMenu();

	//添加新菜
	public boolean insertMenu(Menu menu);

	//删除菜单
	public boolean deleteMenu(Menu menu);

	//删除全部菜单
	public boolean deleteAll();
}
