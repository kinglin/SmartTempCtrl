package com.kinglin.model;

public class MyTimer {

	long id; //���ѵ����ʱ�������
	long ringtime; //��һ������ʱ��
	long circle; //��������
	int timeron; //��ʱ������
	String remark; //��ע��Ϣ
	int content; //��������ͼƬ
	long cleanstart; //�����ʱ�ο�ʼʱ��
	long cleanend; //�����ʱ�ν���ʱ��
	String musicUrl; //����·��
	
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
	
	//���ߺ�������ⶨʱ��ʱ���Ƿ��������ʱ��
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
