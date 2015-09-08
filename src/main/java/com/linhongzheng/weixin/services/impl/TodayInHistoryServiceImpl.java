package com.linhongzheng.weixin.services.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.linhongzheng.weixin.services.ITodayInHistoryService;
import com.linhongzheng.weixin.utils.DateUtil;
import com.linhongzheng.weixin.utils.HttpUtil;
import com.linhongzheng.weixin.utils.StringUtil;

@Service("todayInHistoryService")
public class TodayInHistoryServiceImpl implements ITodayInHistoryService {

	private static final String TODAY_IN_HISTORY_URL = "http://www.rijiben.com";

	/**
	 * 封装历史上的今天查询方法，供外部调用
	 * 
	 * @return
	 */
	public String getTodayInHistoryInfo() {
		// 获取网页源代码
		String html = null;
		try {
			html = HttpUtil.get(TODAY_IN_HISTORY_URL);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 从网页中抽取信息
		String result = parseHtml(html);

		return result;
	}

	private String parseHtml(String html) {

		StringBuffer buffer = new StringBuffer();

		// 日期标签：区分是昨天还是今天
		String dateTag = DateUtil.getMonthDay(0);
		Document doc = Jsoup.parse(html);
		Element listren = doc.select("div.listren").first();
		Elements as = listren.select("a");
		// 拼装标题

		for (Element element : as) {

			String text = element.text().trim();
			if (text.contains(DateUtil.getMonthDay(-1))) {
				dateTag = DateUtil.getMonthDay(-1);
			}
			if (StringUtil.isNotEmpty(text)) {
				buffer.append(text).append("\n\n");
			}
		}
		String title = "≡≡历史上的" + dateTag + "≡≡\n\n";
		// 将buffer最后两个换行符移除并返回
		return (null == buffer) ? null : title
				+ buffer.substring(0, buffer.lastIndexOf("\n\n"));
	}

	private String extract(String html) {
		StringBuffer buffer = null;
		// 日期标签：区分是昨天还是今天
		String dateTag = DateUtil.getMonthDay(0);

		Pattern p = Pattern
				.compile("(.*)(<div class=\"listren\">)(.*?)(</div>)(.*)");
		Matcher m = p.matcher(html);
		if (m.matches()) {
			buffer = new StringBuffer();
			if (m.group(3).contains(DateUtil.getMonthDay(-1)))
				dateTag = DateUtil.getMonthDay(-1);

			// 拼装标题
			buffer.append("≡≡ ").append("历史上的").append(dateTag).append(" ≡≡")
					.append("\n\n");

			// 抽取需要的数据
			for (String info : m.group(3).split("  ")) {
				info = info.replace(dateTag, "").replace("（图）", "")
						.replaceAll("</?[^>]+>", "").trim();
				// 在每行末尾追加2个换行符
				if (!"".equals(info)) {
					buffer.append(info).append("\n\n");
				}
			}
		}
		// 将buffer最后两个换行符移除并返回
		return (null == buffer) ? null : buffer.substring(0,
				buffer.lastIndexOf("\n\n"));
	}

}
