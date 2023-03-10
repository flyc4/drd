package com.multi.drd.food;

import java.util.List;

public interface FoodService { 
	
	public List<FoodDTO> findAll();
	
	// SQL의 In 연산자를 이용하여 조회
	public List<FoodDTO> findFoodListByPK(List<String> foodSEQList);

}
