package com.imooc.myo2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.myo2o.entity.PersonInfo;
import com.imooc.myo2o.entity.Shop;
import com.imooc.myo2o.enums.ShopStateEnum;
import com.imooc.myo2o.service.ShopService;
import com.imooc.myo2o.util.FileUtil;
import com.imooc.myo2o.util.HttpServletRequestUtil;
import com.imooc.myo2o.util.ImageUtil;
import com.imooc.myo2o.vo.ShopExecution;
import com.sun.javafx.scene.shape.PathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xyzzg on 2018/7/12.
 */
@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {

    @Autowired
    private ShopService shopService;

    @RequestMapping(value = "/registershop",method = RequestMethod.POST)
    private Map<String,Object> registerShop(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        //1.接受并转化相应的参数，包括店铺信息以及图片信息 --使用工具类
        String shopStr = HttpServletRequestUtil.getString(request,"shopStr");

        ObjectMapper mapper = new ObjectMapper();
        Shop shop =null;
        try {
            //转换成Java对象匹配JSON结构
            shop = mapper.readValue(shopStr,Shop.class);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return modelMap;
        }
        //接收图片
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        //是不是有上传的文件流
        if (commonsMultipartResolver.isMultipart(request)){
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        } else {
            modelMap.put("success",false);
            modelMap.put("errMsg","上传图片不得为空");
            return modelMap;
        }
        //2.注册店铺
        if (shop != null && shopImg != null){
            //一定要假定前端信息不靠谱
            PersonInfo owner = new PersonInfo();
            owner.setUserId(1L);
            shop.setOwnerId(owner.getUserId());
            File shopImgFile = new File(FileUtil.getImgBasePath()+ FileUtil.getRandomFileName());
            try {
                shopImgFile.createNewFile();
            } catch (IOException e) {
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
                return modelMap;
            }
            try {
                inputStreamToFile(shopImg.getInputStream(),shopImgFile);
            } catch (IOException e) {
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
                return modelMap;
            }
            ShopExecution shopExecution = shopService.addShop(shop,shopImgFile);
            if (shopExecution.getState() == ShopStateEnum.CHECK.getState()){
                modelMap.put("success",true);
            } else {
                modelMap.put("success",false);
                modelMap.put("errMsg",shopExecution.getStateInfo());
            }
            return modelMap;
        } else{
            modelMap.put("success",false);
            modelMap.put("errMsg","请输入店铺信息");
            return modelMap;
        }
    }

    private static void inputStreamToFile(InputStream inputStream,File file){
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            int bytes = 0;
            byte[] buffer = new byte[1024];
            while ((bytes = inputStream.read(buffer)) != -1){
                //读满1024个字节就往输出流写入
                outputStream.write(buffer,0,bytes);
            }
        } catch (Exception e){
            throw new RuntimeException("调用inputStreamToFile产生异常"+e.getMessage());
        } finally {
            try {
                if (outputStream != null){
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e){
                throw new RuntimeException("调用inputStreamToFile关闭IO产生异常"+e.getMessage());
            }
        }
    }
}
