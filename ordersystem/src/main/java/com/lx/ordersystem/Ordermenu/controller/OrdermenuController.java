package com.lx.ordersystem.Ordermenu.controller;


import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.lx.ordersystem.AlipayConfig;
import com.lx.ordersystem.AlipayOrder;
import com.lx.ordersystem.Ordermenu.entity.Ordermenu;
import com.lx.ordersystem.Ordermenu.service.IOrdermenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lx
 * @since 2019-03-08
 */
@RestController
@RequestMapping("/ordermenu")
//@CrossOrigin(origins = "*",maxAge = 3600)
public class OrdermenuController {

	@Autowired
	private IOrdermenuService ordermenuService;

	@PostMapping(value = "/save")
	@ResponseBody
	public boolean save(@RequestBody List<Ordermenu> list){
		return ordermenuService.createOrder(list);
	}

	@GetMapping(value = "/checkHistoryOrder")
	public List<Ordermenu> checkHistoryOrder(String username){
		return ordermenuService.checkHistoryMenu(username);
	}

	@GetMapping(value = "/checkHistoryOrderAndTime")
	public List<Ordermenu> checkHistoryOrder(String username,String data){
		return ordermenuService.checkHistoryMenu(username,data);
	}

	@GetMapping(value = "/checkAllOrder")
	public List<Ordermenu> checkAllOrder(){
		return ordermenuService.checkAllOrder();
	}

	@GetMapping(value = "/checkAllOrderAndTime")
	public List<Ordermenu> checkAllOrder(String data){
		return ordermenuService.checkAllOrder(data);
	}

	//支付
	@PostMapping(value = "/alipay")
	public String alipayTest(@RequestBody AlipayOrder order) throws IOException {
		String timeout_espress="2m";
		String product_code="QUICK_WAP_WAY";
		AlipayClient client = new DefaultAlipayClient(AlipayConfig.URL,AlipayConfig.APPID,AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY,AlipayConfig.SIGNTYPE);
		AlipayTradeWapPayRequest alipay_request=new AlipayTradeWapPayRequest();

		AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
		model.setOutTradeNo(order.getWidout_trade_no());
		model.setSubject(order.getWidsubject());
		model.setTotalAmount(order.getWidtotal_amount());
		model.setBody(order.getWidbody());

		model.setTimeoutExpress(timeout_espress);
		model.setProductCode(product_code);

		alipay_request.setBizModel(model);
		alipay_request.setNotifyUrl(AlipayConfig.notify_url);
		alipay_request.setReturnUrl(AlipayConfig.return_url);

		//表单生产
		String form="";
		try{
			//调用SDK生成表单
			form = client.pageExecute(alipay_request).getBody();
//			response.setContentType("text/html;charset=" + AlipayConfig.CHARSET);
//			response.getWriter().write(form);//直接将完整的表单html输出到页面
//			response.getWriter().flush();
//			response.getWriter().close();
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		return form;
	}

	//查询支付宝订单
	@GetMapping(value = "/selectAli")
	public void selectAli(String number,HttpServletRequest request,HttpServletResponse response) throws AlipayApiException {
		AlipayClient client = new DefaultAlipayClient(AlipayConfig.URL,AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY,AlipayConfig.SIGNTYPE);
		AlipayTradeQueryRequest alipay_request = new AlipayTradeQueryRequest();

		AlipayTradeQueryModel model = new AlipayTradeQueryModel();
		model.setOutTradeNo(number);
		alipay_request.setBizModel(model);
		AlipayTradeQueryResponse alipay_response = client.execute(alipay_request);
//		System.out.println("交易信息:"+alipay_response.getBody());

		Map maps = (Map)JSON.parse(alipay_response.getBody());
		String result = maps.get("alipay_trade_query_response").toString();
		Map maps2 = (Map)JSON.parse(result);
		String result2 = maps2.get("trade_status").toString();
//		for (Object map : maps.entrySet()){
//			System.out.println(((Map.Entry)map).getKey()+"     " + ((Map.Entry)map).getValue());
//		}

		if(result2.equals("TRADE_SUCCESS")){
			ordermenuService.updatePay(number);
		}
	}

}
