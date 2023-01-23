package com.multi.drd.record;

import java.util.Date;
import java.util.List;

import com.multi.drd.cardio.CardioDTO;
import com.multi.drd.fitness.FitnessDTO;

public interface RecordDAO { 
 
	public RecordDTO findLatestRecord (int memberSEQ); 
	
	public RecordDTO findTodayRecord(int memberSEQ); 
	
	public List<RecordDTO> findMonthlyRecord(int memberSEQ, int year, int month);
	
	public RecordDTO findDailyRecord(int memberSEQ, Date date); 
	
	public int createOne(RecordDTO record);
	
	public int updateCardio(RecordDTO record); 

	public int updateFitness(RecordDTO record);  
	
	public int updateFood(RecordDTO record); 
	
	public int updateStatus(RecordDTO record);

	public int deleteField(RecordDTO record, String field); 
	
	public List<CardioDTO> findAllCardio(); 
	
	public List<FitnessDTO> findAllFitness();
	

}
