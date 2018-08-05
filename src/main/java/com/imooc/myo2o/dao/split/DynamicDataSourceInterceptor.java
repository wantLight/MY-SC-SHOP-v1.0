package com.imooc.myo2o.dao.split;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Locale;
import java.util.Properties;


/**
 * mybatis拦截器、
 * Created by xyzzg on 2018/8/5.
 */
//增删改封装在update中
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {
                MappedStatement.class, Object.class }),
        @Signature(type = Executor.class, method = "query", args = {
                MappedStatement.class, Object.class, RowBounds.class,
                ResultHandler.class }) })
public class DynamicDataSourceInterceptor implements Interceptor{

    private static Logger logger = LoggerFactory.getLogger(DynamicDataSourceInterceptor.class);

    private static final String REGEX = ".*insert\\u0020.*|.*delete\\u0020.*|.*update\\u0020.*";

    //主要拦截方法 返回代理对象
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] objects = invocation.getArgs();
        MappedStatement ms = (MappedStatement) objects[0];
        String lookupKey = DynamicDataSourceHolder.DB_MASTER;
        //判断是否是事务管理
        boolean synchronizationActive = TransactionSynchronizationManager.isActualTransactionActive();
        if (synchronizationActive != true){
            //读方法
            if (ms.getSqlCommandType().equals(SqlCommandType.SELECT)){
                //selectKey 为自增id查询主键(SELECT LAST_INSERT_ID())方法，使用主库
                if (ms.getId().contains(SelectKeyGenerator.SELECT_KEY_SUFFIX)){
                    lookupKey = DynamicDataSourceHolder.DB_MASTER;
                } else {
                    BoundSql boundSql = ms.getSqlSource().getBoundSql(objects[1]);
                    String sql = boundSql.getSql().toLowerCase(Locale.CHINA).replaceAll("[\\t\\n\\r]"," ");
                    if (sql.matches(REGEX)){
                        lookupKey =  DynamicDataSourceHolder.DB_MASTER;
                    } else {
                        //从库不能写数据
                        lookupKey = DynamicDataSourceHolder.DB_SLAVE;
                    }
                }
            }
        } else {
            lookupKey =DynamicDataSourceHolder.DB_MASTER;
        }
        logger.debug("设置方法[{}]use[{}]Strategy,SqlConnanType[{}]...",ms.getId(),lookupKey,
                ms.getSqlCommandType().name());
        DynamicDataSourceHolder.setDbType(lookupKey);
        return invocation.proceed();
    }

    //决定返回本体还是代理
    @Override
    public Object plugin(Object target) {
        //Executor支持一系列增删改查的操作
        if (target instanceof Executor){
            return Plugin.wrap(target,this);
        } else{
            return target;
        }
    }

    //类初始化做的相关设置
    @Override
    public void setProperties(Properties properties) {

    }
}
