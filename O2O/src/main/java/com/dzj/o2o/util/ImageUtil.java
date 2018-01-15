package com.dzj.o2o.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.dzj.o2o.dto.ImageHolder;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class ImageUtil {
	private static String bathpath = Thread.currentThread()
			.getContextClassLoader().getResource("").getPath();
	private static final SimpleDateFormat format = new SimpleDateFormat(
			"yyyyMMddHHmmss");
	private static final Random r = new Random();

	/**
	 * 将CommonsMultipartFile转换成File类
	 * 
	 * @param cFile
	 * @return
	 */
	public static File transferCommonsMultipartFileToFile(
			CommonsMultipartFile files) {
		File neFile = new File(files.getOriginalFilename());
		try {
			files.transferTo(neFile);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return neFile;
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
		makeDirPath(targetAddr);
		String relativePath=targetAddr + realFileName + extension;
		File descFile=new File(PathUtil.getImgBasePath()+relativePath);
		
		try {
			Thumbnails.of(thumbnail.getImage()).size(200, 200).watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(bathpath + "watermark.jpg")), 0.25f)
			.outputQuality(0.8f).toFile(descFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return relativePath;
		
	}

	private static void makeDirPath(String targetAddr) {

		String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
		File disFile = new File(realFileParentPath);
		if (!disFile.exists()) {
			disFile.mkdirs();
		}

	}

	private static String getFileExtension(String fileName) {

		return fileName.substring(fileName.lastIndexOf("."));
	}

	private static String getRandomFileName() {
		int random = r.nextInt(89999) + 10000;
		String nowtimeString = format.format(new Date());

		return random + nowtimeString;
	}
	
	
	public static void deleteFileorPath(String storePath){
		File fileorPath=new File(PathUtil.getImgBasePath()+storePath);
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

	public static void main(String[] args) {
		try {
			Thumbnails
					.of(new File("G:/aaa.png"))
					.size(200, 200)
					.watermark(Positions.BOTTOM_RIGHT,
							ImageIO.read(new File(bathpath + "watermark.jpg")),
							0.25f).outputQuality(0.8f).toFile("G:/qwe.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String generateNormalImg(ImageHolder thumbnail,
			String targetAddr) {

		String realFileName=getRandomFileName();
		String extension = getFileExtension(thumbnail.getImageName());
		makeDirPath(targetAddr);
		String relativePath=targetAddr + realFileName + extension;
		File descFile=new File(PathUtil.getImgBasePath()+relativePath);
		
		try {
			Thumbnails.of(thumbnail.getImage()).size(337, 640).watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(bathpath + "watermark.jpg")), 0.25f)
			.outputQuality(0.9f).toFile(descFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return relativePath;
	}

}
