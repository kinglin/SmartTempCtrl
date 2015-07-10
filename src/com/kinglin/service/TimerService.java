package com.kinglin.service;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.widget.Toast;

import com.kinglin.dao.MyTimerDaoImp;
import com.kinglin.model.MyTimer;

public class TimerService extends Service {

	public TimerService() {
	}

	@Override
	public void onCreate() {
		super.onCreate();

		//��ʱ����ÿ�����ӽ���һ������ͬ��
		Timer timer = new Timer();
		timer.schedule(new CheckRingTime(), 0, 5*1000);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	//����Ƿ������
	public class CheckRingTime extends TimerTask{

		List<MyTimer> myTimers;
		MyTimerDaoImp mtdi;
		
		public CheckRingTime(){
			mtdi = new MyTimerDaoImp(getApplicationContext());
		}
		
		@Override
		public void run() {
			myTimers = mtdi.getAllMyTimers();
			if (myTimers.size() != 0) {
				for(MyTimer myTimer : myTimers){
					
					//�����������ʱ��					
					if (myTimer.getRingtime() <= System.currentTimeMillis()) {
						//����
						RingTheReminder(myTimer);
						
						//�����ݿ��и��¸ö�ʱ����Ϣ
						mtdi.updateTimer(myTimer);
					}
				}
			}
		}
	}

	//��ʾ����
	public void RingTheReminder(MyTimer myTimer) {
		new ToastMessageTask().execute(""+myTimer.getContent()+":"+myTimer.getRemark());
	}
	
	//�����࣬�κεط�����toast
	private class ToastMessageTask extends AsyncTask<String, String, String> {
		String toastMessage;

		protected String doInBackground(String... params) {
			toastMessage = params[0];
			return toastMessage;
		}

		@SuppressWarnings("unused")
		protected void OnProgressUpdate(String... values) { 
			super.onProgressUpdate(values);
		}

		protected void onPostExecute(String result){
			Toast toast = Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT);
			toast.show();
		}
	}
}
