package com.linhongzheng.weixin.message;

import junit.framework.TestCase;

import org.junit.Test;

import com.linhongzheng.weixin.services.ITodayInHistoryService;
import com.linhongzheng.weixin.services.impl.TodayInHistoryServiceImpl;

public class TodayInHistoryTest extends TestCase {

	private ITodayInHistoryService todayInHistoryService;

	protected void setUp() throws Exception {
		todayInHistoryService = new TodayInHistoryServiceImpl();
	}

	@Test
	public void testTodayInHistory() {
		System.out.println(todayInHistoryService.getTodayInHistoryInfo());
	}
}
