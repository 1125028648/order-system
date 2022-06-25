package com.lx.ordersystem.User.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lx.ordersystem.User.entity.User;
import com.lx.ordersystem.User.mapper.UserMapper;
import com.lx.ordersystem.User.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lx
 * @since 2019-03-01
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public User findOne(User user) {
		QueryWrapper<User> wrapper = new QueryWrapper<>();
		wrapper.eq("username",user.getUsername())
				.eq("password",user.getPassword());
		User findUser = userMapper.selectOne(wrapper);
		return findUser;
	}

	@Override
	public boolean insert(User user) {
		QueryWrapper<User> wrapper = new QueryWrapper<>();
		wrapper.eq("username",user.getUsername());
		User findUser = userMapper.selectOne(wrapper);
		if (findUser != null){
			return false;
		}else{
			user.setRole(1);
			userMapper.insert(user);
			return true;
		}
	}

	@Override
	public List<User> findAll() {
		List<User> list = userMapper.selectList(null);
		for(int i=0;i<list.size();i++){
			list.get(i).setPassword(null);
		}

		//去掉管理员
		Iterator<User> it = list.iterator();
		while (it.hasNext()){
			User user = it.next();
			if (user.getRole().equals(0)){
				it.remove();
			}
		}
		return list;
	}

	@Override
	public boolean updateRole(User user) {
		QueryWrapper<User> wrapper = new QueryWrapper<>();
		wrapper.eq("username",user.getUsername());
		userMapper.update(user,wrapper);
		return true;
	}

	@Override
	public User information(String username) {
		QueryWrapper<User> wrapper = new QueryWrapper<>();
		wrapper.eq("username",username);
		User user = userMapper.selectOne(wrapper);
		user.setPassword(null);
		user.setRole(null);
		return user;
	}

	@Override
	public void updateInformation(User user) {
		QueryWrapper<User> wrapper = new QueryWrapper<>();
		wrapper.eq("username",user.getUsername());
		userMapper.update(user,wrapper);
	}
}
