package org.swchalu.wxpay.prepay;

/**
 * 预支付下单返回实体类
 * 
 * @author swchalu
 *
 */
public class PrepayCallback {
	private String return_code;
	private String return_msg;
	private String appid;
	private String mch_id;
	private String device_info;
	private String nonce_str;
	private String sign;
	private String result_code;
	private String err_code;
	private String err_code_des;
	private String trade_type;
	private String prepay_id;
	private String code_url;

	public String getReturn_code() {
		return return_code;
	}

	public PrepayCallback setReturn_code(String return_code) {
		this.return_code = return_code;
		return this;
	}

	public String getReturn_msg() {
		return return_msg;
	}

	public PrepayCallback setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
		return this;
	}

	public String getAppid() {
		return appid;
	}

	public PrepayCallback setAppid(String appid) {
		this.appid = appid;
		return this;
	}

	public String getMch_id() {
		return mch_id;
	}

	public PrepayCallback setMch_id(String mch_id) {
		this.mch_id = mch_id;
		return this;
	}

	public String getDevice_info() {
		return device_info;
	}

	public PrepayCallback setDevice_info(String device_info) {
		this.device_info = device_info;
		return this;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public PrepayCallback setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
		return this;
	}

	public String getSign() {
		return sign;
	}

	public PrepayCallback setSign(String sign) {
		this.sign = sign;
		return this;
	}

	public String getResult_code() {
		return result_code;
	}

	public PrepayCallback setResult_code(String result_code) {
		this.result_code = result_code;
		return this;
	}

	public String getErr_code() {
		return err_code;
	}

	public PrepayCallback setErr_code(String err_code) {
		this.err_code = err_code;
		return this;
	}

	public String getErr_code_des() {
		return err_code_des;
	}

	public PrepayCallback setErr_code_des(String err_code_des) {
		this.err_code_des = err_code_des;
		return this;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public PrepayCallback setTrade_type(String trade_type) {
		this.trade_type = trade_type;
		return this;
	}

	public String getPrepay_id() {
		return prepay_id;
	}

	public PrepayCallback setPrepay_id(String prepay_id) {
		this.prepay_id = prepay_id;
		return this;
	}

	public String getCode_url() {
		return code_url;
	}

	public PrepayCallback setCode_url(String code_url) {
		this.code_url = code_url;
		return this;
	}

	public String toString() {
		StringBuffer out = new StringBuffer("");
		out.append(PrepayCallback.class.getName() + "[ \n");

		if (getReturn_code() != null)
			out.append("\treturn_code = " + getReturn_code() + ";\n");
		if (getReturn_msg() != null)
			out.append("\treturn_msg = " + getReturn_msg() + ";\n");

		if (getAppid() != null)
			out.append("\tappid = " + getAppid() + ";\n");
		if (getMch_id() != null)
			out.append("\tmch_id = " + getMch_id() + ";\n");
		if (getDevice_info() != null)
			out.append("\tdevice_info = " + getDevice_info() + ";\n");
		if (getNonce_str() != null)
			out.append("\tnonce_str = " + getNonce_str() + ";\n");
		if (getSign() != null)
			out.append("\tsign = " + getSign() + ";\n");
		if (getResult_code() != null)
			out.append("\tresult_code = " + getResult_code() + ";\n");
		if (getErr_code() != null)
			out.append("\terr_code = " + getErr_code() + ";\n");
		if (getErr_code_des() != null)
			out.append("\terr_code_des = " + getErr_code_des() + ";\n");

		if (getTrade_type() != null)
			out.append("\ttrade_type = " + getTrade_type() + ";\n");
		if (getPrepay_id() != null)
			out.append("\tprepay_id = " + getPrepay_id() + ";\n");
		if (getCode_url() != null)
			out.append("\tcode_url = " + getCode_url() + ";\n");

		out.append(" ]");
		return out.toString();
	}
}
