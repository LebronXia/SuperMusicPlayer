package com.xiamu.supermusicplayer.listener;

import com.xiamu.supermusicplayer.boradcast.BoradCastManager;

import android.view.View;
import android.view.View.OnClickListener;

public class NextButtonListener implements OnClickListener{

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		BoradCastManager.sentSongNextBroadCast();
	}
}
