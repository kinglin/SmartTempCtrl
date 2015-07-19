package com.kinglin.dao;

import java.util.List;

import com.kinglin.model.Temperature;

public interface TempDao {
	
	//�������
	public void addTemp(Temperature temperature);
	
	//�����������
	public List<Temperature> getAllTemperatures();
	
	//��ȡ�������
	public Temperature getLastTemperature();
	
	//��ȡ�����ڶ�������
	public Temperature getSecLastTemperature();
	
	//��ȡ�����6������
	public List<Temperature> getRecentTemperatures();
}
