package com.xiamu.supermusicplayer.listview;

public class Content {
	private String letter;
	private String song_title;
	private String duration;
	private String artist;
	private String url;
	
	
	public Content(String letter, String song_title, String duration,
			String artist, String url) {
		super();
		this.letter = letter;
		this.song_title = song_title;
		this.duration = duration;
		this.artist = artist;
		this.url = url;
	}
	public String getLetter() {
		return letter;
	}
	public void setLetter(String letter) {
		this.letter = letter;
	}
	public String getSong_title() {
		return song_title;
	}
	public void setSong_title(String song_title) {
		this.song_title = song_title;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
