package com.imooc.myo2o.service;

import com.imooc.myo2o.BaseTest;
import com.imooc.myo2o.entity.Area;
import com.imooc.myo2o.entity.PersonInfo;
import com.imooc.myo2o.entity.Shop;
import com.imooc.myo2o.entity.ShopCategory;
import com.imooc.myo2o.enums.ShopStateEnum;
import com.imooc.myo2o.vo.ShopExecution;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.Date;

import static net.sf.ezmorph.test.ArrayAssertions.assertEquals;

/**
 * Created by xyzzg on 2018/7/11.
 */
public class ShopServiceTest extends BaseTest{

    @Autowired
    private ShopService shopService;

    @Test
    public void testAddShop(){
        Shop shop = new Shop();
		PersonInfo personInfo = new PersonInfo();
		personInfo.setUserId(9L);

        Area area = new Area();
        area.setAreaId(3L);

        ShopCategory sc = new ShopCategory();
        sc.setShopCategoryId(9L);

        shop.setOwnerId(9L);
        shop.setShopName("mytest11");
        shop.setShopDesc("mytest11");
        shop.setShopAddr("testaddr11");
        shop.setPhone("138105245261");
        shop.setLongitude(1D);
        shop.setLatitude(1D);
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setAdvice("审核中");
        shop.setArea(area);

        shop.setShopCategory(sc);
        shop.setShopCategoryId(9L);

        File shopImg = new File("C:\\Users\\xyzzg\\Desktop\\1");
        ShopExecution shopExecution =shopService.addShop(shop,shopImg);
        assertEquals(ShopStateEnum.CHECK.getState(),shopExecution.getState());

    }
}
