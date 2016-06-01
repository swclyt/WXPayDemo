package org.swchalu.wxpay.scanpay;

public class ScanpayCallback {
	private String return_code;
	private String return_msg;
	private String appid;
	private String mch_id;
	private String nonce_str;
	private String prepay_id;
	private String result_code;
	private String err_code_des;
	private String sign;

	public String getReturn_code() {
		return return_code;
	}

	public ScanpayCallback setReturn_code(String return_code) {
		this.return_code = return_code;
		return this;
	}

	public String getReturn_msg() {
		return return_msg;
	}

	public ScanpayCallback setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
		return this;
	}

	public String getAppid() {
		return appid;
	}

	public ScanpayCallback setAppid(String appid) {
		this.appid = appid;
		return this;
	}

	public String getMch_id() {
		return mch_id;
	}

	public ScanpayCallback setMch_id(String mch_id) {
		this.mch_id = mch_id;
		return this;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public ScanpayCallback setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
		return this;
	}

	public String getPrepay_id() {
		return prepay_id;
	}

	public ScanpayCallback setPrepay_id(String prepay_id) {
		this.prepay_id = prepay_id;
		return this;
	}

	public String getResult_code() {
		return result_code;
	}

	public ScanpayCallback setResult_code(String result_code) {
		this.result_code = result_code;
		return this;
	}

	public String getErr_code_des() {
		return err_code_des;
	}

	public ScanpayCallback setErr_code_des(String err_code_des) {
		this.err_code_des = err_code_des;
		return this;
	}

	public String getSign() {
		return sign;
	}

	public ScanpayCallback setSign(String sign) {
		this.sign = sign;
		return this;
	}

	public String toParamStr() {
		StringBuffer out = new StringBuffer("");
		// getAppid
		out.append(out.length() > 0 ? "&appid=" + getAppid() : "appid="
				+ getAppid());

		// getErr_code_des
		if (getErr_code_des() != null && getErr_code_des().length() > 0)
			out.append(out.length() > 0 ? "&err_code_des=" + getErr_code_des()
					: "err_code_des=" + getErr_code_des());

		// getMch_id
		out.append(out.length() > 0 ? "&mch_id=" + getMch_id() : "mch_id="
				+ getMch_id());

		// getNonce_str
		out.append(out.length() > 0 ? "&nonce_str=" + getNonce_str()
				: "nonce_str=" + getNonce_str());

		// getPrepay_id
		out.append(out.length() > 0 ? "&prepay_id=" + getPrepay_id()
				: "err_code_des=" + getPrepay_id());

		// getResult_code
		out.append(out.length() > 0 ? "&result_code=" + getResult_code()
				: "result_code=" + getResult_code());

		// getReturn_code
		out.append(out.length() > 0 ? "&return_code=" + getReturn_code()
				: "return_code=" + getReturn_code());

		// getReturn_msg
		if (getReturn_msg() != null && getReturn_msg().length() > 0)
			out.append(out.length() > 0 ? "&return_msg=" + getReturn_msg()
					: "return_msg=" + getReturn_msg());

		return out.toString();
	}

	public String toXMLString() {
		StringBuffer out = new StringBuffer("");
		out.append("<xml>");
		out.append("<return_code><![CDATA[" + getReturn_code()
				+ "]]></return_code>");
		if (!getReturn_code().equals("SUCCESS"))
			out.append("<return_msg><![CDATA[" + getReturn_msg()
					+ "]]></return_msg>");
		else {
			out.append("<appid><![CDATA[" + getAppid() + "]]></appid>");
			out.append("<mch_id><![CDATA[" + getMch_id() + "]]></mch_id>");
			out.append("<nonce_str><![CDATA[" + getNonce_str()
					+ "]]></nonce_str>");
			out.append("<result_code><![CDATA[" + getResult_code()
					+ "]]></result_code>");
			if (!getResult_code().equals("SUCCESS")) {
				out.append("<err_code_des><![CDATA[" + getErr_code_des()
						+ "]]></err_code_des>");
			} else {
				out.append("<prepay_id><![CDATA[" + getPrepay_id()
						+ "]]></prepay_id>");
			}
			out.append("<sign><![CDATA[" + getSign() + "]]></sign>");
		}
		out.append("</xml>");

		System.out.println("ScanpayCallback->xml : " + out.toString());
		return out.toString();
	}
}
