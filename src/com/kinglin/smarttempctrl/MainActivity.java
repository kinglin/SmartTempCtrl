package com.kinglin.smarttempctrl;

import java.util.List;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;

@SuppressLint({ "NewApi", "HandlerLeak", "ShowToast" })
@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class MainActivity extends ActionBarActivity {

	private Handler handler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().hide();
		setContentView(R.layout.activity_main);
		
		HandlerThread myThread = new HandlerThread("myHandlerThread");  
        myThread.start();  
        handler = new Handler(){  
            @Override  
            public void handleMessage(Message msg) {  
                if(msg.what == 0){ 
                    Intent intent = new Intent(MainActivity.this, AllActivity.class);  
                    startActivity(intent);  
                    MainActivity.this.finish();
                }  
            } 
        };  
        tt.run();
        
        //开启TimerService
//        if (isServiceRunning(getApplicationContext(), "com.kinglin.service.TimerService")) {
//			Toast.makeText(getApplicationContext(), "TimerService is running.", 500).show();
//		}else {
//			Intent intent = new Intent(getApplicationContext(),TimerService.class);
//			startService(intent);
//			if (isServiceRunning(getApplicationContext(), "com.kinglin.service.TimerService")) {
//				Toast.makeText(getApplicationContext(), "TimerService has been started", Toast.LENGTH_SHORT).show();
//			}else {
//				Toast.makeText(getApplicationContext(), "TimerService start failed", Toast.LENGTH_SHORT).show();
//			}
//			
//		}
	}
	TimerTask tt = new TimerTask() {  
        @Override  
        public void run() {  
            handler.sendMessageDelayed(handler.obtainMessage(0), 1000);  
        }  
    };  
    
    //工具函数，判断service是否开启
    public static boolean isServiceRunning(Context mContext,String className) {
    	boolean isRunning = false;
    	ActivityManager activityManager = (ActivityManager)
    			mContext.getSystemService(Context.ACTIVITY_SERVICE); 
    	List<ActivityManager.RunningServiceInfo> serviceList 
    	= activityManager.getRunningServices(200);
    	if (!(serviceList.size()>0)) {
    		return false;
    	}
    	for (int i=0; i<serviceList.size(); i++) {
    		if (serviceList.get(i).service.getClassName().equals(className) == true) {
    			isRunning = true;
    			break;
    		}
    	}
    	return isRunning;
    }
}
