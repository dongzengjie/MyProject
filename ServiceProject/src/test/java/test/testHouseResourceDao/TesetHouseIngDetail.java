package test.testHouseResourceDao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import test.BaseTest;

import com.nizubuzu.androidService.dao.HouseImgDetailDao;
import com.nizubuzu.androidService.entity.House;
import com.nizubuzu.androidService.entity.HouseDetailImg;

public class TesetHouseIngDetail extends BaseTest{
	@Autowired
	private HouseImgDetailDao houseDetailImgdao;
	
	@Test
	public void testbatchinsert(){
		
		House house =new House();
		house.setHouseId(7L);
		
		HouseDetailImg detailImg1 =new HouseDetailImg();
		detailImg1.setImgAddr("测试批量添加1");
		detailImg1.setHouseId(7L);
		HouseDetailImg detailImg2 =new HouseDetailImg();
		detailImg2.setImgAddr("测试批量添加2");
		detailImg2.setHouseId(7L);
		HouseDetailImg detailImg3 =new HouseDetailImg();
		detailImg3.setImgAddr("测试批量添加3");
		detailImg3.setHouseId(7L);
		List<HouseDetailImg> list =new ArrayList<HouseDetailImg>();
		list.add(detailImg1);
		list.add(detailImg2);
		list.add(detailImg3);
		
		houseDetailImgdao.batchinsertHouseDetailImg(list);
	}
	@Test
	public void query(){
		
		List<HouseDetailImg> detailImgs=houseDetailImgdao.queryListHouseDetaiImg(7L);
		for (HouseDetailImg houseDetailImg : detailImgs) {
			System.out.println(houseDetailImg.getImgAddr());
		}
		
	}
	@Test
	public void testDelete(){
		houseDetailImgdao.deleteHouseDetailImg(7L);
	}
}
