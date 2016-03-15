package com.xiamu.supermusicplayer.activity;

import android.app.Fragment;

import com.xiamu.supermusicplayer.activity.base.BaseActivity;
import com.xiamu.supermusicplayer.fragment.MainFragment;


public class MainActivity extends BaseActivity {

	@Override
	protected Fragment createFragment() {
		// TODO Auto-generated method stub
		return new MainFragment();
	}
}
