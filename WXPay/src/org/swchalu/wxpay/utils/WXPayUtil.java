package org.swchalu.wxpay.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.swchalu.wxpay.common.WXPayContants;
import org.swchalu.wxpay.prepay.PrepayCallback;
import org.swchalu.wxpay.scanpay.ScanpayBean;

public class WXPayUtil {
	// 49-57[0-9],65-90[A-Z],97-122[a-z]
	/**
	 * 产生32位含数字及大小写字母随机数字符串
	 * 
	 * @return
	 */
	public static String getNonceStr() {

		StringBuffer nonce_str = new StringBuffer("");
		Random random = new Random();
		DecimalFormat df = new DecimalFormat("000");
		int length = 0;
		while (length < 32) {
			int ran = -1;
			switch (random.nextInt(3)) {
			case 0:
				ran = getRandomFromPeriod(49, 57);
				break;
			case 1:
				ran = getRandomFromPeriod(65, 90);
				break;
			case 2:
				ran = getRandomFromPeriod(97, 122);
				break;
			}
			if (ran != -1) {
				System.out.print("-" + df.format(ran));
				nonce_str.append((char) ran);
				length++;
			}
		}
		System.out.println(" ");
		return nonce_str.toString();
	}

	/**
	 * 产生范围内随机数
	 * 
	 * @param min
	 *            最小值
	 * @param max
	 *            最大值
	 * @return
	 */
	private static int getRandomFromPeriod(int min, int max) {
		Random random = new Random();
		int r = random.nextInt(max) % (max - min + 1) + min;
		return r;
	}

	/**
	 * 根据微信支付文档要求产生签名
	 * 
	 * @param paramStr
	 *            参数字符串
	 * @return
	 */
	public static String sign(String paramStr) {
		StringBuffer unsignStr = new StringBuffer("");
		unsignStr.append(paramStr);
		unsignStr.append("&key=" + WXPayContants.getKey());
		String signStr = null;
		signStr = MD5.sign(unsignStr.toString(), "utf-8").toUpperCase();
		// signStr = new Md5().getMD5ofStr(unsignStr.toString()).toUpperCase();
		return signStr;
	}

	/**
	 * 验证签名是否正确
	 * 
	 * @param paramStr
	 *            参数字符串
	 * @param valid
	 *            需要验证的签名
	 * @return
	 */
	public static boolean validSign(String paramStr, String valid) {
		String sign = sign(paramStr);
		return sign.equals(valid);
	}

	/**
	 * 生成订单开始时间
	 * 
	 * @return
	 */
	public static String createTime_start() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(date);
	}

	/**
	 * 生成订单截至时间
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String createTime_expire() {
		Date date = new Date();
		date.setMinutes(date.getMinutes() + 10);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(date);
	}

	/**
	 * 预支付返回xml解析成对象
	 * 
	 * @param XMLStr
	 *            xml字符串
	 * @return
	 */
	public static PrepayCallback xml2PrepayCallback(String XMLStr) {
		PrepayCallback callback = null;
		try {
			Document document = DocumentHelper.parseText(XMLStr);
			Element e = document.getRootElement();
			callback = new PrepayCallback();

			String return_code = e.element("return_code").getStringValue();
			String return_msg = e.element("return_msg").getStringValue();
			callback.setReturn_code(return_code).setReturn_msg(return_msg);

			if (return_code.equals("SUCCESS")) {
				String appid = e.element("appid").getStringValue();
				String mch_id = e.element("mch_id").getStringValue();
				String device_info = e.element("device_info").getStringValue();
				String nonce_str = e.element("nonce_str").getStringValue();
				String sign = e.element("sign").getStringValue();
				String result_code = e.element("result_code").getStringValue();

				callback.setAppid(appid).setMch_id(mch_id)
						.setDevice_info(device_info).setNonce_str(nonce_str)
						.setSign(sign).setResult_code(result_code);

				if (result_code.equals("SUCCESS")) {
					String trade_type = e.element("trade_type")
							.getStringValue();
					String prepay_id = e.element("prepay_id").getStringValue();
					callback.setTrade_type(trade_type).setPrepay_id(prepay_id);
					if (trade_type.equals("NATIVE")) {
						String code_url = e.element("code_url")
								.getStringValue();
						callback.setCode_url(code_url);
					}
				} else {
					String err_code = e.element("err_code").getStringValue();
					String err_code_des = e.element("err_code_des")
							.getStringValue();
					callback.setErr_code(err_code)
							.setErr_code_des(err_code_des);
				}
				return callback;
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return callback;
	}

	/**
	 * 扫码支付，后台接收xml数据解析成对象
	 * 
	 * @param XMLStr
	 *            xml字符串
	 * @return
	 */
	public static ScanpayBean xml2ScanpayBean(String XMLStr) {
		ScanpayBean bean = null;
		Document document;
		try {
			bean = new ScanpayBean();
			document = DocumentHelper.parseText(XMLStr);
			Element e = document.getRootElement();

			String appid = e.element("appid").getStringValue();
			String is_subscribe = e.element("is_subscribe").getStringValue();
			String mch_id = e.element("mch_id").getStringValue();
			String nonce_str = e.element("nonce_str").getStringValue();
			String openid = e.element("openid").getStringValue();
			String product_id = e.element("product_id").getStringValue();
			String sign = e.element("sign").getStringValue();

			bean.setAppid(appid).setIs_subscribe(is_subscribe)
					.setMch_id(mch_id).setNonce_str(nonce_str)
					.setOpenid(openid).setProduct_id(product_id).setSign(sign);
		} catch (DocumentException e1) {
			e1.printStackTrace();
			return bean;
		}
		return bean;
	}

	/**
	 * 微信支付时间戳
	 * 
	 * @return
	 */
	public static String createTimestamp() {
		Date date = new Date();
		long timestamp = date.getTime() / 1000;
		return String.valueOf(timestamp);
	}

}
