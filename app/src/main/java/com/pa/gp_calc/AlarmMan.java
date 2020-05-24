package com.pa.gp_calc;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PowerManager;
import android.widget.Toast;


public class AlarmMan extends BroadcastReceiver{
	 
	 
	Context context;
	BroadcastReceiver br;
 
		  
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			context = arg0;
			SharedPreferences prf = arg0.getSharedPreferences("settings", Context.MODE_PRIVATE);
			int weekend = prf.getInt("weekend", 0);
			Calendar c = Calendar.getInstance();
		//	Toast.makeText(arg0, "Received alarm", Toast.LENGTH_SHORT).show();
			
		//	toast("today is " + c.get(Calendar.DAY_OF_WEEK));
			if(weekend > 0){
			
			if(c.get(Calendar.DAY_OF_WEEK) == 7 || c.get(Calendar.DAY_OF_WEEK)==1){


				 //Toast.makeText(arg0, "Happy Weekend acade service" + Calendar.SUNDAY + "Happy Weekend",  Toast.LENGTH_LONG).show();
			
				}
			else{
			 
			PowerManager pm = (PowerManager) arg0.getSystemService(Context.POWER_SERVICE);
			PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "com.larysoft.casebook.AlarmMan");
			wl.acquire(); 
			SharedPreferences prf1 = arg0.getSharedPreferences("switch", Context.MODE_PRIVATE); 
			int mo = prf1.getInt("alarm", 1);
			
			if(mo>0){
			Intent i = new Intent(arg0, AlarmActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
			arg0.startActivity(i);
		
			wl.release();
		}
			else{
				
				log("alarm will not ring");
			}
			}
			
			}
			
			else{
				PowerManager pm = (PowerManager) arg0.getSystemService(Context.POWER_SERVICE);
				PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "com.larysoft.casebook.AlarmMan");
				wl.acquire(); 
				SharedPreferences prf1 = arg0.getSharedPreferences("switch", Context.MODE_PRIVATE); 
				int mo = prf1.getInt("alarm", 1);
				
				if(mo>0){
				Intent i = new Intent(arg0, AlarmActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
				arg0.startActivity(i);
			
				wl.release();
				
			}
			}
		}
		 
		 
	
	 
		private void toast(String message){
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
		}

	 
	
	public void setAlarm(Context context, Calendar c){
		
		Intent intent = new Intent(context, AlarmMan.class); 
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		long now = System.currentTimeMillis();
		
		long time = c.getTimeInMillis();
		if(now > c.getTimeInMillis()){
		
		 	time = c.getTimeInMillis() + AlarmManager.INTERVAL_DAY;
		
		//	c.add(c.HOUR_OF_DAY, 1);
	 	//	am.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis() * 2, 1000 * 60 * 60 * 24, pi);
		}
		
		else{
			//am.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), 1000 * 60 * 60 * 24, pi);
		//	log("this alarm is current");
		}
		
		am.setRepeating(AlarmManager.RTC_WAKEUP, time, AlarmManager.INTERVAL_DAY, pi);
	//	Toast.makeText(context, "ALARM SET"+ c.getTime(), Toast.LENGTH_SHORT).show();
		//log("alarm set "  + c.getTime());
	}
	
	public void log(String me){
		
	//	Log.d("Alarm man", me);
	}
	
	
	public void cancelAlarm(Context context, Calendar c){
		
		Intent intent = new Intent(context, AlarmMan.class);
		PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		am.cancel(sender);  
	//	log("alarm cancelled"  + c.getTime());
		 
	} 

	 
}
