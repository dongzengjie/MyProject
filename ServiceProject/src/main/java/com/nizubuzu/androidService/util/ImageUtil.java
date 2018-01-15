package com.nizubuzu.androidService.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;


import com.nizubuzu.androidService.dto.ImageHolder;
import com.nizubuzu.androidService.service.HouseResourceService;
import com.nizubuzu.androidService.service.impl.HouseResourceServiceImpl;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;


public class ImageUtil {
	private static String bathpath= Thread.currentThread().getContextClassLoader().getResource("").getPath();
	private static  final SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
	private static final Random r=new Random();
	
	private HouseResourceService houseResourceService=new HouseResourceServiceImpl();
	
	
	/**
	 * 获取文件后缀名 如.jpg
	 * @param fileName
	 * @return
	 */
	private static String getFileExtension(String fileName){
		return fileName.substring(fileName.lastIndexOf("."));
		
	}
	
	/**
	 * 随机获取文件名
	 * @return
	 */
	private static String getRandomFileName(){
		
		int random=r.nextInt(89999)+10000;
		
		String nowtime=dateFormat.format(new Date());
		
		return random +nowtime;
	}
	/**
	 * 创建路径
	 * @param targetAddr
	 */
	private static void mikepath(String targetAddr){
		String relaypath=PathUtil.getImageBasePath() + targetAddr;
		File disFile=new File(relaypath);
		if (!disFile.exists()) {
			disFile.mkdirs();
		}
	}
	

	/**
	 * 处理缩略图，并返回新生成图片的相对值路径
	 * 
	 * @param thumbnail
	 * @param targetAddr
	 * @return
	 */
	public static String generateThumbnail(ImageHolder thumbnail, String targetAddr){
		
		String realFileName=getRandomFileName();
		String extension = getFileExtension(thumbnail.getImageName());
		mikepath(targetAddr);
		String relativePath=targetAddr + realFileName + extension;
		File descFile=new File(PathUtil.getImageBasePath()+relativePath);
		InputStream inputStream=thumbnail.getImage();
		File file=new File(bathpath + "watermark.jpg");
		System.out.println(bathpath + "watermark.jpg");
		
		try {
			ImageIO.read(file);
			Thumbnails.of(inputStream).size(200, 200).watermark(Positions.BOTTOM_RIGHT, ImageIO.read(file), 0.25f)
			.outputQuality(0.8f).toFile(descFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return relativePath;
		
	}
	public static void main(String[] args) throws FileNotFoundException {
		File file=new File("G:/aaa.png");
		InputStream inputStream=new FileInputStream(file);
	
		ImageHolder holder=new ImageHolder(file.getName(), inputStream);
		System.out.println(file.getName());
		String destion=PathUtil.getHouseResourceImagePath(1L);
		System.out.println(bathpath);
		System.out.println(generateThumbnail(holder, destion));
	}
	/**
	 * 删除文件夹
	 * @param storePath
	 */
	public static void deleteFileorPath(String storePath){
		File fileorPath=new File(PathUtil.getImageBasePath()+storePath);
		if(fileorPath.exists()){
			if(fileorPath.isDirectory()){
				File files[]=fileorPath.listFiles();
				for(int i=0;i<files.length;i++){
					files[i].delete();
				}
			}
			fileorPath.delete();
		}
	}
}
