package com.xiamu.supermusicplayer.listener;

import com.xiamu.supermusicplayer.boradcast.BoradCastManager;
import com.xiamu.supermusicplayer.constant.Constant;

import android.view.View;
import android.view.View.OnClickListener;

public class PlayButtonListener implements OnClickListener{

	@Override
	public void onClick(View v) {
		switch (getPlayState()) {
		case 0:
			BoradCastManager.sentSongResumeBoradCast();
			break;
		case 1:
			BoradCastManager.sentSongPauseBoradCast();
			break;

		default:
			break;
		}
		
	}
	
	private int getPlayState(){
		return Constant.PLAYING_STATE;
	}

}
