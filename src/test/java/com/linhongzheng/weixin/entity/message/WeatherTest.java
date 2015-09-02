package com.linhongzheng.weixin.entity.message;

import junit.framework.TestCase;

import org.junit.Test;
import com.linhongzheng.weixin.utils.weather.WeatherUtil;

public class WeatherTest extends TestCase {

	@Test
	public void testQueryWeather() {
		System.out.println(WeatherUtil.queryWeather("广州"));
	}

}
