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

/*
 * 这个类是使用中国天气网api时使用
 * 但由于获得不到实时天气，目前放弃使用
 */

public class SmartWeatherUrlUtil {  
    private final static String TAG = "WeatherUrlUtil";  
    private static final String MAC_NAME = "HmacSHA1";  
    private static final String ENCODING = "UTF-8";  
    private static final String appid = "42468b21edef4106";  
    private static final String private_key = "1df5e4_SmartWeatherAPI_16529e4";  
    private static final String url_header="http://open.weather.com.cn/data/?";  
  
    /** 
     * 使用 HMAC-SHA1 签名方法对对encryptText进行签名 
     *  
     * @param url 
     *            被签名的字符串 
     * @param privatekey 
     *            密钥 
     * @return 
     * @throws Exception 
     */  
    private static byte[] HmacSHA1Encrypt(String url, String privatekey)  
            throws Exception {  
        byte[] data = privatekey.getBytes(ENCODING);  
        // 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称  
        SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);  
        // 生成一个指定 Mac 算法 的 Mac 对象  
        Mac mac = Mac.getInstance(MAC_NAME);  
        // 用给定密钥初始化 Mac 对象  
        mac.init(secretKey);  
        byte[] text = url.getBytes(ENCODING);  
        // 完成 Mac 操作  
        return mac.doFinal(text);  
    }  
    /** 
     * 获取URL通过privatekey加密后的码 
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
     * 获得接口的URL地址 
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
