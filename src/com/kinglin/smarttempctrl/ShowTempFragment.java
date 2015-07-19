package com.kinglin.smarttempctrl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.kinglin.tools.SmartWeatherUrlUtil;

public class ShowTempFragment extends Fragment {

	ImageView iv_showheadpicture;
	TextView tv_showtempbefore,tv_showtempcur,tv_showcurtime;
	ImageButton ibtn_setalarm;
	
	public ShowTempFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.frag_showtemp, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		//初始化所有控件
		iv_showheadpicture = (ImageView) view.findViewById(R.id.iv_showheadpicture);
		tv_showtempbefore = (TextView) view.findViewById(R.id.tv_showtempbefore);
		tv_showtempcur = (TextView) view.findViewById(R.id.tv_showtempcur);
		tv_showcurtime = (TextView) view.findViewById(R.id.tv_showcurtime);
		ibtn_setalarm = (ImageButton) view.findViewById(R.id.ibtn_setalarm);
		
		ibtn_setalarm.setBackgroundResource(R.drawable.ic_launcher);
		
		GetWeatherThread getWeatherThread = new GetWeatherThread();
		getWeatherThread.start();
		
		ibtn_setalarm.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});
	}
	
	
	public class GetWeatherThread extends Thread{
		@Override
		public void run() {
			String strWeatherUrl = SmartWeatherUrlUtil.getInterfaceURL("101010100", "forecast_v");
			HttpGet httpGet = new HttpGet(strWeatherUrl);
			HttpClient client = new DefaultHttpClient();
			InputStream inputStream = null;
			try {
				HttpResponse httpResponse = client.execute(httpGet);
				HttpEntity httpEntity = httpResponse.getEntity();
				inputStream = httpEntity.getContent();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				String line = "";
				String result = "";
				while((line = bufferedReader.readLine())!= null){
					result = result + line;
				}
				analyzeJson(result);
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally{
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void analyzeJson(String str_responce) {
		
	}

}
