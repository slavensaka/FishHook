package com.fish.net;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		
		//Welcome message
		Thread WelcomeMessage = new Thread(){
			public void run(){
				try{
					sleep(1500);
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