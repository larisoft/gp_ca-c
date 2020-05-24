package com.pa.gp_calc;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Settings extends Activity {
	EditText p1;
	EditText p3;
	EditText p2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_CUSTOM_TITLE);
		
		setContentView(R.layout.activity_settings);
		
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.settingsh);
		
		p1  = (EditText) findViewById(R.id.firstPassword);
		p2 = (EditText) findViewById(R.id.secondPassword);
		p3 = (EditText) findViewById(R.id.secondPassword2);
		
		Button save = (Button) findViewById(R.id.savePassword);
		
		save.setOnClickListener(new OnClickListener(){
		
			@Override
			public void onClick(View v){
				
			if(verify()){
			
			SharedPreferences prefs = getSharedPreferences("settings", Context.MODE_PRIVATE);
			SharedPreferences.Editor editPrefs = prefs.edit();
			
			editPrefs.putString("password", p2.getText().toString().trim());
			editPrefs.commit();
			toast("password changed successfully");
			finish();
			
				
			} 
				
			}
		});
 
	}

	private void toast(String message){
		
		Toast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT).show();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

	
	private boolean verify(){
	boolean answer =true;
	SharedPreferences prefs = getSharedPreferences("settings", Context.MODE_PRIVATE);
	String password = prefs.getString("password", "unn");

	
	if(!p2.getText().toString().trim().equals(p3.getText().toString().trim())){
		
		toast("the new password and its confirmation do not match");
		answer =false;
	}
	
	else if(!p1.getText().toString().trim().equals(password)){
		toast("the old password you entered is incorrect");
		
		answer = false;
		
	}
	
	return answer;
	}


	@Override
	public void onBackPressed(){
		finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	 
}
