package com.kinglin.smarttempctrl;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageButton;

import com.kinglin.smarttempctrl.AddTimerFragment.OnAddCancleClickListener;
import com.kinglin.smarttempctrl.AddTimerFragment.OnAddConfirmClickListener;
import com.kinglin.smarttempctrl.TimerFragment.OnAddClickListener;

public class AllActivity extends FragmentActivity {

	int CUR_FRAGMENT = 0;
	public static final int TIMER_FRAGMENT = 1;
	public static final int ADD_TIMER_FRAGMENT = 2;
	public static final int SHOW_TEMP_FRAGMENT = 3;
	
	TimerFragment timerFragment;
	AddTimerFragment addTimerFragment;
	ShowTempFragment showTempFragment;
	
	ImageButton ibtn_members,ibtn_setting,ibtn_timer,ibtn_temp,ibtn_tools;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all);

		initWidget();
		
        addTimerFragment = new AddTimerFragment();
		timerFragment = new TimerFragment();
		showTempFragment = new ShowTempFragment();
        
		//�ڳ�ʼ��ʱ��չʾ��ʱ���б�
		FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.relayout_all, timerFragment);
        fragmentTransaction.add(R.id.relayout_all, addTimerFragment);
        fragmentTransaction.add(R.id.relayout_all, showTempFragment);
        fragmentTransaction.hide(addTimerFragment);
        fragmentTransaction.hide(showTempFragment);
        CUR_FRAGMENT = TIMER_FRAGMENT;
        fragmentTransaction.commit();
        
        //��Ӷ�ʱ������Ӧ
        timerFragment.setOnAddClickListener(new OnAddClickListener() {
			@Override
			public void GotoAddFragment() {
				FragmentManager fm = getSupportFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				ft.hide(timerFragment);
				ft.show(addTimerFragment);
				CUR_FRAGMENT = ADD_TIMER_FRAGMENT;
				ft.commit();
			}
		});
        
        //��ʱ����������Ӧ
        addTimerFragment.setOnAddConfirmClickListener(new OnAddConfirmClickListener() {
			@Override
			public void ReturnToTimerList() {
				FragmentManager fm = getSupportFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				ft.hide(addTimerFragment);
				ft.show(timerFragment);
				CUR_FRAGMENT = TIMER_FRAGMENT;
				
				timerFragment.onShown();
				
				ft.commit();
			}
		});
        
        //��ʱȡ�������Ӧ
        addTimerFragment.setOnAddCancleClickListener(new OnAddCancleClickListener() {
			@Override
			public void ReturnToTimerList() {
				FragmentManager fm = getSupportFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				ft.hide(addTimerFragment);
				ft.show(timerFragment);
				CUR_FRAGMENT = TIMER_FRAGMENT;
				
				timerFragment.onShown();
				
				ft.commit();
			}
		});
        
        //�·���ʱ����ť�����Ӧ
        ibtn_timer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				FragmentManager fm = getSupportFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				ft.hide(getCurFragment());
				ft.show(timerFragment);
				CUR_FRAGMENT = TIMER_FRAGMENT;
				
				timerFragment.onShown();
				
				ft.commit();
			}
		});
        
        //�·��¶Ȱ�ť�����Ӧ
        ibtn_temp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				FragmentManager fm = getSupportFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				ft.hide(getCurFragment());
				ft.show(showTempFragment);
				CUR_FRAGMENT = SHOW_TEMP_FRAGMENT;
				
				showTempFragment.onShown();
				
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
	
	//��ȡ��ǰ��ʾ��fragment
	Fragment getCurFragment(){
		switch (CUR_FRAGMENT) {
		case TIMER_FRAGMENT:
			return timerFragment;
		case ADD_TIMER_FRAGMENT:
			return addTimerFragment;
		case SHOW_TEMP_FRAGMENT:
			return showTempFragment;
		default:
			return timerFragment;
		}
	}
}