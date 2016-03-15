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
 * ϵͳ�ķ��񣬰����Թ㲥�Ĵ���
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
		initBroadcastReceiver();  //��ʼ���㲥������
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
	 * ע��㲥
	 */
	private void registerBoradcastReceiver() {
		//�ȳ�ʼ������ע��
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
		
		//ע��㲥
		intentFilter.setPriority(100);
		registerReceiver(mBroadcastReceiver, intentFilter);
	}

	/**
	 * ��ʼ���㲥������
	 */
	private void initBroadcastReceiver() {
		mBroadcastReceiver = new BroadcastReceiver(){

			@Override
			public void onReceive(Context context, Intent intent) {
				final String action = intent.getAction();
				//�����㲥
				Log.d(TAG, "�����㲥-----");
				if (action.equals(Constant.ACTION_SONG_NORMAL_PLAY)) {
					startPlaying();
					Constant.PLAYING_STATE = 1;
					Constant.ERER_PLAYED = true;
					Log.d(TAG, "�������յ������б��ŵĹ㲥");					
				}
				if (action.equals(Constant.ACTION_SONG_RESUME)) {
					resumeTrack();
					Constant.PLAYING_STATE= 1;
					Log.d(TAG, "�������յ��������ŵĹ㲥");	
				}
				if (action.equals(Constant.ACTION_SONG_PAUSE)) {
					pauseTrack();
					Constant.PLAYING_STATE = 0;
					Log.d(TAG, "�������յ���ͣ���ŵĹ㲥");						
				}
				if (action.equals(Constant.ACTION_SONG_NEXT)) {
					playNextTrack();
					Constant.PLAYING_STATE = 1;
					Constant.ERER_PLAYED = true;
					Log.d(TAG, "�������յ�������һ�׵Ĺ㲥");
					
				}
				if (action.equals(Constant.ACTION_SONG_PREVIOUS)) {
					playPreviousTrack();
					Constant.PLAYING_STATE = 1;
					Log.d(TAG, "�������յ�������һ�׵Ĺ㲥");
				}
				if (action.equals(Constant.ACTION_MOD_0)) {
					Constant.PLAYMOD = 0;
					System.out.println("�������յ�_1���ŵĹ㲥");

				}
				if (action.equals(Constant.ACTION_MOD_1)) {
					Constant.PLAYMOD = 1;
					System.out.println("�������յ�_1���ŵĹ㲥");

				}
				if (action.equals(Constant.ACTION_MOD_2)) {
					Constant.PLAYMOD = 2;
					System.out.println("�������յ�_1���ŵĹ㲥");

				}
				if (action.equals(Constant.ACTION_MOD_3)) {
					Constant.PLAYMOD = 3;
					System.out.println("�������յ�_1���ŵĹ㲥");

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
