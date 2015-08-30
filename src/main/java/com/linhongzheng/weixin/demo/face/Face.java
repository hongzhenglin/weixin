package com.linhongzheng.weixin.demo.face;

public class Face implements Comparable<Face> {
	private String faceId; // 被检测出的每一张人脸都在Face++系统中的标识符
	private double centerX; // 检出的人脸框的中心点坐标, x表示在图片中的宽度的百分比 (0~100之间的实数)
	private double centerY; // 检出的人脸框的中心点坐标, y表示在图片中的高度的百分比 (0~100之间的实数)
	private String genderValue; // 包含性别分析结果，value的值为Male/Female,
	private double genderConfidence; // 性别置信度
	private int ageValue; // 包含年龄分析结果，value的值为一个非负整数表示估计的年龄;
	private int ageRange; // 表示估计年龄的正负区间
	private String raceValue; // 包含人种分析结果，value的值为Asian/White/Black
	private double raceConfidence; // 人种的置信度
	private double smilingValue; // 包含微笑程度分析结果，value的值为0－100的实数，越大表示微笑程度越高
	private String glassValue; // 包含眼镜佩戴分析结果，value的值为None/Dark/Normal
	private double glassConfidence; // 表示置信度

	public String getFaceId() {
		return faceId;
	}

	public void setFaceId(String faceId) {
		this.faceId = faceId;
	}

	public double getCenterX() {
		return centerX;
	}

	public void setCenterX(double centerX) {
		this.centerX = centerX;
	}

	public double getCenterY() {
		return centerY;
	}

	public void setCenterY(double centerY) {
		this.centerY = centerY;
	}

	public String getGenderValue() {
		return genderValue;
	}

	public void setGenderValue(String genderValue) {
		this.genderValue = genderValue;
	}

	public double getGenderConfidence() {
		return genderConfidence;
	}

	public void setGenderConfidence(double genderConfidence) {
		this.genderConfidence = genderConfidence;
	}

	public int getAgeValue() {
		return ageValue;
	}

	public void setAgeValue(int ageValue) {
		this.ageValue = ageValue;
	}

	public int getAgeRange() {
		return ageRange;
	}

	public void setAgeRange(int ageRange) {
		this.ageRange = ageRange;
	}

	public String getRaceValue() {
		return raceValue;
	}

	public void setRaceValue(String raceValue) {
		this.raceValue = raceValue;
	}

	public double getRaceConfidence() {
		return raceConfidence;
	}

	public void setRaceConfidence(double raceConfidence) {
		this.raceConfidence = raceConfidence;
	}

	public double getSmilingValue() {
		return smilingValue;
	}

	public void setSmilingValue(double smilingValue) {
		this.smilingValue = smilingValue;
	}

	public String getGlassValue() {
		return glassValue;
	}

	public void setGlassValue(String glassValue) {
		this.glassValue = glassValue;
	}

	public double getGlassConfidence() {
		return glassConfidence;
	}

	public void setGlassConfidence(double glassConfidence) {
		this.glassConfidence = glassConfidence;
	}

	@Override
	public int compareTo(Face o) {
		int result = 0;
		if(this.getCenterX()>o.getCenterX())
			result = 1;
		else if(this.getCenterX()<o.getCenterX())
			result = -1;
 		return result;
	}

	@Override
	public String toString() {
		return "Face [faceId=" + faceId + ", centerX=" + centerX + ", centerY="
				+ centerY + ", genderValue=" + genderValue
				+ ", genderConfidence=" + genderConfidence + ", ageValue="
				+ ageValue + ", ageRange=" + ageRange + ", raceValue="
				+ raceValue + ", raceConfidence=" + raceConfidence
				+ ", smilingValue=" + smilingValue + ", glassValue="
				+ glassValue + ", glassConfidence=" + glassConfidence + "]";
	}

}
