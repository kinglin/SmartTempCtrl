package com.kinglin.model;

public class Temperature {

	String time;
	int temp;
	
	public Temperature(){
	}
	
	public Temperature(String time,int temp){
		this.time = time;
		this.temp = temp;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getTemp() {
		return temp;
	}

	public void setTemp(int temp) {
		this.temp = temp;
	}


}
