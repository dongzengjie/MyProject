package test.testHouseResourceeService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.nizubuzu.androidService.dto.HouseResourceExecution;
import com.nizubuzu.androidService.dto.ImageHolder;
import com.nizubuzu.androidService.entity.HouseResource;
import com.nizubuzu.androidService.entity.PersionInfo;
import com.nizubuzu.androidService.service.HouseResourceService;

import test.BaseTest;

public class TestHouseResourceService extends BaseTest{

	@Autowired
	private HouseResourceService houseResourceService;
	
	@Test
	public void testinsetHouseResource() throws FileNotFoundException{
		File file=new File("G:/aaa.png");
		InputStream inputStream=new FileInputStream(file);
	
		ImageHolder holder=new ImageHolder(file.getName(), inputStream);
	
		HouseResource houseResource =new HouseResource();
		houseResource.setHouseResourceName("测试添加图片");
		houseResource.setAdvice("审核中");
		houseResource.setAreaMsg("西藏");
		PersionInfo info=new PersionInfo();
		/*info.setUserId(1L);
		houseResource.setOwner(info);*/
		
		houseResourceService.insertHouseResource(houseResource, holder);
	}
	
	@Test
	public void testqueryList(){
		HouseResource houseResource=new HouseResource();
		PersionInfo info =new PersionInfo();
		info.setUserId(1L);
		houseResource.setOwner(info);
		HouseResourceExecution house=houseResourceService.queryHouseResourceList(0, 100, houseResource);
		List<HouseResource> list=house.getHouseResourceslist();
		for (HouseResource houseResource2 : list) {
			System.out.println(houseResource2.getHouseResourceName());
		}
	}
	
	@Test
	public void testupdata(){
		HouseResource houseResource =new HouseResource();
		houseResource.setHouseResourceId(35L);
		houseResource.setHouseResourceName("测试名字（等待删除）");
		houseResourceService.modifyHouseResource(houseResource, null);
	}
}
