package org.swchalu.wxpay;

import org.swchalu.wxpay.prepay.Prepay;
import org.swchalu.wxpay.prepay.PrepayCallback;
import org.swchalu.wxpay.scanpay.Scanpay;
import org.swchalu.wxpay.scanpay.ScanpayBean;
import org.swchalu.wxpay.scanpay.ScanpayCallback;
import org.swchalu.wxpay.utils.WXPayUtil;

/**
 * 演示使用方法 没法运行的，只是显示使用流程，注意先配置好WXPayContants相关参数
 * 
 * @author swchalu
 *
 */
public class TestExample {

	// 一般网页授权获得code开始
	/**
	 * 通过h5网页回调得到授权的code,进行预付款
	 * 
	 * @param code
	 *            授权回调code
	 * @param body
	 *            商品名称
	 * @param detail
	 *            商品详情
	 * @param ip
	 *            客户ip地址
	 * @param fee
	 *            交易金额，单位分
	 */
	public void payByH5(String code, String body, String detail, String ip,
			String fee) {
		// 直接完成预支付下单
		PrepayCallback callback = Prepay.order(code, body, detail, ip, fee);
		// 输出返回xml解析对象
		System.out.println("微信支付返回xml解析结果 -> " + callback.toString());
		// 输出预支付订单号Prepay_id,仅限return_code及result_code都为SUCCESS时有值
		System.out.println("预支付订单号Prepay_id -> " + callback.getPrepay_id());
	}

	/**
	 * 扫码支付，模式一
	 * 
	 * @param XmlFromQR
	 *            扫码后微信传给支付回调地址的xml字符串
	 * @param ip
	 *            客户ip地址
	 */
	public void scanpayByModeOne(String XmlFromQR, String ip) {

		// 解析xml为bean对象
		ScanpayBean bean = WXPayUtil.xml2ScanpayBean(XmlFromQR);

		// 预支付下单，解析返回xml,生成返回微信客户端支付对象
		ScanpayCallback back = Scanpay.order(bean, ip);

		// 输出对象的xml字符串到微信，自动调用支付
		String xmlStr = back.toXMLString();
		System.out.println("返回预支付结果的xml-> " + xmlStr);
	}
}
