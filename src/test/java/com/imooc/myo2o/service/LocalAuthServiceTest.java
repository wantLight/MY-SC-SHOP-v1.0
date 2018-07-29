package com.imooc.myo2o.service;

import com.imooc.myo2o.BaseTest;
import com.imooc.myo2o.entity.LocalAuth;
import com.imooc.myo2o.entity.PersonInfo;
import com.imooc.myo2o.enums.WechatAuthStateEnum;
import com.imooc.myo2o.vo.LocalAuthExecution;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by xyzzg on 2018/7/28.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LocalAuthServiceTest extends BaseTest{

    @Autowired
    private LocalAuthService localAuthService;

    private static final String username = "testuser";
    private static final String password = "testpass";

    @Test
    public void testABindLocalAuth(){
        //新增一条平台账号
        LocalAuth localAuth = new LocalAuth();
        localAuth.setUserId(12L);
        localAuth.setUserName(username);
        localAuth.setPassword(password);
        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserId(13L);
        localAuth.setPersonInfo(personInfo);

        LocalAuthExecution localAuthExecution = localAuthService.bindLocalAuth(localAuth);
        assertEquals(WechatAuthStateEnum.SUCCESS.getState(),localAuthExecution.getState());

        //通过userID找到新增的localAuth
        localAuth = localAuthService.getLocalAuthByUserId(personInfo.getUserId());
        System.out.print("用户昵称"+localAuth.getPersonInfo().getName());
    }
}
