package com.imooc.myo2o.service;

import com.imooc.myo2o.entity.Shop;
import com.imooc.myo2o.vo.ShopExecution;

import java.io.File;

/**
 * Created by xyzzg on 2018/7/10.
 */
public interface ShopService {

    ShopExecution addShop(Shop shop, File shopImg);
}
