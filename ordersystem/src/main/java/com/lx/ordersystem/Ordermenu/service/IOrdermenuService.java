package com.lx.ordersystem.Ordermenu.service;

import com.lx.ordersystem.AlipayOrder;
import com.lx.ordersystem.Ordermenu.entity.Ordermenu;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lx
 * @since 2019-03-08
 */
public interface IOrdermenuService extends IService<Ordermenu> {

	//新建订单
	public boolean createOrder(List<Ordermenu> list);

	//查看历史订单
	public List<Ordermenu> checkHistoryMenu(String username);

	//查看历史订单（有时间限制）
	public List<Ordermenu> checkHistoryMenu(String username,String data);

	//查看所有订单
	public List<Ordermenu> checkAllOrder();

	//查看所有订单（有时间限制）
	public List<Ordermenu> checkAllOrder(String data);

	//修改支付状态
	public void updatePay(String number);
}
