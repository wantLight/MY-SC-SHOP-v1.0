package com.imooc.myo2o.dao;

import java.util.List;

import com.imooc.myo2o.entity.Award;
import org.apache.ibatis.annotations.Param;

public interface AwardDao {
	List<Award> queryAwardList(@Param("awardCondition") Award awardCondition,
							   @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

	int queryAwardCount(@Param("awardCondition") Award awardCondition);

	Award queryAwardByAwardId(long awardId);

	int insertAward(Award award);

	int updateAward(Award award);

	int deleteAward(long awardId);
}
