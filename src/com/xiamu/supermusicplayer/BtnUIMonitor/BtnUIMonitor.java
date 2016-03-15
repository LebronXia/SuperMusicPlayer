package com.xiamu.supermusicplayer.BtnUIMonitor;

import com.xiamu.supermusicplayer.R;
import com.xiamu.supermusicplayer.constant.Constant;
import com.xiamu.supermusicplayer.utils.ArtWorkUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * @author zxbpc
 *更新UI操作的类
 */
public class BtnUIMonitor {
	ImageButton btn_play, btn_previous, btn_next, btn_playMod;
	SeekBar seekBar;
	TextView musicTitle;
	ImageView artIcon;
	ImageView artImage;
	static BroadcastReceiver mBroadcastReceiver;
	public BtnUIMonitor(ImageButton btn_play, ImageButton btn_previous, ImageButton btn_next,
			ImageButton btn_playMod, SeekBar seekBar, TextView b_text,
			ImageView artIcon,ImageView artImage) {
		super();
		this.btn_play = btn_play;
		this.btn_previous = btn_previous;
		this.btn_next = btn_next;
		this.btn_playMod = btn_playMod;
		this.seekBar = seekBar;
		this.musicTitle = b_text;
		this.artIcon = artIcon;
		this.artImage = artImage;
		initBoradcastReceiver();
	}
	
	/**
	 * 更新页面视图
	 */
	private void updatetitle() {
		// TODO Auto-generated method stub
	if (Constant.ERER_PLAYED) {
					
		Constant.current_title = Constant.trackList.get(Constant.CURRENT)
				.get("title").toString();
		Constant.current_artist = Constant.trackList.get(Constant.CURRENT)
				.get("Artist").toString();
		Constant.current_url = Constant.trackList.get(Constant.CURRENT)
				.get("url").toString();
		Constant.current_duration = Constant.trackList
				.get(Constant.CURRENT).get("duratation").toString();
					
		if (Constant.ERER_PLAYED && musicTitle != null) {
			musicTitle.setText(Constant.current_title);		
		}
		Log.d("BtnUIMonitor", Constant.current_title);
		Log.d("BtnUIMonitor", Constant.current_url);
	}
}
	
	public static void updateCurrent() {
		if (Constant.ERER_PLAYED) {

			Constant.current_title = Constant.trackList.get(Constant.CURRENT)
					.get("title").toString();
			Constant.current_artist = Constant.trackList.get(Constant.CURRENT)
					.get("Artist").toString();
			Constant.current_url = Constant.trackList.get(Constant.CURRENT)
					.get("url").toString();
			Constant.current_duration = Constant.trackList
					.get(Constant.CURRENT).get("duratation").toString();
		}

	}
	
	private void updateArtImageView() {
		if (artIcon != null) {
			ArtWorkUtils.setContent(artIcon);
		}
	}
	
	private void updateArtImageBig(){
		if (artImage != null) {
			ArtWorkUtils.setContent(artImage);
			if (Constant.PLAYING_STATE != 0) {
				// 当服务开始播放音乐后，将专辑图片添加旋转动画效果
				Animation operatingAnim = AnimationUtils.loadAnimation(
						Constant.context, R.anim.tip);
				LinearInterpolator lin = new LinearInterpolator();
				operatingAnim.setInterpolator(lin);
				if (operatingAnim != null) {
					artImage.startAnimation(operatingAnim);
				}
			} else {
				artImage.clearAnimation();// 移除专辑imageView的动画
			}
		}
	}

	private void SetPlayBtnBg(int i) {
		if (btn_play != null) {
		switch (i) {
		case 0:		
			Log.d("xiamu", "改变暂停形状");
			btn_play.setImageDrawable(Constant.context.getResources().getDrawable(R.drawable.stop));
			break;
		case 1:
			Log.d("xiamu", "改变开始形状");
			btn_play.setImageDrawable(Constant.context.getResources().getDrawable(R.drawable.play));

		}		
	}
		
}	

	private void registerBoradcastReceiver() {
		// TODO Auto-generated method stub
		final IntentFilter mIntentFilter = new IntentFilter();
		mIntentFilter.addAction(Constant.ACTION_SONG_NORMAL_PLAY);
		mIntentFilter.addAction(Constant.ACTION_SONG_PAUSE);
		mIntentFilter.addAction(Constant.ACTION_SONG_RESUME);
		mIntentFilter.addAction(Constant.ACTION_SONG_NEXT);
		mIntentFilter.addAction(Constant.ACTION_SONG_PREVIOUS);
		mIntentFilter.addAction(Constant.ACTION_SONG_CHANGED);
		//注册广播
		
		Constant.context.registerReceiver(mBroadcastReceiver, mIntentFilter);
		
	}
	
	private void initBoradcastReceiver() {
		// TODO Auto-generated method stub
		BtnUIMonitor.mBroadcastReceiver = new BroadcastReceiver(){

			@Override
			public void onReceive(Context context, Intent intent) {
				//intent.setPriority(100);
				final String action = intent.getAction();
				if (action.equals(Constant.ACTION_SONG_CHANGED)) {
					updatetitle();
					updateArtImageView();
					updateArtImageBig();
				}
				if (action.equals(Constant.ACTION_SONG_NORMAL_PLAY) ) {
					SetPlayBtnBg(1);
					updatetitle();
					updateArtImageView();
					//updateArtImageView();
					updateArtImageBig();
				}
				if (action.equals(Constant.ACTION_SONG_RESUME)) {
					SetPlayBtnBg(1);
					updateArtImageBig();
				}
				if (action.equals(Constant.ACTION_SONG_PAUSE)) {
					SetPlayBtnBg(0);
					updateArtImageBig();
				}
				if (action.equals(Constant.ACTION_SONG_NEXT)) {
					updatetitle();
					SetPlayBtnBg(1);
					updateArtImageView();
					updateArtImageBig();
				}
				if (action.equals(Constant.ACTION_SONG_PREVIOUS)) {
					updatetitle();
					SetPlayBtnBg(1);
					updateArtImageView();
					updateArtImageBig();
				}
				
			}		
		};
		registerBoradcastReceiver();
	}

	
	

	
}
