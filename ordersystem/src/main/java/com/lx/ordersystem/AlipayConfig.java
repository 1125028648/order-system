package com.lx.ordersystem;

/**
 * @Author:XinLiu
 * @Date:Created in 10:38 2019/4/24
 */
public class AlipayConfig {
	// 商户appid
	public static String APPID = "2016092600599328";
	// 私钥 pkcs8格式的
	public static String RSA_PRIVATE_KEY = "xxx";
	// 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://www.baidu.com";
	// 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
	public static String return_url = "http://127.0.0.1:8080/#/index";
	// 请求网关地址
	public static String URL = "https://openapi.alipaydev.com/gateway.do";
	// 编码
	public static String CHARSET = "UTF-8";
	// 返回格式
	public static String FORMAT = "json";
	// 支付宝公钥
	public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA1CXJXsRYWb/nZ1J9NsotilLEBhvs4nVBWV36scY+FTAULXAEWMi1OXIb6Nv9SGzplxK8egPNweCSPexT+Btn6g/QgzqiXiWPSr6DmP9JC364lltVN1sd4V/v4Fg5rdJ+dVg3K43AWclQw+856wIznJmYaOFE1qWf+BmzCK4Lunq5jPphXFfiIkRxDsdFDWl1XxhYyh3ijNMCwWZ/xkixFhh8H04ZxrPiRrmVIp2xdoQZQRLe+q9P8kDwe09eKY9+gWoipF90RRwLjzlo8OcKT03lrfncRWQJCcSB3pplmowsMbOmuMdShaRW1/rxNCskttSeUlZxzuSFOZwc6zqrLwIDAQAB";
	// 日志记录目录
	public static String log_path = "/log";
	// RSA2
	public static String SIGNTYPE = "RSA2";
}
