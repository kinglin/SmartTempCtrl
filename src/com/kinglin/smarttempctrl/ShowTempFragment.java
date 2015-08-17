package com.kinglin.smarttempctrl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.kinglin.dao.TempDaoImp;
import com.kinglin.model.Temperature;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint({ "ShowToast", "HandlerLeak" })
public class ShowTempFragment extends Fragment {

	ImageView iv_showheadpicture;
	TextView tv_showtempbefore,tv_showtempcur,tv_showcurtime;
	ImageButton ibtn_setalarm;
	LineChart lchart_recentTemp;
	
	MyHandler myHandler;
	
	public static final int GET_WEATHER_SUCCESS = 1;
	public static final int GET_WEATHER_FAILED = 2;
	public static final int OFF_LINE = 3;
	public static final int WEATHER_NEWEST = 4;
	
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
		lchart_recentTemp = (LineChart) view.findViewById(R.id.lchart_recentTemp);
		
		ibtn_setalarm.setBackgroundResource(R.drawable.ic_launcher);
		
		myHandler = new MyHandler();
		
		ibtn_setalarm.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});
		
		
	}
	
	public void showChart(LineChart lineChart,LineData lineData){
		
		lineChart.setDrawBorders(false);
		lineChart.setNoDataTextDescription("no weather data by now");
		lineChart.setDrawGridBackground(false);
		lineChart.setGridBackgroundColor(Color.WHITE);
		lineChart.setTouchEnabled(false);
		lineChart.setDragEnabled(false);
		lineChart.setScaleEnabled(false);
		lineChart.setPinchZoom(false);
		lineChart.setBackgroundColor(Color.WHITE);
		lineChart.setData(lineData);
		
		Legend legend = lineChart.getLegend();
		legend.setForm(LegendForm.CIRCLE);
		legend.setFormSize(6f);
		legend.setTextColor(Color.BLACK);
		
		lineChart.animateX(2500);
		
	}
	
	public LineData getlLineData(List<Temperature> temperatures){
		
		ArrayList<String> xValues = new ArrayList<String>();
		ArrayList<Entry> yValues = new ArrayList<Entry>();
		
		for (int i = 0; i < temperatures.size(); i++) {
			xValues.add(temperatures.get(i).getTime().substring(11, 16));
			yValues.add(new Entry(temperatures.get(i).getTemp(), i));
		}
		
		LineDataSet lineDataSet = new LineDataSet(yValues, "Recent Temperature");
		lineDataSet.setLineWidth(2f);
		lineDataSet.setCircleSize(3f);
		lineDataSet.setColor(Color.WHITE);
		lineDataSet.setCircleColor(Color.WHITE);
		lineDataSet.setHighLightColor(Color.WHITE);
		lineDataSet.setDrawCubic(true);
		lineDataSet.setCubicIntensity(0.5f);
		
		ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();
		lineDataSets.add(lineDataSet);
		
		LineData lineData = new LineData(xValues, lineDataSets);
		
		return lineData;
	}
	
	public class GetWeatherThread extends Thread{
		
		Message msg = Message.obtain();
		@Override
		public void run() {
			
			//先判断网络状态
			if (netWorkState(getActivity().getApplicationContext())==0) {
				msg.arg1 = OFF_LINE;
			}else {
				/*
				 * 这句是中国天气网api的使用
				 * String strWeatherUrl = SmartWeatherUrlUtil.getInterfaceURL("101010100", "forecast_v");
				 */
				
				/*
				 * 以下是心知天气api的实现
				 * 其中城市目前是写死为北京状态
				 * 之后需要扩展为自动获取城市ID
				 */
				String strWeatherUrl="https://api.thinkpage.cn/v2/weather/now.json?city=CHBJ000000&language=zh-chs&unit=c&key=YEPKN68ZJ6";
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
					
					msg.arg1 = GET_WEATHER_SUCCESS;
					
				} catch (Exception e) {
					e.printStackTrace();
					msg.arg1 = WEATHER_NEWEST;
				}
				finally{
					try {
						inputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
						msg.arg1 = GET_WEATHER_FAILED;
					}
				}
			}
			myHandler.sendMessage(msg);
		}
	}
	
	//这个函数解析收到的json，获得天气信息并加入到数据库
	public void analyzeJson(String str_responce) {
		
		try {
			JSONTokener jsonTokener = new JSONTokener(str_responce);
			JSONObject json_responce = (JSONObject) jsonTokener.nextValue();
			
			if (json_responce.getString("status").equals("OK")) {
				String str_time = (String) json_responce.getJSONArray("weather").getJSONObject(0).get("last_update");
				String time = str_time.substring(0, 10)+" "+str_time.substring(11, 19);
				int temp = json_responce.getJSONArray("weather").getJSONObject(0).getJSONObject("now").getInt("temperature");
				
				Temperature temperature = new Temperature(time, temp);
				
				TempDaoImp tdi = new TempDaoImp(getActivity().getApplicationContext());
				tdi.addTemp(temperature);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	//当此fragment显示的时候调用
	public void onShown() {
		GetWeatherThread getWeatherThread = new GetWeatherThread();
		getWeatherThread.start();
		
		TempDaoImp tdi = new TempDaoImp(getActivity().getApplicationContext());
		Temperature lastTemperature = tdi.getLastTemperature();
		Temperature seclastTemperature = tdi.getSecLastTemperature();
		List<Temperature> recentTemperatures = tdi.getRecentTemperatures();
		
		if (seclastTemperature != null) {
			tv_showtempbefore.setText(seclastTemperature.getTemp()+"");
		}else {
			tv_showtempbefore.setText("no data");
		}
		if (lastTemperature != null) {
			tv_showtempcur.setText(lastTemperature.getTemp()+"");
			tv_showcurtime.setText(lastTemperature.getTime());
		}else {
			tv_showtempcur.setText("no data");
			tv_showcurtime.setText("no data");
		}
		
		showChart(lchart_recentTemp, getlLineData(recentTemperatures));
	}
	
	//线程处理UI的handler
	public class MyHandler extends Handler{
		MyHandler() {  
			super(); 
		}  
		
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.arg1) {
			case GET_WEATHER_SUCCESS:
				Toast.makeText(getActivity().getApplicationContext(), "GET_WEATHER_SUCCESS", 1000).show();
				break;
			case GET_WEATHER_FAILED:
				Toast.makeText(getActivity().getApplicationContext(), "GET_WEATHER_FAILED", 1000).show();
				break;
			case OFF_LINE:
				Toast.makeText(getActivity().getApplicationContext(), "you are offline", 1000).show();
				break;
			case WEATHER_NEWEST:
				Toast.makeText(getActivity().getApplicationContext(), "it's already the newest.", 1000).show();
				break;
			default:
				break;
			}
		}
	}
	

	//判断是否联网，WiFi为2，移动网为1，未联网为0,获取信息失败为4
	public int netWorkState(Context context) {
		int  netState=4;
		ConnectivityManager connectivity = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE); 
		NetworkInfo activeNetInfo = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		NetworkInfo mobNetInfo = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (activeNetInfo.isConnected()) {
			netState=2;
		}else if (mobNetInfo.isConnected()) {
			netState=1;
		}
		if(!activeNetInfo.isConnected() && !mobNetInfo.isConnected()) {
			netState=0;
		}
		return netState;
	}
}
