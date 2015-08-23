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

			// ����ͼƬLayout�ĸߺͿ�
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					screenWidth / 4, screenWidth / 4);

			// ����ͼƬ���λ��:��textView���·�
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

			// �������
			HttpURLConnection conn = (HttpURLConnection) myFileURL
					.openConnection();

			// ���ó�ʱʱ��Ϊ6000���룬conn.setConnectTiemeout(0)��ʾû��ʱ������
			conn.setConnectTimeout(6000);

			// �������û��������
			conn.setDoInput(true);

			// ��ʹ�û���
			conn.setUseCaches(false);

			// �õ�������
			InputStream is = conn.getInputStream();

			// �����õ�ͼƬ
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
		screenWidth = metric.widthPixels; // ��Ļ��ȣ����أ�
		screenHeight = metric.heightPixels; // ��Ļ�߶ȣ����أ�
		// float density = metric.density; // ��Ļ�ܶȣ�0.75 / 1.0 / 1.5��
		// int densityDpi = metric.densityDpi; // ��Ļ�ܶ�DPI��120 / 160 / 240��

		Log.i("Activity:MainScreen", "screenWidth:" + screenWidth);
		Log.i("Activity:MainScreen", "screenHeight:" + screenHeight);
	}
}
