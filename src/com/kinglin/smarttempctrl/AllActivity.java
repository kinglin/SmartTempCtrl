package com.kinglin.smarttempctrl;


import com.kinglin.smarttempctrl.AddTimerFragment.OnAddCancleClickListener;
import com.kinglin.smarttempctrl.AddTimerFragment.OnAddConfirmClickListener;
import com.kinglin.smarttempctrl.TimerFragment.OnAddClickListener;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageButton;

public class AllActivity extends FragmentActivity {

	TimerFragment timerFragment;
	AddTimerFragment addTimerFragment;
	
	ImageButton ibtn_members,ibtn_setting,ibtn_timer,ibtn_temp,ibtn_tools;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all);

		initWidget();
		
        addTimerFragment = new AddTimerFragment();
		timerFragment = new TimerFragment();
        
		//在初始的时候展示定时器列表
		FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.relayout_all, timerFragment);
        fragmentTransaction.commit();
        
        //添加定时器的响应
        timerFragment.setOnAddClickListener(new OnAddClickListener() {
			@Override
			public void GotoAddFragment() {
				FragmentManager fm = getSupportFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				ft.replace(R.id.relayout_all, addTimerFragment);
				ft.commit();
			}
		});
        
        //定时器添加完毕响应
        addTimerFragment.setOnAddConfirmClickListener(new OnAddConfirmClickListener() {
			@Override
			public void ReturnToTimerList() {
				FragmentManager fm = getSupportFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				ft.replace(R.id.relayout_all, timerFragment);
				ft.commit();
			}
		});
        
        //定时取消添加响应
        addTimerFragment.setOnAddCancleClickListener(new OnAddCancleClickListener() {
			@Override
			public void ReturnToTimerList() {
				FragmentManager fm = getSupportFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				ft.replace(R.id.relayout_all, timerFragment);
				ft.commit();
			}
		});
        
        //下方定时器按钮点击响应
        ibtn_timer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				FragmentManager fm = getSupportFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				ft.replace(R.id.relayout_all, timerFragment);
				ft.commit();
			}
		});
	}
	
	void initWidget(){
		ibtn_members = (ImageButton)findViewById(R.id.ibtn_members);
		ibtn_setting = (ImageButton)findViewById(R.id.ibtn_setting);
		ibtn_timer = (ImageButton)findViewById(R.id.ibtn_timer);
		ibtn_temp = (ImageButton)findViewById(R.id.ibtn_temp);
		ibtn_tools = (ImageButton)findViewById(R.id.ibtn_tools);
		
		ibtn_members.setBackgroundResource(R.drawable.tag_cloud);
		ibtn_setting.setBackgroundResource(R.drawable.tag_setup);
		ibtn_timer.setBackgroundResource(R.drawable.button_timer_01);
		ibtn_temp.setBackgroundResource(R.drawable.button_main_01);
		ibtn_tools.setBackgroundResource(R.drawable.button_gadget_01);
	}
}