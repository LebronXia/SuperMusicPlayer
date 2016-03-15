package com.xiamu.supermusicplayer.activity;

import android.app.Fragment;

import com.xiamu.supermusicplayer.activity.base.BaseActivity;
import com.xiamu.supermusicplayer.fragment.PlayingFragment;

public class PlayingActivity extends BaseActivity {

	@Override
	protected Fragment createFragment() {
		// TODO Auto-generated method stub
		return new PlayingFragment();
	}

}
