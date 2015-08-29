package com.RunningWheel.everytour;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import API.UriAPI;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Signup extends Activity {

	final String requestURL = UriAPI.touristSignup;

	Button btnBack, btnSubmit;
	EditText edtUsername, edtPassword, edtPassword_reconfirm, edtEmail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);

		initView();
		
	}

	// 返回null表示错误
	protected String sendSignupRequestToHost(String username, String password,
			String email) {
		HttpPost httpRequest = new HttpPost(requestURL);

		// NameValuePair实现请求参数的封装
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("password", password));
		params.add(new BasicNameValuePair("email", email));

		try {
			// 添加请求参数到请求对象
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

			// 发送请求并等待响应
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpRequest);

			// 若状态码为200则ok
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				// 处理返回的信息
				
				return EntityUtils.toString((HttpEntity) httpResponse
						.getEntity());
				
			} else {
				Log.e("MainScreen:Http Post", "Error Response: "
						+ httpResponse.getStatusLine().toString());
				Toast.makeText(
						getApplicationContext(),
						"Error Response: "
								+ httpResponse.getStatusLine().toString(),
						Toast.LENGTH_LONG).show();
				return null;
			}
		} catch (ClientProtocolException e) {
			// Log.e("MainScreen:Http Post", e.getMessage().toString());
			e.printStackTrace();
		} catch (IOException e) {
			// Log.e("MainScreen:Http Post", e.getMessage().toString());
			e.printStackTrace();
		} catch (Exception e) {
			// Log.e("MainScreen:Http Post", e.getMessage().toString());
			e.printStackTrace();
		}
		return null;
	}

	private void initView() {

		edtUsername = (EditText) findViewById(R.id.signup_username);
		edtPassword = (EditText) findViewById(R.id.signup_password);
		edtPassword_reconfirm = (EditText) findViewById(R.id.signup_password_reconfirm);
		edtEmail = (EditText) findViewById(R.id.signup_email);

		btnBack = (Button) findViewById(R.id.signup_back);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				finish();

			}
		});

		btnSubmit = (Button) findViewById(R.id.signup_submit);
		btnSubmit.setOnClickListener(new OnClickListener() {

			private String password;
			private String username;
			private String passwordReconfirm;
			private String email;
			String result;

			Handler handler = new Handler()
			{
				 @Override
				 public void handleMessage(Message msg)
				 {
					 switch(msg.what){
					 case 0:
						 Toast.makeText(getApplicationContext(), msg.getData().getCharSequence("httpResponse"),
									Toast.LENGTH_LONG).show();
					 }
				 }
			};
			
			@Override
			public void onClick(View v) {

				getSignupInfo();

				if (password.compareTo(passwordReconfirm) != 0) {
					Toast.makeText(getApplicationContext(), "请两次输入一致 的密码",
							Toast.LENGTH_LONG).show();
					return;
				}

				if (isEmail(email) == false) {
					Toast.makeText(getApplicationContext(), "请输入正确格式的邮箱",
							Toast.LENGTH_LONG).show();
				}

				Thread sendRequestToHost = new Thread(new Runnable() {

					@Override
					public void run() {

						result = sendSignupRequestToHost(username, password,
								email);
						Message msg = new Message();
						msg.what = 0;
						Bundle data = new Bundle();
						data.putString("httpResponse", result);
						msg.setData(data);
						handler.sendMessage(msg);
					}
				});
				sendRequestToHost.start();
				
			}

			private boolean isEmail(String strEmail) {

				String strPattern = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

				Pattern p = Pattern.compile(strPattern);
				Matcher m = p.matcher(strEmail);
				return m.matches();

			}

			private void getSignupInfo() {

				username = edtUsername.getText().toString();
				password = edtPassword.getText().toString();
				passwordReconfirm = edtPassword_reconfirm.getText().toString();
				email = edtEmail.getText().toString();

			}
		});
	}
}
