package com.linhongzheng.weixin.demo.weather.entity;

import java.util.List;

public class WeatherResult {
	private String currentCity;
	private String pm25;
	private List<ResultIndex> index;
	private List<WeatherData> weather_data;

	public String getCurrentCity() {
		return currentCity;
	}

	public void setCurrentCity(String currentCity) {
		this.currentCity = currentCity;
	}

	public String getPm25() {
		return pm25;
	}

	public void setPm25(String pm25) {
		this.pm25 = pm25;
	}

	public List<ResultIndex> getIndex() {
		return index;
	}

	public void setIndex(List<ResultIndex> index) {
		this.index = index;
	}

	public List<WeatherData> getWeather_data() {
		return weather_data;
	}

	public void setWeather_data(List<WeatherData> weather_data) {
		this.weather_data = weather_data;
	}

}
