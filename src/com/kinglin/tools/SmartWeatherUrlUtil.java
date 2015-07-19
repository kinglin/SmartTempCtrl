package com.kinglin.tools;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import android.annotation.SuppressLint;
import android.util.Base64;
import android.util.Log;

@SuppressLint("SimpleDateFormat")
public class SmartWeatherUrlUtil {  
    private final static String TAG = "WeatherUrlUtil";  
    private static final String MAC_NAME = "HmacSHA1";  
    private static final String ENCODING = "UTF-8";  
    private static final String appid = "42468b21edef4106";  
    private static final String private_key = "1df5e4_SmartWeatherAPI_16529e4";  
    private static final String url_header="http://open.weather.com.cn/data/?";  
  
    /** 
     * ʹ�� HMAC-SHA1 ǩ�������Զ�encryptText����ǩ�� 
     *  
     * @param url 
     *            ��ǩ�����ַ��� 
     * @param privatekey 
     *            ��Կ 
     * @return 
     * @throws Exception 
     */  
    private static byte[] HmacSHA1Encrypt(String url, String privatekey)  
            throws Exception {  
        byte[] data = privatekey.getBytes(ENCODING);  
        // ���ݸ������ֽ����鹹��һ����Կ,�ڶ�����ָ��һ����Կ�㷨������  
        SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);  
        // ����һ��ָ�� Mac �㷨 �� Mac ����  
        Mac mac = Mac.getInstance(MAC_NAME);  
        // �ø�����Կ��ʼ�� Mac ����  
        mac.init(secretKey);  
        byte[] text = url.getBytes(ENCODING);  
        // ��� Mac ����  
        return mac.doFinal(text);  
    }  
    /** 
     * ��ȡURLͨ��privatekey���ܺ���� 
     * @param url 
     * @param privatekey 
     * @return 
     * @throws Exception 
     */  
    private static String getKey(String url, String privatekey) throws Exception {  
        byte[] key_bytes = HmacSHA1Encrypt(url, privatekey);  
        String base64encoderStr = Base64.encodeToString(key_bytes, Base64.NO_WRAP);  
        return URLEncoder.encode(base64encoderStr, ENCODING);  
    }  
    /** 
     * ��ýӿڵ�URL��ַ 
     * @param areaid 
     * @param type 
     * @param date 
     * @return 
     * @throws Exception 
     */  
    
    private static String getInterfaceURL(String areaid,String type,String date) throws Exception{  
        String keyurl=url_header+"areaid="+areaid+"&type="+type+"&date="+date+"&appid=";  
        String key=getKey(keyurl+appid,private_key);  
        String appid6 = appid.substring(0, 6);  
          
        return keyurl+appid6+"&key=" + key;  
    }  
      
    public static String getInterfaceURL(String areaid,String type){  
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmm");  
        String date = dateFormat.format(new Date());  
        //String type="forecast3d";//"index";//"forecast3d";"observe"  
        try {  
            return getInterfaceURL(areaid,type,date);  
        } catch (Exception e) {  
            Log.e(TAG, e.getMessage(),e.fillInStackTrace());  
        }  
        return null;  
    }  
  
}  
