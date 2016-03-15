package com.xiamu.supermusicplayer.service.player;

import java.util.Random;

import com.xiamu.supermusicplayer.boradcast.BoradCastManager;
import com.xiamu.supermusicplayer.constant.Constant;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

/**
 * @author zxbpc
 *	自定义player的类
 */
public class MelodyPlayer extends MediaPlayer {
	
	public static MelodyPlayer player = new MelodyPlayer();
	public static SeekBar seekBar;
	 private int currentTime = 0;//歌曲播放进度
	private static final String TAG = "MelodyPlayer";
	/**
	 * 0:全部播放 1：顺序播放 2.单曲播放 3. 随机播放
	 * 得到下一首的位置
	 * @return next
	 */
	public static int getNextCursor(){
		int next = 0;
		switch (MelodyPlayer.getPlayMod()) {
		case 0:
			if (Constant.CURRENT != Constant.trackList.size()-1) {
				next = Constant.CURRENT + 1 % Constant.trackList.size();
			} else {
				next = 0;
			}
			break;
		case 1:
			if (Constant.CURRENT != Constant.trackList.size()-1) {
				next = Constant.CURRENT + 1 % Constant.trackList.size();
			} else {
				MelodyPlayer.player.stop();
		}
		break;
		case 2:
			next = Constant.CURRENT;
			break;
		case 3:
			next = new Random().nextInt(Constant.trackList.size());
			break;
		}
		return next;
	}
		
	
	/**
	 * 得到上一首歌的位置
	 * @return previous
	 */
	public static int getPreviousCursor(){
		int previous = 0;
		switch (MelodyPlayer.getPlayMod()) {
		case 0:
			previous = Constant.CURRENT - 1 % Constant.trackList.size();
			if (previous < 0) {
				previous = Constant.trackList.size() - 1;
			}
			break;
		case 1:
			previous = Constant.CURRENT - 1 % Constant.trackList.size();
			if (previous < 0) {
				previous = Constant.trackList.size() - 1;
			}
			break;
		case 2:
			previous = Constant.CURRENT;
			break;
		case 3:
			previous = new Random().nextInt(Constant.trackList.size());
			break;
		}
		return previous;
	}
	
	
	/**
	 * 继续播放
	 */
	public static void mResume(){
		if (MelodyPlayer.player != null) {
			MelodyPlayer.player.start();
		}
	}
	
	/**
	 * 暂停播放
	 */
	public static void mPause(){
		if (MelodyPlayer.player != null) {
			MelodyPlayer.player.pause();
		}
	}
	
	public static void mStop() {

		if (MelodyPlayer.player != null) {
			MelodyPlayer.player.stop();
		}
	}
	/**
	 * 播放音乐
	 */
	public void play() {
		// TODO Auto-generated method stub
		MelodyPlayer.player.reset(); //重置资源
		//设置播放资源
		try {
			MelodyPlayer.player.setDataSource(Constant.trackList.get(
					Constant.CURRENT).get("url"));
			MelodyPlayer.player.prepare();
			Log.d(TAG, Constant.trackList.get(
					Constant.CURRENT).get("url") + "--------");
		} catch (Exception e) {
			e.printStackTrace();
		}
		MelodyPlayer.player.start();
		//进度条监听
		MelodyPlayer.player.seekBarMonit();
		//播放器监听器
		MelodyPlayer.player.setOnCompletionListener(new MyplayerListener());
	}
	
	private final class MyplayerListener implements OnCompletionListener {

		@Override
		public void onCompletion(MediaPlayer mp) {
			// TODO Auto-generated method stub
			try {
				MelodyPlayer.next();
				BoradCastManager.sentSongChangedBoradCast();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		// TODO Auto-generated method stub

	}
	private void seekBarMonit(){
		if (MelodyPlayer.seekBar != null) {
			MelodyPlayer.seekBar.setMax(MelodyPlayer.player.getDuration());
			MelodyPlayer.seekBar
					.setOnSeekBarChangeListener(new MySeekBarListener());
		}
	}
	/**
	 * 下一首歌
	 */
	public static void next(){
		Constant.CURRENT = MelodyPlayer.getNextCursor();
		MelodyPlayer.player.stop();
		MelodyPlayer.player.play();
	}
	
	/**
	 * 上一首歌
	 */
	public static void previous(){
		Constant.CURRENT = MelodyPlayer.getPreviousCursor();
		MelodyPlayer.player.stop();
		MelodyPlayer.player.play();
	}
	
	private static int getPlayMod(){
		return Constant.PLAYMOD;
	}
	/**
	 * 获取当前播放时间
	 * @return
	 */
	public int getCurrent(){
		return MelodyPlayer.player.getCurrentPosition();
	}
	
	//SeekBar监听器发生改变
	public class MySeekBarListener implements OnSeekBarChangeListener{

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}

		//结束触发
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			MelodyPlayer.player.seekTo(seekBar.getProgress());
		}
		
	}
	
}
