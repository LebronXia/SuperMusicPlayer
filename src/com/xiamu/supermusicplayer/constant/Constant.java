package com.xiamu.supermusicplayer.constant;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.util.Log;

/**
 * һ����¼�ͻ�ȡ״̬����
 * @author zxbpc
 *
 */
public class Constant {
	public static List<HashMap<String, String>> trackList;
	public static int CURRENT = 0;//��ǰλ��
	public static int PLAYMOD = 1;//����ģʽ
	public static int PLAYING_STATE = 0;//����״̬
	public static boolean ERER_PLAYED = false;//���ڲ���	
	public static String current_title = "MelodyEar";
	public static String current_artist = "you and me";
	public static String current_url;
	public static String current_duration;
	public static Context context;
	public static String ACTION_SONG_NORMAL_PLAY = "NORMAL_PLAY";  //�����б��������ŵĹ㲥
	public static String ACTION_SONG_PAUSE = "SONG_PAUSE";			//��ͣ����
	public static String ACTION_SONG_RESUME = "SONG_RESUME";		//��������
	public static String ACTION_SONG_NEXT = "SONG_NEXT";			//��һ��
	public static String ACTION_SONG_PREVIOUS = "SONG_PREVIOUS";	//��һ��
	public static String ACTION_MOD_0 = "MOD_0";
	public static String ACTION_MOD_1 = "MOD_1";
	public static String ACTION_MOD_2 = "MOD_2";
	public static String ACTION_MOD_3= "MOD_3";
	public static String ACTION_SONG_CHANGED = "SONG_CHANGED";
}
