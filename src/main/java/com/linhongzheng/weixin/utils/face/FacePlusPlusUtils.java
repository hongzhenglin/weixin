package com.linhongzheng.weixin.utils.face;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.linhongzheng.weixin.demo.Face;
import com.linhongzheng.weixin.utils.ConfigUtil;
import com.linhongzheng.weixin.utils.HttpUtil;

public class FacePlusPlusUtils {
	public static final String FACE_DETECT_URL = "http://apicn.faceplusplus.com/v2/detection/detect?api_key="
			+ "{API_KEY}&api_secret={API_SECRET}&url={IMAGE_URL}&attribute=glass,pose,gender,age,race,smiling";
	private static final String FACE_API_CONFIG = "face.properties";

	public static String parseFaceJson(String faceJson) {

		JSONArray jsonArray = JSON.parseObject(faceJson).getJSONArray("face");
		List<Face> faceList = new ArrayList<Face>();
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject faceObject = (JSONObject) jsonArray.get(i);

			JSONObject attrObject = (JSONObject) faceObject.get("attribute");
			JSONObject positionObject = (JSONObject) faceObject.get("position");

			Face face = new Face();
			String faceId = faceObject.getString("face_id");
			face.setFaceId(faceId);
			face.setCenterX(positionObject.getJSONObject("center")
					.getDoubleValue("x"));
			face.setCenterY(positionObject.getJSONObject("center")
					.getDoubleValue("y"));
			face.setAgeValue(attrObject.getJSONObject("age").getIntValue(
					"value"));
			face.setAgeRange(attrObject.getJSONObject("age").getIntValue(
					"range"));
			face.setGenderValue(genderConvert(attrObject
					.getJSONObject("gender").getString("value")));
			face.setGenderConfidence(attrObject.getJSONObject("gender")
					.getIntValue("confidence"));
			face.setRaceValue(raceConvert(attrObject.getJSONObject("race")
					.getString("value")));
			face.setRaceConfidence(attrObject.getJSONObject("race").getDouble(
					"confidence"));
			face.setGlassValue(attrObject.getJSONObject("race").getString(
					"value"));
			face.setGlassConfidence(attrObject.getJSONObject("race").getDouble(
					"confidence"));
			faceList.add(face);

		}
		StringBuilder buffer = new StringBuilder();
		Collections.sort(faceList);
		if (faceList.size() == 1) {
			buffer.append("共检测到").append(faceList.size()).append("张人脸\n\n");
			for (Face face : faceList) {
				// 人种
				buffer.append(face.getGenderValue()).append("人种，");
				// 性别
				buffer.append(face.getRaceValue()).append("，");
				// 年龄
				buffer.append(face.getAgeValue()).append("岁左右");
			}

		} else if (faceList.size() > 1) {
			buffer.append("共检测到").append(faceList.size())
					.append("张人脸，按脸部中心位置从左到右依次为：\n\n");
			for (Face face : faceList) {
				// 人种
				buffer.append(face.getGenderValue()).append("人种，");
				// 性别
				buffer.append(face.getRaceValue()).append("，");
				// 年龄
				buffer.append(face.getAgeValue()).append("岁左右").append("\n");
			}
		}

		return buffer.toString();
	}

	/**
	 * 人脸识别
	 * 
	 * @param imageUrl
	 * 
	 */
	public static String detectFace(String imageUrl) {
		ConfigUtil config = new ConfigUtil(FACE_API_CONFIG);
		String requestUrl = FACE_DETECT_URL.replace("{API_KEY}",
				config.getValue("FACE_API_ID"));
		requestUrl = requestUrl.replace("{API_SECRET}",
				config.getValue("FACE_API_SECRET"));
		requestUrl = requestUrl.replace("{IMAGE_URL}", imageUrl);
		String respStr = null;
		try {
			respStr = HttpUtil.get(requestUrl);
		} catch (KeyManagementException | NoSuchAlgorithmException
				| NoSuchProviderException | IOException | ExecutionException
				| InterruptedException e) {

			e.printStackTrace();
		}
		return respStr;
	}

	private static String genderConvert(String genderValue) {
		String result = "男性";
		if (genderValue.equals("Female"))
			result = "女性";
		return result;
	}

	private static String raceConvert(String raceValue) {
		String result = "白色";
		if (raceValue.equals("Asian"))//
			result = "黄色";
		else if (raceValue.equals("White"))
			result = "白色";
		else if (raceValue.equals("Black"))
			result = "黑色";
		return result;
	}
}
