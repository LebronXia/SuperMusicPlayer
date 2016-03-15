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

	private Context mContext; 			//�����Ķ�������
	private List<SortModel> mp3Infos; 	//���Mp3Info�ļ���
	private SortModel sortModel;
	//private Mp3Bean mp3Info;			//MP3��������
	private int currentPosition = -1;	//��ǰλ��
	private static final String TAG = "MusicListAdapter";
	
	public MusicListAdapter(Context mContext, List<SortModel> mp3Infos) {
		super(mContext, mp3Infos);
		// TODO Auto-generated constructor stub
		this.mp3Infos = mp3Infos;
		this.mContext = mContext;
	}
	
	/**
	 * ��ListView���ݷ����仯ʱ,���ô˷���������ListView
	 * @param list
	 */
	public void updateListView(List<SortModel> list){
		this.mp3Infos = list;
		notifyDataSetChanged();
	}

	/**
	 * ����һ���ڲ���
	 * ������Ӧ�Ŀؼ�����
	 * @author zxbpc
	 *
	 */
	private class Holder{
		//���пؼ���������
		public TextView tvLetter;//��������
		public ImageView albumImage;	//ר��ͼƬ
		public TextView musicTitle;		//���ֱ���
		public TextView musicDuration;	//����ʱ��
		public TextView musicArtist;	//����������
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
		
		//����position��ȡ���������ĸ��Char asciiֵ
		int section = getSectionForPosition(position);
				
		//�����ǰλ�õ��ڸ÷�������ĸ��Char��λ�� ������Ϊ�ǵ�һ�γ���
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

	/* ���ݷ��������ĸ��Char asciiֵ��ȡ���һ�γ��ָ�����ĸ��λ��
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

	/* ͨ�������λ�ã�������ڷ������������
	 * ����ListView�ĵ�ǰλ�û�ȡ���������ĸ��Char asciiֵ
	 * @see android.widget.SectionIndexer#getSectionForPosition(int)
	 */
	@Override
	public int getSectionForPosition(int position) {
		return mp3Infos.get(position).getSortLetters().charAt(0);
	}
	
	

}
