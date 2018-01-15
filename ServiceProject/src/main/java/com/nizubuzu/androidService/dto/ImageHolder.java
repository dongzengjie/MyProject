package com.nizubuzu.androidService.dto;

import java.io.InputStream;

public class ImageHolder {
	private String imageName;
	private InputStream imageStream;
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public InputStream getImage() {
		return imageStream;
	}
	public void setImage(InputStream image) {
		this.imageStream = image;
	}
	public ImageHolder (String imageName,InputStream imageStream) {
		this.imageStream=imageStream;
		this.imageName=imageName;
	}
}
