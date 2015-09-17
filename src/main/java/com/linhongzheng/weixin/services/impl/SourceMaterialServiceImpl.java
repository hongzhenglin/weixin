package com.linhongzheng.weixin.services.impl;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.channels.FileChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.linhongzheng.weixin.entity.material.WeiXinMedia;
import com.linhongzheng.weixin.services.AbstractWeChatService;
import com.linhongzheng.weixin.services.IAccessTokenService;
import com.linhongzheng.weixin.services.ISourceMaterialService;
import com.linhongzheng.weixin.utils.CommonUtil;
import com.linhongzheng.weixin.utils.URLConstants;

@Service("sourceMaterialService")
public class SourceMaterialServiceImpl extends AbstractWeChatService implements
		ISourceMaterialService {
	private static Logger log = LoggerFactory
			.getLogger(SourceMaterialServiceImpl.class);

	private static final String END = "\r\n";
	private static final String TWOHyphens = "--";
	@Autowired
	IAccessTokenService accessTokenService;

	/**
	 * 图片大小不超过2M，支持bmp/png/jpeg/jpg/gif格式，语音大小不超过5M，长度不超过60秒，支持mp3/wma/wav/amr格式
	 */
	@Override
	public WeiXinMedia uploadMedia(String type, String mediaFileUrl) {
		String at = null;
		try {
			at = accessTokenService.getAccessToken();
		} catch (Exception e) {
			e.printStackTrace();
		}
		WeiXinMedia weixinMedia = new WeiXinMedia();
		if (at != null) {
			try {
				JSONObject jsonObject = JSON.parseObject(upload(at, type,
						mediaFileUrl));
				weixinMedia.setType(jsonObject.getString("type"));
				weixinMedia.setMediaId(jsonObject.getString("media_id"));
				weixinMedia.setCreatedAt(jsonObject.getIntValue("created_at"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return weixinMedia;
	}

	@Override
	public String getMedia(String mediaId, String savePath) {
		String at = null;
		HttpURLConnection downloadConn = null;
		try {
			at = accessTokenService.getAccessToken();

			if (!savePath.endsWith("/")) {
				savePath += "/";
			}

			downloadConn = getDownloadConnection(at, mediaId);
			String fileExt = CommonUtil.getFileExt(downloadConn
					.getHeaderField("Content-Type"));

			String filePath = savePath + mediaId + fileExt;
			downloadFile(downloadConn, filePath);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			downloadConn.disconnect();
		}

		return null;
	}

	/**
	 * 这里说下，在上传视频素材的时候，微信说不超过20M，我试了下，超过10M调通的可能性都比较小，建议大家上传视频素材的大小小于10M比交好
	 * 
	 * @param accessToken
	 * @param file
	 *            上传的文件
	 * @param title
	 *            上传类型为video的参数
	 * @param introduction
	 *            上传类型为video的参数
	 */
	// public void uploadPermanentMedia2(String accessToken,
	// File file,String title,String introduction) {
	// try {
	//
	// //这块是用来处理如果上传的类型是video的类型的
	// JSONObject j=new JSONObject();
	// j.put("title", title);
	// j.put("introduction", introduction);
	//
	//
	//

	// // 使用JSON-lib解析返回结果
	// JSONObject jsonObject = JSONObject.fromObject(result);
	// if (jsonObject.has("media_id")) {
	// System.out.println("media_id:"+jsonObject.getString("media_id"));
	// } else {
	// System.out.println(jsonObject.toString());
	// }
	// System.out.println("json:"+jsonObject.toString());
	// } catch (IOException e) {
	// e.printStackTrace();
	// } finally {
	//
	// }
	// }

	private String upload(String accessToken, String type, File file,
			String contentType) throws Exception {

		HttpURLConnection uploadConn = getUploadConnection(accessToken, type);

		writeContentToWeiXin(uploadConn, file, contentType);

		String result = readContentFromWeiXin(uploadConn);
		uploadConn.disconnect();
		return result;

	}

	private String upload(String accessToken, String type, String mediaFileUrl)
			throws Exception {

		HttpURLConnection uploadConn = getUploadConnection(accessToken, type);

		writeContentToWeiXin(mediaFileUrl, uploadConn);

		String result = readContentFromWeiXin(uploadConn);
		uploadConn.disconnect();
		return result;
	}

	private HttpURLConnection getUploadConnection(String accessToken,
			String type) throws MalformedURLException, IOException,
			ProtocolException {

		String uploadMediaUrl = URLConstants.MATERIAL.UPLOAD_TEMP_MEDIA_URL
				.replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);
		URL uploadUrl = new URL(uploadMediaUrl);
		HttpURLConnection uploadConn = (HttpURLConnection) uploadUrl
				.openConnection();
		uploadConn.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
		uploadConn.setDoInput(true);
		uploadConn.setDoOutput(true);
		uploadConn.setUseCaches(false); // post方式不能使用缓存
		// 设置请求头信息
		uploadConn.setRequestProperty("Connection", "Keep-Alive");
		uploadConn.setRequestProperty("Charset", "UTF-8");
		return uploadConn;
	}

	private HttpURLConnection getDownloadConnection(String accessToken,
			String mediaId) throws MalformedURLException, IOException,
			ProtocolException {

		String downloadMediaUrl = URLConstants.MATERIAL.GET_TEMP_MEDIA_URL
				.replace("ACCESS_TOKEN", accessToken).replace("MEDIA_ID",
						mediaId);
		URL downloadUrl = new URL(downloadMediaUrl);
		HttpURLConnection downloadConn = (HttpURLConnection) downloadUrl
				.openConnection();
		downloadConn.setRequestMethod("GET"); // 以Post方式提交表单，默认get方式
		downloadConn.setDoInput(true);

		return downloadConn;
	}

	private void writeContentToWeiXin(String mediaFileUrl,
			HttpURLConnection uploadConn) throws IOException,
			MalformedURLException, ProtocolException,
			UnsupportedEncodingException {
		// 设置边界,这里的boundary是http协议里面的分割符
		String BOUNDARY = "----------" + System.currentTimeMillis();
		uploadConn.setRequestProperty("Content-Type",
				"multipart/form-data; boundary=" + BOUNDARY);
		// 获取媒体文件上传的输出流(往微信服务器写数据)
		OutputStream outputStream = uploadConn.getOutputStream();

		URL mediaUrl = new URL(mediaFileUrl);
		HttpURLConnection mediaConn = (HttpURLConnection) mediaUrl
				.openConnection();
		mediaConn.setRequestMethod("GET"); // 以Post方式提交表单，默认get方式
		mediaConn.setDoOutput(true);
		// 从请求头中获取内容类型
		String contentType = mediaConn.getHeaderField("Content-Type");

		// 请求体信息

		StringBuilder sb = new StringBuilder();

		// 这块是post提交type的值也就是文件对应的mime类型值
		sb.append("--" + BOUNDARY + END); // 必须多两道线
											// 这里说明下，这两个横杠是http协议要求的，用来分隔提交的参数用的，
		sb.append("Content-Disposition: form-data;name=\"media\";filename=\""
				+ mediaUrl.getFile() + "\"" + END);
		sb.append(String.format("Content-Type:%s" + END + END, contentType));

		System.out.println(sb.toString());
		byte[] head = sb.toString().getBytes("utf-8");

		// 输出表头
		outputStream.write(head);
		// 文件正文部分
		// 把文件已流文件的方式 推入到url中
		BufferedInputStream in = new BufferedInputStream(
				mediaConn.getInputStream());
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = in.read(bufferOut)) != -1) {
			outputStream.write(bufferOut, 0, bytes);
		}
		in.close();
		// 结尾部分，这里结尾表示整体的参数的结尾，结尾要用"--"作为结束，这些都是http协议的规定
		byte[] foot = (END + TWOHyphens + BOUNDARY + TWOHyphens + END)
				.getBytes("utf-8");// 定义最后数据分隔线
		outputStream.write(foot);
		outputStream.flush();
		outputStream.close();
	}

	private void writeContentToWeiXin(HttpURLConnection uploadConn, File file,
			String contentType) throws IOException,
			UnsupportedEncodingException, FileNotFoundException {
		// 设置边界,这里的boundary是http协议里面的分割符
		String BOUNDARY = "----------" + System.currentTimeMillis();
		uploadConn.setRequestProperty("Content-Type",
				"multipart/form-data; boundary=" + BOUNDARY);

		// 请求体信息
		StringBuilder sb = new StringBuilder();
		sb.append(TWOHyphens + BOUNDARY + END);
		sb.append("Content-Disposition: form-data;name=\"media\";filename=\""
				+ file.getName() + "\"" + END);
		sb.append(String.format("Content-Type: %s" + END + END, contentType));

		System.out.println(sb.toString());
		byte[] head = sb.toString().getBytes("utf-8");

		try (// 获取媒体文件上传的输出流(往微信服务器写数据)
		OutputStream outputStream = uploadConn.getOutputStream();
				// 把文件已流文件的方式 推入到url中
				DataInputStream in = new DataInputStream(new FileInputStream(
						file));) {
			// 输出表头
			outputStream.write(head);
			// 文件正文部分

			int bytes = 0;
			byte[] bufferOut = new byte[1024];
			while ((bytes = in.read(bufferOut)) != -1) {
				outputStream.write(bufferOut, 0, bytes);
			}
			in.close();
			// 结尾部分，这里结尾表示整体的参数的结尾，结尾要用"--"作为结束，这些都是http协议的规定
			byte[] foot = (END + TWOHyphens + BOUNDARY + TWOHyphens + END)
					.getBytes("utf-8");// 定义最后数据分隔线
			outputStream.write(foot);
			outputStream.flush();
		}
	}

	private String readContentFromWeiXin(HttpURLConnection uploadConn)
			throws IOException, UnsupportedEncodingException {

		// 从微信服务器读取数据
		StringBuffer resultSB = new StringBuffer();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(
				uploadConn.getInputStream(), "utf-8"));) {
			String line = null;

			while ((line = reader.readLine()) != null) {
				resultSB.append(line);
			}
		}

		return resultSB.toString();
	}

	private void downloadFile(HttpURLConnection uploadConn, String filePath)
			throws Exception {
		// 从微信服务器读取数据
		StringBuffer resultSB = new StringBuffer();
		try (BufferedInputStream bis = new BufferedInputStream(
				uploadConn.getInputStream());
				FileOutputStream fos = new FileOutputStream(new File(filePath));) {
			byte[] buf = new byte[8096];
			int size = 0;
			while ((size = bis.read(buf)) != -1) {
				fos.write(buf, 0, size);
			}
		}

	}

	private long getFileSize(File file) throws FileNotFoundException,
			IOException {
		long fileLength = 0L;
		FileChannel fc = null;
		if (file.exists() && file.isFile()) {
			try (FileInputStream fis = new FileInputStream(file);) {
				fc = fis.getChannel();
				fileLength = fc.size();
			}
		} else {
			log.info("file doesn't exist or is not a file");
		}
		return fileLength;
	}

	public IAccessTokenService getAccessTokenService() {
		return accessTokenService;
	}

	public void setAccessTokenService(IAccessTokenService accessTokenService) {
		this.accessTokenService = accessTokenService;
	}

}
