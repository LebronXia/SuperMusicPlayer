package com.xiamu.supermusicplayer.constant;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.util.Log;

/**
 * 一个记录和获取状态的类
 * @author zxbpc
 *
 */
public class Constant {
	public static List<HashMap<String, String>> trackList;
	public static int CURRENT = 0;//当前位置
	public static int PLAYMOD = 1;//播放模式
	public static int PLAYING_STATE = 0;//播放状态
	public static boolean ERER_PLAYED = false;//正在播放	
	public static String current_title = "MelodyEar";
	public static String current_artist = "you and me";
	public static String current_url;
	public static String current_duration;
	public static Context context;
	public static String ACTION_SONG_NORMAL_PLAY = "NORMAL_PLAY";  //按下列表正常播放的广播
	public static String ACTION_SONG_PAUSE = "SONG_PAUSE";			//暂停播放
	public static String ACTION_SONG_RESUME = "SONG_RESUME";		//继续播放
	public static String ACTION_SONG_NEXT = "SONG_NEXT";			//下一首
	public static String ACTION_SONG_PREVIOUS = "SONG_PREVIOUS";	//上一首
	public static String ACTION_MOD_0 = "MOD_0";
	public static String ACTION_MOD_1 = "MOD_1";
	public static String ACTION_MOD_2 = "MOD_2";
	public static String ACTION_MOD_3= "MOD_3";
	public static String ACTION_SONG_CHANGED = "SONG_CHANGED";
}
