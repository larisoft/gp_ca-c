package com.pa.gp_calc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SpokesMan extends Activity {

	TextView GP;
	TextView advice;
	Button  saveData;
	Button send;
	Button exit;
	
	Semester sem;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.talker);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.header_talker);
	
		 getValue();
		 load();
	}
	
	public void toast(String message){
		
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	}

	private void load(){
	advice = (TextView) findViewById(R.id.detail);	
	advice.setText("calculating...");
	GP = (TextView) findViewById(R.id.GP);
	saveData = (Button) findViewById(R.id.saveToCumulative);
	send = (Button) findViewById(R.id.sendGP);
	
	send.setOnClickListener(new View.OnClickListener(){
		@Override
		public void onClick(View v){
			
			Send(sem);
		}
	});
	saveData.setOnClickListener(new  View.OnClickListener(){
		@Override
		public void onClick(View v){
			
			LayoutInflater inf = LayoutInflater.from(SpokesMan.this);
			View view = inf.inflate(R.layout.password, null);
			final EditText pwdq = (EditText) view.findViewById(R.id.userpassword);
			
			AlertDialog.Builder build = new AlertDialog.Builder(SpokesMan.this);
			build.setView(view);
			build.setPositiveButton("OK", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int id){
				SharedPreferences prefs = getSharedPreferences("settings", Context.MODE_PRIVATE);
				
				String oldPwd = prefs.getString("password", "unn");
				dialog.cancel();
				if(pwdq.getText().toString().equals(oldPwd)){
				sem.save(getApplicationContext());
				Intent i = new Intent(SpokesMan.this, Cumulative.class);
				i.putExtra("requirePassword", false);
				startActivity(i);
			
			}
				else{
					toast("Incorrect Password");
				}
				
			}});
			
			build.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
				
				@Override
				public void onClick(DialogInterface dialog, int id){
					
					dialog.cancel();
				}
			});
			
			AlertDialog dialog = build.create();
			dialog.show();
			
		}
	});
	
	exit = (Button) findViewById(R.id.exit);
	exit.setOnClickListener(new View.OnClickListener(){
	@Override
	public void onClick(View v){
		
		home();
	}
	});
		
	}
	
	public void info(View v){
		
	new Helper(getResources().getString(R.string.spokesmanhelp), SpokesMan.this).show();
	
	}
	private void getValue(){
		
	Intent i = getIntent();
	  sem = (Semester) i.getSerializableExtra("semester");
	  suspense(sem.getGp());
	//GP.setText(gp);
	}
	
	double fake = 0;
	public void suspense(final double gp){
	
		fake = gp-4.0;
	
	final Handler h = new Handler();
	
	Runnable run  = new Runnable(){
		@Override
		public void run(){
		if(fake < gp){	
		fake+=0.1;
		GP.setText(String.format("%.3f", fake));
		h.postDelayed(this, 100);
		}
		
		else{
			
			
			
			
			
			h.removeCallbacks(this);
			GP.setText(String.format("%.3f", gp));
			advice.setText(getAdvice(gp)); 
		}
	}
		
	};
	
	h.post(run);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.spokes_man, menu);
		return true;
	}

	public String getAdvice(double g){
	String advice  = null;
	if(g> 4.5 ){
		
		advice = getResources().getString(R.string.firstClass);
		GP.setTextColor(getResources().getColor(R.color.sgreen));
		
	}
	
	else if (g> 3.5){
		
		advice = getResources().getString(R.string.SecondClassUpper);
		GP.setTextColor(getResources().getColor(R.color.sgreen));
	}
	
	
	else if(g> 2.4){
		
		advice = getResources().getString(R.string.SecondClassLower);
		GP.setTextColor(getResources().getColor(R.color.red));
	}
	
	else if(g> 1.5){
		advice = getResources().getString(R.string.thirdClass);
		GP.setTextColor(getResources().getColor(R.color.red));
	}
	
	else{
		
		advice = getResources().getString(R.string.pass);
		GP.setTextColor(getResources().getColor(R.color.red));
	}
	
	return advice;
		
	}
	
	private void Send(Semester sem){
		
		StringBuilder build = new StringBuilder();
		build.append(" Total UnitLoad \n\n" +sem.Ul());
		build.append("GP: " +sem.getGp() + " For "+ sem.getSession() + " session");
		build.append("\n\nDETAILS \n\n");
		 
		for(int i = 0; i <15; i++){
			try {
				build.append(sem.getCourses().split("----")[i] +

						" " + sem.getGrades().split("----")[i] + "\n");

			}
			catch(Exception es){

			}
		
		}
		 
		Intent se = new Intent(Intent.ACTION_SEND);
		se.setType("text/plain");
		se.putExtra(Intent.EXTRA_TEXT, build.toString());
		startActivityForResult(se, 100);
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

	
	@Override
	public void onBackPressed(){
	
	AlertDialog.Builder build = new AlertDialog.Builder(SpokesMan.this);
	LayoutInflater inf = LayoutInflater.from(getApplicationContext());
	View v = inf.inflate(R.layout.helper, null);
	TextView txt = (TextView) v.findViewById(R.id.helpText);
	txt.setText("Click BACK to go back to the input section and edit something, or HOME to go to First Screen");
	build.setView(v); 
	build.setPositiveButton("Back", new DialogInterface.OnClickListener(){
	@Override
	public void onClick(DialogInterface dialog, int id){
		dialog.cancel();
		Intent i = new Intent(getApplicationContext(), Collector.class);
	i.putExtra("back", true);
	i.putExtra("semester", sem);
	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	startActivity(i);
	finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}
	});
	
	
	build.setNegativeButton("Home", new DialogInterface.OnClickListener(){
		@Override
		public void onClick(DialogInterface dialog, int id){
			
			home();
		}
		});
	
 
AlertDialog dialog = build.create();
dialog.show();
} 
	
	public void home(){  
	finish();
	}
}