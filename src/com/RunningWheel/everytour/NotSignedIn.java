package com.RunningWheel.everytour;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class NotSignedIn extends Activity {
	
	Button signin,signup;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.not_signed_in);
		
		signin = (Button)findViewById(R.id.not_signed_in_signin);
		signin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//startActivity(new Intent(getApplicationContext(),Signin.class));
			}
		});
		
		signup = (Button)findViewById(R.id.not_signed_in_signup);
		signup.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(),Signup.class));
			}
		});
	}
}
