package com.xiamu.supermusicplayer.fragment;

import com.xiamu.supermusicplayer.R;
import com.xiamu.supermusicplayer.BtnUIMonitor.BtnUIMonitor;
import com.xiamu.supermusicplayer.boradcast.BoradCastManager;
import com.xiamu.supermusicplayer.constant.Constant;
import com.xiamu.supermusicplayer.listener.NextButtonListener;
import com.xiamu.supermusicplayer.listener.PlayButtonListener;
import com.xiamu.supermusicplayer.listener.PreviousButtonListener;
import com.xiamu.supermusicplayer.service.player.MelodyPlayer;
import com.xiamu.supermusicplayer.utils.ArtWorkUtils;
import com.xiamu.supermusicplayer.view.RoundImageView;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class PlayingFragment extends Fragment {

	private View mRootView;
	private TextView title,artist;
	private TextView tv_currentTime , tv_allTime;
	private SeekBar seekBar;
	private Button btn_play;
	private Button btn_next, btn_pre, btn_playMod;
	private RoundImageView imageView;
	private Handler handler = new Handler(); 
	private BroadcastReceiver mBroadcastReceiver;
	private Toast mToast;
	private int currentTime = 0;//歌曲播放进度
	private int playMod = 1;
	private static final String TAG = "PlayingFragment";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mRootView = inflater.inflate(R.layout.fragment_playmusic, container, false);
		init();
		initBoradcastReceiver();
		Constant.context = getActivity();
		//启动
		handler.post(updateThread);
		return mRootView;
	}
	private void init() {
		// TODO Auto-generated method stub
		initView();
		initButton();
		initSeekBar();
		new BtnUIMonitor(null, null, null, null, null, null, null,imageView);
	}
	
	Runnable updateThread = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			// 获得歌曲的长度并设置成播放进度条的最大值
			seekBar.setMax(getDuration());
			// 获得歌曲现在播放位置并设置成播放进度条的值
			seekBar.setProgress(getCurrent());
			tv_currentTime.setText(formatTime(getCurrent()));
			tv_allTime.setText(formatTime(getDuration()));
			updateTitle();
			SetPlayBtnBg();
			//
			// 每次延迟100毫秒再启动线程
			handler.postDelayed(updateThread, 100);
		}
	};
	/**
	 * 初始化字体
	 */
	private void initView() {
		// TODO Auto-generated method stub
		title = (TextView) mRootView.findViewById(R.id.title_text);
		artist = (TextView) mRootView.findViewById(R.id.art_text);
		imageView = (RoundImageView) mRootView.findViewById(R.id.infoOperating);						
		ArtWorkUtils.setContent(imageView);
		if (Constant.PLAYING_STATE != 0) {
			// 当服务开始播放音乐后，将专辑图片添加旋转动画效果
			Animation operatingAnim = AnimationUtils.loadAnimation(
					Constant.context, R.anim.tip);
			LinearInterpolator lin = new LinearInterpolator();
			operatingAnim.setInterpolator(lin);
			if (operatingAnim != null) {
				imageView.startAnimation(operatingAnim);
			}
		} else {
			imageView.clearAnimation();// 移除专辑imageView的动画
		}
	}

	/**
	 * 初始化进度条
	 */
	private void initSeekBar() {
		// TODO Auto-generated method stub
		seekBar = (SeekBar) mRootView.findViewById(R.id.sb_music);
		tv_currentTime = (TextView) mRootView.findViewById(R.id.music_current_time);
		tv_allTime = (TextView) mRootView.findViewById(R.id.music_always_time);
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				// fromUser判断是用户改变的滑块的值
				if (fromUser == true) {
					movePlay(progress);
				}
			}
		});
	}
	
	/**
	 * 初始化按钮
	 */
	private void initButton() {
		// TODO Auto-generated method stub
		btn_play = (Button) mRootView.findViewById(R.id.btn_play_ing);
		btn_next = (Button) mRootView.findViewById(R.id.btn_next_ing);
		btn_pre = (Button) mRootView.findViewById(R.id.btn_pre_ing);
		btn_playMod = (Button) mRootView.findViewById(R.id.btn_playmod);
		btn_play.setOnClickListener(new PlayButtonListener());
		btn_next.setOnClickListener(new NextButtonListener());
		btn_pre.setOnClickListener(new PreviousButtonListener());
		btn_playMod.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				playMod = (playMod +1) % 4;
				Constant.PLAYMOD = playMod;
				Log.d(TAG, Constant.PLAYMOD+"播放模式-----------");
				SetPlayModBtnBg(playMod);
			}
		});
	}
	
	private void registerBoradcastReceiver() {
		// TODO Auto-generated method stub

		IntentFilter mIntentFilter = new IntentFilter();
		mIntentFilter.addAction(Constant.ACTION_SONG_CHANGED);
		Constant.context.registerReceiver(mBroadcastReceiver, mIntentFilter);
		
	}
	
	private void initBoradcastReceiver() {
		// TODO Auto-generated method stub
		mBroadcastReceiver = new BroadcastReceiver(){

			@Override
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub
				final String action = intent.getAction();
				if (action.equals(Constant.ACTION_SONG_CHANGED)) {
					updateTitle();
				}			
			}
			
		};
		registerBoradcastReceiver();
	}
	
	/**
	 * 更新歌曲名
	 */
	private void updateTitle() {
		// TODO Auto-generated method stub
		title.setText(Constant.current_title);
		artist.setText(Constant.current_artist);
	}

	/**
	 * 设置播放按钮外形
	 */
	private void SetPlayBtnBg() {
		final int state = Constant.PLAYING_STATE;
		switch (state) {
		case 0:// 1要求设置为play背景
			btn_play.setBackgroundResource(R.drawable.btn_play);
			break;
		case 1:
			btn_play.setBackgroundResource(R.drawable.btn_pause);
		}

	}
	
	private void SetPlayModBtnBg(int command) {

		if (btn_playMod != null) {
			switch (command) {
			case 0:// 设置playMod背景
				btn_playMod.setBackgroundResource(R.drawable.playing_repeat_all_normal);
				mToast.setText("全部循环");
				mToast.setDuration(Toast.LENGTH_SHORT);
				BoradCastManager.sentModChangedTo_0();
				break;
			case 1:
				btn_playMod.setBackgroundResource(R.drawable.playing_play_all_normal);
				if (mToast != null) {
					mToast.setText("顺序播放");
				} else {
					mToast = Toast.makeText(getActivity(), "顺序播放", Toast.LENGTH_SHORT);
				}				
				BoradCastManager.sentModChangedTo_1();
				break;
			case 2:
				btn_playMod.setBackgroundResource(R.drawable.playing_repeat_single_normal);				
				if (mToast != null) {
				mToast.setText("单曲循环");
				} else {
					mToast = Toast.makeText(getActivity(), "单曲循环", Toast.LENGTH_SHORT);
				}
				BoradCastManager.sentModChangedTo_2();
				break;				
			case 3:
				btn_playMod.setBackgroundResource(R.drawable.playing_random_normal);
				mToast.setText("随机播放");
				mToast.setDuration(Toast.LENGTH_SHORT);
				BoradCastManager.sentModChangedTo_3();
				break;
			}
			mToast.show();
		}
	}
	/**
	 * 格式化时间，将其变成00:00的形式
	 */
	public String formatTime(int time) {
		int secondSum = time / 1000;
		int minute = secondSum / 60;
		int second = secondSum % 60;

		String result = "";		
		result = result + minute + ":";
		if (second < 10)
			result = result + "0";
		result = result + second;

		return result;
	}
	
	/**
	 *得到当前播放进度 
	 */
	public int getCurrent() {
			return MelodyPlayer.player.getCurrentPosition();
		
	}	
	/**
	 *	跳到输入的进度 
	 */
	public void movePlay(int progress) {
		MelodyPlayer.player.seekTo(progress);
		currentTime = progress;
	}
	
	/**
	 * 得到歌曲时长
	 * @return
	 */
	public int getDuration() {
		return MelodyPlayer.player.getDuration();
	}
}
