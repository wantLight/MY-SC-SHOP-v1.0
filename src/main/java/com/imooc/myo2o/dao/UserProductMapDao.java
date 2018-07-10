package com.imooc.myo2o.dao;

import java.util.List;

import com.imooc.myo2o.entity.UserProductMap;
import org.apache.ibatis.annotations.Param;

public interface UserProductMapDao {
	/**
	 * 
	 * @param userProductCondition
	 * @param rowIndex
	 * @param pageSize
	 * @return
	 */
	List<UserProductMap> queryUserProductMapList(
            @Param("userProductCondition") UserProductMap userProductCondition,
            @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

	/**
	 * 
	 * @param userProductCondition
	 * @return
	 */
	int queryUserProductMapCount(
            @Param("userProductCondition") UserProductMap userProductCondition);

	/**
	 * 
	 * @param userProductMap
	 * @return
	 */
	int insertUserProductMap(UserProductMap userProductMap);
}
