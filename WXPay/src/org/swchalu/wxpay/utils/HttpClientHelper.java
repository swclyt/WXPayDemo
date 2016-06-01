package org.swchalu.wxpay.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.cookie.NetscapeDraftSpec;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpClientHelper {
	public static Logger log = Logger.getLogger(HttpClientHelper.class);
	private static final String COOKIE_POLICY_CUSTOM = "custom";
	public static final int CONNECT_TIMEOUT2 = 10;
	public static final int SO_TIMEOUT2 = 30;
	public static final int RETRY_TIMES2 = 3;

	public static final String DEFAULT_CHARSET = "UTF-8";

	public static String doPost(DefaultHttpClient client, String postUrl,
			String referer, List<NameValuePair> params, int timeout) {
		return doPost(client, postUrl, referer, params, timeout,
				DEFAULT_CHARSET);
	}

	public static String doPost(DefaultHttpClient client, String postUrl,
			String referer, List<NameValuePair> params, int timeout,
			String encoding) {
		supportSSL(postUrl, client);
		HttpPost post = new HttpPost(postUrl);
		String result = null;

		if (params != null) {
			try {
				post.setEntity(new UrlEncodedFormEntity(params, encoding));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		try {
			HttpResponse response = executeHttpRequest(client, post, referer,
					timeout, encoding);
			result = getContent(response, encoding);

		} catch (ClientProtocolException e) {
			log.error("", e);
			throw new RuntimeException(e);
		} catch (IOException e) {
			log.error("", e);
			throw new RuntimeException(e);
		}
		return result;

	}

	public static String doPost(DefaultHttpClient client, String postUrl,
			String referer, String xmlData, int timeout, String encoding) {
		supportSSL(postUrl, client);
		HttpPost post = new HttpPost(postUrl);
		String result = null;

		if (xmlData != null) {
			try {
				StringEntity entity = new StringEntity(xmlData, encoding);
				post.setEntity(entity);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		try {
			HttpResponse response = executeHttpRequest(client, post, referer,
					timeout, encoding);
			result = getContent(response, encoding);

		} catch (ClientProtocolException e) {
			log.error("", e);
			throw new RuntimeException(e);
		} catch (IOException e) {
			log.error("", e);
			throw new RuntimeException(e);
		}
		return result;

	}

	public static String doGet(DefaultHttpClient client, String getUrl,
			String referer, int timeout) {
		return doGet(client, getUrl, referer, timeout, DEFAULT_CHARSET);
	}

	public static String doGet(DefaultHttpClient client, String getUrl,
			String referer) {
		return doGet(client, getUrl, referer, 10, DEFAULT_CHARSET);
	}

	public static String doGet(DefaultHttpClient client, String getUrl,
			String referer, String encoding) {
		return doGet(client, getUrl, referer, 10, encoding);
	}

	public static String doGet(DefaultHttpClient client, String getUrl,
			String referer, int timeout, String encoding) {
		supportSSL(getUrl, client);
		HttpGet get = new HttpGet(getUrl);
		String result = null;

		try {
			HttpResponse response = executeHttpRequest(client, get, referer,
					timeout, encoding);
			// result = EntityUtils.toString(response.getEntity(), encoding);
			result = getContent(response, encoding);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	private static String getContent(HttpResponse response, String encoding) {
		String result = null;
		if (response == null) {
			return result;
		}
		HttpEntity entity = response.getEntity();
		if (entity == null) {
			return result;
		}
		try {
			result = EntityUtils.toString(entity, encoding);
		} catch (ParseException e) {
			log.error("", e);
			throw new RuntimeException(e);
		} catch (IOException e) {
			log.error("", e);
			throw new RuntimeException(e);
		}

		return result;
	}

	private static void supportSSL(String url, HttpClient client) {
		if ("".equals(url) || url == null) {
			return;
		}
		String siteUrl = url.toLowerCase();
		if (!(siteUrl.startsWith("https"))) {
			return;
		}

		try {
			setSSLProtocol(siteUrl, client);
		} catch (Exception e) {
			log.error(" setProtocol error :", e);
		}
	}

	// add by los 20100920 增加对https的支持
	private static void setSSLProtocol(String strUrl, HttpClient client)
			throws Exception {

		URL url = new URL(strUrl);
		int port = url.getPort();

		if (port <= 0) {
			port = 443;
		}
		TrustManager[] tm = { new TrustAnyTrustManager() };
		SSLContext sslContext = SSLContext.getInstance("TLS");
		sslContext.init(null, tm, new java.security.SecureRandom());
		SSLSocketFactory factory = new SSLSocketFactory(sslContext,
				SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		Scheme scheme = new Scheme("https", port, factory);
		client.getConnectionManager().getSchemeRegistry().register(scheme);
	}

	private static HttpResponse executeHttpRequest(DefaultHttpClient client,
			HttpUriRequest request, String referer, int timeout, String encoding)
			throws ClientProtocolException, IOException {
		client.getParams()
				.setParameter(CoreProtocolPNames.USER_AGENT,
						"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; CIBA; InfoPath.2)");
		request.setHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		request.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
		request.setHeader("Accept-Charset", "gb2312,utf-8;q=0.7,*;q=0.7");

		request.setHeader("If-Modified-Since", "Sat, 17 Nov 2012 20:23:10 GMT");
		request.setHeader("If-None-Match", "08b6e5c1c5cd1:5fe");
		request.setHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=" + encoding);

		client.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS,
				Boolean.TRUE);

		// client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
		// CONNECT_TIMEOUT*1000);
		if (timeout > 0) {
			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
					timeout * 1000);
		}

		if (referer != null && !"".equals(referer)) {
			request.setHeader("referer", referer);
		}
		addRedirect(client);
		addZip(client);
		setCookiePolicy(client);
		setRetryHandler(client);

		HttpResponse response = null;
		try {
			response = client.execute(request);
		} catch (Exception e) {
			log.error(e);
			throw new RuntimeException(e);
		}
		return response;
	}

	public static void addZip(DefaultHttpClient clinet) {

		if (clinet == null) {
			return;
		}

		clinet.addRequestInterceptor(new HttpRequestInterceptor() {
			@Override
			public void process(HttpRequest arg0, HttpContext arg1)
					throws HttpException, IOException {
				if (!arg0.containsHeader("Accept-Encoding")) {
					arg0.addHeader("Accept-Encoding", "gzip");
				}
			}
		});

		// 拦截器：返回增加gzip解压
		clinet.addResponseInterceptor(new HttpResponseInterceptor() {
			public void process(final HttpResponse response,
					final HttpContext context) throws HttpException,
					IOException {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					Header ceheader = entity.getContentEncoding();
					if (ceheader != null) {
						HeaderElement[] codecs = ceheader.getElements();
						for (int i = 0; i < codecs.length; i++) {
							if (codecs[i].getName().equalsIgnoreCase("gzip")) {
								response.setEntity(new GzipDecompressingEntity(
										response.getEntity()));
								return;
							}
						}
					}
				}
			}
		});
	}

	public static void addRedirect(DefaultHttpClient client) {
		if (client == null) {
			return;
		}
		client.setRedirectStrategy(new LaxRedirectStrategy());

	}

	private static void setCookiePolicy(DefaultHttpClient client) {

		if (client != null) {
			CookieSpec cookieSpec = null;
			try {
				cookieSpec = client.getCookieSpecs().getCookieSpec(
						COOKIE_POLICY_CUSTOM);
			} catch (IllegalStateException e) {
			}

			if (cookieSpec == null) {
				CookieSpecFactory csf = new CookieSpecFactory() {
					public CookieSpec newInstance(HttpParams params) {

						return new NetscapeDraftSpec() {
							public boolean match(Cookie cookie,
									CookieOrigin origin) {
								if (cookie == null) {
									throw new IllegalArgumentException(
											"Cookie may not be null");
								}
								if (origin == null) {
									throw new IllegalArgumentException(
											"Cookie origin may not be null");
								}
								String host = origin.getHost();
								String domain = cookie.getDomain();
								if (domain != null && domain.startsWith("www.")) {
									domain = domain.substring(3);
								}

								if (domain == null) {
									return false;
								}
								if (host.equals(domain)) {
									return true;
								}
								if (!domain.startsWith(".")) {
									domain = '.' + domain;
								}
								return host.endsWith(domain)
										|| host.equals(domain.substring(1));
							};
						};
					}
				};
				client.getCookieSpecs().register(COOKIE_POLICY_CUSTOM, csf);
			}

			Object registry = client.getParams().getParameter(
					ClientPNames.COOKIE_POLICY);
			if (registry == null) {
				client.getParams().setParameter(ClientPNames.COOKIE_POLICY,
						COOKIE_POLICY_CUSTOM);
			}
		}
	}

	private static void setRetryHandler(DefaultHttpClient client) {
		// System.out.println("RetryHandler="+client.getHttpRequestRetryHandler());
		HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
			public boolean retryRequest(IOException exception,
					int executionCount, HttpContext context) {
				if (executionCount >= 5) {
					// 如果超过最大重试次数，那么就不要继续了
					return false;
				}
				if (exception instanceof NoHttpResponseException) {
					// 如果服务器丢掉了连接，那么就重试
					return true;
				}
				if (exception instanceof SSLHandshakeException) {
					// 不要重试SSL握手异常
					return false;
				}
				HttpRequest request = (HttpRequest) context
						.getAttribute(ExecutionContext.HTTP_REQUEST);
				boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
				if (idempotent) {
					// 如果请求被认为是幂等的，那么就重试
					return true;
				}
				return false;
			}
		};

		client.setHttpRequestRetryHandler(httpRequestRetryHandler);

	}

	/**
	 * 下载文件
	 * 
	 * @param client
	 *            [参数1说明]
	 * @param getUrl
	 *            [参数2说明]
	 * @param refererURL
	 *            [参数2说明]
	 * @param timeout
	 *            [参数2说明]
	 * @param savePath
	 *            [参数2说明]
	 * @return [返回类型说明]
	 * @exception/throws [异常类型] [异常说明]
	 * @see [类、类#方法、类#成员]
	 * @since 1.0
	 * @author xsc
	 */
	public static File downLoadFile(DefaultHttpClient client, String getUrl,
			String refererURL, int timeout, String savePath) {

		if (client == null || getUrl == null || "".equals(getUrl.trim())
				|| savePath == null || "".equals(savePath.trim())) {
			return null;
		}

		HttpGet get = new HttpGet(getUrl);
		HttpResponse response = null;
		try {
			response = executeHttpRequest(client, get, refererURL, 0, "GBK");
		} catch (ClientProtocolException e1) {
			log.info("", e1);
		} catch (IOException e1) {
			log.error("", e1);
		}

		if (response == null) {
			return null;
		}

		byte[] buf = new byte[1024];
		int size = -1;
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		int flag = 1;
		try {
			InputStream is = response.getEntity().getContent();
			bis = new BufferedInputStream(is);

			fos = new FileOutputStream(savePath);
			while ((size = bis.read(buf)) != -1) {
				fos.write(buf, 0, size);
			}
		} catch (Exception e) {
			log.error("", e);
			flag = 0;
		} finally {
			try {
				bis.close();
				fos.close();
			} catch (Exception e) {
				log.error("", e);
			}
		}
		if (flag == 1)
			return new File(savePath);
		else
			return null;

	}
}

class TrustAnyTrustManager implements X509TrustManager {

	public void checkClientTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {

	}

	public void checkServerTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {

	}

	public X509Certificate[] getAcceptedIssuers() {
		return new X509Certificate[] {};
	}
}
