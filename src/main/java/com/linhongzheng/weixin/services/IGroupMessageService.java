package com.linhongzheng.weixin.services;

import java.util.List;

import com.linhongzheng.weixin.entity.group.GroupArticle;

public interface IGroupMessageService {

	public void uploadImage(String fileName);

	public void uploadNews(List<GroupArticle> articles);

	public void sendMessageByGroupid(String jsonData);

	public void sendMessageByOpenids(String jsonData);

	public void deleteMessage(String msgId);

	public void preview(String jsonData);
	
	public String queryMessageSendStatus(String msgId);

}
