package com.kinglin.tools;

public class MusicInfo {
	private long id;  
	private String title;  
	private String album;  
	private int duration;  
	private long size;  
	private String artist;        
	private String url;       

	public MusicInfo(long pId, String pTitle){  
		id = pId;  
		title = pTitle;  
	}  

	public String getArtist() {  
		return artist;  
	}  

	public void setArtist(String artist) {  
		this.artist = artist;  
	}  

	public long getSize() {  
		return size;  
	}  

	public void setSize(long size) {  
		this.size = size;  
	}         

	public long getId() {  
		return id;  
	}  

	public void setId(long id) {  
		this.id = id;  
	}  

	public String getTitle() {  
		return title;  
	}  

	public void setTitle(String title) {  
		this.title = title;  
	}  

	public String getAlbum() {  
		return album;  
	}  

	public void setAlbum(String album) {  
		this.album = album;  
	}  

	public int getDuration() {  
		return duration;  
	}  

	public void setDuration(int duration) {  
		this.duration = duration;  
	}     

	public String getUrl() {  
		return url;  
	}  

	public void setUrl(String url) {  
		this.url = url;  
	}  


}
