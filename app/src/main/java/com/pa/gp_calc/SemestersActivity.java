package com.pa.gp_calc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class SemestersActivity extends Activity {

	ListView semesters;
	ArrayList<Semester> sems;
	ArrayList<String> names;
	ArrayList<String> detais;
	boolean whatIfMode = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_semesters);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.semesters_header);
		if(getIntent().getBooleanExtra("whatIf", false)){
			whatIfMode = true;
		}
		
		 
		SemesterDatabase db = new SemesterDatabase(getApplicationContext());
		semesters = (ListView) findViewById(R.id.SemestersList);
		names = new ArrayList<String>();
		detais = new ArrayList<String>();
	  sems = db.getAllMembers();
	  
	  
	   processMembers();
     simpleAdapter ad = new simpleAdapter(this, names, detais);
 	
     
     semesters.setAdapter(ad);
     semesters.setOnItemClickListener(new OnItemClickListener(){
		 
 		@Override
 		public void onItemClick(AdapterView<?> arg0, View arg1, final int position, long arg3) {
 			
 			
 			if(whatIfMode){
 				Intent i = new Intent(getApplicationContext(), WhatIf.class);
 				i.putExtra("semester", sems.get(position));
 				i.putExtra("totalCredits", getSpecialTotalCredits(position));
				i.putExtra("totalPoints", getSpecialTotalPoints(position));
 				i.putExtra("totalSemesters", sems.size());
 				startActivity(i);
 			
 			
 			}
 			else{
 			Intent i = new Intent(getApplicationContext(), Collector.class);
 			i.putExtra("edit", true);
 			i.putExtra("semester", sems.get(position));
 			startActivity(i);	
 			finish();
 			}
	} 
     });
     
     
     registerForContextMenu(semesters);
     
	}


	@Override
	public void onBackPressed(){
		finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}

	public double getSpecialTotalPoints(int exclude){
		
	//get all the gp values of each semester excluding the one we are supposed to be playing with

		int totalPoints= 0;


		for(int i = 0; i <sems.size(); i++){
			if(i!=exclude){
			 totalPoints+= sems.get(i).getTotalPointsInteger();
			}
		}
		
 		return totalPoints;
	
	}

	public double getSpecialTotalCredits(int exclude){
		int totalCredits= 0;


		for(int i = 0; i <sems.size(); i++){
			if(i!=exclude){
				totalCredits+= sems.get(i).getTotalUnitsInteger();
			}
		}

		return totalCredits;


	}


	
	public String getSession(int no){
		  
		int answer = 2000+no;
		
		return (answer-2) + "/"+(answer-1);
		
	}
	
	public String getSemester(int no){
		String answer ;
		switch(no){
		case 0:
		
		answer="none";
		
		break;
		
		case 1:
			
		answer = "1st";
		break;
		case 2:
		answer = "2nd";
		
		break;
		
		default:
		answer = "none";
		
		}
		
		return answer;
		
	}
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo){
		
		if(v.getId()==R.id.SemestersList){ 
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo; 
			menu.add(Menu.NONE, 0, 0, "Edit Semester");  
		}
	}
	
	
	
	public void info(View v){
	
		if(!whatIfMode){
	new Helper(getResources().getString(R.string.semestersHelp), SemestersActivity.this).show();
		}
		else{
			new Helper(getResources().getString(R.string.whatIfHelp), SemestersActivity.this).show();
		}
		
		
	}
	
	 
	@Override
	public boolean onContextItemSelected(MenuItem item){
		
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		int index = item.getItemId();
		int clickedItem = info.position;
		toast("Index : " + index + " clicked " + clickedItem + " possible " + info.id);
		
		switch(index){
		
		case 0:
		Intent i = new Intent(getApplicationContext(), Collector.class);
		i.putExtra("edit", true);
		i.putExtra("semester", sems.get(clickedItem));
		startActivity(i);
		break;
		
		case 2:
		
			
		break;
		
		
		
		}
		return true;
	}
	
	
	private void toast(String message){
	Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	
	}
	private void processMembers(){
		 
		 
		log("array is "+names.size() + " while list is " + sems.size());
	for(int i = 0; i <sems.size(); i ++){
		log("adding ");	
	names.add(getSemester(sems.get(i).getSemester()) + " Semester: " +  getSession(sems.get(i).getSession()) + "  Session. "+ " \n Semester GP: " + String.format("%.3f", sems.get(i).getGp()));
	
	detais.add(sems.get(i).getAs() + " A(s), "+ sems.get(i).getBs() + " B(s), "+  sems.get(i).getCs() + " C(s), " + sems.get(i).getDs() + " D(s), " + sems.get(i).getEs()+ " E(s)");
		log("adding ");		
	}
	 
	}
	
	private void log(String message){
		Log.d("SemestersActivity", message);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.semesters, menu);
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
