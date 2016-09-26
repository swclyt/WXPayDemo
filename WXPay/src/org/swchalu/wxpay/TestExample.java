package org.swchalu.wxpay;

import org.swchalu.wxpay.common.WXPayError;
import org.swchalu.wxpay.orderquery.OrderQueryCallback;
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

	/**
	 * 微信支付订单查询，transaction_id和out_trade_no是二选一
	 * 
	 * @param transaction_id
	 *            微信给的订单号
	 * @param out_trade_no
	 *            自己系统后台保存的订单号，跟预支付下单的一致
	 * @param openid
	 */
	public void orderQuery(String transaction_id, String out_trade_no,
			String openid) {
		// transaction_id是微信给的订单号
		// out_trade_no是自己系统后台保存的订单号，跟预支付下单的一致
		// transaction_id和out_trade_no是二选一
		OrderQueryCallback callback = Prepay.orderQuery(transaction_id,
				out_trade_no, openid);
		if (callback != null) {
			String trade_state = callback.getTrade_state();
			// 根据自己的需求判读返回码
			if (trade_state.equals(WXPayError.TRADE_STATE_CLOSED)
					|| trade_state.equals(WXPayError.TRADE_STATE_PAYERROR)) {
			} else if (trade_state.equals(WXPayError.TRADE_STATE_SUCCESS)) {
			} else if (trade_state.equals(WXPayError.TRADE_STATE_REVOKED)
					|| trade_state.equals(WXPayError.TRADE_STATE_REFUND)) {
			} else {
			}
		}
	}
}
