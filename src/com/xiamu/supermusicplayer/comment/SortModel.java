package com.xiamu.supermusicplayer.comment;

public class SortModel {
	private String sortLetters;  //显示数据拼音的首字母
	private String title; //音乐标题
	private String art; //艺术家
	private String duration; //时长
	private String url; //音乐路径
	public String getSortLetters() {
		return sortLetters;
	}
	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
