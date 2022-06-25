package com.lx.ordersystem.Ordermenu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lx.ordersystem.Ordermenu.entity.Ordermenu;
import com.lx.ordersystem.Ordermenu.mapper.OrdermenuMapper;
import com.lx.ordersystem.Ordermenu.service.IOrdermenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lx
 * @since 2019-03-08
 */
@Service
public class OrdermenuServiceImpl extends ServiceImpl<OrdermenuMapper, Ordermenu> implements IOrdermenuService {

	@Autowired
	private IOrdermenuService ordermenuService;

	@Autowired
	private OrdermenuMapper ordermenuMapper;

	@Override
	public boolean createOrder(List<Ordermenu> list) {
		ordermenuService.saveBatch(list);
		return true;
	}

	@Override
	public List<Ordermenu> checkHistoryMenu(String username) {
		QueryWrapper<Ordermenu> wrapper = new QueryWrapper<>();
		wrapper.eq("username",username)
				.eq("pay_state",1);
		List<Ordermenu> list = ordermenuMapper.selectList(wrapper);
		return list;
	}

	//查看历史订单，通过时间
	@Override
	public List<Ordermenu> checkHistoryMenu(String username, String data) {
		QueryWrapper<Ordermenu> wrapper = new QueryWrapper<>();
		wrapper.eq("username",username)
				.eq("pay_state",1)
				.ge("create_time",data);
		List<Ordermenu> list = ordermenuMapper.selectList(wrapper);
		return list;
	}

	@Override
	public List<Ordermenu> checkAllOrder() {
		List<Ordermenu> list = ordermenuMapper.selectList(null);
		return list;
	}

	@Override
	public List<Ordermenu> checkAllOrder(String data) {
		QueryWrapper<Ordermenu> wrapper = new QueryWrapper<>();
		wrapper.ge("create_time",data);
		List<Ordermenu> list = ordermenuMapper.selectList(wrapper);
		return list;
	}

	@Override
	public void updatePay(String number) {
		QueryWrapper<Ordermenu> wrapper = new QueryWrapper<>();
		wrapper.eq("order_number",number);
		Ordermenu ordermenu = new Ordermenu();
		ordermenu.setPayState(1);
		ordermenuMapper.update(ordermenu,wrapper);
	}
}
