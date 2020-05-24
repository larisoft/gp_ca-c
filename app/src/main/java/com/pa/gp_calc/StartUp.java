package com.pa.gp_calc;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class StartUp extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.start);

		Handler h = new Handler();
		
		Runnable change = new Runnable(){
			@Override
			public void run(){
			finish();
			}
		};
		
		h.postDelayed(change, 3000);
		 
	}
	


	 
}
	 