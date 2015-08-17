package com.kinglin.model;

public class MyTimer {

	long id; //提醒的添加时间毫秒数
	long ringtime; //下一次提醒时间
	long circle; //提醒周期
	int timeron; //定时器开关
	String remark; //备注信息
	int content; //提醒内容图片
	long cleanstart; //免打扰时段开始时间
	long cleanend; //免打扰时段结束时间
	String musicUrl; //音乐路径
	
	public MyTimer() {
	}

	public MyTimer(long id,long ringtime,long circle,int timeron,String remark,int content,long cleanstart,long cleanend,String musicUrl) {
		this.id = id;
		this.ringtime = ringtime;
		this.circle = circle;
		this.timeron = timeron;
		this.remark = remark;
		this.content = content;
		this.cleanstart = cleanstart;
		this.cleanend = cleanend;
		this.musicUrl = musicUrl;
	}
	
	//工具函数，检测定时器时间是否在免打扰时段
	public boolean isTimerAvaliable(){
		
		if (ringtime < cleanend && ringtime >cleanstart) {
			return false;
		}
		return true;
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

	public long getCleanstart() {
		return cleanstart;
	}

	public void setCleanstart(long cleanstart) {
		this.cleanstart = cleanstart;
	}

	public long getCleanend() {
		return cleanend;
	}

	public void setCleanend(long cleanend) {
		this.cleanend = cleanend;
	}

	public String getMusicUrl() {
		return musicUrl;
	}

	public void setMusicUrl(String musicUrl) {
		this.musicUrl = musicUrl;
	}

}
