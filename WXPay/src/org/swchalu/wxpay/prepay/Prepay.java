package org.swchalu.wxpay.prepay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.Random;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.swchalu.wxpay.bean.AccessToken;
import org.swchalu.wxpay.common.WXPayContants;
import org.swchalu.wxpay.utils.OauthUtils;
import org.swchalu.wxpay.utils.WXPayUtil;

public class Prepay {

	/**
	 * 统一下单
	 * 
	 * @param body
	 *            商品
	 * @param detail
	 *            商品详情
	 * @param openid
	 *            用户唯一标识
	 * @param out_trade_no
	 *            商户后台订单号
	 * @param ip
	 *            客户端ip地址
	 * @param fee
	 *            交易金额，单位分
	 * @param trade_type
	 *            交易类型
	 * @return
	 */
	public static PrepayCallback prepay(String body, String detail,
			String openid, String out_trade_no, String ip, String fee,
			String trade_type) {
		// 生成预支付bean
		PrepayBean bean = new PrepayBean();
		bean.setAppid(WXPayContants.getAppid())
				.setAttach(WXPayContants.getAttach()).setBody(body)
				.setDetail(detail)
				.setDevice_info(WXPayContants.device_info_web)
				.setFee_type(WXPayContants.getFee_type())
				.setMch_id(WXPayContants.getMch_id())
				.setNonce_str(WXPayUtil.getNonceStr())
				.setNotify_url(WXPayContants.getNotify_url()).setOpenid(openid)
				.setOut_trade_no(out_trade_no).setSpbill_create_ip(ip)
				.setTotal_fee(fee).setTrade_type(trade_type);
		String time_start = WXPayUtil.createTime_start();
		String time_expire = WXPayUtil.createTime_expire();
		bean.setTime_expire(time_expire).setTime_start(time_start);
		System.out.println(bean.toString());

		// 按照微信文档进行签名
		bean.setSign(WXPayUtil.sign(bean.toString()));
		System.out.println("sign -> " + bean.getSign());
		// 输出上传至微信的xml数据
		System.out.println("xml -> " + bean.toXMLString());

		// 以post方式上传数据
		String backXMLStr = post(bean.toXMLString());
		if (backXMLStr.length() > 0) {
			// 解析返回预支付结果
			PrepayCallback callback = WXPayUtil.xml2PrepayCallback(backXMLStr);
			System.out.println(callback.toString());
			return callback;
		}
		return null;
	}

	/**
	 * 下单
	 * 
	 * @param code
	 *            授权获得的code
	 * @param body
	 *            商品
	 * @param detail
	 *            商品详情
	 * @param ip
	 *            客户端ip地址
	 * @param fee
	 *            交易金额，单位分
	 * @return
	 */
	public static PrepayCallback order(String code, String body, String detail,
			String ip, String fee) {
		// 第一步拿到code
		// 第二步拿到openid
		AccessToken mAccessToken = OauthUtils.getAccessToken(code);
		String openid = mAccessToken.getOpenid();

		// 这里订单号以时间（14位）+随机数（4位）组成
		Random random = new Random();
		DecimalFormat df = new DecimalFormat("0000");
		int r = random.nextInt(1000);
		String out_trade_no = WXPayUtil.createTime_start() + df.format(r);

		// 第三步预支付下单
		return prepay(body, detail, openid, out_trade_no, ip, fee,
				WXPayContants.trade_type_jsapi);
	}

	/**
	 * 扫码支付下单
	 * 
	 * @param openid
	 *            用户唯一标识
	 * @param body
	 *            商品
	 * @param detail
	 *            商品详情
	 * @param ip
	 *            用户ip地址
	 * @param fee
	 *            交易金额，单位分
	 * @return
	 */
	public static PrepayCallback scanOrder(String openid, String body,
			String detail, String ip, String fee) {
		// 扫码支付，已有openid
		Random random = new Random();
		DecimalFormat df = new DecimalFormat("0000");
		int r = random.nextInt(1000);
		String out_trade_no = WXPayUtil.createTime_start() + df.format(r);

		return prepay(body, detail, openid, out_trade_no, ip, fee,
				WXPayContants.trade_type_native);
	}

	/**
	 * 以POST方式向微信发送预支付xml
	 * 
	 * @param xml
	 *            预支付数据xml字符串
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String post(String xml) {
		// 请求地址
		PostMethod post = new PostMethod(WXPayContants.getPrepay_url());
		String backXML = "";

		post.setRequestBody(xml);// 这里添加xml字符串
		post.setRequestHeader("Content-type", "text/xml; charset=utf-8");
		HttpClient httpclient = new HttpClient();// 创建 HttpClient 的实例
		int result;
		try {
			result = httpclient.executeMethod(post);
			System.out.println("Response status code: " + result);// 返回200为成功
			System.out.println("Response body: ");

			// 以输入流读取返回数据
			InputStream ins = post.getResponseBodyAsStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(ins,
					"utf-8"));
			StringBuffer sbf = new StringBuffer();
			String line = null;
			while ((line = br.readLine()) != null) {
				sbf.append(line);
			}
			br.close();

			System.out.println(sbf.toString());// 返回的内容
			post.releaseConnection();// 释放连接
			backXML = sbf.toString();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return backXML;
	}
}
