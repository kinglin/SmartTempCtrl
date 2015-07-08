package com.kinglin.model;

public class MyTimer {

	long id; //提醒的添加时间毫秒数
	long ringtime; //下一次提醒时间
	long circle; //提醒周期
	int timeron; //定时器开关
	String remark; //备注信息
	int content; //提醒内容图片
	
	public MyTimer() {
	}

	public MyTimer(long id,long ringtime,long circle,int timeron,String remark,int content) {
		this.id = id;
		this.ringtime = ringtime;
		this.circle = circle;
		this.timeron = timeron;
		this.remark = remark;
		this.content = content;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getRingtime() {
		return ringtime;
	}

	public void setRingtime(long ringtime) {
		this.ringtime = ringtime;
	}

	public long getCircle() {
		return circle;
	}

	public int getTimeron() {
		return timeron;
	}

	public void setTimeron(int timeron) {
		this.timeron = timeron;
	}

	public void setCircle(long circle) {
		this.circle = circle;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getContent() {
		return content;
	}

	public void setContent(int content) {
		this.content = content;
	}

}
