package com.pa.gp_calc;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity implements   OnClickListener{

	Button semester, cumulative, help, about, alarm;
 	TextView school;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_main);
		 getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.header);
		 
		 if(savedInstanceState == null){
			 
			 openStart();
		 }
		load();
		if(firstTime()){
			
		///do firsttime things();
		}
	}
	
	public void info(View v){
	
	new Helper(getResources().getString(R.string.mainHelp), MainActivity.this).show();
	
		
	}
	
	public void settings(View v){
		
		Intent i = new Intent(getApplicationContext(), Settings.class);
		startActivity(i);
		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
	}
	private void openStart(){
		
		Intent i = new Intent(getApplicationContext(), StartUp.class);
		startActivity(i);

	}
	private void toast(String message){
		
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
	}
	
	private boolean firstTime(){
		 
	SharedPreferences prefs = getSharedPreferences("settings", Context.MODE_PRIVATE);
	
boolean	answer = false;
	if(!prefs.getBoolean("loaded", false)){
		
		SharedPreferences.Editor editPrefs = prefs.edit();
		
		editPrefs.putString("password", "abc");
		editPrefs.putBoolean("loaded", true);
		editPrefs.commit();
		answer = true;
		
	}
	
	return answer;
	}
	
	
	private void load(){ 
		semester = (Button) findViewById(R.id.once);
		cumulative = (Button) findViewById(R.id.cumulative);
	
		about = (Button) findViewById(R.id.advert); 
		semester.setOnClickListener(this);
		cumulative.setOnClickListener(this);

		alarm = (Button) findViewById(R.id.alarm);
		
		alarm.setOnClickListener(this);
		about.setOnClickListener(this);


	 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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
	
	private void openSemester(){
	
	 
	Intent i = new Intent(getApplicationContext(), Collector.class);
	i.putExtra("username", "  ");
	i.putExtra("back", false);
	startActivity(i);

		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
	 
	 
		
	}
 
	@Override
	public void onClick(View arg0) {
		 
		switch(arg0.getId()){
		
		case R.id.once:
			openSemester();
			break;
			
		case R.id.cumulative:
			Intent i = new Intent(getApplicationContext(), Cumulative.class);
			startActivity(i);

			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
			break;
			
		case R.id.help:
			
			break;
			
		case R.id.advert:
			showAbout();
			break;
			
			
		case R.id.alarm:
			Intent i2 = new Intent(getApplicationContext(), Timer.class);
			startActivity(i2);

			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
			break;
			
		}
		
	}

	private void showAbout(){
		AlertDialog.Builder build  = new AlertDialog.Builder(MainActivity.this);
		LayoutInflater inf = LayoutInflater.from(MainActivity.this);
		View v = inf.inflate(R.layout.about, null);
		build.setView(v);
		build.setPositiveButton("Okay", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int id){
				dialog.cancel();
			}
		});
		AlertDialog ad = build.create();
		ad.show();
	}


	public void facebook_like(View v){

		Intent i =new Intent(Intent.ACTION_VIEW);
		Uri ur = Uri.parse("https://facebook.com/larisoftcomputerhacks/");
		i.setData(ur);
		startActivityForResult(i, 1);
	}

	 
}
