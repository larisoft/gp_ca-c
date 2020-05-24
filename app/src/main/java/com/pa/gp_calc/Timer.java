package com.pa.gp_calc;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class Timer extends  Activity {
 
EditText alarm;
Button save, discard;
AlarmManager alaram;
 Intent intent;
 BroadcastReceiver br;
 PendingIntent pi; 
 Spinner am;
Calendar c;
CheckBox weekend;
AlarmMan ala;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_timer);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.alarm_header);
		weekend = (CheckBox) findViewById(R.id.exclude);
		setUp();
		
		
		
		
		weekend.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				 
				SharedPreferences prefs = getSharedPreferences("settings", Context.MODE_PRIVATE);
				SharedPreferences.Editor editPref = prefs.edit();
				
				if(arg1){
			
				editPref.putInt("weekend", 1);
				toast("Got it! won't disturb ur weekend!");
			
				}
				
				else{
					
					editPref.putInt("weekend", 0);
				 
				}
			
				editPref.commit();
			 
			}
			
		});
		
		
		save.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				 
			
			AlertDialog.Builder sureDialog = new AlertDialog.Builder(Timer.this);
			
			sureDialog.setMessage("Are you sure you want to set this alarm?");
			
			sureDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int id){
					dialog.cancel();
					 
					 
				 					
					if( verify(alarm.getText().toString())){
						
						
					if(!alarm.getText().toString().equals("0:00")){
				//	c.setTimeInMillis(System.currentTimeMillis());
					int mode =  am.getSelectedItemPosition();
			 
					
					
					int hour =   (Integer.valueOf(alarm.getText().toString().split(":")[0]));
					int minute = (Integer.valueOf(alarm.getText().toString().split(":")[1]));
					
				  
					c.set(Calendar.HOUR, hour);
				
					log("Hour set " + hour);
					log(" mode is" + mode);
					c.set(Calendar.MINUTE, minute);
					if(mode == 0){
						
					c.set(Calendar.AM_PM, Calendar.AM);
						mode = 0;
						log("mode is am" + mode);
					}
					
					else{
					 
					c.set(Calendar.AM_PM, Calendar.PM);
				 
					log("mode is pm" + mode);
					}
					
					 
					 saver("alarm", alarm.getText().toString() + "="+ mode);
					 
					
			 		ala.setAlarm(Timer.this, c);
					 log("alarm set");
					
					finish();
					}
				}
				
				else{
					
					showError("Alarm", "5:00");
				}
					 
				}});
			
			AlertDialog showSure = sureDialog.create();
			showSure.show();
			
			}
		});
		
		
		 
		
		
		discard.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
		 	
			 
				alarm.setText("--:--");

				SharedPreferences prefs = getSharedPreferences("settings", Context.MODE_PRIVATE);
				
				SharedPreferences.Editor editPrefs = prefs.edit();
				
				editPrefs.putInt("alarm", 0);
				
				editPrefs.commit();
 
			}
		});
	}
	
	 
	
	public void info(View v){
	
		
	
	}
	private void toast(String message){
		
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	}
	
	public boolean verify(String ver){
		boolean tell = true;
	
		try{
	if(ver.split(":")[0].length()>2){
		tell =false;
		
	}
	
	 
	
	if(ver.split(":")[0].length() < 0){
		
		tell = false;
	}
	
	 
	if(ver.split(":")[1].length() < 0){
		
		tell = false;
	}
	
	if(ver.split(":")[1].length() > 2){
		
		tell  = false;
	}
		}
		catch(IndexOutOfBoundsException e){
			
			tell = true;
		}
		
	try{
		int in = 0;
		 in = Integer.valueOf(ver.split(":")[0]);
		 if(in> 12){
			 
			 tell = false;
		 }
		 
		 
		  in = Integer.valueOf(ver.split(":")[1]);
		  
		  if(in > 59){
			  
			  tell = false;
		  }
	 
	}

	catch(NumberFormatException | IndexOutOfBoundsException e){
		
		tell = false;
	}
	
	
	return tell;
	}
	
	
	public void showError(String string, String example){
		
	AlertDialog.Builder build = new AlertDialog.Builder(Timer.this);
	build.setMessage("The format of the " + string + " you entered is invalid. \n The correct example is "+example);
	build.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
		@Override
		public void onClick(DialogInterface dialog, int id){
			dialog.cancel();
		}
	});
	
	AlertDialog showBuild = build.create();
	showBuild.show();
	
	
	}
		  
	public void setUp(){ 
		alarm = (EditText) findViewById(R.id.alarmHour);
		save = (Button) findViewById(R.id.saveAlarm);
		discard  = (Button) findViewById(R.id.cancelAlarm); 
		alaram = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE); 
		c = Calendar.getInstance();
		intent = new Intent(getApplicationContext(), Timer.class); 
		am = (Spinner)findViewById(R.id.AM);
		ala = new AlarmMan();
		
		SharedPreferences prefs = getSharedPreferences("settings", Context.MODE_PRIVATE);
		
		int i = prefs.getInt("weekend", 0);
		
		if(i>0){
			
			weekend.setChecked(true);
		}
		 
		else{
			
			weekend.setChecked(false);
		}
		 
		String alarmHour = requit("alarm").split("=")[0];
		
		int alarmMode = Integer.valueOf(requit("alarm").split("=")[1]);
		
		  
	  
		alarm.setText((alarmHour)); 
		am.setSelection(alarmMode); 
	 
	   
		}
 
 
	public String saver(String type, String addition){ 
	log(type+" "+addition);
	SharedPreferences pref = this.getSharedPreferences("settings", Context.MODE_PRIVATE);
	
	String already = pref.getString(type, "none");
	SharedPreferences.Editor edit = pref.edit(); 
	edit.putString(type, addition);
	edit.commit();
	
	return already;
	}
	
	
	
	
	public String requit(String type){
		
		log("Type is " + type);
		String returnThis = "0:0:0";
		SharedPreferences pref = this.getSharedPreferences("settings", Context.MODE_PRIVATE);
	  
		 
			returnThis = pref.getString("alarm", "0:00=0");
		
		  
		log(returnThis);
		return returnThis;
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
 
	public void log(String mes){
		
		//Log.d("Timer", mes);
	}

}
