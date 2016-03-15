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
	private int currentTime = 0;//�������Ž���
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
		//����
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
			// ��ø����ĳ��Ȳ����óɲ��Ž����������ֵ
			seekBar.setMax(getDuration());
			// ��ø������ڲ���λ�ò����óɲ��Ž�������ֵ
			seekBar.setProgress(getCurrent());
			tv_currentTime.setText(formatTime(getCurrent()));
			tv_allTime.setText(formatTime(getDuration()));
			updateTitle();
			SetPlayBtnBg();
			//
			// ÿ���ӳ�100�����������߳�
			handler.postDelayed(updateThread, 100);
		}
	};
	/**
	 * ��ʼ������
	 */
	private void initView() {
		// TODO Auto-generated method stub
		title = (TextView) mRootView.findViewById(R.id.title_text);
		artist = (TextView) mRootView.findViewById(R.id.art_text);
		imageView = (RoundImageView) mRootView.findViewById(R.id.infoOperating);						
		ArtWorkUtils.setContent(imageView);
		if (Constant.PLAYING_STATE != 0) {
			// ������ʼ�������ֺ󣬽�ר��ͼƬ�����ת����Ч��
			Animation operatingAnim = AnimationUtils.loadAnimation(
					Constant.context, R.anim.tip);
			LinearInterpolator lin = new LinearInterpolator();
			operatingAnim.setInterpolator(lin);
			if (operatingAnim != null) {
				imageView.startAnimation(operatingAnim);
			}
		} else {
			imageView.clearAnimation();// �Ƴ�ר��imageView�Ķ���
		}
	}

	/**
	 * ��ʼ��������
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
				// fromUser�ж����û��ı�Ļ����ֵ
				if (fromUser == true) {
					movePlay(progress);
				}
			}
		});
	}
	
	/**
	 * ��ʼ����ť
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
				Log.d(TAG, Constant.PLAYMOD+"����ģʽ-----------");
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
	 * ���¸�����
	 */
	private void updateTitle() {
		// TODO Auto-generated method stub
		title.setText(Constant.current_title);
		artist.setText(Constant.current_artist);
	}

	/**
	 * ���ò��Ű�ť����
	 */
	private void SetPlayBtnBg() {
		final int state = Constant.PLAYING_STATE;
		switch (state) {
		case 0:// 1Ҫ������Ϊplay����
			btn_play.setBackgroundResource(R.drawable.btn_play);
			break;
		case 1:
			btn_play.setBackgroundResource(R.drawable.btn_pause);
		}

	}
	
	private void SetPlayModBtnBg(int command) {

		if (btn_playMod != null) {
			switch (command) {
			case 0:// ����playMod����
				btn_playMod.setBackgroundResource(R.drawable.playing_repeat_all_normal);
				mToast.setText("ȫ��ѭ��");
				mToast.setDuration(Toast.LENGTH_SHORT);
				BoradCastManager.sentModChangedTo_0();
				break;
			case 1:
				btn_playMod.setBackgroundResource(R.drawable.playing_play_all_normal);
				if (mToast != null) {
					mToast.setText("˳�򲥷�");
				} else {
					mToast = Toast.makeText(getActivity(), "˳�򲥷�", Toast.LENGTH_SHORT);
				}				
				BoradCastManager.sentModChangedTo_1();
				break;
			case 2:
				btn_playMod.setBackgroundResource(R.drawable.playing_repeat_single_normal);				
				if (mToast != null) {
				mToast.setText("����ѭ��");
				} else {
					mToast = Toast.makeText(getActivity(), "����ѭ��", Toast.LENGTH_SHORT);
				}
				BoradCastManager.sentModChangedTo_2();
				break;				
			case 3:
				btn_playMod.setBackgroundResource(R.drawable.playing_random_normal);
				mToast.setText("�������");
				mToast.setDuration(Toast.LENGTH_SHORT);
				BoradCastManager.sentModChangedTo_3();
				break;
			}
			mToast.show();
		}
	}
	/**
	 * ��ʽ��ʱ�䣬������00:00����ʽ
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
	 *�õ���ǰ���Ž��� 
	 */
	public int getCurrent() {
			return MelodyPlayer.player.getCurrentPosition();
		
	}	
	/**
	 *	��������Ľ��� 
	 */
	public void movePlay(int progress) {
		MelodyPlayer.player.seekTo(progress);
		currentTime = progress;
	}
	
	/**
	 * �õ�����ʱ��
	 * @return
	 */
	public int getDuration() {
		return MelodyPlayer.player.getDuration();
	}
}
