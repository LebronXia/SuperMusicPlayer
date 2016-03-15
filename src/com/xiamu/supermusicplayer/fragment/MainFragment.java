package com.xiamu.supermusicplayer.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.xiamu.supermusicplayer.R;
import com.xiamu.supermusicplayer.BtnUIMonitor.BtnUIMonitor;
import com.xiamu.supermusicplayer.activity.PlayingActivity;
import com.xiamu.supermusicplayer.comment.Mp3Bean;
import com.xiamu.supermusicplayer.comment.SortModel;
import com.xiamu.supermusicplayer.constant.Constant;
import com.xiamu.supermusicplayer.listener.ItemClickListener;
import com.xiamu.supermusicplayer.listener.NextButtonListener;
import com.xiamu.supermusicplayer.listener.PlayButtonListener;
import com.xiamu.supermusicplayer.listener.PreviousButtonListener;
import com.xiamu.supermusicplayer.listview.MusicListAdapter;
import com.xiamu.supermusicplayer.service.PlayTrackService;
import com.xiamu.supermusicplayer.utils.CharacterParser;
import com.xiamu.supermusicplayer.utils.MusicUtils;
import com.xiamu.supermusicplayer.utils.PinyinComparator;
import com.xiamu.supermusicplayer.view.SideBar;
import com.xiamu.supermusicplayer.view.SideBar.OnTouchingLetterChangedListener;

import android.app.Activity;
import android.app.Fragment;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainFragment extends Fragment {
	public View mRootView;
	private ImageButton btn_play, btn_next, btn_previous;
	private ImageView artImage;
	private ListView listView;
	private TextView musicTitle;
	private SideBar sideBar;
	private TextView dialog;
	private MusicListAdapter adapter;
	private Context mContext;
	private List<Mp3Bean> mp3Infos;
	private List<HashMap<String, String>> trackList;	
	/**
	 * 汉字转换成拼音的类
	 */
	private CharacterParser characterParser;
	private List<SortModel> SourceDateList;
	
	/**
	 * 根据拼音来排列ListView里面的数据类
	 */
	private PinyinComparator pinyinComparator;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//实例化汉字转拼音类
		characterParser = CharacterParser.getInstance();
				
		pinyinComparator = new PinyinComparator();
		mp3Infos = MusicUtils.getMp3Infos(getActivity());
		SourceDateList = filledData(mp3Infos);
		
		// 根据a-z进行排序源数据
		Collections.sort(SourceDateList, pinyinComparator);
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		mContext = activity.getApplicationContext();
		Constant.context = mContext;
		Intent mIntent = new Intent(mContext,PlayTrackService.class);
		mContext.startService(mIntent);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mRootView = inflater.inflate(R.layout.fragment_main, container, false);
		initButton();	
		initListview();			
		initListItemClickListener();
		initItemLongPressedControler();
		return mRootView;
	}

	//在这里初始化listview
	private void initListview() {
		// TODO Auto-generated method stub
		listView = (ListView) mRootView.findViewById(R.id.listview);
		artImage = (ImageView) mRootView.findViewById(R.id.artIcon);
		trackList = MusicUtils.getMusicMaps(SourceDateList);
		sideBar = (SideBar) mRootView.findViewById(R.id.sidrbar);
		dialog = (TextView) mRootView.findViewById(R.id.dialog);
		sideBar.setTextView(dialog);
		
		//设置右侧触摸监听
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {
			
			@Override
			public void onTouchingLetterChanged(String s) {
				//该字母首次出现的位置
				int position = adapter.getPositionForSection(s.charAt(0));
				if(position != -1){
					//ListView还有一个方法叫setSelection()，传入一个index整型数值，就可以让ListView定位到指定Item的位置。
					listView.setSelection(position);
				}
				
			}
		});
		
		
		adapter = new MusicListAdapter(getActivity(), SourceDateList);
		//listView.setOnItemClickListener(new MusicListItemClickListener());
		listView.setAdapter(adapter);
		//设置专辑头像点击事件
		artImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), PlayingActivity.class);
				startActivity(intent);
			}
		});
	}
	
	/**
	 * 为ListView填充数据
	 * @param date
	 * @return
	 */
	private List<SortModel> filledData(List<Mp3Bean> mp3Infos){
		List<SortModel> mSortList = new ArrayList<SortModel>();
		
		for(int i=0; i<mp3Infos.size(); i++){
			SortModel sortModel = new SortModel();
			sortModel.setArt(mp3Infos.get(i).getArt());
			sortModel.setTitle(mp3Infos.get(i).getTitle());
			sortModel.setDuration(mp3Infos.get(i).getDuration());
			sortModel.setUrl(mp3Infos.get(i).getUrl());
			//汉字转换成拼音
			String pinyin = characterParser.getSelling(mp3Infos.get(i).getTitle());
			String sortString = pinyin.substring(0, 1).toUpperCase();
			
			// 正则表达式，判断首字母是否是英文字母
			if(sortString.matches("[A-Z]")){
				sortModel.setSortLetters(sortString.toUpperCase());
			}else{
				sortModel.setSortLetters("#");
			}
			
			mSortList.add(sortModel);
		}
		return mSortList;
		
	}

	private void initButton() {
		// TODO Auto-generated method stub
		btn_play = (ImageButton) mRootView.findViewById(R.id.playBtn);
		btn_next = (ImageButton) mRootView.findViewById(R.id.nextBtn);
		btn_previous = (ImageButton) mRootView.findViewById(R.id.previoueBtn);
		artImage = (ImageView) mRootView.findViewById(R.id.artIcon);
		musicTitle = (TextView) mRootView.findViewById(R.id.musicTitle);
		btn_play.setOnClickListener(new PlayButtonListener());
		btn_next.setOnClickListener(new NextButtonListener());
		btn_previous.setOnClickListener(new PreviousButtonListener());
		//更新UI操作
		new BtnUIMonitor(btn_play, btn_previous, btn_next, null, null, musicTitle, artImage,null);
	}

	//初始化长按事件
	private void initItemLongPressedControler() {
		// TODO Auto-generated method stub
		
	}
	//初始化listview上item的点击
	private void initListItemClickListener() {
		// TODO Auto-generated method stub
		listView.setOnItemClickListener(new ItemClickListener(trackList));
		
	}

}
