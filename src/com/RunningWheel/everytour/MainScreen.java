package com.RunningWheel.everytour;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainScreen extends Activity {

	final String requestURL = "http://evertour.sinaapp.com/android_request.php";
	final int MY_TEXT_VIEW_ID = 0x88888888;

	int screenWidth, screenHeight;
	GridLayout hotPlaceGrid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_screen);

		JSONArray most8HotPlaceJSONArray = getHotPlaceJSONArray(requestURL,0,7);

		hotPlaceGrid = (GridLayout) findViewById(R.id.main_screen_hot_place_grid);
		for (int i = 0; i < 8; i++) {
			RelativeLayout relativeLayout = new RelativeLayout(
					getApplicationContext());

			TextView textView = new TextView(getApplicationContext());
			ImageView imageView = new ImageView(getApplicationContext());

			textView.setText("test" + i);

			textView.setId(MY_TEXT_VIEW_ID + i);

			/*
			 * Bitmap bm = getHttpBitmap(baidu_icon);
			 * 
			 * imageView.setImageBitmap(bm);
			 */

			// 设置图片Layout的高和宽
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					screenWidth / 4, screenWidth / 4);

			// 设置图片层的位置:在textView的下方
			params.addRule(RelativeLayout.BELOW, textView.getId());

			Log.i("Activity:MainScreen",
					"textVeiw" + i + " id = " + textView.getId());

			imageView.setLayoutParams(params);
			imageView.setImageResource(R.drawable.ic_launcher);

			relativeLayout.addView(imageView);
			relativeLayout.addView(textView);
			hotPlaceGrid.addView(relativeLayout, i);
		}

		Log.i("MainScreen", "content view has set.");
	}

	public JSONArray getHotPlaceJSONArray(String httpBitmap,int start,int end) {
		HttpPost httpRequest = new HttpPost(requestURL);
		
		//NameValuePair实现请求参数的封装
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("requestType","hotplace"));
		params.add(new BasicNameValuePair("start",String.valueOf(start)));
		params.add(new BasicNameValuePair("end",String.valueOf(end)));
		
		try
		{
			//添加请求参数到请求对象
			httpRequest.setEntity( new UrlEncodedFormEntity(params,HTTP.UTF_8));
			
			//发送请求并等待响应
			HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
			
			//若状态码为200则ok
			if(httpResponse.getStatusLine().getStatusCode() == 200)
			{
				String strResult = EntityUtils.toString(httpResponse.getEntity());
				JSONArray result = new JSONArray(strResult);
				return result;
			}
			else
			{
				Log.e("MainScreen:Http Post","Error Response: "+httpResponse.getStatusLine().toString());
				return null;
			}
		}
		catch(ClientProtocolException e)
		{
			Log.e("MainScreen:Http Post",e.getMessage().toString());
			e.printStackTrace();
		}
		catch(IOException e)
		{
			Log.e("MainScreen:Http Post",e.getMessage().toString());
			e.printStackTrace();
		}
		catch(Exception e)
		{
			Log.e("MainScreen:Http Post",e.getMessage().toString());
			e.printStackTrace();
		}
		return null;
	}

	void getScreenSize() {
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		screenWidth = metric.widthPixels; // 屏幕宽度（像素）
		screenHeight = metric.heightPixels; // 屏幕高度（像素）
		// float density = metric.density; // 屏幕密度（0.75 / 1.0 / 1.5）
		// int densityDpi = metric.densityDpi; // 屏幕密度DPI（120 / 160 / 240）

		Log.i("Activity:MainScreen", "screenWidth:" + screenWidth);
		Log.i("Activity:MainScreen", "screenHeight:" + screenHeight);
	}
}