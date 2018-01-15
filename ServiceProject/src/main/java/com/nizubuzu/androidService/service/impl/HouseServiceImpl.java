package com.nizubuzu.androidService.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nizubuzu.androidService.dao.HouseDao;
import com.nizubuzu.androidService.dao.HouseImgDetailDao;
import com.nizubuzu.androidService.dto.HouseExecution;
import com.nizubuzu.androidService.dto.ImageHolder;
import com.nizubuzu.androidService.entity.House;
import com.nizubuzu.androidService.entity.HouseDetailImg;
import com.nizubuzu.androidService.entity.HouseResource;
import com.nizubuzu.androidService.enums.HouseState;
import com.nizubuzu.androidService.exception.HouseOperationException;
import com.nizubuzu.androidService.service.HouseService;
import com.nizubuzu.androidService.util.ImageUtil;
import com.nizubuzu.androidService.util.PageCalculator;
import com.nizubuzu.androidService.util.PathUtil;

@Service
public class HouseServiceImpl implements HouseService {

	@Autowired
	private HouseDao houseDao;
	@Autowired
	private HouseImgDetailDao houseImgDetailDao;

	private String addimg(House house, ImageHolder holder) {
		String destion = PathUtil.getHouseResourceImagePath(house
				.getHouseResource().getHouseResourceId());
		String path = ImageUtil.generateThumbnail(holder, destion);
		return path;

	}

	private void addHouseImgList(House house, List<ImageHolder> houseDetailImg) {
		String destion = PathUtil.getHouseResourceImagePath(house
				.getHouseResource().getHouseResourceId());
		List<HouseDetailImg> detailImgslist = new ArrayList<HouseDetailImg>();
		for (ImageHolder info : houseDetailImg) {
			String path = ImageUtil.generateThumbnail(info, destion);
			HouseDetailImg detailImg = new HouseDetailImg();
			detailImg.setCreateTime(new Date());
			detailImg.setHouseId(house.getHouseId());
			detailImg.setImgAddr(path);
			detailImgslist.add(detailImg);
		}
		if (detailImgslist.size() > 0) {
			try {
				int effect = houseImgDetailDao
						.batchinsertHouseDetailImg(detailImgslist);
				if (effect <= 0) {
					throw new HouseOperationException("创建详情图失败");
				}
			} catch (Exception e) {
				throw new HouseOperationException("创建详情图失败" + e.getMessage());
			}
		}
	}

	/**
	 * 删除详情图片
	 * 
	 * @param houseId
	 */
	private void deleteHouseDetailImg(Long houseId) {
		List<HouseDetailImg> detailImgs = houseImgDetailDao
				.queryListHouseDetaiImg(houseId);
		if (detailImgs.size() > 0) {
			for (HouseDetailImg houseDetailImg : detailImgs) {
				ImageUtil.deleteFileorPath(houseDetailImg.getImgAddr());

			}
			int effect = houseImgDetailDao.deleteHouseDetailImg(houseId);
		}
	}

	@Transactional
	public HouseExecution addHouse(House house, ImageHolder houseImg,
			List<ImageHolder> houseDetailImg) throws HouseOperationException {

		if (house != null) {

			house.setCreate_time(new Date());
			house.setLastEditTime(new Date());
			house.setEnableStatus(1);// 1 为上架状态
			if (houseImg != null) {
				String path = addimg(house, houseImg);
				house.setImgAddr(path);
			}

			try {
				int effect = houseDao.insertHouse(house);
				if (effect <= 0) {
					return new HouseExecution(HouseState.INNER_ERROR);
				}

				if (houseDetailImg != null && houseDetailImg.size() > 0) {
					addHouseImgList(house, houseDetailImg);
				}
			} catch (Exception e) {
				throw new HouseOperationException("添加失败" + e.getMessage());
			}

			return new HouseExecution(HouseState.SUCCESS, house);
		} else {
			return new HouseExecution(HouseState.EMPTY);
		}

	}

	@Override
	public House queryHouseById(Long houseId) {

		return houseDao.queryHouseByHouseId(houseId);
	}

	@Override
	public HouseExecution modifyHouseInfo(House house, ImageHolder houseImg,
			List<ImageHolder> houseDetailImg) throws HouseOperationException {
		if (house != null && house.getHouseId() > 0) {
			house.setLastEditTime(new Date());

			if (houseImg != null) {
				House tempHouse = houseDao.queryHouseByHouseId(house
						.getHouseId());
				if (tempHouse.getImgAddr() != null) {
					ImageUtil.deleteFileorPath(tempHouse.getImgAddr());
					String path = addimg(house, houseImg);
					house.setImgAddr(path);

				}

			}
			if (houseDetailImg != null && houseDetailImg.size() > 0) {
				deleteHouseDetailImg(house.getHouseId());
				addHouseImgList(house, houseDetailImg);
			}
			try {
				int effect = houseDao.updateHouse(house);
				if (effect > 0) {
					return new HouseExecution(HouseState.SUCCESS, house);
				} else {
					return new HouseExecution(HouseState.INNER_ERROR);
				}
			} catch (Exception e) {
				System.out.println(e.getMessage()+ "= 错误信息");
				throw new HouseOperationException("更新失败" + e.getMessage());
			}

		} else {
			return new HouseExecution(HouseState.EMPTY);
		}

	}

	@Override
	public HouseExecution queryHouseList(House house, int pageIndex,
			int pageSize) throws HouseOperationException {
		int rowIndex=PageCalculator.calculatorRowindex(pageIndex, pageSize);
		List<House> houseList=houseDao.queryHouseList(house, rowIndex, pageSize);
		int count =houseDao.queryCount(house);
		HouseExecution houseExecution =new HouseExecution();
		if(houseList.size()>0){
			houseExecution.setCount(count);
			houseExecution.setHouselist(houseList);
			houseExecution.setState(HouseState.SUCCESS.getState());
		}else {
			houseExecution.setState(HouseState.EMPTY.getState());
		}
		
		return houseExecution;
	}

}
