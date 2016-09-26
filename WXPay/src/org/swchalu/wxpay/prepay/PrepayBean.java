package org.swchalu.wxpay.prepay;

/**
 * 预支付下单实体类
 * 
 * @author swchalu
 *
 */
public class PrepayBean {
	/** 公众账号ID */
	private String appid;
	/** 商户号 */
	private String mch_id;
	/** 设备号 */
	private String device_info;
	/** 随机字符串 */
	private String nonce_str;
	/** 签名 */
	private String sign;
	/** 商品描述 */
	private String body;
	/** 商品详情 */
	private String detail;
	/** 附加数据 */
	private String attach;
	/** 商户订单号 */
	private String out_trade_no;
	/** 货币类型 */
	private String fee_type;
	/** 总金额(分) */
	private String total_fee;
	/** 终端IP */
	private String spbill_create_ip;
	/** 交易起始时间 */
	private String time_start;
	/** 交易结束时间 */
	private String time_expire;
	/** 商品标记 */
	private String goods_tag;
	/** 通知地址 */
	private String notify_url;
	/** 交易类型 */
	private String trade_type;
	/** 商品ID */
	private String product_id;
	/** 指定支付方式 */
	private String limit_pay;
	/** 用户标识 */
	private String openid;

	public String getAppid() {
		return appid;
	}

	public PrepayBean setAppid(String appid) {
		this.appid = appid;
		return this;
	}

	public String getMch_id() {
		return mch_id;
	}

	public PrepayBean setMch_id(String mch_id) {
		this.mch_id = mch_id;
		return this;
	}

	public String getDevice_info() {
		return device_info;
	}

	public PrepayBean setDevice_info(String device_info) {
		this.device_info = device_info;
		return this;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public PrepayBean setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
		return this;
	}

	public String getSign() {
		return sign;
	}

	public PrepayBean setSign(String sign) {
		this.sign = sign;
		return this;
	}

	public String getBody() {
		return body;
	}

	public PrepayBean setBody(String body) {
		this.body = body;
		return this;
	}

	public String getDetail() {
		return detail;
	}

	public PrepayBean setDetail(String detail) {
		this.detail = detail;
		return this;
	}

	public String getAttach() {
		return attach;
	}

	public PrepayBean setAttach(String attach) {
		this.attach = attach;
		return this;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public PrepayBean setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
		return this;
	}

	public String getFee_type() {
		return fee_type;
	}

	public PrepayBean setFee_type(String fee_type) {
		this.fee_type = fee_type;
		return this;
	}

	public String getTotal_fee() {
		return total_fee;
	}

