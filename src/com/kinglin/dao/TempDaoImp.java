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
		db.execSQL("insert into temp(tempId,temp) values(?,?)",
				new Object[]{temperature.getTempId(),temperature.getTemp()});
	}

	@Override
	public List<Temperature> getAllTemperatures() {
		List<Temperature> temperatures=new ArrayList<Temperature>();
		Cursor cursor=db.rawQuery("select * from temp",null);
		while (cursor.moveToNext()) {
			long tempId=cursor.getLong(cursor.getColumnIndex("tempId"));
			float temp=cursor.getFloat(cursor.getColumnIndex("temp"));
			
			temperatures.add(new Temperature(tempId,temp));
		}
		cursor.close();
		return temperatures;
	}

	@Override
	public Temperature getLastTemperature() {
		return null;
	}

	@Override
	public Temperature getSecLastTemperature() {
		return null;
	}

	@Override
	public List<Temperature> getRecentTemperatures() {
		return null;
	}

	
	
}
