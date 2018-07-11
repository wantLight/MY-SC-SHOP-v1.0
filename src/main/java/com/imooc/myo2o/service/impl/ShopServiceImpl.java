package com.imooc.myo2o.service.impl;

import com.imooc.myo2o.dao.ShopDao;
import com.imooc.myo2o.entity.Shop;
import com.imooc.myo2o.enums.ShopStateEnum;
import com.imooc.myo2o.exception.ShopOperationException;
import com.imooc.myo2o.service.ShopService;
import com.imooc.myo2o.util.FileUtil;
import com.imooc.myo2o.util.ImageUtil;
import com.imooc.myo2o.vo.ShopExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;

/**
 * Created by xyzzg on 2018/7/10.
 */
@Service("shopService")
public class ShopServiceImpl implements ShopService{

    @Autowired
    private ShopDao shopDao;
    @Override
    public ShopExecution addShop(Shop shop, File shopImg) {
        //各种非空逻辑判断
        if (shop == null){
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }
        try{
            //赋予初始值
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            //添加店铺信息
            int effectedNum = shopDao.insertShop(shop);
            if (effectedNum <=0 ){
                //只有RuntimeException，事务中止并回滚
                throw new ShopOperationException("店铺创建失败");
            } else{
                if (shopImg != null){
                    try {
                        addShopImg(shop,shopImg);
                    } catch (Exception e){
                        throw new ShopOperationException("addShopImg error:"+e.getMessage());
                    }
                    //跟新店铺图片地址
                    effectedNum = shopDao.updateShop(shop);
                    if (effectedNum <= 0){
                        throw new ShopOperationException("跟新图片地址失败");
                    }
                }
            }
        } catch (Exception e){
            throw new ShopOperationException("addShop error:"+e.getMessage());
        }
        return new ShopExecution(ShopStateEnum.CHECK,shop);
    }

    private void addShopImg(Shop shop, File shopImg) {
        //获取shop图片目录相对子路径
        String dest = FileUtil.getShopImagePath(shop.getShopId());
        String shopImgAddr = ImageUtil.generateThumbnail(shopImg,dest);
        shop.setShopImg(shopImgAddr);
    }
}
