package org.swchalu.wxpay.scanpay;

/**
 * 扫码支付实体类
 * 
 * @author swchalu
 *
 */
public class ScanpayBean {
	private String appid;
	private String openid;
	private String mch_id;
	private String is_subscribe;
	private String nonce_str;
	private String product_id;
	private String sign;

	public String getAppid() {
		return appid;
	}

	public ScanpayBean setAppid(String appid) {
		this.appid = appid;
		return this;
	}

	public String getOpenid() {
		return openid;
	}

	public ScanpayBean setOpenid(String openid) {
		this.openid = openid;
		return this;
	}

	public String getMch_id() {
		return mch_id;
	}

	public ScanpayBean setMch_id(String mch_id) {
		this.mch_id = mch_id;
		return this;
	}

	public String getIs_subscribe() {
		return is_subscribe;
	}

	public ScanpayBean setIs_subscribe(String is_subscribe) {
		this.is_subscribe = is_subscribe;
		return this;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public ScanpayBean setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
		return this;
	}

	public String getProduct_id() {
		return product_id;
	}

	public ScanpayBean setProduct_id(String product_id) {
		this.product_id = product_id;
		return this;
	}

	public String getSign() {
		return sign;
	}

	public ScanpayBean setSign(String sign) {
		this.sign = sign;
		return this;
	}

	public String toString() {
		StringBuffer out = new StringBuffer("");
		// getAppid
		if (getAppid() != null && getAppid().length() > 0)
			out.append(out.length() > 0 ? "&appid=" + getAppid() : "appid="
					+ getAppid());
		// getIs_subscribe
		if (getIs_subscribe() != null && getIs_subscribe().length() > 0)
			out.append(out.length() > 0 ? "&is_subscribe=" + getIs_subscribe()
					: "is_subscribe=" + getIs_subscribe());
		// getMch_id
		if (getMch_id() != null && getMch_id().length() > 0)
			out.append(out.length() > 0 ? "&mch_id=" + getMch_id() : "mch_id="
					+ getMch_id());
		// getNonce_str
		if (getNonce_str() != null && getNonce_str().length() > 0)
			out.append(out.length() > 0 ? "&nonce_str=" + getNonce_str()
					: "nonce_str=" + getNonce_str());
		// getOpenid
		if (getOpenid() != null && getOpenid().length() > 0)
			out.append(out.length() > 0 ? "&openid=" + getOpenid() : "openid="
					+ getOpenid());
		// getProduct_id
		if (getProduct_id() != null && getProduct_id().length() > 0)
			out.append(out.length() > 0 ? "&product_id=" + getProduct_id()
					: "product_id=" + getProduct_id());
		return out.toString();
	}
}
