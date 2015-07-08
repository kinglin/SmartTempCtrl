package com.kinglin.smarttempctrl;


import com.kinglin.smarttempctrl.AddTimerFragment.OnAddConfirmClickListener;
import com.kinglin.smarttempctrl.TimerFragment.OnAddClickListener;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class AllActivity extends FragmentActivity {

	TimerFragment timerFragment;
	AddTimerFragment addTimerFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all);

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
	}
}
