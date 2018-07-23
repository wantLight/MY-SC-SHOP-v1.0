package com.imooc.myo2o.service;

import com.imooc.myo2o.entity.Shop;
import com.imooc.myo2o.entity.ShopCategory;
import com.imooc.myo2o.vo.ShopExecution;

import java.io.File;
import java.util.List;

/**
 * Created by xyzzg on 2018/7/10.
 */
public interface ShopCatetoryService {

    List<ShopCategory> getShopCategory(ShopCategory shopCategory);
}
