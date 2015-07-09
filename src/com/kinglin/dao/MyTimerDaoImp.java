package com.kinglin.dao;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kinglin.model.MyTimer;

public class MyTimerDaoImp implements MyTimerDao {
	
	SQLiteDatabase db;
	
	public MyTimerDaoImp(Activity activity) {
		DBHelper helper=new DBHelper(activity, "user.db", null, 1);
	    db=helper.getWritableDatabase();
	}

	@Override
	public void addTimer(MyTimer mytimer) {
		db.execSQL("insert into timer(timerId,ringtime,circle,timeron,remark,content) values(?,?,?,?,?,?)",
				new Object[]{mytimer.getId(),mytimer.getRingtime(),mytimer.getCircle(),mytimer.getTimeron(),mytimer.getRemark(),mytimer.getContent()});
	}

	public List<MyTimer> getAllMyTimers() {
		List<MyTimer> myTimers=new ArrayList<MyTimer>();
		Cursor cursor=db.rawQuery("select * from timer",null);
		while (cursor.moveToNext()) {
			long timerId=cursor.getLong(cursor.getColumnIndex("timerId"));
			long ringtime=cursor.getLong(cursor.getColumnIndex("ringtime"));
			long circle=cursor.getLong(cursor.getColumnIndex("circle"));
			int timeron=cursor.getInt(cursor.getColumnIndex("timeron"));
			String remark=cursor.getString(cursor.getColumnIndex("remark"));
			int content=cursor.getInt(cursor.getColumnIndex("content"));
			
			myTimers.add(new MyTimer(timerId,ringtime,circle,timeron,remark,content));
		}
		cursor.close();
		return myTimers;
	}
}