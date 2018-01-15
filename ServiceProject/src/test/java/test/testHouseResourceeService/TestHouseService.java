package test.testHouseResourceeService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


import com.nizubuzu.androidService.dto.HouseExecution;
import com.nizubuzu.androidService.dto.ImageHolder;
import com.nizubuzu.androidService.entity.House;
import com.nizubuzu.androidService.entity.HouseCategory;
import com.nizubuzu.androidService.entity.HouseResource;
import com.nizubuzu.androidService.service.HouseService;

import test.BaseTest;

public class TestHouseService extends BaseTest{
	@Autowired
	private HouseService houseService;
	@Test
	public void testaddhouse() throws FileNotFoundException{
		House house=new House();
	
		HouseResource houseResource=new HouseResource();
		HouseCategory houseCategory=new HouseCategory();
		houseResource.setHouseResourceId(35L);
		houseCategory.setHouseCategoryId(13L);
		house.setHouseResource(houseResource);
		house.setHouseCategory(houseCategory);
		house.setCreate_time(new Date());
		house.setHouseName("好大啊");
		house.setPrice("2000");
		house.setPriority(20);
		
		File file=new File("G:/aaa.png");
		InputStream inputStream=new FileInputStream(file);
		ImageHolder holder=new ImageHolder(file.getName(),inputStream);
		
		File file1=new File("G:/bizhi/329397-106.jpg");
		File file2=new File("G:/bizhi/335853-106.jpg");
		InputStream inputStream1=new FileInputStream(file1);
		InputStream inputStream2=new FileInputStream(file2);
		List<ImageHolder> holders=new ArrayList<ImageHolder>();
		holders.add(new ImageHolder(file1.getName(), inputStream1));
		holders.add(new ImageHolder(file2.getName(), inputStream2));
		houseService.addHouse(house, holder, holders);
		//ProductExecution e=productService.addProduct(product, holder, holders);
	}
	@Test
	public void update() throws FileNotFoundException{
/*		House house=new House();
		house.setHouseId(10L);
		HouseResource houseResource=new HouseResource();
		HouseCategory houseCategory=new HouseCategory();
		houseResource.setHouseResourceId(35L);
		
		houseCategory.setHouseCategoryId(13L);
		house.setHouseResource(houseResource);
		house.setHouseCategory(houseCategory);
		
		house.setHouseName("修改");
		house.setPrice("2000");
		house.setPriority(20);*/
		
		House house = new House();
		HouseCategory houseCategory = new HouseCategory();
		HouseResource houseResource=new HouseResource();
		houseResource.setHouseResourceId(35L);
		houseCategory.setHouseCategoryId(17L);
		house.setHouseId(11L);
	
		
		house.setHouseDesc("这是描述2");
		house.setHouseName("这是名称2");
		house.setPrice("12121");
		house.setHouseResource(houseResource);
	
		house.setHouseCategory(houseCategory);
		
		File file=new File("G:/bizhi/q.png");
		InputStream inputStream=new FileInputStream(file);
		ImageHolder holder=new ImageHolder(file.getName(),inputStream);
		
		File file1=new File("G:/bizhi/314935-106.jpg");
		File file2=new File("G:/bizhi/timg.jpg");
		InputStream inputStream1=new FileInputStream(file1);
		InputStream inputStream2=new FileInputStream(file2);
		List<ImageHolder> holders=new ArrayList<ImageHolder>();
		holders.add(new ImageHolder(file1.getName(), inputStream1));
		holders.add(new ImageHolder(file2.getName(), inputStream2));
		houseService.modifyHouseInfo(house, holder, holders);
	}
	@Test
	public void testquertList(){
		House house=new House();
		HouseCategory houseCategory =new HouseCategory();
		houseCategory.setHouseCategoryId(17L);
		house.setHouseCategory(houseCategory);
		HouseExecution execution=houseService.queryHouseList(house, 0, 100);
		List<House> list=execution.getHouselist();
		
		for (House house2 : list) {
			System.out.println(house2.getHouseName());
		}
	}
}
