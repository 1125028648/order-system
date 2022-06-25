package com.lx.ordersystem;

import lombok.Data;

/**
 * @Author:XinLiu
 * @Date:Created in 16:37 2019/4/23
 */
@Data
public class AlipayOrder {

	//商户订单号
	private String widout_trade_no="";

	//订单名称
	private String widsubject="";

	//付款金额
	private String widtotal_amount="";

	//商品描述
	private String widbody="";
}
