package com.kinglin.smarttempctrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kinglin.dao.MyTimerDaoImp;
import com.kinglin.model.MyTimer;

@SuppressLint({ "ViewHolder", "InflateParams" })
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
		
		//ibtn_addtimer.setBackgroundResource(R.drawable.timer_button_list_new_01);
		ibtn_addtimer.setImageResource(R.drawable.timer_button_list_new_01);
		
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
	
	//当此fragment显示的时候调用
	public void onShown() {
		MyTimerDaoImp mtdi = new MyTimerDaoImp(getActivity());
		List<MyTimer> myTimers = mtdi.getAllMyTimers();
		updateListview(myTimers);
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
	            
	            if (myTimer.getTimeron() == 1) {
	            	item.put("downtime", "downtime: "+downtime+" seconds");
				}else {
					item.put("downtime", "downtime: "+" this timer is off");
				}
	            item.put("circle", "circle: "+circle+" minutes");
	            item.put("content", showContent(myTimer.getContent()));
	            item.put("remark", myTimer.getRemark());
	            item.put("timeron", showTimerOn(myTimer.getTimeron()));
	            
	            data.add(item);  
	        }  
 			
			MyAdapter myAdapter = new MyAdapter(getActivity().getApplicationContext(),data, myTimers);
			
			//实现列表的显示  
	        lv_timerlist.setAdapter(myAdapter);
		}
	}
	
	public class MyAdapter extends BaseAdapter{

		private LayoutInflater inflater;
		private List<HashMap<String, Object>> data;
		private List<MyTimer> myTimers;
		
		public MyAdapter(Context context,List<HashMap<String, Object>> data,List<MyTimer> myTimers) {
			this.data = data;
			this.myTimers = myTimers;
			inflater = LayoutInflater.from(context);
		}
		
		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			return myTimers.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			View view = inflater.inflate(R.layout.lvitem_timerlist, null);
			
			TextView tv_showdowntime = (TextView) view.findViewById(R.id.tv_showdowntime);
			TextView tv_showcircle = (TextView) view.findViewById(R.id.tv_showcircle);
			TextView tv_showremark = (TextView) view.findViewById(R.id.tv_showremark);
			ImageView iv_showcontent = (ImageView) view.findViewById(R.id.iv_showcontent);
			final ImageButton ibtn_showtimeron = (ImageButton) view.findViewById(R.id.ibtn_showtimeron);
			
			tv_showdowntime.setText((String) data.get(position).get("downtime"));
			tv_showcircle.setText((String) data.get(position).get("circle"));
			tv_showremark.setText((String) data.get(position).get("remark"));
			iv_showcontent.setBackgroundResource((int) data.get(position).get("content"));
			ibtn_showtimeron.setBackgroundResource((int) data.get(position).get("timeron"));
			
			ibtn_showtimeron.setFocusable(false);
			final int tmp_positon = position;
			ibtn_showtimeron.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (myTimers.get(tmp_positon).getTimeron() == 1) {
						ibtn_showtimeron.setBackgroundResource(R.drawable.ic_launcher);
						MyTimerDaoImp mtdi = new MyTimerDaoImp(getActivity().getApplicationContext());
						mtdi.endTimer(myTimers.get(tmp_positon));
					}else {
						ibtn_showtimeron.setBackgroundResource(R.drawable.swicher_01);
						MyTimerDaoImp mtdi = new MyTimerDaoImp(getActivity().getApplicationContext());
						mtdi.startTimer(myTimers.get(tmp_positon));
					}
				}
			});
			
			return view;
		}
		
	}
	
	public int showContent(int content){
		switch (content) {
		case 1:
			return R.drawable.timer_sector_ico_01;
		case 2:
			return R.drawable.timer_sector_ico_02;
		case 3:
			return R.drawable.timer_sector_ico_03;
		case 4:
			return R.drawable.timer_sector_ico_04;
		case 5:
			return R.drawable.timer_sector_ico_05;
		case 6:
			return R.drawable.timer_sector_ico_06;
		case 7:
			return R.drawable.timer_sector_ico_07;
		case 8:
			return R.drawable.timer_sector_ico_08;
		default:
			return R.drawable.timer_sector_ico_01;
		}
	}
	
	public int showTimerOn(int timeron){
		switch (timeron) {
		case 1:
			return R.drawable.swicher_01;
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
