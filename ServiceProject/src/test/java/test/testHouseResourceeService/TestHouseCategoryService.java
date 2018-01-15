package test.testHouseResourceeService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.nizubuzu.androidService.entity.HouseCategory;
import com.nizubuzu.androidService.service.HouseCategoryService;

import test.BaseTest;

public class TestHouseCategoryService extends BaseTest{

	@Autowired
	private HouseCategoryService housecategoryService;
	
	@Test
	public void Testinsert(){
		
		HouseCategory category1 = new HouseCategory();
		category1.setHouseResourceId(35L);
		category1.setHouseCategoryName("二手房1");
		category1.setPriority(1);
		category1.setCreateTime(new Date());

		HouseCategory category2 = new HouseCategory();
		category2.setHouseResourceId(35L);
		category2.setHouseCategoryName("二手房2");
		category2.setPriority(2);
		category2.setCreateTime(new Date());

		HouseCategory category3 = new HouseCategory();
		category3.setHouseResourceId(35L);
		category3.setHouseCategoryName("二手房3");
		category3.setPriority(3);
		category3.setCreateTime(new Date());

		List<HouseCategory> categories = new ArrayList<HouseCategory>();
		categories.add(category1);
		categories.add(category2);
		categories.add(category3);
		
		housecategoryService.batchinserthouseCategory(categories);
	}
	
	@Test
	public void testquery(){
		
	List<HouseCategory> list=housecategoryService.queryhouseCategoryByHouseResourceId(35L);
	for (HouseCategory houseCategory : list) {
		System.out.println(houseCategory.getHouseCategoryName());
		
	}
	}
	
	@Test
	public void testDelete(){
		housecategoryService.deleteByhouseResourceIdAndhouseCategoryId(6l, 35l);
	}
}
