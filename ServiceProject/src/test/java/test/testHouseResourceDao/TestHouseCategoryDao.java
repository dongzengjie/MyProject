package test.testHouseResourceDao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.nizubuzu.androidService.dao.HouseCategoryDao;
import com.nizubuzu.androidService.entity.HouseCategory;

import test.BaseTest;

public class TestHouseCategoryDao extends BaseTest {

	@Autowired
	private HouseCategoryDao housecategoryDao;

	@Test
	public void testbatchinserthouseCategory() {

		HouseCategory category1 = new HouseCategory();
		category1.setHouseResourceId(35L);
<<<<<<< HEAD
<<<<<<< HEAD
		category1.setHouseCategoryName("二手房777");
		category1.setPriority(2);
		
=======
		category1.setHouseCategoryName("二手房1");
		category1.setPriority(1);
		category1.setCreateTime(new Date());
>>>>>>> parent of b5a2f27... zz
=======
		category1.setHouseCategoryName("二手房1");
		category1.setPriority(1);
		category1.setCreateTime(new Date());
>>>>>>> parent of b5a2f27... zz

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

		int i = housecategoryDao.batchinserthouseCategory(categories);
		System.out.println(i);

	}
	
	
	@Test
	public void testQuery(){
		
		
		List<HouseCategory> list=housecategoryDao.queryhouseCategoryByHouseResourceId(35L);
		
		//System.out.println(list.get(0));
		
		for (HouseCategory houseCategory : list) {
			System.out.println(houseCategory.getHouseCategoryName());
		}
	}
	
	@Test
	public void testDelete(){
		int i=housecategoryDao.deleteByhouseResourceIdAndhouseCategoryId(5l, 35l);
		System.out.println(i);
	
	}
}
