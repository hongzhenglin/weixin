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
    public static String get(String url) throws Exception {
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
    public static String get(String url, Map<String, String> params) throws Exception {
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
    public static String get(String url, Map<String, String> params, Map<String, String> headers) throws  Exception {
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

     
    public static String post(String url, String params) throws  Exception {
        AsyncHttpClient http = new AsyncHttpClient();
        AsyncHttpClient.BoundRequestBuilder builder = http.preparePost(url);
        builder.setBodyEncoding(DEFAULT_CHARSET);
        if (StringUtil.isNotEmpty( params )) {
        	Part stringPart = new StringPart(null,params);
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
    public static String post(String url,Map<String, String>  params) throws  Exception {
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

    /**
     * 上传媒体文件
     *
     * @param url
     * @param
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     * @throws KeyManagementException
     */
    public static String upload(String url, List<File> fileList, Map<String, String> paramsMap) throws  Exception {
        String responseBody = null;
        if (url != null && fileList != null && fileList.size() > 0) {

            AsyncHttpClient httpClient = new AsyncHttpClient();
            try {
                RequestBuilder requestBuilder = new RequestBuilder();

                // FilePart
                for (File file : fileList) {
                    Part filePart = new FilePart(file.getName(), file);
                    requestBuilder.addBodyPart(filePart);
                }

                // StringPart
                if (paramsMap != null) {
                    Set<Map.Entry<String, String>> entrySet = paramsMap.entrySet();
                    Iterator<Map.Entry<String, String>> it = entrySet.iterator();
                    while (it.hasNext()) {
                        Map.Entry<String, String> entry = it.next();
                        Part stringPart = new StringPart(entry.getKey(), entry.getValue());
                        requestBuilder.addBodyPart(stringPart);
                    }
                }

                // 添加RequestHeader，key
                requestBuilder.addHeader("Content-type", "multipart/form-data; charset=UTF-8");
                requestBuilder.setMethod("POST");
                // 添加URL
                requestBuilder.setUrl(url);

                // request
                Request request = requestBuilder.build();

                // 提交
                ListenableFuture<Response> f = httpClient.executeRequest(request);
                responseBody = f.get().getResponseBody(DEFAULT_CHARSET);

            } finally {
                httpClient.close();
            }

        }
        return responseBody;
    }

    /**
     * 下载资源
     *
     * @param
     * @return
     * @throws IOException
     */
  /*  public static Attachment download(String url) throws ExecutionException, InterruptedException, IOException {
        Attachment att = new Attachment();
        AsyncHttpClient http = new AsyncHttpClient();
        AsyncHttpClient.BoundRequestBuilder builder = http.prepareGet(url);
        builder.setBodyEncoding(DEFAULT_CHARSET);
        Future<Response> f = builder.execute();
        if (f.get().getContentType().equalsIgnoreCase("text/plain")) {
            att.setError(f.get().getResponseBody(DEFAULT_CHARSET));
        } else {
            BufferedInputStream bis = new BufferedInputStream(f.get().getResponseBodyAsStream());
            String ds = f.get().getHeader("Content-disposition");
            String fullName = ds.substring(ds.indexOf("filename=\"") + 10, ds.length() - 1);
            String relName = fullName.substring(0, fullName.lastIndexOf("."));
            String suffix = fullName.substring(relName.length() + 1);

            att.setFullName(fullName);
            att.setFileName(relName);
            att.setSuffix(suffix);
            att.setContentLength(f.get().getHeader("Content-Length"));
            att.setContentType(f.get().getContentType());
            att.setFileStream(bis);
        }
        http.close();
        return att;
    }*/



  /*  public  static void main(String[] args) throws Exception{
        String accessToken = "ulhEL9F2CciJezmGj47C-d3hAJZwXiAANctVIwSHwBRK7Z1enIRWeZKZekk8jS5abIkCo2YmMSDlqUFKOKvSaw";
        String openId = "oeZTVt6XlCphRnCI-DlpdTyk27p4";
        UserInfo u = WeChat.user.getUserInfo(accessToken, openId);
        System.out.println(JSON.toJSONString(u));
        //System.out.println(WeChat.message.sendText(accessToken , openId , "测试"));
        //Map<String, Object> mgs = WeChat.uploadMedia(accessToken, "image", new File("C:\\Users\\郭华\\Pictures\\13.jpg"));
        //System.out.println(JSON.toJSONString(mgs));
    }*/
}
