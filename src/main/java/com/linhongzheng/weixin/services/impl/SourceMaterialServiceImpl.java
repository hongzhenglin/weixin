package com.linhongzheng.weixin.services.impl;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.alibaba.fastjson.JSONObject;
import com.linhongzheng.weixin.entity.material.WeiXinMedia;
import com.linhongzheng.weixin.services.AbstractWeChatService;
import com.linhongzheng.weixin.services.ISourceMaterialService;

public class SourceMaterialServiceImpl extends AbstractWeChatService implements
		ISourceMaterialService {

	@Override
	public WeiXinMedia uploadMedia(String type, String mediaFileUrl) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMedia(String mediaId, String savePath) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 这里说下，在上传视频素材的时候，微信说不超过20M，我试了下，超过10M调通的可能性都比较小，建议大家上传视频素材的大小小于10M比交好
	 * @param accessToken
	 * @param file  上传的文件
	 * @param title  上传类型为video的参数
	 * @param introduction 上传类型为video的参数
	 */
	public void uploadPermanentMedia2(String accessToken,
			File file,String title,String introduction) {
		try {
			
			//这块是用来处理如果上传的类型是video的类型的
			JSONObject j=new JSONObject();
			j.put("title", title);
			j.put("introduction", introduction);
			
			// 拼装请求地址
			String uploadMediaUrl = "http://api.weixin.qq.com/cgi-bin/material/add_material?access_token=##ACCESS_TOKEN##";
			uploadMediaUrl = uploadMediaUrl.replace("##ACCESS_TOKEN##",
					accessToken);

			URL url = new URL(uploadMediaUrl);
			String result = null;
			long filelength = file.length();
			String fileName=file.getName();
			String suffix=fileName.substring(fileName.lastIndexOf("."),fileName.length());
			String type="video/mp4"; //我这里写死
			/**
			 *  你们需要在这里根据文件后缀suffix将type的值设置成对应的mime类型的值
			 */
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false); // post方式不能使用缓存
			// 设置请求头信息
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Charset", "UTF-8");
			
			// 设置边界,这里的boundary是http协议里面的分割符，不懂的可惜百度(http 协议 boundary)，这里boundary 可以是任意的值(111,2222)都行
			String BOUNDARY = "----------" + System.currentTimeMillis();
			con.setRequestProperty("Content-Type",
					"multipart/form-data; boundary=" + BOUNDARY);
			// 请求正文信息
			// 第一部分：
			
			StringBuilder sb = new StringBuilder();
			
			
			
			//这块是post提交type的值也就是文件对应的mime类型值
			sb.append("--"); // 必须多两道线 这里说明下，这两个横杠是http协议要求的，用来分隔提交的参数用的，不懂的可以看看http 协议头
			sb.append(BOUNDARY);
			sb.append("\r\n");
			sb.append("Content-Disposition: form-data;name=\"type\" \r\n\r\n"); //这里是参数名，参数名和值之间要用两次
			sb.append(type+"\r\n"); //参数的值
			
			//这块是上传video是必须的参数，你们可以在这里根据文件类型做if/else 判断
			sb.append("--"); // 必须多两道线
			sb.append(BOUNDARY);
			sb.append("\r\n");
			sb.append("Content-Disposition: form-data;name=\"description\" \r\n\r\n");
			sb.append(j.toString()+"\r\n");
			
			/**
			 * 这里重点说明下，上面两个参数完全可以卸载url地址后面 就想我们平时url地址传参一样，
			 * http://api.weixin.qq.com/cgi-bin/material/add_material?access_token=##ACCESS_TOKEN##&type=""&description={} 这样，如果写成这样，上面的
			 * 那两个参数的代码就不用写了，不过media参数能否这样提交我没有试，感兴趣的可以试试
			 */
			
			sb.append("--"); // 必须多两道线
			sb.append(BOUNDARY);
			sb.append("\r\n");
			//这里是media参数相关的信息，这里是否能分开下我没有试，感兴趣的可以试试
			sb.append("Content-Disposition: form-data;name=\"media\";filename=\""
					+ fileName + "\";filelength=\"" + filelength + "\" \r\n");
			sb.append("Content-Type:application/octet-stream\r\n\r\n");
			System.out.println(sb.toString());
			byte[] head = sb.toString().getBytes("utf-8");
			// 获得输出流
			OutputStream out = new DataOutputStream(con.getOutputStream());
			// 输出表头
			out.write(head);
			// 文件正文部分
			// 把文件已流文件的方式 推入到url中
			DataInputStream in = new DataInputStream(new FileInputStream(file));
			int bytes = 0;
			byte[] bufferOut = new byte[1024];
			while ((bytes = in.read(bufferOut)) != -1) {
				out.write(bufferOut, 0, bytes);
			}
			in.close();
			// 结尾部分，这里结尾表示整体的参数的结尾，结尾要用"--"作为结束，这些都是http协议的规定
			byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
			out.write(foot);
			out.flush();
			out.close();
			StringBuffer buffer = new StringBuffer();
			BufferedReader reader = null;
			// 定义BufferedReader输入流来读取URL的响应
			reader = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			if (result == null) {
				result = buffer.toString();
			}
			// 使用JSON-lib解析返回结果
			JSONObject jsonObject = JSONObject.fromObject(result);
			if (jsonObject.has("media_id")) {
				System.out.println("media_id:"+jsonObject.getString("media_id"));
			} else {
				System.out.println(jsonObject.toString());
			}
			System.out.println("json:"+jsonObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}
	}
}
