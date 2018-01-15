package com.dzj.o2o.entity.WeChart;



import com.fasterxml.jackson.annotation.JsonProperty;

public class WeChartUser{

	
	@JsonProperty("openid")
	private String openid;
	
	@JsonProperty("nickname")
	private String nickname;
	
	@JsonProperty("sex")
	private String sex;
	
	@JsonProperty("province")
	private String province;
	
	@JsonProperty("city")
	private String city;
	
	@JsonProperty("country")
	private String country;
	
	@JsonProperty("headimgurl")
	private String headimgurl;
	
	@JsonProperty("privilege")
	private String [] privilege;
	
	@JsonProperty("language")
	private String language;
	
	@JsonProperty("unionid")
	private String unionid;
	
	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public String[] getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String[] privilege) {
		this.privilege = privilege;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	
	
}
