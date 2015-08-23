package com.RunningWheel.everytour;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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

	final String baidu_icon = "http://www.baidu.com/img/bd_logo1.png";
	final int MY_TEXT_VIEW_ID = 0x88888888;

	int screenWidth, screenHeight;
	GridLayout hotPlaceGrid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_screen);

		getScreenSize();

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

	public Bitmap getHttpBitmap(String httpBitmap) {
		URL myFileURL;
		Bitmap bitmap = null;
		try {
			myFileURL = new URL(httpBitmap);

			// 获得连接
			HttpURLConnection conn = (HttpURLConnection) myFileURL
					.openConnection();

			// 设置超时时间为6000毫秒，conn.setConnectTiemeout(0)表示没有时间限制
			conn.setConnectTimeout(6000);

			// 连接设置获得数据流
			conn.setDoInput(true);

			// 不使用缓存
			conn.setUseCaches(false);

			// 得到数据流
			InputStream is = conn.getInputStream();

			// 解析得到图片
			bitmap = BitmapFactory.decodeStream(is);

			is.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return bitmap;
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
