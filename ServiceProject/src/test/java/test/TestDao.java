package test;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.nizubuzu.androidService.dao.AreaDao;
import com.nizubuzu.androidService.entity.Area;



public class TestDao extends BaseTest {

	@Autowired
	private AreaDao areaDao;
	
	@Test
	public void testqueryAreaDao(){
		List<Area> arealist=areaDao.queryAllArea();
		for (Area area : arealist) {
			System.out.println(area.getAreaName());
		}
	}
	@Test
	public void testInsertDao(){
		Area area=new Area();
		area.setAreaName("西藏");
		area.setCreateTime(new Date());
		
		areaDao.insertArea(area);
	}

}
