package com.multi.drd.personalroutine;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PersonalRoutineDAOImpl implements PersonalRoutineDAO{
	SqlSession sqlSession;
	
	public PersonalRoutineDAOImpl() {}
		
	@Autowired
	public PersonalRoutineDAOImpl(SqlSession sqlSession) {
		super();
		this.sqlSession = sqlSession;
	} 
	
	public PersonalRoutineDTO findOne() {
		return sqlSession.selectOne("com.multi.drd.personalroutine.findOne");
	}
	public PersonalRoutineDTO findOne1(int PersonalRoutineSEQ) {
		return sqlSession.selectOne("com.multi.drd.personalroutine.findOne1", PersonalRoutineSEQ);
	}
	public int updateOne(PersonalRoutineDTO pr) {
		return sqlSession.update("com.multi.drd.personalroutine.updateOne", pr);
	}

	@Override
	public void updatefitness(Map<String, Object> map) {
		sqlSession.update("com.multi.drd.personalroutine.updatefitness", map);
	}

	@Override
	public void updatecardio(Map<String, Object> map) {
		sqlSession.update("com.multi.drd.personalroutine.updatecardio", map);		
	}
	
}
