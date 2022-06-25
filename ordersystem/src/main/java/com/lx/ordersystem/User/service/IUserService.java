package com.lx.ordersystem.User.service;

import com.lx.ordersystem.User.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lx
 * @since 2019-03-01
 */
public interface IUserService extends IService<User> {

	//查询指定的用户
	public User findOne(User user);

	//新增一个用户
	public boolean insert(User user);

	//查询所有用户
	public List findAll();

	//更新用户角色
	public boolean updateRole(User user);

	//查询个人用户
	public User information(String username);

	//个人信息修改
	public void updateInformation(User user);
}
