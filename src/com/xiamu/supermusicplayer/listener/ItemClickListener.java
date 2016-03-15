package com.xiamu.supermusicplayer.listener;

import java.util.HashMap;
import java.util.List;
import com.xiamu.supermusicplayer.boradcast.BoradCastManager;
import com.xiamu.supermusicplayer.constant.Constant;
import com.xiamu.supermusicplayer.service.PlayTrackService;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class ItemClickListener implements OnItemClickListener{

	private final List<HashMap<String,String>> trackList;
	
 	public ItemClickListener(List<HashMap<String,String>> trackList){
		this.trackList = trackList;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Constant.trackList = trackList;
		Constant.CURRENT = position;		
		BoradCastManager.sentNormalPlayBoradCast();
		
		
	}

}
