package com.pa.gp_calc;/*package com.larry.universitiesgpcalculator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.widget.Toast;
/*
public class GPService extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
		
	}

	public class getAdvert extends AsyncTask<String, String, String>{
		@Override
		protected String doInBackground(String... arg0) {
			Bitmap bit = null;
			String result = "Nothing found";
			try {
				bit = BitmapFactory.decodeStream((InputStream) new URL("http://unecgeek.com/gp/advert").getContent());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			if(bit!=null){
			result = saveBitmap(bit);
			}
			return result;
		}
		
		@Override
		protected void onPreExecute(){
			toast("started");
		}
	
		@Override
		protected void onPostExecute(String answer){
			
			toast(answer);
			
		}
	
	}
	
	
	private String saveBitmap(Bitmap b){
		String result = "Not Saved";
		String output = Environment.getExternalStorageDirectory() +  "\\gpService";
		
		File folder = new File(output);
		
		
		FileOutputStream streamOut = null;
		try{
		if(!folder.exists()){
			folder.mkdirs();
		}
		streamOut = new FileOutputStream(new File(folder, "bitmap.png"));
		b.compress(CompressFormat.PNG, 100, streamOut);
		result = "Saved successfully";
		}
		catch(IOException e){
			
			 
		}
		
		finally{
			
			if(streamOut!=null){
				try{
				streamOut.close();
				}
				catch(IOException e){
					
				}
			}
		}
	
	
	return result;
	}
	
	private String getPhoneNo(){
	
	String phoneNo = null;
	TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
	
	try{
	 phoneNo = tm.getLine1Number();
	}
	catch(Exception e){
		
	}
	toast(phoneNo);
	return phoneNo;
	}
	
	
	@Override
	public void onCreate(){
		  new getAdvert().execute();
	}
	
	private void toast(String message){
		
	Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
	
	
	@Override
	public void onDestroy(){
		
	}


	
}
*/