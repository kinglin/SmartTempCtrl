package com.kinglin.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kinglin.model.Temperature;

public class TempDaoImp implements TempDao {

	SQLiteDatabase db;

	public TempDaoImp(Context context) {
		DBHelper helper=new DBHelper(context, "user.db", null, 1);
		db=helper.getWritableDatabase();
	}

	@Override
	public void addTemp(Temperature temperature) {
		Cursor cursor=db.rawQuery("select * from temperature",null);
		if (cursor.getCount() == 0) {
			db.execSQL("insert into temperature(time,temp) values(?,?)",
					new Object[]{temperature.getTime(),temperature.getTemp()});
		}else {
			Temperature lasTemperature = getLastTemperature();
			if (lasTemperature.getTime() != temperature.getTime()) {
				db.execSQL("insert into temperature(time,temp) values(?,?)",
						new Object[]{temperature.getTime(),temperature.getTemp()});
			}
		}
	}

	@Override
	public List<Temperature> getAllTemperatures() {
		List<Temperature> temperatures=new ArrayList<Temperature>();
		Cursor cursor=db.rawQuery("select * from temperature",null);
		while (cursor.moveToNext()) {
			String time=cursor.getString(cursor.getColumnIndex("time"));
			int temp=cursor.getInt(cursor.getColumnIndex("temp"));
			
			temperatures.add(new Temperature(time,temp));
		}
		cursor.close();
		return temperatures;
	}

	@Override
	public Temperature getLastTemperature() {
		Temperature temperature = null;
		Cursor cursor=db.rawQuery("select * from temperature",null);
		if (cursor.getCount() == 1) {
			cursor.moveToFirst();
			String time=cursor.getString(cursor.getColumnIndex("time"));
			int temp=cursor.getInt(cursor.getColumnIndex("temp"));
			temperature = new Temperature(time, temp);
		}else if (cursor.getCount() > 1) {
			while (cursor.moveToNext()) {
				if (cursor.isLast()) {
					String time=cursor.getString(cursor.getColumnIndex("time"));
					int temp=cursor.getInt(cursor.getColumnIndex("temp"));
					temperature = new Temperature(time, temp);
					break;
				}
			}
		}
		cursor.close();
		return temperature;
	}

	@Override
	public Temperature getSecLastTemperature() {
		Temperature temperature = null;
		Cursor cursor=db.rawQuery("select * from temperature",null);
		if (cursor.getCount() > 1) {
			cursor.moveToFirst();
			while (cursor.moveToNext()) {
				if (cursor.isLast()) {
					cursor.moveToPrevious();
					String time=cursor.getString(cursor.getColumnIndex("time"));
					int temp=cursor.getInt(cursor.getColumnIndex("temp"));
					temperature = new Temperature(time, temp);
					break;
				}
			}
		}
		cursor.close();
		return temperature;
	}

	@Override
	public List<Temperature> getRecentTemperatures() {
		return null;
	}

	
	
}
