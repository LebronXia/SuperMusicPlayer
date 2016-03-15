package com.xiamu.supermusicplayer.listener;

import com.xiamu.supermusicplayer.boradcast.BoradCastManager;

import android.view.View;
import android.view.View.OnClickListener;

public class PreviousButtonListener implements OnClickListener{

	@Override
	public void onClick(View v) {
		BoradCastManager.sentSongPreviosBoradCast();		
	}

}
