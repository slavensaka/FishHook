package com.fish.net;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;


public class MainActivity extends FragmentActivity {

	ImageView icon;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		
		icon = (ImageView) findViewById(R.id.icona);
		icon.setImageResource(R.drawable.prazno1);
		//Welcome message
		Thread WelcomeMessage = new Thread(){
			public void run(){
				try{
					sleep(500);
					startActivity(new Intent(getApplicationContext(),WelcomePage.class));
				} catch(InterruptedException e){
					e.printStackTrace();
				} finally {
					finish();
				}
			}
		}; 
		WelcomeMessage.start();
		
		
		
	}

	
}