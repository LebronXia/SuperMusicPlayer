package com.xiamu.supermusicplayer.boradcast;

import com.xiamu.supermusicplayer.constant.Constant;

import android.content.Intent;
import android.util.Log;

public class BoradCastManager {
	
	private static final String TAG = "BoradCastManager";
	/**
	 * ���Ͱ����б���͵Ĺ㲥
	 */
	public static void sentNormalPlayBoradCast(){
		
		final Intent mIntent = new Intent(Constant.ACTION_SONG_NORMAL_PLAY);
		mIntent.putExtra("xiamu", "����������BrardCastManager�Ĺ㲥");		
		Log.d(TAG, "Manager���Ͱ����б��ŵĹ㲥");
		
		//���͹㲥
		Constant.context.sendBroadcast(mIntent);
	}
	
	/**
	 * ���ͼ������Ź㲥
	 */
	public static void sentSongResumeBoradCast(){
		
		final Intent mIntent = new Intent(Constant.ACTION_SONG_RESUME);
		mIntent.putExtra("xiamu", "RESUME������BrardCastManager�Ĺ㲥");
		Log.d(TAG, "RESUME������BrardCastManager�Ĺ㲥");
		//���͹㲥
		Constant.context.sendBroadcast(mIntent);
	}
	
	/**
	 * ������ͣ���Ź㲥
	 */
	public static void sentSongPauseBoradCast(){
		final Intent mIntent = new Intent(Constant.ACTION_SONG_PAUSE);
		mIntent.putExtra("xiamu", "PAUSE������BrardCastManager�Ĺ㲥");
		Log.d(TAG, "PAUSE������BrardCastManager�Ĺ㲥");
		//���͹㲥
	    Constant.context.sendBroadcast(mIntent);
	}
	
	public static void sentSongPreviosBoradCast(){
		 final Intent mIntent = new Intent(Constant.ACTION_SONG_PREVIOUS);
		 mIntent.putExtra("xiamu", "PREVIOUS������BrardCastManager�Ĺ㲥");
		 Log.d(TAG, "PREVIOUS������BrardCastManager�Ĺ㲥");
		//���͹㲥
		 Constant.context.sendBroadcast(mIntent);		 
	}
	
	public static void sentSongNextBroadCast(){
		final Intent mIntent = new Intent(Constant.ACTION_SONG_NEXT);
		mIntent.putExtra("ximau", "NEXT������BrardCastManager�Ĺ㲥");
		
		Log.d(TAG, "NEXT������BrardCastManager�Ĺ㲥");
		//���͹㲥
		Constant.context.sendBroadcast(mIntent);		
	}
	
	public static void sentModChangedTo_0() {

		final Intent mIntent = new Intent(Constant.ACTION_MOD_0);
		mIntent.putExtra("xiamu", "0������BrardCastManager�Ĺ㲥");

		// ���͹㲥
		Constant.context.sendBroadcast(mIntent);
	}
	
	public static void sentModChangedTo_1() {

		final Intent mIntent = new Intent(Constant.ACTION_MOD_1);
		mIntent.putExtra("xiamu", "1������BrardCastManager�Ĺ㲥");

		// ���͹㲥
		Constant.context.sendBroadcast(mIntent);
	}
	
	public static void sentModChangedTo_2() {

		final Intent mIntent = new Intent(Constant.ACTION_MOD_2);
		mIntent.putExtra("xiamu", "2������BrardCastManager�Ĺ㲥");

		// ���͹㲥
		Constant.context.sendBroadcast(mIntent);
	}
	
	public static void sentModChangedTo_3() {

		final Intent mIntent = new Intent(Constant.ACTION_MOD_3);
		mIntent.putExtra("xiamu", "3������BrardCastManager�Ĺ㲥");

		// ���͹㲥
		Constant.context.sendBroadcast(mIntent);
	}
	
	public static void sentSongChangedBoradCast() {

		final Intent mIntent = new Intent(Constant.ACTION_SONG_CHANGED);
		mIntent.putExtra("xiamu", "CHANGED������BrardCastManager�Ĺ㲥");

		// ���͹㲥
		Constant.context.sendBroadcast(mIntent);
	}
}
