package com.xiamu.supermusicplayer.listview;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.xiamu.supermusicplayer.R;
import com.xiamu.supermusicplayer.comment.Mp3Bean;
import com.xiamu.supermusicplayer.comment.SortModel;

public class MusicListAdapter extends AdapterBase implements SectionIndexer{

	private Context mContext; 			//上下文对象引用
	private List<SortModel> mp3Infos; 	//存放Mp3Info的集合
	private SortModel sortModel;
	//private Mp3Bean mp3Info;			//MP3对象引用
	private int currentPosition = -1;	//当前位置
	private static final String TAG = "MusicListAdapter";
	
	public MusicListAdapter(Context mContext, List<SortModel> mp3Infos) {
		super(mContext, mp3Infos);
		// TODO Auto-generated constructor stub
		this.mp3Infos = mp3Infos;
		this.mContext = mContext;
	}
	
	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 * @param list
	 */
	public void updateListView(List<SortModel> list){
		this.mp3Infos = list;
		notifyDataSetChanged();
	}

	/**
	 * 定义一个内部类
	 * 声明相应的控件引用
	 * @author zxbpc
	 *
	 */
	private class Holder{
		//所有控件对象引用
		public TextView tvLetter;//索引标题
		public ImageView albumImage;	//专辑图片
		public TextView musicTitle;		//音乐标题
		public TextView musicDuration;	//音乐时长
		public TextView musicArtist;	//音乐艺术家
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder viewHolder = null;
		if (convertView == null) {
			viewHolder = new Holder();
			convertView = getLayoutInflater().inflate(R.layout.local_item, parent, false);
			viewHolder.musicTitle = (TextView) convertView.findViewById(R.id.title);
			viewHolder.musicDuration = (TextView) convertView.findViewById(R.id.duration);
			viewHolder.musicArtist = (TextView) convertView.findViewById(R.id.artist);
			viewHolder.tvLetter = (TextView) convertView.findViewById(R.id.catalog);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (Holder) convertView.getTag();
		}
		
		sortModel = mp3Infos.get(position);
		
		//根据position获取分类的首字母的Char ascii值
		int section = getSectionForPosition(position);
				
		//如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
		if(position == getPositionForSection(section)){
			viewHolder.tvLetter.setVisibility(View.VISIBLE);
			viewHolder.tvLetter.setText(sortModel.getSortLetters());
		}else{
			viewHolder.tvLetter.setVisibility(View.GONE);
		}			
		//viewHolder.tvTitle.setText(this.list.get(position).getName());
		
		viewHolder.musicArtist.setText(sortModel.getArt());
		viewHolder.musicTitle.setText(sortModel.getTitle());
		viewHolder.musicDuration.setText(sortModel.getDuration());
		
		return convertView;
	}

	@Override
	public Object[] getSections() {
		// TODO Auto-generated method stub
		return null;
	}

	/* 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 * @see android.widget.SectionIndexer#getPositionForSection(int)
	 */
	@Override
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = mp3Infos.get(i).getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}		
		return -1;
	}

	/* 通过该项的位置，获得所在分类组的索引号
	 * 根据ListView的当前位置获取分类的首字母的Char ascii值
	 * @see android.widget.SectionIndexer#getSectionForPosition(int)
	 */
	@Override
	public int getSectionForPosition(int position) {
		return mp3Infos.get(position).getSortLetters().charAt(0);
	}
	
	

}
