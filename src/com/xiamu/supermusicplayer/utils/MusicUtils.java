package com.xiamu.supermusicplayer.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.xiamu.supermusicplayer.comment.Mp3Bean;
import com.xiamu.supermusicplayer.comment.SortModel;

public class MusicUtils {

	public static List<HashMap<String, Object>> mp3list;
	
	/**
	 * 格式化时间，将其变成00:00的形式
	 */
	public static String formatTime(long time) {
		int secondSum = (int) (time / 1000);
		int minute = secondSum / 60;
		int second = secondSum % 60;

		String result = "";
		if (minute < 10)
			result = "0";
		result = result + minute + ":";
		if (second < 10)
			result = result + "0";
		result = result + second;

		return result;
	}
	
	public static List<Mp3Bean> getMp3Infos(Context mContext){
		Cursor cursor = mContext.getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, 
				MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
		List<Mp3Bean> mp3Infos = new ArrayList<Mp3Bean>();
		if (cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				Mp3Bean mp3Info = new Mp3Bean();
				long id = cursor.getLong(
						cursor.getColumnIndex(MediaStore.Audio.Media._ID));
				String title = cursor.getString(cursor
						.getColumnIndex(MediaStore.Audio.Media.TITLE));
				String artist = cursor.getString(cursor
						.getColumnIndex(MediaStore.Audio.Media.ARTIST)); // 艺术家
				long duration = cursor.getLong(cursor
						.getColumnIndex(MediaStore.Audio.Media.DURATION));
				long size = cursor.getLong(cursor
						.getColumnIndex(MediaStore.Audio.Media.SIZE));
				String url = cursor.getString(cursor
						.getColumnIndex(MediaStore.Audio.Media.DATA));
				int isMusic = cursor.getInt(cursor
						.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));
				if (isMusic != 0) {
					// 只把音乐添加到集合
					mp3Info.setId(id);
					mp3Info.setTitle(title);
					mp3Info.setArt(artist);
					mp3Info.setDuration(MusicUtils.formatTime(duration));
					mp3Info.setSize(size);
					mp3Info.setUrl(url);
					mp3Infos.add(mp3Info);
				}
				cursor.moveToNext();				
			}
		}
		return mp3Infos;
		
	}
	
	/**
	 * 往List集合中添加Map对象数据，每一个Map对象存放一首音乐的所有属性
	 * @param mp3Infos
	 */
	public static List<HashMap<String, String>> getMusicMaps(
			List<SortModel> mp3Infos){
		List<HashMap<String, String>> mp3lList = new ArrayList<HashMap<String,String>>();
		
		for (Iterator<SortModel> iterator = mp3Infos.iterator(); iterator.hasNext();) {
			SortModel mp3Info = iterator.next();
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("title", mp3Info.getTitle());
			map.put("Artist", mp3Info.getArt());
			map.put("duratation", String.valueOf(mp3Info.getDuration()));
			//map.put("Size", String.valueOf(mp3Info.getSize()));
			map.put("url", mp3Info.getUrl());
			mp3lList.add(map);
		}
		return mp3lList;		
	}	
	
	

}
