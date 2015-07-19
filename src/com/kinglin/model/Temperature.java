package com.kinglin.model;

public class Temperature {

	long tempId;
	float temp;
	
	public Temperature(long tempId,float temp){
		this.tempId = tempId;
		this.temp = temp;
	}
	
	public long getTempId() {
		return tempId;
	}

	public void setTempId(long tempId) {
		this.tempId = tempId;
	}

	public float getTemp() {
		return temp;
	}

	public void setTemp(float temp) {
		this.temp = temp;
	}

}
