package com.multi.drd.food;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FoodDAOImpl implements FoodDAO {
	
	SqlSession sqlSession;

	public FoodDAOImpl() {
		super();
	}

	@Autowired
	public FoodDAOImpl(SqlSession sqlSession) {
		super();
		this.sqlSession = sqlSession;
	} 
	
	@Override
	public List<FoodDTO> findFoodListByPK(List<Integer> foodSEQList){
		return sqlSession.selectList("com.multi.drd.food.findFoodListByPK", foodSEQList);
	}
}