package com.imooc.myo2o.dao.split;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xyzzg on 2018/8/5.
 */
public class DynamicDataSourceHolder {

    private static Logger logger = LoggerFactory.getLogger(DynamicDataSourceHolder.class);

    //线程安全
    private static ThreadLocal<String> contextHolder = new ThreadLocal <>();

    public static final String DB_MASTER = "master";

    public static final String DB_SLAVE = "slave";

    public static String getDbType(){
        String db = contextHolder.get();
        if (db == null){
            db = DB_MASTER;
        }
        return db;
    }

    //设置线程的dbType
    public static void setDbType(String str){
        logger.debug("所使用数据源："+str);
        contextHolder.set(str);
    }

    //清理连接类型
    public static void clearDBType(){
        contextHolder.remove();
    }
}
