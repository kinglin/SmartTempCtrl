package com.kinglin.dao;

import java.util.List;

import com.kinglin.model.MyTimer;

public interface MyTimerDao {
	
	//����һ����ʱ����
	public void addTimer(MyTimer mytimer);
	
	//������еĶ�ʱ����
	public List<MyTimer> getAllMyTimers();
}