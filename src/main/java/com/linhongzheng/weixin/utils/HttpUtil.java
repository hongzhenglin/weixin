package com.linhongzheng.weixin.utils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.ListenableFuture;
import com.ning.http.client.Request;
import com.ning.http.client.RequestBuilder;
import com.ning.http.client.Response;
import com.ning.http.client.multipart.FilePart;
import com.ning.http.client.multipart.Part;
import com.ning.http.client.multipart.StringPart;

/**
 * Created by linhz on 2015/8/17.
 */
public class HttpUtil {
	private static final String DEFAULT_CHARSET = "UTF-8";

	/**
	 * @return 返回类型:
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchProviderException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @description 功能描述: get 请求
	 */
	public static String get(String url) throws KeyManagementException,
			NoSuchAlgorithmException, NoSuchProviderException,
			UnsupportedEncodingException, IOException, ExecutionException,
			InterruptedException {
		return get(url, null);
	}

	/**
	 * @return 返回类型:
	 * @throws IOException
	 * @throws NoSuchProviderException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws UnsupportedEncodingException
	 * @description 功能描述: get 请求
	 */
	public static String get(String url, Map<String, String> params)
			throws KeyManagementException, NoSuchAlgorithmException,
			NoSuchProviderException, UnsupportedEncodingException, IOException,
			ExecutionException, InterruptedException {
		return get(url, params, null);
	}

	/**
	 * @return 返回类型:
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchProviderException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @description 功能描述: get 请求
	 */
	public static String get(String url, Map<String, String> params,
			Map<String, String> headers) throws IOException,
			ExecutionException, InterruptedException {
		AsyncHttpClient http = new AsyncHttpClient();
		AsyncHttpClient.BoundRequestBuilder builder = http.prepareGet(url);
		builder.setBodyEncoding(DEFAULT_CHARSET);
		if (params != null && !params.isEmpty()) {
			Set<String> keys = params.keySet();
			for (String key : keys) {
				builder.addQueryParam(key, params.get(key));
			}
		}
		if (headers != null && !headers.isEmpty()) {
			Set<String> keys = headers.keySet();
			for (String key : keys) {
				builder.addHeader(key, params.get(key));
			}
		}
		Future<Response> f = builder.execute();
		String body = f.get().getResponseBody(DEFAULT_CHARSET);
		http.close();
		return body;
	}

	public static String post(String url, String params) throws IOException,
			ExecutionException, InterruptedException {
		AsyncHttpClient http = new AsyncHttpClient();
		AsyncHttpClient.BoundRequestBuilder builder = http.preparePost(url);
		builder.setBodyEncoding(DEFAULT_CHARSET);
		if (StringUtil.isNotEmpty(params)) {
			Part stringPart = new StringPart(null, params);
			builder.addBodyPart(stringPart);
		}
		Future<Response> f = builder.execute();
		String body = f.get().getResponseBody(DEFAULT_CHARSET);
		http.close();
		return body;
	}

	/**
	 * @return 返回类型:
	 * @throws IOException
	 * @throws NoSuchProviderException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @description 功能描述: POST 请求
	 */
	public static String post(String url, Map<String, String> params)
			throws IOException, ExecutionException, InterruptedException {
		AsyncHttpClient http = new AsyncHttpClient();
		AsyncHttpClient.BoundRequestBuilder builder = http.preparePost(url);
		builder.setBodyEncoding(DEFAULT_CHARSET);
		if (params != null && !params.isEmpty()) {
			Set<String> keys = params.keySet();
			for (String key : keys) {
				builder.addFormParam(key, params.get(key).toString());
			}
		}
		Future<Response> f = builder.execute();
		String body = f.get().getResponseBody(DEFAULT_CHARSET);
		http.close();
		return body;
	}

}
