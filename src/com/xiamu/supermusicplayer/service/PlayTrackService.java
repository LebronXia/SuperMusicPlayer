package com.xiamu.supermusicplayer.service;

import com.xiamu.supermusicplayer.constant.Constant;
import com.xiamu.supermusicplayer.service.player.MelodyPlayer;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

/**
 * 系统的服务，包括对广播的处理
 * @author zxbpc
 *
 */
public class PlayTrackService extends Service {

	BroadcastReceiver mBroadcastReceiver;
	private static final String TAG = "PlayTrackService";
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		initBroadcastReceiver();  //初始化广播接收器
		return super.onStartCommand(intent, flags, startId);
	}
	

	private void startPlaying() {
		MelodyPlayer.player.play();
	}
	
	private void pauseTrack() {

		MelodyPlayer.mPause();
	}

	private void resumeTrack() {

		MelodyPlayer.mResume();
	}
	
	private void playNextTrack() {		
		MelodyPlayer.next();
	}
	
	private void playPreviousTrack(){
		MelodyPlayer.previous();
	}
	
	
	
	/**
	 * 注册广播
	 */
	private void registerBoradcastReceiver() {
		//先初始化，后注册
		final IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(Constant.ACTION_SONG_NORMAL_PLAY);
		intentFilter.addAction(Constant.ACTION_SONG_PAUSE);
		intentFilter.addAction(Constant.ACTION_SONG_RESUME);
		intentFilter.addAction(Constant.ACTION_SONG_NEXT);
		intentFilter.addAction(Constant.ACTION_SONG_PREVIOUS);
		intentFilter.addAction(Constant.ACTION_MOD_0);
		intentFilter.addAction(Constant.ACTION_MOD_1);
		intentFilter.addAction(Constant.ACTION_MOD_2);
		intentFilter.addAction(Constant.ACTION_MOD_3);
		
		//注册广播
		intentFilter.setPriority(100);
		registerReceiver(mBroadcastReceiver, intentFilter);
	}

	/**
	 * 初始化广播接收器
	 */
	private void initBroadcastReceiver() {
		mBroadcastReceiver = new BroadcastReceiver(){

			@Override
			public void onReceive(Context context, Intent intent) {
				final String action = intent.getAction();
				//分析广播
				Log.d(TAG, "分析广播-----");
				if (action.equals(Constant.ACTION_SONG_NORMAL_PLAY)) {
					startPlaying();
					Constant.PLAYING_STATE = 1;
					Constant.ERER_PLAYED = true;
					Log.d(TAG, "服务器收到按下列表播放的广播");					
				}
				if (action.equals(Constant.ACTION_SONG_RESUME)) {
					resumeTrack();
					Constant.PLAYING_STATE= 1;
					Log.d(TAG, "服务器收到继续播放的广播");	
				}
				if (action.equals(Constant.ACTION_SONG_PAUSE)) {
					pauseTrack();
					Constant.PLAYING_STATE = 0;
					Log.d(TAG, "服务器收到暂停播放的广播");						
				}
				if (action.equals(Constant.ACTION_SONG_NEXT)) {
					playNextTrack();
					Constant.PLAYING_STATE = 1;
					Constant.ERER_PLAYED = true;
					Log.d(TAG, "服务器收到播放下一首的广播");
					
				}
				if (action.equals(Constant.ACTION_SONG_PREVIOUS)) {
					playPreviousTrack();
					Constant.PLAYING_STATE = 1;
					Log.d(TAG, "服务器收到播放上一首的广播");
				}
				if (action.equals(Constant.ACTION_MOD_0)) {
					Constant.PLAYMOD = 0;
					System.out.println("服务器收到_1播放的广播");

				}
				if (action.equals(Constant.ACTION_MOD_1)) {
					Constant.PLAYMOD = 1;
					System.out.println("服务器收到_1播放的广播");

				}
				if (action.equals(Constant.ACTION_MOD_2)) {
					Constant.PLAYMOD = 2;
					System.out.println("服务器收到_1播放的广播");

				}
				if (action.equals(Constant.ACTION_MOD_3)) {
					Constant.PLAYMOD = 3;
					System.out.println("服务器收到_1播放的广播");

				}
			}			
		};
		registerBoradcastReceiver();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	
}
