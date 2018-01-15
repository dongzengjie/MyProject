package com.nizubuzu.androidService.util;

public class PathUtil {

	private static String seperator = System.getProperty("file.separator");
	
	/**
	 * 获取保存图片的根路径
	 * @return
	 */
	public static String getImageBasePath(){
		String os=System.getProperty("os.name");
		String basePath="";
		if(os.toLowerCase().startsWith("win")){
			basePath = "C:/houseFriendImg/image";
		}else {
			basePath = "/Users/baidu/work/image";
		}
		basePath =basePath.replace("/", seperator);
		return basePath;
	}
	
	/**
	 * 获取保存图片的相对路径
	 * @param houseResourceId 房源id ,根据不同的id创建不同的文件夹
	 * @return
	 */
	public static String getHouseResourceImagePath(long houseResourceId) {
		String imagePath = "/upload/item/houseResource/" + houseResourceId + "/";
		return imagePath.replace("/", seperator);
	}
}
