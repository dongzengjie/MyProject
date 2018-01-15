package test.testHouseResourceDao;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.nizubuzu.androidService.dao.HouseResourceDao;
import com.nizubuzu.androidService.entity.Area;
import com.nizubuzu.androidService.entity.HouseResource;
import com.nizubuzu.androidService.entity.PersionInfo;

import test.BaseTest;

public class TestHouseResourceDao extends BaseTest {

	@Autowired
	private HouseResourceDao houseResourceDao;
	
	@Test
	public void testInsertDao(){
		HouseResource houseResource=new HouseResource();
		Area area=new Area();
		PersionInfo info=new PersionInfo();
		area.setAreaId(1L);
		info.setUserId(1L);
		houseResource.setArea(area);
		houseResource.setOwner(info);
		houseResource.setHouseResourceName("测试房源");
		houseResourceDao.insertHouseResource(houseResource);
	}
	
	@Test
	public void selectByid(){
		HouseResource house=houseResourceDao.queryByHouseResourceId(1L);
		System.out.println(house.getOwner().getName());
	}
	@Test
	public void testuodate(){
		HouseResource houseResource=new HouseResource();
		houseResource.setHouseResourceId(1L);
		houseResource.setHouseResourceName("测试1");
		Area area=new Area();
		area.setAreaId(4L);
		houseResource.setArea(area);
		houseResourceDao.updataHouseResource(houseResource);
	}
	
	@Test
	public void testQueryList(){
		HouseResource houseResource=new HouseResource();
		PersionInfo info =new PersionInfo();
		info.setUserId(1L);
		houseResource.setOwner(info);
		//houseResource.setHouseResourceId(1L);
		List<HouseResource> list =houseResourceDao.queryHouseResourceList(houseResource, 0, 100);
		for (HouseResource houseResource2 : list) {
			System.out.println(houseResource2.getHouseResourceName());
		}
	}
	
	@Test
	public void testcount(){
		HouseResource houseResource=new HouseResource();
		PersionInfo info =new PersionInfo();
		info.setUserId(1L);
		houseResource.setOwner(info);
		int i=houseResourceDao.queryHouseResouceCount(houseResource);
		System.out.println(i);
	}
	
}
