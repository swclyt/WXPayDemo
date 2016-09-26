package org.swchalu.wxpay.scanpay;

import org.swchalu.wxpay.common.WXPayContants;
import org.swchalu.wxpay.prepay.Prepay;
import org.swchalu.wxpay.prepay.PrepayCallback;
import org.swchalu.wxpay.utils.WXPayUtil;

/**
 * 扫码支付
 * 
 * @author swchalu
 *
 */
public class Scanpay {

	/**
	 * 扫码支付下单
	 * 
	 * @param bean
	 * @param ip
	 * @return
	 */
	public static ScanpayCallback order(ScanpayBean bean, String ip) {

		ScanpayCallback back = new ScanpayCallback();

		// 验证签名是否正确
		boolean isVaild = WXPayUtil.validSign(bean.toString(), bean.getSign());
		System.out.println("验证sign是否正确,验证串[ " + bean.toString() + " ],验证结果[ "
				+ isVaild + " ]");
		if (!isVaild) {
			back.setReturn_code(WXPayContants.FAIL).setReturn_msg("签名失败");
			return back;
		}

		// 验证通过，输入基本参数
		back.setReturn_code(WXPayContants.SUCCESS)
				.setAppid(WXPayContants.getAppid())
				.setMch_id(WXPayContants.getMch_id())
				.setNonce_str(WXPayUtil.getNonceStr());

		String product_id = bean.getProduct_id();
		// 根据product_id，可以决定代表商品数据
		String body = "微信扫码支付测试";
		String detail = "微信扫码支付测试详细";
		String fee = "1";

		// 验证二维码参数
		if (!bean.getAppid().equals(WXPayContants.getAppid())) {
			back.setResult_code(WXPayContants.FAIL).setErr_code_des("公众账号ID不符");
			back.setSign(WXPayUtil.sign(back.toParamStr()));
			return back;
		}

		if (!bean.getMch_id().equals(WXPayContants.getMch_id())) {
			back.setResult_code(WXPayContants.FAIL).setErr_code_des("商户号不符");
			back.setSign(WXPayUtil.sign(back.toParamStr()));
			return back;
		}

		// 产生统一下单
		PrepayCallback prepayCallback = Prepay.scanOrder(bean.getOpenid(),
				body, detail, ip, fee);

		// 解析返回数据
		// 服务器端错误识别
		if (prepayCallback.getReturn_code().equals(WXPayContants.FAIL)) {
			back.setResult_code(WXPayContants.FAIL)
					.setErr_code_des("商户服务器签名失败");
			back.setSign(WXPayUtil.sign(back.toParamStr()));
			return back;
		}

		// 统一下单返回错误识别
		if (prepayCallback.getResult_code().equals(WXPayContants.FAIL)) {
			back.setResult_code(WXPayContants.FAIL).setErr_code_des(
					prepayCallback.getErr_code_des());
			back.setSign(WXPayUtil.sign(back.toParamStr()));
			return back;
		}

		// 输入预支付订单号
		back.setPrepay_id(prepayCallback.getPrepay_id());
		back.setResult_code(WXPayContants.SUCCESS);
		back.setSign(WXPayUtil.sign(back.toParamStr()));

		return back;
	}
}
