# WXPayDemo
 2016-06-01
 给后台的demo，包含公众号(H5网页)支付，扫码模式一，模式二为公众号(H5网页)支付的后半步骤
 2016-10-11
 小更新，添加了微信支付订单查询...
 
 转载请附上 [WXPayDemo](https://github.com/swclyt/WXPayDemo) 项目的github链接...
 
 尊重作者劳动成果...
# 目录
 - [*公众号(H5网页)支付](#H5pay)

## <a name="H5pay">*公众号(H5网页)支付</a>
 1) 先在微信公众平台设置OAuth2授权回调路径，在接口权限里面

 ![接口权限](http://ww3.sinaimg.cn/large/8589667bgw1f4fi2e8xnqj207u05sglo.jpg)

 ![网页授权](http://ww1.sinaimg.cn/large/8589667bgw1f4fi5epse8j20mn01njri.jpg)

 ![回调域名](http://ww4.sinaimg.cn/large/8589667bgw1f4fi67yzysj20jw092wfw.jpg)

 2）然后微信支付的授权接口如下
 ``` bash
  https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect
 ```
 具体参数，看[文档](https://mp.weixin.qq.com/wiki/17/c0f37d5704f0b64713d5d2c37b468d75.html)

 3）回调接收参数是以 redirect_uri ? code=CODE & state=State ，所以redirect_uri必须是不带参数的url

 4）获得code之后就能用我写的预支付来进行统一下单了

 5）支付相关在预支付里面说明

## *扫码支付(特指模式一)
1）先在微信支付菜单里面，设置好扫码支付回调url，`注意的是这个url是以接收微信xml数据，接收部分没写进来，拿到xml可用TestExample里面的去转换成对象`

![微信支付菜单](http://ww2.sinaimg.cn/large/8589667bgw1f4fih5mggdj206u03m0sq.jpg)

![扫码支付回调url](http://ww2.sinaimg.cn/large/8589667bgw1f4fio8q14sj20hx04jdg7.jpg)

具体参数，看[文档](https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=6_4)

`模式二在下方的预支付下单中说明`

2）支付相关在预支付里面说明

## *预支付下单(最重要的部分)
预支付下单是公众号(H5网页)支付和扫码支付最重要的地方，涉及调用微信App支付相关参数获取

分为一下几个部分
  - 公众号(H5网页)支付下单
  - 扫码支付(模式一)下单
  - 相关区别

### 配置相关参数
在我的 [WXPayDemo/WXPay/src/org/swchalu/wxpay/common/WXPayContants.java](https://github.com/swclyt/WXPayDemo/blob/master/WXPay/src/org/swchalu/wxpay/common/WXPayContants.java) 中配置

具体参数内容，自行进链接看，有注释应该很清楚...

### 公众号(H5网页)支付下单
 - 流程如下

授权 -> 获得code及state -> 利用code去获得AccessToken及openid(关键参数) -> 组成预支付参数xml数据 -> post提交预支付 -> 获得返回的prepay_id -> 调用js启动app微信支付功能 -> 完成支付 -> 跳转结果页面

- 授权及获得code等

略过，前面说了

 - 获得AccessToken及openid
 
 在我的 [WXPayDemo/WXPay/src/org/swchalu/wxpay/utils/OauthUtils.java](https://github.com/swclyt/WXPayDemo/blob/master/WXPay/src/org/swchalu/wxpay/utils/OauthUtils.java) 中写了两个方法
 
 ``` java 
  OauthUtils.class
   //通过授权得到的code获取token的json字符串
   public static JSON getTokenJson(String code) {}
   //通过token的json字符串转对象
   public static AccessToken getAccessToken(String code) {}
 ```
 
 使用的时候直接调下面的方法解析json并转成 [AccessToken](https://github.com/swclyt/WXPayDemo/blob/master/WXPay/src/org/swchalu/wxpay/bean/AccessToken.java) 对象
 
 通过 AccessToken.getOpenid() 拿到openid
 
 - 组xml数据、post提交、获得返回prepay_id
 
 见 [WXPayDemo/WXPay/src/org/swchalu/wxpay/prepay/Prepay.java](https://github.com/swclyt/WXPayDemo/blob/master/WXPay/src/org/swchalu/wxpay/prepay/Prepay.java)
 
 里面封装好一个order方法，仅限于公众号(H5)支付，并post到微信支付系统及解析返回xml数据，最后转换成对象。代码就不多说了...
 
 `prepay_id 根据微信支付文档可以知道，仅当 return_code 及 result_code 同时为 SUCCESS 时才有此字段，届时可以直接通过 Prepay.order(...) 得到的 PrepayCallback 的 getPrepay_id() 是否为null判断`
 
 - js调用支付
 
 ``` javascript 
 function pay(data) {
		var appId = data.appId;
		var timeStamp = data.timeStamp;
		var nonceStr = data.nonceStr;
		var packageStr = data.packageStr;
		var signType = data.signType;
		var paySign = data.paySign;
		WeixinJSBridge.invoke('getBrandWCPayRequest', {
			"appId" : appId, //公众号名称，由商户传入     
			"timeStamp" : timeStamp, //时间戳，自1970年以来的秒数     
			"nonceStr" : nonceStr, //随机串     
			"package" : packageStr,
			"signType" : signType, //微信签名方式:     
			"paySign" : paySign
		//微信签名 
		}, function(res) {
			//if (res.err_msg == "get_brand_wcpay_request:ok") {
			alert(res.err_msg);
			//} // 使用以上方式判断前端返回,微信团队郑重提示:res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。 
		});
	}
	if (typeof WeixinJSBridge == "undefined") {
		if (document.addEventListener) {
			document.addEventListener('WeixinJSBridgeReady', onBridgeReady,
					false);
		} else if (document.attachEvent) {
			document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
			document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
		}
	}
	``` 
	上面的js代码来自微信支付文档，不是我写的，不要问为什么...
	
	之后就会弹出微信支付控件，完成支付，判断结果跳转显示结果页面就好了

### 扫码支付(模式一)下单
 - 流程如下

生成二维码 -> 微信扫码回调到商户链接 -> 后台解析xml数据 -> 组成预支付参数xml数据 -> post提交预支付 -> 获得返回xml -> 重组xml数据返回微信 -> 完成支付

 - 生成二维码
 
 二维码中的内容为链接，形式为：

 weixin：//wxpay/bizpayurl?sign=XXXXX&appid=XXXXX&mch_id=XXXXX&product_id=XXXXXX&time_stamp=XXXXXX&nonce_str=XXXXX
 
 参数请看[文档](https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=6_4)
 
 - 后台解析xml数据
 
 接收部分自行完成，获得xml字符串，调用如下，将xml字符串转换成 [ScanpayBean](https://github.com/swclyt/WXPayDemo/blob/master/WXPay/src/org/swchalu/wxpay/scanpay/ScanpayBean.java) 对象

 ``` java 
 // 解析xml为bean对象
 ScanpayBean bean = WXPayUtil.xml2ScanpayBean(XmlFromQR);
 ```
 - 组预支付xml数据、post数据、获得返回xml、重组xml数据
  我把全部封装起来了，预支付参数根据代码去改，调用如下。最后转换成 [ScanpayCallback](https://github.com/swclyt/WXPayDemo/blob/master/WXPay/src/org/swchalu/wxpay/scanpay/ScanpayCallback.java) 对象

 ``` java 
 // 预支付下单，解析返回xml,生成返回微信客户端支付对象
 ScanpayCallback back = Scanpay.order(bean, ip);

 // 输出对象的xml字符串到微信，自动调用支付
 String xmlStr = back.toXMLString();
 ``` 
 - 返回重组的xml、完成支付
  
  把上方的xmlStr直接输出给微信就可以，只要正常会自动调用支付控件，完成支付

### 相关区别
 - 公众号(H5网页)支付与扫码支付区别
  
  * 扫码支付分为，模式一和模式二，这两种模式是不一样的。
   
  模式一是通过二维码中的 product_id 到系统后台来判断产品是什么，达到一系统，多个产品二维码通用的效果。
 
  模式二呢，是公众号(H5网页)支付的后半的步骤，单独分出了的。在公众号(H5网页)支付的预支付下单之后，会有一个微信二维码链接。模式二就是扫的这个链接产生的二维码，微信判断连接有效就会调用支付。

  公众号(H5网页)支付是获得授权来得到，授权用户的openid，再给出页面给出多钟产品选择，或者用户自己输入金额。之后再去预支付下单，让页面调js，启动微信支付控件，完成支付。
  
  这3种流程都不一样，不要混为一谈，不知道我解释的对不对...
  
### 一些开发出现的错误交流

 - 很多都是在平台上配的链接不对导致的，注意看清楚要求

 - 商户后台返回数据字段结构不合法

  ![商户后台返回数据字段结构不合法](http://ww1.sinaimg.cn/large/8589667bgw1f4fkpnczd7j20ku112q6b.jpg)

  这个是出在扫码支付模式一的，是我一开始设置扫码预支付下单时候用了跟公众号(H5网页)支付一样的 trade_type ，jsapi然后的到的 prepay_id 不可用于扫码支付 ， 后来改成 native 就好了

 - package info not match special pay url 
  
  ![package info not match special pay url](http://ww4.sinaimg.cn/large/8589667bgw1f4fl2nw4anj20ku112tei.jpg)

  这个也是出现在扫码支付的，是刚开始开发，以为跟公众号(H5网页)支付一样是以 url 拼接参数传的值，结果不是，是xml数据post的方式。
