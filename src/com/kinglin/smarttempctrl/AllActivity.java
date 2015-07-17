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
        
		//�ڳ�ʼ��ʱ��չʾ��ʱ���б�
		FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.relayout_all, timerFragment);
        fragmentTransaction.commit();
        
        //��Ӷ�ʱ������Ӧ
        timerFragment.setOnAddClickListener(new OnAddClickListener() {
			@Override
			public void GotoAddFragment() {
				FragmentManager fm = getSupportFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				ft.replace(R.id.relayout_all, addTimerFragment);
				ft.commit();
			}
		});
        
        //��ʱ����������Ӧ
        addTimerFragment.setOnAddConfirmClickListener(new OnAddConfirmClickListener() {
			@Override
			public void ReturnToTimerList() {
				FragmentManager fm = getSupportFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				ft.replace(R.id.relayout_all, timerFragment);
				ft.commit();
			}
		});
        
        //��ʱȡ�������Ӧ
        addTimerFragment.setOnAddCancleClickListener(new OnAddCancleClickListener() {
			@Override
			public void ReturnToTimerList() {
				FragmentManager fm = getSupportFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				ft.replace(R.id.relayout_all, timerFragment);
				ft.commit();
			}
		});
        
        //�·���ʱ����ť�����Ӧ
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