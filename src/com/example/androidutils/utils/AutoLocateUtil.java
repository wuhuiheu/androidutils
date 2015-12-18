
package com.example.androidutils.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;
import android.util.Log;

/** 查询ip和所在城市，数据来自http://iframe.ip138.com/ic.asp */
public class AutoLocateUtil{

	public static class IPResult{

		public String ip = null;
		public String location = null; // maybe null
		public String province = null;
	}

	private static final String IP138_URL = "http://iframe.ip138.com/ic.asp";
	private static final String SINA_URL = "http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js";
	private static final String SOHU_URL = "http://pv.sohu.com/cityjson?ie=utf-8";

	/**
	 * 查询IP及所在地,根据ip38查询
	 *
	 * @return IPResult
	 */
	public static IPResult queryIPLocationByIP38(){
		IPResult result = new IPResult();

		URL url = null;
		InputStream is = null;
		try{
			url = new URL(IP138_URL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestProperty("content-type", "Text/html;charset=GBK");
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(10000);
			conn.setUseCaches(false);

			int responseCode = conn.getResponseCode();
			if(responseCode == HttpURLConnection.HTTP_OK){
				is = conn.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(is, "GBK"));
				StringBuffer sb = new StringBuffer();
				String line = null;
				while((line = reader.readLine()) != null){
					sb.append(line + "\n");
				}
				is.close();
				conn.disconnect();

				System.out.println(sb.toString());
				// 从反馈的结果中提取出IP地址和所在城市
				int start = -1;
				int end = -1;
				String ip;
				String location;
				String province;

				start = sb.indexOf("[");
				end = sb.indexOf("]", start + 1);
				if(start != -1 && end > start){
					ip = sb.substring(start + 1, end);
					// System.out.println("ip=" + ip);
				}
				else{
					ip = null;
				}

				start = sb.indexOf("来自：");
				int tmp = sb.lastIndexOf(" ");
				end = tmp == -1 ? (sb.length() - 1) : tmp;
				if(start != -1 && end > start){
					location = sb.substring(start + 3, end);
					// System.out.println("location=" + location);
					province = getLegalProvinceName(location);
					location = getLegalCityName(location);

				}
				else{
					location = null;
					province = null;
				}

				result.ip = ip;
				result.location = location;
				result.province = province;
				return result;
			}
		}
		catch(MalformedURLException e){
			e.printStackTrace();
			result.ip = null;
			result.location = null;
			result.province = null;
		}
		catch(IOException e){
			e.printStackTrace();
			result.ip = null;
			result.location = null;
			result.province = null;
		}

		// ip138查询失败则从sina服务端查询IP-城市定位
		// 如果新浪端再查询失败，则从搜狐查询
		return queryIPLocationBySina();
	}

	/**
	 * 根据新浪接口查询IP-城市
	 *
	 * @return
	 */
	public static IPResult queryIPLocationBySina(){
		IPResult result = new IPResult();
		String content = getInfoByUrl(SINA_URL);
		LogUtil.d("通过新浪的ip查询接口获取的数据为:" + content);

		if(TextUtils.isEmpty(content)){
			LogUtil.w("通过新浪的接口查询不到ip地址信息,换sohu的");
			return queryIPLocationBySohu();
		}

		int start = content.indexOf("{");
		int end = content.indexOf("};");
		if(start < 0 || end < 0 || start >= end){
			return queryIPLocationBySohu();
		}
		String jsonString = content.substring(start, end + 1);
		try{
			JSONObject json = new JSONObject(jsonString);
			result.ip = json.getString("start");
			result.location = json.getString("city");
			result.province = json.getString("province");
			return result;
		}
		catch(JSONException e){
			e.printStackTrace();
		}

		LogUtil.w("通过新浪的接口查询不到ip地址信息,换sohu的");
		return queryIPLocationBySohu();

	}

	/**
	 * 根据搜狐接口查询IP-城市
	 *
	 * @return
	 */
	public static IPResult queryIPLocationBySohu(){
		String content = getInfoByUrl(SOHU_URL);

		if(content == null || content.length() == 0){
			return null;
		}

		int start = content.indexOf("{");
		int end = content.indexOf("};");
		if(start < 0 || end < 0 || start >= end){
			return null;
		}
		IPResult result = new IPResult();
		content = content.substring(start, end + 1);
		try{
			JSONObject json = new JSONObject(content);
			result.ip = json.getString("cip");
			result.location = getLegalCityName(json.getString("cname"));
			result.province = getLegalProvinceName(json.getString("cname"));
			return result;
		}
		catch(JSONException e){
			e.printStackTrace();
			result = null;
		}

		return result;
	}

