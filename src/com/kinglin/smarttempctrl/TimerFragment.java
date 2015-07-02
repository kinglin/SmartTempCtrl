package com.kinglin.smarttempctrl;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class TimerFragment extends Fragment {

	ImageButton ibtn_addtimer;
	
	public interface OnAddClickListener {
		public void GotoAddFragment();
	}
	
	private OnAddClickListener myAddListener;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.frag_timerlist, container, false);
	}
	
	public void setOnAddClickListener(OnAddClickListener onAddClickListener) {
		myAddListener = onAddClickListener;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		ibtn_addtimer = (ImageButton) view.findViewById(R.id.ibtn_addtimer);
		ibtn_addtimer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (null != myAddListener) {
					myAddListener.GotoAddFragment();
				}
			}
		});
	}

	@Override
	public void onPause() {
		super.onPause();
	}

}
