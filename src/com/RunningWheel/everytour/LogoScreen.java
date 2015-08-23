package com.RunningWheel.everytour;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.Window;

public class LogoScreen extends Activity {

	private long m_dwSplashTime = 3000; // 闪屏窗口保留时间
	private boolean m_bPaused = false; // 是否暂停显示闪屏界面
	private boolean m_bSplashActive = true; // 设置该变量可以跳过闪屏

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_logo_screen);
        
        Thread splashTimer = new Thread()
        {
        	@Override
        	public void run()
        	{
        		try
        		{
        			long ms = 0;
        			while(m_bSplashActive && ms < m_dwSplashTime)
        			{
        				sleep(100);
        				if(!m_bPaused)
        				{
        					ms += 100;
        				}
        			}
        			startActivity(new Intent(getApplicationContext(),MainScreen.class));
        		}
        		catch(Exception ex)
        		{
        			Log.e("Splash",ex.getMessage());
        		}
        		finally
        		{
        			finish();
        		}
        	}
        };
        splashTimer.start();
    }

	@Override
	protected void onPause()
	{
		super.onPause();
		m_bPaused = true;	//停止闪屏界面的计时
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		m_bPaused = false;	//继续闪屏界面的计时
	}
	
	@Override
	public boolean onKeyDown(int keyCode,KeyEvent event)
	{
		super.onKeyDown(keyCode, event);
		switch(keyCode)
		{
		case KeyEvent.KEYCODE_MENU:
			m_bSplashActive = false;
			break;
		case KeyEvent.KEYCODE_BACK:
			android.os.Process.killProcess(android.os.Process.myPid());
			/*或者System.exit(0);*/
			break;
		default:
			break;
		}
		return true;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.logo_screen, menu);
		return true;
	}

}
