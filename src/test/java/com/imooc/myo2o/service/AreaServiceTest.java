package com.imooc.myo2o.service;

import com.imooc.myo2o.BaseTest;
import com.imooc.myo2o.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by xyzzg on 2018/7/9.
 */
public class AreaServiceTest extends BaseTest {

    @Autowired
    private AreaService areaService;
    @Test
    public void testGetAreaList(){
        List<Area> areaList = areaService.getAreaList();
        assertEquals("东苑",areaList.get(0).getAreaName());
    }
}
