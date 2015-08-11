package com.kinglin.dao;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kinglin.model.MyTimer;

@SuppressLint("SimpleDateFormat")
public class MyTimerDaoImp implements MyTimerDao {
	
	SQLiteDatabase db;
	
	public MyTimerDaoImp(Context context) {
		DBHelper helper=new DBHelper(context, "user.db", null, 1);
	    db=helper.getWritableDatabase();
	}

	@Override
	public void addTimer(MyTimer mytimer) {
		if (!mytimer.isTimerAvaliable()) {
			updateTimer(mytimer);
		}
		db.execSQL("insert into timer(timerId,ringtime,circle,timeron,remark,content,cleanstart,cleanend) values(?,?,?,?,?,?,?,?)",
				new Object[]{mytimer.getId(),mytimer.getRingtime(),mytimer.getCircle(),mytimer.getTimeron(),mytimer.getRemark(),mytimer.getContent(),mytimer.getCleanstart(),mytimer.getCleanend()});
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
			long cleanstart=cursor.getLong(cursor.getColumnIndex("cleanstart"));
			long cleanend=cursor.getLong(cursor.getColumnIndex("cleanend"));
			
			myTimers.add(new MyTimer(timerId,ringtime,circle,timeron,remark,content,cleanstart,cleanend));
		}
		cursor.close();
		return myTimers;
	}

	public void updateTimer(MyTimer myTimer) {
		
		if (myTimer.getCircle() == 0) {
			
			//循环为0则代表闹钟要被删除
			deleteTimer(myTimer);
		}else if ((myTimer.getRingtime() + myTimer.getCircle()) < myTimer.getCleanstart()) {
			
			//表示下次闹铃时间在免打扰时间之前
			myTimer.setRingtime(myTimer.getRingtime() + myTimer.getCircle());
			db.execSQL("update timer set ringtime=? where timerId=?",
					new Object[]{myTimer.getRingtime(),myTimer.getId()});
		}else{
			while (true) {
				myTimer.setRingtime(myTimer.getRingtime() + myTimer.getCircle());
				if (myTimer.getRingtime() < myTimer.getCleanend()) {
					continue;
				}
				break;
			}
			
			myTimer.setCleanstart(myTimer.getCleanstart() + 24*60*60*1000);
			myTimer.setCleanend(myTimer.getCleanend() + 24*60*60*1000);
			
			//执行更新操作
			db.execSQL("update timer set ringtime=?,cleanstart=?,cleanend=? where timerId=?",
					new Object[]{myTimer.getRingtime(),myTimer.getCleanstart(),myTimer.getCleanend(),myTimer.getId()});
		}
	}

	@Override
	public void deleteTimer(MyTimer myTimer) {
		db.execSQL("delete from timer where timerId=?", new Object[]{myTimer.getId()});
	}
	
}