package com.pa.gp_calc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.Menu;

import java.io.IOException;
import java.util.Calendar;

public class AlarmActivity extends Activity {

	SoundPool soundPool;
	String time;
	float volume;
	int soundID;
	int ala;
	MediaPlayer mp;
	Runnable run; 
	AlertDialog.Builder alarmDialog;
	AlertDialog showAlarm;
	Runnable repeat;
	Vibrator v;
	
	Handler h;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 
		setContentView(R.layout.activity_alarm);
		
		AudioManager soundMode = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
		v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
		
		soundMode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
		soundMode.setRingerMode(AudioManager.ADJUST_RAISE);
			
	//	log("alarm activity received");
		
		h = new Handler();
		  
		Runnable repeat = new Runnable(){
			@Override
			public void run(){
				blow();
			}
		};
		
		blow();
		
	}
	
	 
	public void blow(){
		try {
		AssetFileDescriptor afd = getAssets().openFd("censor.mp3");
		mp.setDataSource(afd.getFileDescriptor());
		mp.prepare();
		}
		catch(IllegalStateException | IOException es){

		}
		  AudioManager man = (AudioManager) this.getSystemService(AUDIO_SERVICE);
		  volume = man.getStreamMaxVolume(AudioManager.STREAM_ALARM);
		  mp.setVolume(volume, volume);


		Calendar c = Calendar.getInstance();
		 
		 time = c.getTime().toString().split("WAT")[0];
		jukeBox();
		v.vibrate(1000 * 60 * 5);
		showDialog();
		
		
	}
	
	
	public void showDialog(){
	 
	  alarmDialog  = new AlertDialog.Builder(AlarmActivity.this);
	  alarmDialog.setCancelable(false);
	alarmDialog.setTitle("Rise and Shine!");
	alarmDialog.setMessage("The time is : \n" + time);
	alarmDialog.setPositiveButton("10 More Minutes", new DialogInterface.OnClickListener(){
		@Override
		public void onClick(DialogInterface dialog, int id){
			dialog.cancel();
			
			startService(new Intent(getApplicationContext(), AlarmService.class));
			stop();
			
		}
	});
	 
	
	
	
	alarmDialog.setNegativeButton("I m awake", new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int id) {

			dialog.cancel();
			//	AlarmMan man = new AlarmMan();
			//	man.cancelAlarm(getApplicationContext(), getAlarm());
			stop();
		}
	});
	
	
	 showAlarm = alarmDialog.create();
	showAlarm.show(); 
	
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alarm, menu);
		return true;
	}

	public void jukeBox(){
		 
	
	  mp.setLooping(true);
	mp.start();
		
	}
	
	
	public Calendar getAlarm(){
		
		Calendar al = Calendar.getInstance();
		SharedPreferences pref = this.getPreferences(Context.MODE_PRIVATE);
		String alarm = pref.getString("alarm", "none");
		
		int hour = 0;
		int minute = 0;
		
		if(!alarm.equals("none")){
			
			hour = Integer.valueOf(alarm.split(":")[0]);
			minute = Integer.valueOf(alarm.split(":")[1].split(",,")[0] + alarm.split(":")[1].split(",,")[1]);
			al.set(Calendar.HOUR_OF_DAY, hour);
			al.set(Calendar.MINUTE, minute);
		}
		 
		return al;
	}
	
	public void stop(){
		mp.stop();
		mp.release();
		v.cancel();
		if(showAlarm.isShowing()){
			
			showAlarm.dismiss();
		}
		
		Intent i = new Intent(Intent.ACTION_MAIN);
		i.addCategory(Intent.CATEGORY_HOME);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(i);
		finish();
	}

	@Override
	public void onBackPressed(){
		finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}
 
}
