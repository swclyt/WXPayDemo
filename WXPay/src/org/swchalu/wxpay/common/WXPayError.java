package org.swchalu.wxpay.common;

public class WXPayError {
	/** 商户无此接口权限 */
	public static final String NOAUTH = "NOAUTH";
	/** 余额不足 */
	public static final String NOTENOUGH = "NOTENOUGH";
	/** 商户订单已支付 */
	public static final String ORDERPAID = "ORDERPAID";
	/** 订单已关闭 */
	public static final String ORDERCLOSED = "ORDERCLOSED";
	/** 系统错误 */
	public static final String SYSTEMERROR = "SYSTEMERROR";
	/** APPID不存在 */
	public static final String APPID_NOT_EXIST = "APPID_NOT_EXIST";
	/** MCHID不存在 */
	public static final String MCHID_NOT_EXIST = "MCHID_NOT_EXIST";
	/** appid和mch_id不匹配 */
	public static final String APPID_MCHID_NOT_MATCH = "APPID_MCHID_NOT_MATCH";
	/** 缺少参数 */
	public static final String LACK_PARAMS = "LACK_PARAMS";
	/** 商户订单号重复 */
	public static final String OUT_TRADE_NO_USED = "OUT_TRADE_NO_USED";
	/** 签名错误 */
	public static final String SIGNERROR = "SIGNERROR";
	/** XML格式错误 */
	public static final String XML_FORMAT_ERROR = "XML_FORMAT_ERROR";
	/** 请使用post方法 */
	public static final String REQUIRE_POST_METHOD = "REQUIRE_POST_METHOD";
	/** post数据为空 */
	public static final String POST_DATA_EMPTY = "POST_DATA_EMPTY";
	/** 编码格式错误 */
	public static final String NOT_UTF8 = "NOT_UTF8";
	/** 交易状态-支付成功 */
	public static final String TRADE_STATE_SUCCESS = "SUCCESS";
	/** 交易状态—转入退款 */
	public static final String TRADE_STATE_REFUND = "REFUND";
	/** 交易状态—未支付 */
	public static final String TRADE_STATE_NOTPAY = "NOTPAY";
	/** 交易状态—已关闭 */
	public static final String TRADE_STATE_CLOSED = "CLOSED";
	/** 交易状态—已撤销（刷卡支付） */
	public static final String TRADE_STATE_REVOKED = "REVOKED";
	/** 交易状态—用户支付中 */
	public static final String TRADE_STATE_USERPAYING = "USERPAYING";
	/** 交易状态—支付失败(其他原因，如银行返回失败) */
	public static final String TRADE_STATE_PAYERROR = "PAYERROR";

}
