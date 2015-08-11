package com.kinglin.smarttempctrl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;

import com.kinglin.dao.MyTimerDaoImp;
import com.kinglin.model.MyTimer;
import com.kinglin.tools.MusicInfo;
import com.kinglin.tools.MusicLoader;

@SuppressLint({ "InflateParams", "ShowToast", "SimpleDateFormat" })
public class AddTimerFragment extends Fragment implements OnSeekBarChangeListener{

	SeekBar sb_downtime;
	TextView tv_downtime,tv_circletime,tv_cleanstart,tv_cleanend;
	ImageButton ibtn_circle,ibtn_content,ibtn_remark,ibtn_music,ibtn_timerconfirm,ibtn_timercancle;
	EditText et_circle;
	Button btn_circleconfirm;
	TimePicker tp_cleanstart,tp_cleanend;
	
	ListView lv_musiclist;

	MyTimer myTimer;
	long long_downtime = 0;
	long long_circle = 0;
	int int_content = 1;
	String str_remark = "";
	long long_cleanstart = 0;
	long long_cleanend = 0;
	
	//三个工具变量
	SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
	Calendar c;
	String str_time;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.frag_addtimer, container, false);
	}

	//确定添加按钮回调
	public interface OnAddConfirmClickListener {
		public void ReturnToTimerList();
	}
	private OnAddConfirmClickListener myAddConfirmListener;
	public void setOnAddConfirmClickListener(OnAddConfirmClickListener onAddConfirmListener) {
		myAddConfirmListener = onAddConfirmListener;
	}

	//取消添加按钮回调
	public interface OnAddCancleClickListener {
		public void ReturnToTimerList();
	}
	private OnAddCancleClickListener myAddCancleListener;
	public void setOnAddCancleClickListener(OnAddCancleClickListener onAddCancleListener) {
		myAddCancleListener = onAddCancleListener;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		//初始化控件
		sb_downtime = (SeekBar)view.findViewById(R.id.sb_downtime);
		tv_downtime = (TextView)view.findViewById(R.id.tv_downtime);
		tv_circletime = (TextView)view.findViewById(R.id.tv_circletime);
		ibtn_circle = (ImageButton)view.findViewById(R.id.ibtn_circle);
		ibtn_content = (ImageButton)view.findViewById(R.id.ibtn_content);
		ibtn_remark = (ImageButton)view.findViewById(R.id.ibtn_remark);
		ibtn_music = (ImageButton)view.findViewById(R.id.ibtn_music);
		ibtn_timerconfirm = (ImageButton)view.findViewById(R.id.ibtn_timerconfirm);
		ibtn_timercancle = (ImageButton)view.findViewById(R.id.ibtn_timercancle);
		sb_downtime.setOnSeekBarChangeListener(this);

		ibtn_circle.setBackgroundResource(R.drawable.timer_repeat);
		ibtn_content.setBackgroundResource(R.drawable.timer_sector_ico_01);
		ibtn_remark.setBackgroundResource(R.drawable.ic_launcher);
		ibtn_music.setBackgroundResource(R.drawable.timer_button_music_01);
		ibtn_timerconfirm.setBackgroundResource(R.drawable.timer_button_done_01);
		ibtn_timercancle.setBackgroundResource(R.drawable.ic_launcher);
		
		//添加循环按钮响应
		ibtn_circle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showPopCircle(v);
			}
		});

		ibtn_content.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showPopContent(v);
			}
		});

		ibtn_remark.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showPopRemark(v);
			}
		});

		ibtn_timerconfirm.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				long long_currenttime = System.currentTimeMillis();
				
				//当结束时间小于开始时间，则结束时间调整为第二天这个时间
				if (long_cleanstart >= long_cleanend) {
					long_cleanend += 24*60*60*1000;
				}
				
				myTimer = new MyTimer(long_currenttime, 
						long_currenttime+long_downtime, 
						long_circle, 
						1, 
						str_remark, 
						int_content,long_cleanstart,long_cleanend);
				MyTimerDaoImp mtdi = new MyTimerDaoImp(getActivity());
				mtdi.addTimer(myTimer);

				if (!myTimer.isTimerAvaliable()) {
					Toast.makeText(getActivity().getApplicationContext(), 
							"ringtime is in clean time, it has been adjust to the first ringtime off the clean time", 500);
				}
				
				if (null != myAddConfirmListener) {
					myAddConfirmListener.ReturnToTimerList();
				}

				//初始化计时器属性
				long_downtime = 0;
				long_circle = 0;
				int_content = 1;
				str_remark = "";
				tv_circletime.setText("no circle");
				long_cleanstart = 0;
				long_cleanend = 0;
			}
		});

		ibtn_timercancle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//初始化计时器属性
				long_downtime = 0;
				long_circle = 0;
				int_content = 1;
				str_remark = "";

				if (null != myAddCancleListener) {
					myAddCancleListener.ReturnToTimerList();
				}
			}
		});

		ibtn_music.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				showPopMusic(v);
			}
		});
	}

	public void showPopCircle(View v) {
		View popView = getActivity().getLayoutInflater().inflate(R.layout.pop_choosecircle, null);
		final PopupWindow popwin_circle = new PopupWindow(popView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		popwin_circle.setFocusable(true);
		popwin_circle.setBackgroundDrawable(new ColorDrawable());
		popwin_circle.update();
		int location[] = new int[2];
		location[0] = 50;
		location[1] = 50;
		v.getLocationOnScreen(location);
		popwin_circle.showAtLocation(v, Gravity.NO_GRAVITY, location[0], location[1]);
		
		et_circle = (EditText)popView.findViewById(R.id.et_circle);
		btn_circleconfirm = (Button)popView.findViewById(R.id.btn_circleconfirm);
		tv_cleanstart = (TextView)popView.findViewById(R.id.tv_cleanstart);
		tv_cleanend = (TextView)popView.findViewById(R.id.tv_cleanend);
		tp_cleanstart = (TimePicker)popView.findViewById(R.id.tp_cleanstart);
		tp_cleanend = (TimePicker)popView.findViewById(R.id.tp_cleanend);

		tp_cleanstart.setIs24HourView(true);
		tp_cleanend.setIs24HourView(true);
		
		c = Calendar.getInstance();
		str_time = formatter.format(c.getTime()).substring(0, 10)+" ";
		
		tp_cleanstart.setOnTimeChangedListener(new OnTimeChangedListener() {
			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				tv_cleanstart.setText("start: " + hourOfDay + ":" + minute);
				try {
					c.setTime(formatter.parse(str_time + hourOfDay + ":" + minute + ":00"));
					long_cleanstart = c.getTimeInMillis();
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		});
		
		tp_cleanend.setOnTimeChangedListener(new OnTimeChangedListener() {
			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				tv_cleanend.setText("start: " + hourOfDay + ":" + minute);
				try {
					c.setTime(formatter.parse(str_time + hourOfDay + ":" + minute + ":00"));
					long_cleanend = c.getTimeInMillis();
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		});
		
		
		btn_circleconfirm.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (et_circle.getText().toString().equals("") || Integer.parseInt(et_circle.getText().toString()) == 0) {
					//为空或为0时，设置为无循环
					long_circle = 0;
					tv_circletime.setText("no circle");
					popwin_circle.dismiss();
				}else if (Integer.parseInt(et_circle.getText().toString()) < 1) {
					//小于5时不行
					Toast.makeText(getActivity(), "circle can be more than 1m or 0", 1000).show();
				}else {
					//正常输入时，存储循环的值
					long_circle = Long.parseLong(et_circle.getText().toString())*60*1000;
					tv_circletime.setText(et_circle.getText().toString()+"minutes");
					popwin_circle.dismiss();
				}			
			}
		});
	}

	public void showPopContent(View v) {
		View popView = getActivity().getLayoutInflater().inflate(R.layout.pop_choosecontent, null);
		final PopupWindow popwin_content = new PopupWindow(popView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		popwin_content.setFocusable(true);
		popwin_content.setBackgroundDrawable(new ColorDrawable());
		popwin_content.update();  
		int location[] = new int[2];
		location[0] = 50;
		location[1] = 50;
		v.getLocationOnScreen(location);
		popwin_content.showAtLocation(v, Gravity.NO_GRAVITY, location[0], location[1]);

		ImageButton ibtn_food1 = (ImageButton) popView.findViewById(R.id.ibtn_food1);
		ImageButton ibtn_food2 = (ImageButton) popView.findViewById(R.id.ibtn_food2);
		ImageButton ibtn_food3 = (ImageButton) popView.findViewById(R.id.ibtn_food3);
		ImageButton ibtn_food4 = (ImageButton) popView.findViewById(R.id.ibtn_food4);
		ImageButton ibtn_life1 = (ImageButton) popView.findViewById(R.id.ibtn_life1);
		ImageButton ibtn_life2 = (ImageButton) popView.findViewById(R.id.ibtn_life2);
		ImageButton ibtn_life3 = (ImageButton) popView.findViewById(R.id.ibtn_life3);
		ImageButton ibtn_life4 = (ImageButton) popView.findViewById(R.id.ibtn_life4);
		
		ibtn_food1.setBackgroundResource(R.drawable.timer_sector_ico_01);
		ibtn_food2.setBackgroundResource(R.drawable.timer_sector_ico_02);
		ibtn_food3.setBackgroundResource(R.drawable.timer_sector_ico_03);
		ibtn_food4.setBackgroundResource(R.drawable.timer_sector_ico_04);
		ibtn_life1.setBackgroundResource(R.drawable.timer_sector_ico_05);
		ibtn_life2.setBackgroundResource(R.drawable.timer_sector_ico_06);
		ibtn_life3.setBackgroundResource(R.drawable.timer_sector_ico_07);
		ibtn_life4.setBackgroundResource(R.drawable.timer_sector_ico_08);

		ibtn_food1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int_content = 1;
				ibtn_content.setBackgroundResource(R.drawable.timer_sector_ico_01);
				popwin_content.dismiss();
			}
		});
		ibtn_food2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int_content = 2;
				ibtn_content.setBackgroundResource(R.drawable.timer_sector_ico_02);
				popwin_content.dismiss();
			}
		});
		ibtn_food3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int_content = 3;
				ibtn_content.setBackgroundResource(R.drawable.timer_sector_ico_03);
				popwin_content.dismiss();
			}
		});
		ibtn_food4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int_content = 4;
				ibtn_content.setBackgroundResource(R.drawable.timer_sector_ico_04);
				popwin_content.dismiss();
			}
		});
		ibtn_life1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int_content = 5;
				ibtn_content.setBackgroundResource(R.drawable.timer_sector_ico_05);
				popwin_content.dismiss();
			}
		});
		ibtn_life2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int_content = 6;
				ibtn_content.setBackgroundResource(R.drawable.timer_sector_ico_06);
				popwin_content.dismiss();
			}
		});
		ibtn_life3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int_content = 7;
				ibtn_content.setBackgroundResource(R.drawable.timer_sector_ico_07);
				popwin_content.dismiss();
			}
		});
		ibtn_life4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int_content = 8;
				ibtn_content.setBackgroundResource(R.drawable.timer_sector_ico_08);
				popwin_content.dismiss();
			}
		});

	}

	public void showPopRemark(View v) {
		View popView = getActivity().getLayoutInflater().inflate(R.layout.pop_addremark, null);
		final PopupWindow popwin_remark = new PopupWindow(popView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		popwin_remark.setFocusable(true);
		popwin_remark.setBackgroundDrawable(new ColorDrawable());
		popwin_remark.update();  
		int location[] = new int[2];
		location[0] = 50;
		location[1] = 50;
		v.getLocationOnScreen(location);
		popwin_remark.showAtLocation(v, Gravity.NO_GRAVITY, location[0], location[1]);

		final EditText et_remark = (EditText) popView.findViewById(R.id.et_remark);
		Button btn_addremark = (Button) popView.findViewById(R.id.btn_addremark);

		btn_addremark.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				str_remark = et_remark.getText().toString();
				popwin_remark.dismiss();
			}
		});
	}

	public void showPopMusic(View v) {
		
		MusicLoader musicLoader = new MusicLoader(getActivity().getContentResolver());
		List<MusicInfo> musicInfos = musicLoader.getMusicList();
		
		View popView = getActivity().getLayoutInflater().inflate(R.layout.pop_choosemusic, null);
		final PopupWindow popwin_music = new PopupWindow(popView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		popwin_music.setFocusable(true);
		popwin_music.setBackgroundDrawable(new ColorDrawable());
		popwin_music.update();  
		int location[] = new int[2];
		location[0] = 50;
		location[1] = 50;
		v.getLocationOnScreen(location);
		popwin_music.showAtLocation(v, Gravity.NO_GRAVITY, location[0], location[1]);
		
		//控件初始化
		lv_musiclist = (ListView)popView.findViewById(R.id.lv_musiclist);

		//将获取的音乐信息显示在listview上
		updateMusicList(musicInfos);
		
		//list条目点击响应
		lv_musiclist.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			}
		});

	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		String str_downtime = "";

		switch (progress/20) {
		case 0:
			str_downtime = "1 minutes";
			long_downtime = 1*60*1000;
			break;
		case 1:
			str_downtime = "10 minutes";
			long_downtime = 10*60*1000;
			break;
		case 2:
			str_downtime = "15 minutes";
			long_downtime = 15*60*1000;
			break;
		case 3:
			str_downtime = "30 minutes";
			long_downtime = 30*60*1000;
			break;
		case 4:
			str_downtime = "1 hour";
			long_downtime = 60*60*1000;
			break;
		case 5:
			str_downtime = "2 hours";
			long_downtime = 2*60*60*1000;
			break;
		default:
			str_downtime = "5 minutes";
			long_downtime = 5*60*1000;
			break;
		}
		tv_downtime.setText("down time:"+str_downtime);
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
	}

	public void updateMusicList(List<MusicInfo> musicInfos) {
		
		//lv_musiclist.removeAllViewsInLayout();
		
		if (musicInfos.size() != 0) {
			List<HashMap<String, Object>> data = new ArrayList<HashMap<String,Object>>(); 
			for( MusicInfo musicInfo : musicInfos){  
				HashMap<String, Object> item = new HashMap<String, Object>();
				item.put("musicId", musicInfo.getId());
				item.put("musicname", musicInfo.getTitle());
				item.put("musicartist", musicInfo.getArtist());
				item.put("musicduration", musicInfo.getDuration());

				data.add(item);  
			}  
			SimpleAdapter adapter = new SimpleAdapter(getActivity(), data, R.layout.lvitem_musiclist,   
					new String[]{"musicname","musicartist","musicduration"}, 
					new int[]{R.id.tv_musicname, R.id.tv_musicartist,R.id.tv_musicduration});

			//实现列表的显示  
			lv_musiclist.setAdapter(adapter);
		}
	}
}
