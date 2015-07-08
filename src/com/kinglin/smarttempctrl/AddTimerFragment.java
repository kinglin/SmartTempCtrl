package com.kinglin.smarttempctrl;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.kinglin.dao.MyTimerDaoImp;
import com.kinglin.model.MyTimer;

@SuppressLint({ "InflateParams", "ShowToast" })
public class AddTimerFragment extends Fragment implements OnSeekBarChangeListener{

	SeekBar sb_downtime;
	TextView tv_downtime,tv_circletime;
	ImageButton ibtn_circle,ibtn_content,ibtn_remark,ibtn_music,ibtn_timerconfirm;
	EditText et_circle;
	Button btn_circleconfirm;
	
	MyTimer myTimer;
	long long_downtime = 0;
	long long_circle = 0;
	int int_content = 1;
	String str_remark = "";

	public interface OnAddConfirmClickListener {
		public void ReturnToTimerList();
	}
	
	private OnAddConfirmClickListener myAddConfirmListener;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.frag_addtimer, container, false);
	}
	
	public void setOnAddConfirmClickListener(OnAddConfirmClickListener onAddConfirmListener) {
		myAddConfirmListener = onAddConfirmListener;
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
		sb_downtime.setOnSeekBarChangeListener(this);

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
				myTimer = new MyTimer(long_currenttime, 
						long_currenttime+long_downtime, 
						long_circle, 
						1, 
						str_remark, 
						int_content);
				MyTimerDaoImp mtdi = new MyTimerDaoImp();
				mtdi.addTimer(myTimer);
				
				if (null != myAddConfirmListener) {
					myAddConfirmListener.ReturnToTimerList();
				}
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
		
		btn_circleconfirm.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (et_circle.getText().toString().equals("") || Integer.parseInt(et_circle.getText().toString()) == 0) {
					//为空或为0时，设置为无循环
					long_circle = 0;
					tv_circletime.setText("no circle");
					popwin_circle.dismiss();
				}else if (Integer.parseInt(et_circle.getText().toString()) < 5) {
					//小于5时不行
					Toast.makeText(getActivity(), "circle can be more than 5m or 0", 1000).show();
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
		
		ibtn_food1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int_content = 1;
				popwin_content.dismiss();
			}
		});
		ibtn_food2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int_content = 2;
				popwin_content.dismiss();
			}
		});
		ibtn_food3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int_content = 3;
				popwin_content.dismiss();
			}
		});
		ibtn_food4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int_content = 4;
				popwin_content.dismiss();
			}
		});
		ibtn_life1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int_content = 5;
				popwin_content.dismiss();
			}
		});
		ibtn_life2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int_content = 6;
				popwin_content.dismiss();
			}
		});
		ibtn_life3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int_content = 7;
				popwin_content.dismiss();
			}
		});
		ibtn_life4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int_content = 8;
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
	
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		String str_downtime = "";

		switch (progress/20) {
		case 0:
			str_downtime = "5 minutes";
			long_downtime = 5*60*1000;
			break;
		case 1:
			str_downtime = "30 minutes";
			long_downtime = 30*60*1000;
			break;
		case 2:
			str_downtime = "1 hour";
			long_downtime = 60*60*1000;
			break;
		case 3:
			str_downtime = "2 hours";
			long_downtime = 2*60*60*1000;
			break;
		case 4:
			str_downtime = "6 hours";
			long_downtime = 6*60*60*1000;
			break;
		case 5:
			str_downtime = "12 hours";
			long_downtime = 12*60*60*1000;
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

}
