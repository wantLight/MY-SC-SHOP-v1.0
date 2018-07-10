package com.imooc.o2o.dao;

import com.imooc.myo2o.dao.AreaDao;
import com.imooc.myo2o.entity.Area;
import com.imooc.o2o.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static net.sf.ezmorph.test.ArrayAssertions.assertEquals;

/**
 * Created by xyzzg on 2018/7/9.
 */
public class AreaDaoTest extends BaseTest{

    @Autowired
    private AreaDao areaDao;

    @Test
    public void testQueryArea(){
        List<Area> areaList = areaDao.queryArea();
        assertEquals(2,areaList.size());
    }
}
