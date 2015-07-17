package com.kinglin.tools;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Media;
import android.util.Log;

public class MusicLoader {  

	private static final String TAG = "com.kinglin.tools.MusicLoader";  

	private List<MusicInfo> musicList = new ArrayList<MusicInfo>();  

	private ContentResolver contentResolver = null;
	
	//Uri，指向external的database  
	private Uri contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;   
	
	//projection：选择的列; where：过滤条件; sortOrder：排序。  
	private String[] projection = {  
			Media._ID,  
			Media.DISPLAY_NAME,  
			Media.DATA,  
			Media.ALBUM,  
			Media.ARTIST,  
			Media.DURATION,           
			Media.SIZE  
	};  
	private String where =  "mime_type = 'audio/mpeg' and is_music > 0 " ;  
	private String sortOrder = MediaStore.Audio.Media.DEFAULT_SORT_ORDER;  

	public MusicLoader(ContentResolver contentResolver){ 
		this.contentResolver = contentResolver;
	}  

	public List<MusicInfo> getMusicList(){  
		Cursor cursor = contentResolver.query(contentUri, projection, where, null, sortOrder);
		if(cursor == null){  
			Log.v(TAG,"Line(37  )   Music Loader cursor == null.");  
		}else if(!cursor.moveToFirst()){  
			Log.v(TAG,"Line(39  )   Music Loader cursor.moveToFirst() returns false.");  
		}else{            
			int displayNameCol = cursor.getColumnIndex(Media.DISPLAY_NAME);  
			int albumCol = cursor.getColumnIndex(Media.ALBUM);  
			int idCol = cursor.getColumnIndex(Media._ID);  
			int durationCol = cursor.getColumnIndex(Media.DURATION);  
			int sizeCol = cursor.getColumnIndex(Media.SIZE);  
			int artistCol = cursor.getColumnIndex(Media.ARTIST);  
			int urlCol = cursor.getColumnIndex(Media.DATA);           
			do{  
				String title = cursor.getString(displayNameCol);  
				String album = cursor.getString(albumCol);  
				long id = cursor.getLong(idCol);                  
				int duration = cursor.getInt(durationCol);  
				long size = cursor.getLong(sizeCol);  
				String artist = cursor.getString(artistCol);  
				String url = cursor.getString(urlCol);  

				MusicInfo musicInfo = new MusicInfo(id, title);  
				musicInfo.setAlbum(album);  
				musicInfo.setDuration(duration);  
				musicInfo.setSize(size);  
				musicInfo.setArtist(artist);  
				musicInfo.setUrl(url);  
				musicList.add(musicInfo);  

			}while(cursor.moveToNext());  
		}  
		return musicList;  
	}  

	public Uri getMusicUriById(long id){  
		Uri uri = ContentUris.withAppendedId(contentUri, id);  
		return uri;  
	}     
}  