package com.pa.gp_calc;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.widget.Toast;

public class AlarmService extends Service {

	 
	@Override
	public void onCreate(){
	super.onCreate();
	
	Toast.makeText(AlarmService.this, "Alarm will ring again in 10 Minutes", Toast.LENGTH_SHORT).show();
	
	Handler h = new Handler();
	
	Runnable run = new Runnable(){
		@Override 
		public void run(){
		PowerManager pm  = (PowerManager) AlarmService.this.getSystemService(Context.POWER_SERVICE);
		PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "com.larysoft.casebook.AlarmService");
		wl.acquire(); 
		Intent i = new Intent(AlarmService.this, AlarmActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
		startActivity(i);
		//  }
		
		wl.release();
		AlarmService.this.stopSelf();
		
	}};
	
	h.postDelayed(run, 1000 * 60 *10);
	}
	
	
	
	@Override
	public void onDestroy(){
	//	Log.d("Executed", " Stopped" );
	super.onDestroy();	
	
	}
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
