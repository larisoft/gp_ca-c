package com.pa.gp_calc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Cumulative extends Activity {
 
ArrayList<Semester> sems;
TextView gp;
TextView details; 
Button semesters;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_cumulative);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.cumulative_header);
		if(getIntent().getBooleanExtra("requirePassword", true)){
			
			
			AlertDialog.Builder password = new AlertDialog.Builder(Cumulative.this);
			LayoutInflater inf = LayoutInflater.from(Cumulative.this);
			View v = inf.inflate(R.layout.password, null);
			final EditText value = (EditText) v.findViewById(R.id.userpassword);
		 
			password.setView(v);
			final SharedPreferences prefs = getSharedPreferences("settings", Context.MODE_PRIVATE);
		 
			 final String oldP = prefs.getString("password", "abc");
			 
			 Helper help = new Helper("default password is " + oldP, Cumulative.this);
			 help.Welcomer();
			 password.setCancelable(false);
			password.setPositiveButton("Continue", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int id){
				
				if(value.getText().toString().equalsIgnoreCase(oldP)){
				
					dialog.cancel();
						load();	 
				}
				
				else{
					
					dialog.cancel();
					toast("incorrect password");
					finish();
					overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
				}
				
			
				
			}
			});
			
			password.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int id){
					 
						dialog.cancel(); 
						finish();
					overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
					
				}
				});
				
			AlertDialog dial = password.create();
			dial.show();
	
		 }
		 else{
			 load();	 
	}
		
	}
	 
	
	double fake = 0;
	public void suspense(final double gpa){
		log("real GP is " + gpa);
		fake = gpa-5.0;
	
	final Handler h = new Handler();
	
	Runnable run  = new Runnable(){
		@Override
		public void run(){
		if(fake < gpa){	
		fake+=0.1;
		gp.setText(String.format("%.3f", fake));
		h.postDelayed(this, 50);
		}
		
		else{
			 
			
			h.removeCallbacks(this);
			gp.setText(String.format("%.3f", gpa));
			details.setText("You are in " +getAdvice(gpa) + " after " + sems.size() + " semesters(s) "); 
		}
	}
		
	};
	
	h.post(run);
	}
	
	
	public void info(View v){
	
	new Helper(getResources().getString(R.string.cumulativeHelp), Cumulative.this).show();
	}
	
	public String getAdvice(double g){
		String advice  = null;
		if(g> 4.5 ){ 
			advice = "First Class";
			gp.setTextColor(getResources().getColor(R.color.sgreen)); 
		}
		
		else if (g> 3.5){
			
			advice = "Second Class Upper";
		}
		
		
		else if(g> 2.5){
			
			advice = "Second Class Lower";
			gp.setTextColor(getResources().getColor(R.color.red));
		}
		
		else if(g> 1.5){
			advice = "Third Class";
			gp.setTextColor(getResources().getColor(R.color.red));
		}
		
		else{  
			advice = "Pass";
			gp.setTextColor(getResources().getColor(R.color.red));
		}
		
		return advice;
			
		}
	
	private void toast(String message){
		
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	}
	 
	public void load(){ 
	 retrieveSems();
	//log(sems.get(0).Name + " semester gotten ");  

	gp = (TextView) findViewById(R.id.currentGP);
		details = (TextView) findViewById(R.id.explain);
	if(sems.size()<1){ 
		suspense(0.00);
		details.setText(" No semesters entered yet. Click the New Semester Button to add cumulative Semesters ");
	} 
	else{
	suspense(getGP());
	details.setText("" + sems.size() + " semesters(s) "); 
	 
	
	}

	Button whatIf = (Button) findViewById(R.id.whatIf);
	
	whatIf.setOnClickListener(new View.OnClickListener(){
		@Override
		public void onClick(View v){ 
			new Helper( "The new WHAT IF feature lets you see what your gp would be if you had scored higher or lower. \n\nSimply select a semester, once it opens in the What if mode, change the grades, and  the hypothetical   Overall GP and semester GP adjust right away.", Cumulative.this).Welcomer();
		Intent i = new Intent(getApplicationContext(), SemestersActivity.class);
		i.putExtra("whatIf", true);
		startActivity(i);
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
		}
	});
	
	Button newSem = (Button) findViewById(R.id.newSemester);
	semesters = (Button) findViewById(R.id.view);
	semesters.setOnClickListener(new View.OnClickListener(){
		@Override
		public void onClick(View v){
		Intent i = new Intent(getApplicationContext(), SemestersActivity.class);
		startActivity(i);
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
		}
	});
	newSem.setOnClickListener(new View.OnClickListener() {
								  @Override
								  public void onClick(View v) {


									  Intent i = new Intent(getApplicationContext(), Collector.class);
									  i.putExtra("back", false);
									  i.putExtra("cumulative", true);
									  i.putExtra("semesters", sems);
									  startActivity(i);
									  finish();
									  overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
								  }
							  }
	);
	}



	@Override
	public void onBackPressed(){
		finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}
	
	private double  getGP(){
	double p = 0;
		double totalGrades = 0;
		double totalCredits = 0;

		for(int i  = 0;  i < sems.size(); i++){
			totalGrades+=sems.get(i).getTotalPointsInteger();
			totalCredits+= sems.get(i).getTotalUnitsInteger();
		}
		log(totalGrades+"/"+totalCredits);
		return (totalGrades/totalCredits);
	}

	private void retrieveSems(){
		
		SemesterDatabase db = new SemesterDatabase(getApplicationContext());
		sems = db.getAllMembers();
		log("size of list is" + sems.size());
		 
		 
		
	}


	public void clear(View v){

		AlertDialog.Builder build = new AlertDialog.Builder(Cumulative.this);

		LayoutInflater inf = LayoutInflater.from(getApplicationContext());
		View dv = inf.inflate(R.layout.helper, null);
		TextView text = (TextView) dv.findViewById(R.id.helpText);
		text.setText("Are you sure you want to remove all semesters saved in this app? This can not be undone!");
		build.setView(dv);
		build.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {

				new SemesterDatabase(getApplicationContext()).clearData();
				finish();
				overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
			}

		});

		build.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				dialogInterface.cancel();
			}
		});
		AlertDialog dial = build.create();
		dial.show();
	}
	private void log(String message){
		
		Log.d("Cumulative", message);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cumulative, menu);
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
}

 