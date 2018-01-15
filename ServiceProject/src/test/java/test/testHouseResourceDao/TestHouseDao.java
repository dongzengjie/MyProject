package test.testHouseResourceDao;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.nizubuzu.androidService.dao.HouseDao;
import com.nizubuzu.androidService.entity.House;
import com.nizubuzu.androidService.entity.HouseCategory;
import com.nizubuzu.androidService.entity.HouseDetailImg;
import com.nizubuzu.androidService.entity.HouseResource;

import test.BaseTest;

public class TestHouseDao extends BaseTest {

	@Autowired
	private HouseDao houseDao;

	@Test
	public void testInsertHouse() {
		House house = new House();
		HouseCategory houseCategory = new HouseCategory();
		HouseResource houseResource = new HouseResource();
		houseCategory.setHouseCategoryId(17L);
		houseResource.setHouseResourceId(35L);
		house.setCreate_time(new Date());
		house.setEnableStatus(1);
		house.setHouseDesc("这是描述");
		house.setHouseName("这是名称");
		house.setPrice("1212");
		house.setHouseResource(houseResource);
		house.setHouseCategory(houseCategory);

		houseDao.insertHouse(house);
	}

	@Test
	public void testqueryByid() {
		House house = houseDao.queryHouseByHouseId(7L);
		List<HouseDetailImg> detailImgs = house.getHouseDetailImgs();
		for (HouseDetailImg houseDetailImg : detailImgs) {
			System.out.println(houseDetailImg.getImgAddr());
		}
		/*
		 * System.out.println(house.getHouseName());
		 * System.out.println(house.getHouseCategory().getHouseCategoryId());
		 * System.out.println(house.getHouseResource().getHouseResourceId());
		 */
	}

	@Test
	public void updateToNull() {
		houseDao.updateHouseCategoryIdToNull(17L);
	}

	@Test
	public void queryList() {
		House house = new House();
		HouseResource houseResource = new HouseResource();
		houseResource.setHouseResourceId(35l);
		house.setHouseResource(houseResource);

		List<House> list = houseDao.queryHouseList(house, 0, 100);
		for (House house2 : list) {
			System.out.println(house2.getHouseName());
		}
		int i=houseDao.queryCount(house);
		System.out.println(i);
	}
	
	@Test
	public void update(){
		House house = new House();
		HouseCategory houseCategory = new HouseCategory();
		HouseResource houseResource=new HouseResource();
		houseResource.setHouseResourceId(35L);
		houseCategory.setHouseCategoryId(17L);
		house.setHouseId(10L);
		house.setCreate_time(new Date());
		house.setEnableStatus(1);
		house.setHouseDesc("这是描述2");
		house.setHouseName("这是名称2");
		house.setPrice("12121");
		house.setHouseResource(houseResource);
	
		house.setHouseCategory(houseCategory);
		
		houseDao.updateHouse(house);

		
	}
	
	
}
