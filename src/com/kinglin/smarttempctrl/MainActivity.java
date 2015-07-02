package com.kinglin.smarttempctrl;

import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;

@SuppressLint({ "NewApi", "HandlerLeak" })
@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class MainActivity extends ActionBarActivity {

	private Handler handler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().hide();
		setContentView(R.layout.activity_main);
		
//		RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relayout_main);
//		relativeLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.main));
		
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
	}
	TimerTask tt = new TimerTask() {  
        @Override  
        public void run() {  
            handler.sendMessageDelayed(handler.obtainMessage(0), 1000);  
        }  
    };  
}
