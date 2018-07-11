package com.imooc.myo2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import com.imooc.myo2o.entity.PersonInfo;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.myo2o.BaseTest;
import com.imooc.myo2o.entity.Area;
import com.imooc.myo2o.entity.Shop;
import com.imooc.myo2o.entity.ShopCategory;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ShopDaoTest extends BaseTest {
	@Autowired
	private ShopDao shopDao;

	@Test
	public void testAInsertShop() throws Exception {
		Shop shop = new Shop();
		PersonInfo personInfo = new PersonInfo();
		Area area = new Area();
		ShopCategory shopCategory = new ShopCategory();

		// 因为tb_shop表中有外键约束,因此务必确保 设置的这几个id在对应的表中存在.
		// 我们提前在tb_person_inf tb_area
		// tb_shop_category分别添加了如下id的数据,以避免插入tb_shop时抛出如下异常
		// com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException:
		// Cannot add or update a child row: a foreign key constraint fails
		// (`o2o`.`tb_shop`, CONSTRAINT `fk_shop_area` FOREIGN KEY (`area_id`)
		// REFERENCES `tb_area` (`area_id`))
		personInfo.setUserId(1L);
		area.setAreaId(1L);
		shopCategory.setShopCategoryId(1L);

		shop.setOwnerId(9L);

		shop.setArea(area);
		shop.setShopCategory(shopCategory);
		shop.setShopName("Artisan");
		shop.setShopDesc("ArtisanDesc");
		shop.setShopAddr("NanJing");
		shop.setPhone("123456");
		shop.setShopImg("/xxx/xxx");
		shop.setPriority(99);
		shop.setCreateTime(new Date());
		shop.setLastEditTime(new Date());
		shop.setEnableStatus(0);
		shop.setAdvice("Waring");

		int effectNum = shopDao.insertShop(shop);

		assertEquals(effectNum, 1);
		//logger.debug("insert  successfully");
}

	@Test
	public void testBQueryByEmployeeId() throws Exception {
		long employeeId = 1;
		List<Shop> shopList = shopDao.queryByEmployeeId(employeeId);
		for (Shop shop : shopList) {
			System.out.println(shop);
		}
	}

	@Test
	public void testBQueryShopList() throws Exception {
		Shop shop = new Shop();
		List<Shop> shopList = shopDao.queryShopList(shop, 0, 2);
		assertEquals(2, shopList.size());
		int count = shopDao.queryShopCount(shop);
		assertEquals(3, count);
		shop.setShopName("花");
		shopList = shopDao.queryShopList(shop, 0, 3);
		assertEquals(2, shopList.size());
		count = shopDao.queryShopCount(shop);
		assertEquals(2, count);
		shop.setShopId(1L);
		shopList = shopDao.queryShopList(shop, 0, 3);
		assertEquals(1, shopList.size());
		count = shopDao.queryShopCount(shop);
		assertEquals(1, count);

	}

	@Test
	public void testCQueryByShopId() throws Exception {
		long shopId = 1;
		Shop shop = shopDao.queryByShopId(shopId);
		System.out.println(shop);
	}

	@Test
	public void testDUpdateShop() {
		long shopId = 28;
		Shop shop = shopDao.queryByShopId(shopId);
		Area area = new Area();
		area.setAreaId(3L);
		shop.setArea(area);
		ShopCategory shopCategory = new ShopCategory();
		shopCategory.setShopCategoryId(14L);
		shop.setShopCategory(shopCategory);
		shop.setShopName("四季花");
		int effectedNum = shopDao.updateShop(shop);
		assertEquals(1, effectedNum);
	}

	@Test
	public void testEDeleteShopByName() throws Exception {
		String shopName = "mytest1";
		int effectedNum = shopDao.deleteShopByName(shopName);
		assertEquals(1, effectedNum);

	}

}
