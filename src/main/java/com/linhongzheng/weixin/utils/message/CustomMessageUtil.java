package com.linhongzheng.weixin.utils.message;

import java.util.List;

import com.linhongzheng.weixin.entity.message.response.Article;
import com.linhongzheng.weixin.entity.message.response.Music;
import com.linhongzheng.weixin.utils.JSONUtil;

public class CustomMessageUtil {

	/**
	 * 组装文本客服消息
	 * 
	 * @param openId
	 *            消息发送的用户
	 * @param content
	 *            文本消息内容
	 * @return
	 */
	public static String makeTextCustomMessage(String openId, String content) {
		content = content.replace("\"", "\\\""); // 对消息内容中的双引号进行转义
		String jsonMsg = "{\"touser\":\"%s\", \"msgtype\":\"text\", \"text\": {\"content\":\"%s\"}}";
		return String.format(jsonMsg, openId, content);
	}

	/**
	 * 组装语音客服消息
	 * 
	 * @param openId
	 *            消息发送的用户
	 * @param mediaId
	 *            媒体文档ID
	 * @return
	 */
	public static String makeVoiceCustomMessage(String openId, String mediaId) {
		String jsonMsg = "{\"touser\":\"%s\", \"msgtype\":\"voice\", \"voice\": {\"media_id\":\"%s\" } }";
		return String.format(jsonMsg, openId, mediaId);
	}

	/**
	 * 组装视频客服消息
	 * 
	 * @param openId
	 *            消息发送的用户
	 * @param mediaId
	 *            发送的视频的媒体ID
	 * @param thumbMediaId
	 *            缩略图的媒体ID
	 * @param title
	 *            视频消息的标题
	 * @param description
	 *            视频消息的描述
	 * @return
	 */
	public static String makeVideoCustomMessage(String openId, String mediaId,
			String thumbMediaId, String title, String description) {
		String jsonMsg = "{\"touser\":\"%s\", \"msgtype\":\"video\", \"video\": {\"media_id\":\"%s\", \"thumb_media_id\":\"%s\", \"title\":\"%s\", \"description\":\"%s\" } }";
		return String.format(jsonMsg, openId, mediaId, thumbMediaId, title,
				description);
	}

	/**
	 * 组装图片客服消息
	 * 
	 * @param openId
	 *            消息发送的用户
	 * @param mediaId
	 *            媒体文档ID
	 * @return
	 */
	public static String makeMusicCustomMessage(String openId, Music music) {
		String jsonMsg = "{\"touser\":\"%s\", \"msgtype\":\"music\", \"music\": %s  }";
		jsonMsg = String.format(jsonMsg, JSONUtil.objectToJson(music, "0"));
		jsonMsg = jsonMsg.replace("musicUrl", "musicurl");
		jsonMsg = jsonMsg.replace("hQMusicUrl", "hqmusicurl");
		jsonMsg = jsonMsg.replace("thumbMediaId", "thumb_media_id");

		return jsonMsg;
	}

	/**
	 * 组装图片客服消息
	 * 
	 * @param openId
	 *            消息发送的用户
	 * @param mediaId
	 *            媒体文档ID
	 * @return
	 */
	public static String makeNewsCustomMessage(String openId,
			List<Article> articleList) {
		String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"news\",\"news\":{\"articles\": %s}}";
		jsonMsg = String.format(jsonMsg,
				JSONUtil.listToJsonString(articleList, "0"));
		jsonMsg = jsonMsg.replace("picUrl", "picurl");

		return jsonMsg;
	}

	public static void main(String[] args) {
		Music music = new Music();
		music.setDescription("描述");
		music.setHQMusicUrl("http://ifeng.com");
		music.setMusicUrl("http://ifeng.com");
		music.setThumbMediaId("111");
		music.setTitle("test");
		System.out.println(JSONUtil.objectToJson(music, "0"));
		// {"description":"描述","hQMusicUrl":"http://ifeng.com","musicUrl":"http://ifeng.com","thumbMediaId":"111","title":"test"}

	}

}
