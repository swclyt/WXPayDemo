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
import org.swchalu.wxpay.orderquery.OrderQueryCallback;
import org.swchalu.wxpay.prepay.PrepayCallback;
import org.swchalu.wxpay.scanpay.ScanpayBean;

/**
 * 微信支付工具类
 * 
 * @author swchalu
 *
 */
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

	/**
	 * 微信订单查询返回xml解析成实体类
	 * 
	 * @param XMLStr
	 * @return
	 */
	public static OrderQueryCallback xml2OrderQueryCallback(String XMLStr) {
		// TODO Auto-generated method stub
		OrderQueryCallback callback = null;

		Document document;
		try {
			document = DocumentHelper.parseText(XMLStr);
			Element e = document.getRootElement();
			callback = new OrderQueryCallback();

			callback.setReturn_code(e.element("return_code").getStringValue());
			// return_msg 可空
			if (e.element("return_msg") != null)
				callback.setReturn_msg(e.element("return_msg").getStringValue());
			if (callback.getReturn_code() != null
					&& callback.getReturn_code().equals(WXPayContants.SUCCESS)) {
				callback.setAppid(e.element("appid").getStringValue());
				callback.setMch_id(e.element("mch_id").getStringValue());
				callback.setNonce_str(e.element("nonce_str").getStringValue());
				callback.setSign(e.element("sign").getStringValue());
				callback.setResult_code(e.element("result_code")
						.getStringValue());

				// err_code可空
				if (e.element("err_code") != null)
					callback.setErr_code(e.element("err_code").getStringValue());
				// err_code_des可空
				if (e.element("err_code_des") != null)
					callback.setErr_code_des(e.element("err_code_des")
							.getStringValue());

				if (callback.getResult_code() != null
						&& callback.getResult_code().equals(
								WXPayContants.SUCCESS)) {
					// device_info
					if (e.element("device_info") != null)
						callback.setDevice_info(e.element("device_info")
								.getStringValue());
					if (e.element("openid") != null)
						callback.setOpenid(e.element("openid").getStringValue());
					// is_subscribe
					if (e.element("is_subscribe") != null)
						callback.setIs_subscribe(e.element("is_subscribe")
								.getStringValue());
					if (e.element("trade_type") != null)
						callback.setTrade_type(e.element("trade_type")
								.getStringValue());
					if (e.element("trade_state") != null)
						callback.setTrade_state(e.element("trade_state")
								.getStringValue());
					if (e.element("bank_type") != null)
						callback.setBank_type(e.element("bank_type")
								.getStringValue());
					if (e.element("total_fee") != null)
						callback.setTotal_fee(e.element("total_fee")
								.getStringValue());
					// settlement_total_fee
					if (e.element("settlement_total_fee") != null)
						callback.setSettlement_total_fee(e.element(
								"settlement_total_fee").getStringValue());
					// fee_type
					if (e.element("fee_type") != null)
						callback.setFee_type(e.element("fee_type")
								.getStringValue());
					if (e.element("cash_fee") != null)
						callback.setCash_fee(e.element("cash_fee")
								.getStringValue());
					// cash_fee_type
					if (e.element("cash_fee_type") != null)
						callback.setCash_fee_type(e.element("cash_fee_type")
								.getStringValue());
					// coupon_fee
					if (e.element("coupon_fee") != null)
						callback.setCoupon_fee(e.element("coupon_fee")
								.getStringValue());
					// coupon_count
					if (e.element("coupon_count") != null)
						callback.setCoupon_count(e.element("coupon_count")
								.getStringValue());
					if (e.element("attach") != null)
						callback.setAttach(e.element("attach").getStringValue());
					if (e.element("transaction_id") != null)
						callback.setTransaction_id(e.element("transaction_id")
								.getStringValue());
					if (e.element("out_trade_no") != null)
						callback.setOut_trade_no(e.element("out_trade_no")
								.getStringValue());
					if (e.element("time_end") != null)
						callback.setTime_end(e.element("time_end")
								.getStringValue());
					if (e.element("trade_state_desc") != null)
						callback.setTrade_state_desc(e.element(
								"trade_state_desc").getStringValue());
				}
			}
			return callback;
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}
}
