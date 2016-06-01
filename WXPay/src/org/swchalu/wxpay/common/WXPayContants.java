package org.swchalu.wxpay.common;

public class WXPayContants {
	/**
	 * 公众账号ID 微信分配的公众账号ID（企业号corpid即为此appId）
	 */
	private static String appid = "";
	/**
	 * 商户号 微信支付分配的商户号
	 */
	private static String mch_id = "";
	/**
	 * 设备号 终端设备号(门店号或收银设备ID)，注意：PC网页或公众号内支付请传"WEB"
	 */
	public static String device_info_web = "WEB";
	/**
	 * 附加数据 附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
	 */
	private static String attach = "";
	/**
	 * 货币类型 符合ISO 4217标准的三位字母代码，默认人民币：CNY
	 */
	private static String fee_type = "CNY";
	/**
	 * 通知地址 接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
	 */
	private static String notify_url = "";
	/**
	 * 交易类型 取值如下：JSAPI，NATIVE，APP
	 */
	public static String trade_type_jsapi = "JSAPI";
	public static String trade_type_native = "NATIVE";
	public static String trade_type_app = "APP";
	/**
	 * 商品ID trade_type=NATIVE，此参数必传。此id为二维码中包含的商品ID，商户自行定义。
	 */
	private static String product_id;
	/**
	 * 指定支付方式 no_credit--指定不能使用信用卡支付
	 */
	private static String limit_pay;
	/**
	 * 微信预支付下单接口
	 */
	private static String prepay_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	/**
	 * 微信开发AppSecret(应用密钥)
	 */
	private static String secret = "";
	/**
	 * 微信商户平台设置的key
	 */
	private static String key = "";

	public static String FAIL = "FAIL";
	public static String SUCCESS = "SUCCESS";

	/**
	 * 获得公众账号ID
	 * 
	 * @return String
	 */
	public static String getAppid() {
		return appid;
	}

	/**
	 * 获得商户号
	 * 
	 * @return String
	 */
	public static String getMch_id() {
		return mch_id;
	}

	/**
	 * 获得附加数据
	 * 
	 * @return String
	 */
	public static String getAttach() {
		return attach;
	}

	/**
	 * 获得货币类型
	 * 
	 * @return String
	 */
	public static String getFee_type() {
		return fee_type;
	}

	/**
	 * 获得通知地址
	 * 
	 * @return String
	 */
	public static String getNotify_url() {
		return notify_url;
	}

	/**
	 * 获得商品ID
	 * 
	 * @return String
	 */
	public static String getProduct_id() {
		return product_id;
	}

	/**
	 * 获得指定支付方式
	 * 
	 * @return String
	 */
	public static String getLimit_pay() {
		return limit_pay;
	}

	public static String getSecret() {
		return secret;
	}

	public static String getPrepay_url() {
		return prepay_url;
	}

	public static String getKey() {
		return key;
	}
}
