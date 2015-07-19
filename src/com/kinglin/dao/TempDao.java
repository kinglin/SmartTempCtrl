package com.kinglin.dao;

import java.util.List;

import com.kinglin.model.Temperature;

public interface TempDao {
	
	//添加天气
	public void addTemp(Temperature temperature);
	
	//获得所有天气
	public List<Temperature> getAllTemperatures();
	
	//获取最近天气
	public Temperature getLastTemperature();
	
	//获取倒数第二条天气
	public Temperature getSecLastTemperature();
	
	//获取最近的6条天气
	public List<Temperature> getRecentTemperatures();
}
