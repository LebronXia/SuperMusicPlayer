package com.xiamu.supermusicplayer.comment;

import android.graphics.Bitmap;

public class Mp3Bean {
	long id, size;//	音乐Id  文件大小

	public String title; //音乐标题

	String url; //音乐路径

	String art; //艺术家

	String duration; //时长
	Bitmap icon;
	private String sortLetters;  //显示数据拼音的首字母
	
	public String getSortLetters() {
		return sortLetters;
	}
	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getArt() {
		return art;
	}
	public void setArt(String art) {
		this.art = art;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public Bitmap getIcon() {
		return icon;
	}
	public void setIcon(Bitmap icon) {
		this.icon = icon;
	}
	
}
