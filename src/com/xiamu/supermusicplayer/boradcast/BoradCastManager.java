package com.xiamu.supermusicplayer.boradcast;

import com.xiamu.supermusicplayer.constant.Constant;

import android.content.Intent;
import android.util.Log;

public class BoradCastManager {
	
	private static final String TAG = "BoradCastManager";
	/**
	 * 发送按下列表后发送的广播
	 */
	public static void sentNormalPlayBoradCast(){
		
		final Intent mIntent = new Intent(Constant.ACTION_SONG_NORMAL_PLAY);
		mIntent.putExtra("xiamu", "正常，来自BrardCastManager的广播");		
		Log.d(TAG, "Manager发送按下列表播放的广播");
		
		//发送广播
		Constant.context.sendBroadcast(mIntent);
	}
	
	/**
	 * 发送继续播放广播
	 */
	public static void sentSongResumeBoradCast(){
		
		final Intent mIntent = new Intent(Constant.ACTION_SONG_RESUME);
		mIntent.putExtra("xiamu", "RESUME，来自BrardCastManager的广播");
		Log.d(TAG, "RESUME，来自BrardCastManager的广播");
		//发送广播
		Constant.context.sendBroadcast(mIntent);
	}
	
	/**
	 * 发送暂停播放广播
	 */
	public static void sentSongPauseBoradCast(){
		final Intent mIntent = new Intent(Constant.ACTION_SONG_PAUSE);
		mIntent.putExtra("xiamu", "PAUSE，来自BrardCastManager的广播");
		Log.d(TAG, "PAUSE，来自BrardCastManager的广播");
		//发送广播
	    Constant.context.sendBroadcast(mIntent);
	}
	
	public static void sentSongPreviosBoradCast(){
		 final Intent mIntent = new Intent(Constant.ACTION_SONG_PREVIOUS);
		 mIntent.putExtra("xiamu", "PREVIOUS，来自BrardCastManager的广播");
		 Log.d(TAG, "PREVIOUS，来自BrardCastManager的广播");
		//发送广播
		 Constant.context.sendBroadcast(mIntent);		 
	}
	
	public static void sentSongNextBroadCast(){
		final Intent mIntent = new Intent(Constant.ACTION_SONG_NEXT);
		mIntent.putExtra("ximau", "NEXT，来自BrardCastManager的广播");
		
		Log.d(TAG, "NEXT，来自BrardCastManager的广播");
		//发送广播
		Constant.context.sendBroadcast(mIntent);		
	}
	
	public static void sentModChangedTo_0() {

		final Intent mIntent = new Intent(Constant.ACTION_MOD_0);
		mIntent.putExtra("xiamu", "0，来自BrardCastManager的广播");

		// 发送广播
		Constant.context.sendBroadcast(mIntent);
	}
	
	public static void sentModChangedTo_1() {

		final Intent mIntent = new Intent(Constant.ACTION_MOD_1);
		mIntent.putExtra("xiamu", "1，来自BrardCastManager的广播");

		// 发送广播
		Constant.context.sendBroadcast(mIntent);
	}
	
	public static void sentModChangedTo_2() {

		final Intent mIntent = new Intent(Constant.ACTION_MOD_2);
		mIntent.putExtra("xiamu", "2，来自BrardCastManager的广播");

		// 发送广播
		Constant.context.sendBroadcast(mIntent);
	}
	
	public static void sentModChangedTo_3() {

		final Intent mIntent = new Intent(Constant.ACTION_MOD_3);
		mIntent.putExtra("xiamu", "3，来自BrardCastManager的广播");

		// 发送广播
		Constant.context.sendBroadcast(mIntent);
	}
	
	public static void sentSongChangedBoradCast() {

		final Intent mIntent = new Intent(Constant.ACTION_SONG_CHANGED);
		mIntent.putExtra("xiamu", "CHANGED，来自BrardCastManager的广播");

		// 发送广播
		Constant.context.sendBroadcast(mIntent);
	}
}
