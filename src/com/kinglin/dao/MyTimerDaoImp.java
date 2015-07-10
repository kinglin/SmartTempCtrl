package com.kinglin.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

	@SuppressWarnings("deprecation")
	public void updateTimer(MyTimer myTimer) {
		
		long newRingTime,endRingTime,startRingTime;
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		
		//没循环就删除此定时器
		if (myTimer.getCircle() == 0) {
			deleteTimer(myTimer);
		}else {
			newRingTime = myTimer.getRingtime() + myTimer.getCircle();
			Date curDate = new Date(newRingTime);
			String str_time = formatter.format(curDate);
			if (curDate.getHours()>=7 && curDate.getHours()<22) {
			}else {
				if (curDate.getHours()<24 && curDate.getHours()>=22 ) {
					//拿到晚十点和早七点的毫秒数
					try {
						c.setTime(formatter.parse(str_time.substring(0, 10)+" 22:00:00"));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					
					endRingTime = c.getTimeInMillis();
					startRingTime = endRingTime + 9*60*60*1000;
					
					//当响铃时间在晚十点到早七点，跳过响铃
					while (true) {
						newRingTime = newRingTime + myTimer.getCircle();
						if (newRingTime < startRingTime) {
							continue;
						}else {
							break;
						}
					}
				}else {
					try {
						String str = str_time.substring(0, 10)+" 07:00:00";
						c.setTime(formatter.parse(str));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					
					startRingTime = c.getTimeInMillis();
					
					//当响铃时间在晚十点到早七点，跳过响铃
					while (true) {
						newRingTime = newRingTime + myTimer.getCircle();
						if (newRingTime < startRingTime) {
							continue;
						}else {
							break;
						}
					}
				}
			}
			
			//执行更新操作
			db.execSQL("update timer set ringtime=? where timerId=?",
					new Object[]{newRingTime,myTimer.getId()});
		}
	}

	@Override
	public void deleteTimer(MyTimer myTimer) {
		db.execSQL("delete from timer where timerId=?", new Object[]{myTimer.getId()});
	}
}