	public PrepayBean setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
		return this;
	}

	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}

	public PrepayBean setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
		return this;
	}

	public String getTime_start() {
		return time_start;
	}

	public PrepayBean setTime_start(String time_start) {
		this.time_start = time_start;
		return this;
	}

	public String getTime_expire() {
		return time_expire;
	}

	public PrepayBean setTime_expire(String time_expire) {
		this.time_expire = time_expire;
		return this;
	}

	public String getGoods_tag() {
		return goods_tag;
	}

	public PrepayBean setGoods_tag(String goods_tag) {
		this.goods_tag = goods_tag;
		return this;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public PrepayBean setNotify_url(String notify_url) {
		this.notify_url = notify_url;
		return this;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public PrepayBean setTrade_type(String trade_type) {
		this.trade_type = trade_type;
		return this;
	}

	public String getProduct_id() {
		return product_id;
	}

	public PrepayBean setProduct_id(String product_id) {
		this.product_id = product_id;
		return this;
	}

	public String getLimit_pay() {
		return limit_pay;
	}

	public PrepayBean setLimit_pay(String limit_pay) {
		this.limit_pay = limit_pay;
		return this;
	}

	public String getOpenid() {
		return openid;
	}

	public PrepayBean setOpenid(String openid) {
		this.openid = openid;
		return this;
	}

	/**
	 * 输出PrepayBean的参数字符串
	 */
	public String toString() {
		StringBuffer out = new StringBuffer("");
		// getAppid
		if (getAppid() != null && getAppid().length() > 0)
			out.append(out.length() > 0 ? "&appid=" + getAppid() : "appid="
					+ getAppid());
		// getAttach
		if (getAttach() != null && getAttach().length() > 0)
			out.append(out.length() > 0 ? "&attach=" + getAttach() : "attach="
					+ getAttach());
		// getBody
		if (getBody() != null && getBody().length() > 0)
			out.append(out.length() > 0 ? "&body=" + getBody() : "body="
					+ getBody());
		// getDetail
		if (getDetail() != null && getDetail().length() > 0)
			out.append(out.length() > 0 ? "&detail=" + getDetail() : "detail="
					+ getDetail());
		// getDevice_info
		if (getDevice_info() != null && getDevice_info().length() > 0)
			out.append(out.length() > 0 ? "&device_info=" + getDevice_info()
					: "device_info=" + getDevice_info());
		// getFee_type
		if (getFee_type() != null && getFee_type().length() > 0)
			out.append(out.length() > 0 ? "&fee_type=" + getFee_type()
					: "fee_type=" + getFee_type());
		// getGoods_tag
		if (getGoods_tag() != null && getGoods_tag().length() > 0)
			out.append(out.length() > 0 ? "&goods_tag=" + getGoods_tag()
					: "goods_tag=" + getGoods_tag());
		// getLimit_pay
		if (getLimit_pay() != null && getLimit_pay().length() > 0)
			out.append(out.length() > 0 ? "&limit_pay=" + getLimit_pay()
					: "limit_pay=" + getLimit_pay());
		// getMch_id
		if (getMch_id() != null && getMch_id().length() > 0)
			out.append(out.length() > 0 ? "&mch_id=" + getMch_id() : "mch_id="
					+ getMch_id());
		// getNonce_str
		if (getNonce_str() != null && getNonce_str().length() > 0)
			out.append(out.length() > 0 ? "&nonce_str=" + getNonce_str()
					: "nonce_str=" + getNonce_str());
		// getNot//ify_url
		if (getNotify_url() != null && getNotify_url().length() > 0)
			out.append(out.length() > 0 ? "&notify_url=" + getNotify_url()
					: "notify_url=" + getNotify_url());
		// getOpenid
		if (getOpenid() != null && getOpenid().length() > 0)
			out.append(out.length() > 0 ? "&openid=" + getOpenid() : "openid="
					+ getOpenid());
		// getOut_trade_no
		if (getOut_trade_no() != null && getOut_trade_no().length() > 0)
			out.append(out.length() > 0 ? "&out_trade_no=" + getOut_trade_no()
					: "out_trade_no=" + getOut_trade_no());
		// getProduct_id
		if (getProduct_id() != null && getProduct_id().length() > 0)
			out.append(out.length() > 0 ? "&product_id=" + getProduct_id()
					: "product_id=" + getProduct_id());
		// getSign
		if (getSign() != null && getSign().length() > 0)
			out.append(out.length() > 0 ? "&sign=" + getSign() : "sign="
					+ getSign());
		// getSpbill_create_ip
		if (getSpbill_create_ip() != null && getSpbill_create_ip().length() > 0)
			out.append(out.length() > 0 ? "&spbill_create_ip="
					+ getSpbill_create_ip() : "spbill_create_ip="
					+ getSpbill_create_ip());
		// getTime_expire
		if (getTime_expire() != null && getTime_expire().length() > 0)
			out.append(out.length() > 0 ? "&time_expire=" + getTime_expire()
					: "time_expire=" + getTime_expire());
		// getTime_start
		if (getTime_start() != null && getTime_start().length() > 0)
			out.append(out.length() > 0 ? "&time_start=" + getTime_start()
					: "time_start=" + getTime_start());
		// getTotal_fee
		if (getTotal_fee() != null && getTotal_fee().length() > 0)
			out.append(out.length() > 0 ? "&total_fee=" + getTotal_fee()
					: "total_fee=" + getTotal_fee());
		// getTrade_type
		if (getTrade_type() != null && getTrade_type().length() > 0)
			out.append(out.length() > 0 ? "&trade_type=" + getTrade_type()
					: "trade_type=" + getTrade_type());

		return out.toString();
	}

	/**
	 * 输出xml格式字符串
	 * 
	 * @return
	 */
	public String toXMLString() {
		StringBuffer out = new StringBuffer("");
		out.append("<xml>");
		// getAppid
		if (getAppid() != null && getAppid().length() > 0)
			out.append("<appid>" + getAppid() + "</appid>");
		// getAttach
		if (getAttach() != null && getAttach().length() > 0)
			out.append("<attach>" + getAttach() + "</attach>");
		// getBody
		if (getBody() != null && getBody().length() > 0)
			out.append("<body>" + getBody() + "</body>");
		// getDetail
		if (getDetail() != null && getDetail().length() > 0)
			out.append("<detail>" + getDetail() + "</detail>");
		// getDevice_info
		if (getDevice_info() != null && getDevice_info().length() > 0)
			out.append("<device_info>" + getDevice_info() + "</device_info>");
		// getFee_type
		if (getFee_type() != null && getFee_type().length() > 0)
			out.append("<fee_type>" + getFee_type() + "</fee_type>");
		// getGoods_tag
		if (getGoods_tag() != null && getGoods_tag().length() > 0)
			out.append("<goods_tag>" + getGoods_tag() + "</goods_tag>");
		// getLimit_pay
		if (getLimit_pay() != null && getLimit_pay().length() > 0)
			out.append("<limit_pay>" + getLimit_pay() + "</limit_pay>");
		// getMch_id
		if (getMch_id() != null && getMch_id().length() > 0)
			out.append("<mch_id>" + getMch_id() + "</mch_id>");
		// getNonce_str
		if (getNonce_str() != null && getNonce_str().length() > 0)
			out.append("<nonce_str>" + getNonce_str() + "</nonce_str>");
		// getNot//ify_url
		if (getNotify_url() != null && getNotify_url().length() > 0)
			out.append("<notify_url>" + getNotify_url() + "</notify_url>");
		// getOpenid
		if (getOpenid() != null && getOpenid().length() > 0)
			out.append("<openid>" + getOpenid() + "</openid>");
		// getOut_trade_no
		if (getOut_trade_no() != null && getOut_trade_no().length() > 0)
			out.append("<out_trade_no>" + getOut_trade_no() + "</out_trade_no>");
		// getProduct_id
		if (getProduct_id() != null && getProduct_id().length() > 0)
			out.append("<product_id>" + getProduct_id() + "</product_id>");
		// getSign
		if (getSign() != null && getSign().length() > 0)
			out.append("<sign>" + getSign() + "</sign>");
		// getSpbill_create_ip
		if (getSpbill_create_ip() != null && getSpbill_create_ip().length() > 0)
			out.append("<spbill_create_ip>" + getSpbill_create_ip()
					+ "</spbill_create_ip>");
		// getTime_expire
		if (getTime_expire() != null && getTime_expire().length() > 0)
			out.append("<time_expire>" + getTime_expire() + "</time_expire>");
		// getTime_start
		if (getTime_start() != null && getTime_start().length() > 0)
			out.append("<time_start>" + getTime_start() + "</time_start>");
		// getTotal_fee
		if (getTotal_fee() != null && getTotal_fee().length() > 0)
			out.append("<total_fee>" + getTotal_fee() + "</total_fee>");
		// getTrade_type
		if (getTrade_type() != null && getTrade_type().length() > 0)
			out.append("<trade_type>" + getTrade_type() + "</trade_type>");

		out.append("</xml>");
		return out.toString();
	}
}
