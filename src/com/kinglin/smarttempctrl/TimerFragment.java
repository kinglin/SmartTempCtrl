package com.kinglin.smarttempctrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.kinglin.dao.MyTimerDaoImp;
import com.kinglin.model.MyTimer;

public class TimerFragment extends Fragment {

	ImageButton ibtn_addtimer;
	ListView lv_timerlist;
	
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
		
		//控件初始化
		ibtn_addtimer = (ImageButton) view.findViewById(R.id.ibtn_addtimer);
		lv_timerlist = (ListView)view.findViewById(R.id.lv_timerlist);
		
		//更新定时器列表
		MyTimerDaoImp mtdi = new MyTimerDaoImp(getActivity());
		List<MyTimer> myTimers = mtdi.getAllMyTimers();
		updateListview(myTimers);
		
		//添加定时器按钮响应
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
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {
			//每5秒刷新一次listview
			Timer timer = new Timer();
			timer.schedule(new RefreshLiseview(), 0, 5*1000);
		}
	}

	//listview刷新函数
	public void updateListview(List<MyTimer> myTimers) {
		lv_timerlist.removeAllViewsInLayout();
		
		if (myTimers.size() != 0) {
			List<HashMap<String, Object>> data = new ArrayList<HashMap<String,Object>>(); 
			for(MyTimer myTimer : myTimers){  
	            HashMap<String, Object> item = new HashMap<String, Object>();
	            item.put("timerId", myTimer.getId());
	            int downtime = (int) ((myTimer.getRingtime()-System.currentTimeMillis())/1000);
	            int circle = (int) (myTimer.getCircle()/1000/60);
	            item.put("downtime", "downtime: "+downtime+" seconds");
	            item.put("circle", "circle: "+circle+" minutes");
	            item.put("content", showContent(myTimer.getContent()));
	            item.put("remark", myTimer.getRemark());
	            item.put("timeron", showTimerOn(myTimer.getTimeron()));
	            
	            data.add(item);  
	        }  
			SimpleAdapter adapter = new SimpleAdapter(getActivity(), data, R.layout.lvitem_timerlist,   
	                new String[]{"downtime", "circle","content","remark","timeron"}, 
	                new int[]{R.id.tv_showdowntime, R.id.tv_showcircle,R.id.iv_showcontent,R.id.tv_showremark,R.id.iv_showtimeron});  
	       
			//实现列表的显示  
	        lv_timerlist.setAdapter(adapter);
		}
	}
	
	//这个类用来周期性更新listview
	public class RefreshLiseview extends TimerTask{

		@Override
		public void run() {
			MyTimerDaoImp mtdi = new MyTimerDaoImp(getActivity());
			List<MyTimer> myTimers = mtdi.getAllMyTimers();
			updateListview(myTimers);
		}
		
	}
	
	public int showContent(int content){
		switch (content) {
		case 1:
			return R.drawable.ic_launcher;
		case 2:
			return R.drawable.ic_launcher;
		case 3:
			return R.drawable.ic_launcher;
		case 4:
			return R.drawable.ic_launcher;
		case 5:
			return R.drawable.ic_launcher;
		case 6:
			return R.drawable.ic_launcher;
		case 7:
			return R.drawable.ic_launcher;
		case 8:
			return R.drawable.ic_launcher;
		default:
			return R.drawable.ic_launcher;
		}
	}
	
	public int showTimerOn(int timeron){
		switch (timeron) {
		case 1:
			return R.drawable.ic_launcher;
		case 2:
			return R.drawable.ic_launcher;
		default:
			return R.drawable.ic_launcher;
		}
	}

	@Override
	public void onPause() {
		super.onPause();
	}

}
