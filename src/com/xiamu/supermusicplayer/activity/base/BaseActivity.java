package com.xiamu.supermusicplayer.activity.base;

import com.xiamu.supermusicplayer.R;
import com.xiamu.supermusicplayer.BtnUIMonitor.BtnUIMonitor;
import com.xiamu.supermusicplayer.activity.PlayingActivity;
import com.xiamu.supermusicplayer.constant.Constant;
import com.xiamu.supermusicplayer.utils.ArtWorkUtils;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

public abstract class BaseActivity extends Activity {
	NotificationManager mNotificationManager;
	BroadcastReceiver mBroadcastReceiver;
	//新增一个名为createFragmenr的抽象方法，我们可使用它实例化新的fragment
	protected abstract Fragment createFragment(); 
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fragmentactivity);
		initBoradcastReceiver();
		FragmentManager fm = getFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
		
		if (fragment == null) {
			fragment = createFragment();
			fm.beginTransaction()
			.add(R.id.fragmentContainer, fragment)
			.commit();
		}
	}
	
	protected void showMsg(String msg){
    	Toast.makeText(this, msg, Toast.LENGTH_SHORT);
    }
	
    protected void openActivity(Class<?> pClass)
	{
		Intent intent = new Intent();
		intent.setClass(this, pClass);
		startActivity(intent);
	}
    
    private void registerBoradcastReceiver() {
		// 先初始化 后注册！！
		final IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction(Constant.ACTION_SONG_NORMAL_PLAY);
		myIntentFilter.addAction(Constant.ACTION_SONG_NEXT);
		myIntentFilter.addAction(Constant.ACTION_SONG_PAUSE);
		myIntentFilter.addAction(Constant.ACTION_SONG_PREVIOUS);
		myIntentFilter.addAction(Constant.ACTION_SONG_RESUME);

		// 注册广播
		registerReceiver(mBroadcastReceiver, myIntentFilter);
	}

	private void initBoradcastReceiver() {

		mBroadcastReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				final String action = intent.getAction();

				if (action.equals(Constant.ACTION_SONG_NEXT)
						|| action.equals(Constant.ACTION_SONG_CHANGED)
						|| action.equals(Constant.ACTION_SONG_NORMAL_PLAY)
						|| action.equals(Constant.ACTION_SONG_PAUSE)
						|| action.equals(Constant.ACTION_SONG_PREVIOUS)
						|| action.equals(Constant.ACTION_SONG_RESUME)) {
					BtnUIMonitor.updateCurrent();
					updateNoti(Constant.current_title, Constant.current_artist);
					//updateNoti(Constant.current_title, Constant.current_artist);
					System.out
							.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>APP收到广播");
				}
			}

		};
		registerBoradcastReceiver();
	}
	
	@SuppressLint("NewApi") public void updateNoti(String currentTitle, String artist) {

		final Intent resultIntent = new Intent(this, PlayingActivity.class);
		final TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		// Adds the back stack
		stackBuilder.addParentStack(PlayingActivity.class);
		// Adds the Intent to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

		mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		// Sets an ID for the notification, so it can be updated
		final int notifyID = 1;
		final NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(
				this).setContentTitle(currentTitle).setContentText(artist)
				.setSmallIcon(R.drawable.music).setLargeIcon(getIcon())
				.setAutoCancel(true).setOngoing(true);
		// Start of a loop that processes data and then notifies the user...
		// Because the ID remains unchanged, the existing notification is
		// updated.

		mNotificationManager.notify(notifyID, mNotifyBuilder.build());
	}

	// 获取专辑图片，交给通知栏
	private Bitmap getIcon() {
		Bitmap icon = null;
		icon = ArtWorkUtils.getContent();
		return icon;
	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