	private static String getLegalProvinceName(String name){
		Log.d("ljh", name + "");
		if(name == null || name.equals(""))
			return null;

		int start, end;
		if(name.contains("北京")){
			return "北京";
		}
		else if(name.contains("上海")){
			return "上海";
		}
		else if(name.contains("天津")){
			return "天津";
		}
		else if(name.contains("重庆")){
			return "重庆";
		}
		else if(name.contains("香港")){
			return "香港";
		}
		else if(name.contains("澳门")){
			return "澳门";
		}
		else if(name.contains("省")){
			start = 0;
			end = name.indexOf("省");
			if(start < end)
				return name.substring(start, end);
		}
		/*-else if(name.contains("自治区")){
			start = 0;
			end = name.indexOf("自治区");
			if(start < end)
				return name.substring(start, end);
		}*/
		else if(name.contains("内蒙古")){
			return "内蒙古";
		}
		else if(name.contains("新疆")){
			return "新疆";
		}
		else if(name.contains("广西")){
			return "广西";
		}
		else if(name.contains("西藏")){
			return "西藏";
		}
		else if(name.contains("宁夏")){
			return "宁夏";
		}

		return null;
	}

	/**
	 * 根据ip获得的模糊地区名返回新浪天气所需的精准城市名.
	 *
	 * @param name
	 *            模糊地区名 eg.“广东省深圳市”、“内蒙古呼和浩特市等”
	 * @return 匹配成功：精准城市名 eg “深圳”，“呼和浩特” 匹配失败：return null
	 */
	private static String getLegalCityName(String name){
		if(name == null || name.equals(""))
			return null;

		int start, end;
		if(name.contains("北京")){
			return "北京";
		}
		else if(name.contains("上海")){
			return "上海";
		}
		else if(name.contains("天津")){
			return "天津";
		}
		else if(name.contains("重庆")){
			return "重庆";
		}
		else if(name.contains("香港")){
			return "香港";
		}
		else if(name.contains("澳门")){
			return "澳门";
		}
		else if(name.contains("省")){
			start = name.indexOf("省");
			end = name.indexOf("市");
			if(start < end)
				return name.substring(start + 1, end);
		}
		else if(name.contains("自治区")){
			start = name.indexOf("自治区");
			end = name.indexOf("市");
			if(start < end)
				return name.substring(start + 3, end);
		}
		else if(name.contains("内蒙古")){
			start = name.indexOf("内蒙古");
			end = name.indexOf("市");
			if(start < end)
				return name.substring(start + 3, end);
		}
		else if(name.contains("新疆")){
			start = name.indexOf("新疆");
			end = name.indexOf("市");
			if(start < end)
				return name.substring(start + 2, end);
		}
		else if(name.contains("广西")){
			start = name.indexOf("广西");
			end = name.indexOf("市");
			if(start < end)
				return name.substring(start + 2, end);
		}
		else if(name.contains("西藏")){
			start = name.indexOf("西藏");
			end = name.indexOf("市");
			if(start < end)
				return name.substring(start + 2, end);
		}
		else if(name.contains("宁夏")){
			start = name.indexOf("宁夏");
			end = name.indexOf("市");
			if(start < end)
				return name.substring(start + 2, end);
		}

		return null;
	}

	private static String getInfoByUrl(String url){
		/*-HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 10000);
		HttpConnectionParams.setSoTimeout(httpClient.getParams(), 10000);*/

		StringBuilder sb = new StringBuilder();
		HttpClient httpClient = new DefaultHttpClient();
		// 设置超时10s
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
		// 读取超时10s
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);

		HttpGet get = new HttpGet(url);
		try{
			HttpResponse httpResponse = httpClient.execute(get);
			if(httpResponse.getStatusLine().getStatusCode() == 200){

				HttpEntity entity = httpResponse.getEntity();
				if(entity != null){
					// 读取服务器响应
					sb.append(EntityUtils.toString(entity));
				}
				return sb.toString();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}

		return sb.toString();

	}

}
