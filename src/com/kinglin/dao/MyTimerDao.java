package com.kinglin.dao;

import java.util.List;

import com.kinglin.model.MyTimer;

public interface MyTimerDao {
	
	//添加一个定时提醒
	public void addTimer(MyTimer mytimer);
	
	//获得所有的定时提醒
	public List<MyTimer> getAllMyTimers();
	
	//更新特定定时器
	public void updateTimer(MyTimer myTimer);
	
	//删除定时器
	public void deleteTimer(MyTimer myTimer);
}
