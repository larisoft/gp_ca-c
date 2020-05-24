package com.pa.gp_calc;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class WhatIf extends Activity implements OnItemSelectedListener{
	ArrayList<String>grades;
	ArrayList<String>courses;
	ArrayList<Integer>uls;   
	 
	Semester sems;
	TextView overall, semester;
	int size;
	double totalPoints;
	double totalCredits;
	Button calculate;
	LinearLayout layout;

	int index= 0;

	 @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_what_if);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.what_if_header);
		
		
		grades = new ArrayList<String>();
		courses = new ArrayList<String>();
		uls = new ArrayList<Integer>();
		 layout = (LinearLayout) findViewById(R.id.rowsw);
	load();	
	
	
	sems = (Semester) getIntent().getSerializableExtra("semester");
	
	refill(sems);

	
	}
	
	
	public void info(View v){
		
		new Helper(getResources().getString(R.string.whatIfHelp), WhatIf.this).show();
		
	}


	@Override
	public void onBackPressed(){
		finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}


	private void load(){

		overall = (TextView) findViewById(R.id.overallGP);
		semester = (TextView) findViewById(R.id.jSemester);

		
		

		size = getIntent().getIntExtra("totalSemesters", 1);
		totalCredits= getIntent().getDoubleExtra("totalCredits", 0.00);
		totalPoints = getIntent().getDoubleExtra("totalPoints", 0.00);

		
		
		}
		
	 
	
	private void log(String message){
	
		//Log.d("WhatIf", message);
	}
	
	
	
	private void calculate(){
		 grades.clear();
		 courses.clear();
		 uls.clear();
		RowView row;
		log("count is "+layout.getChildCount());
		for(int i =0; i < layout.getChildCount(); i++){
		row = (RowView) layout.getChildAt(i);

		grades.add(row.getGrade().getSelectedItem().toString());
		log("added grade "+ grades.get(i));
		courses.add(row.getCourse().getText().toString());
		log("added course" + courses.get(i));
		uls.add(row.getUnitLoad().getSelectedItemPosition());
		log("added unit" + uls.get(i));
		}
		 
			
		int tu = sems.Ul();
		log("total unitload is"+ tu);

		int Sem = sems.getSemester();
		
		String user = sems.Name();
		
		int sess = sems.getSession();
		
		int lev=  sems.Level();
		
		  
		sems.setSemester(Sem);
		sems.setLevel(lev);
		sems.setSession(sess);
		sems.setTul(tu);
		sems.setUser(user);
		sems.setGrades(grades);
		sems.setCourses(courses);
		sems.setUls(uls);

		int dummyUnits = 0;
		int dummyPoints = 0;
		for(int i: uls){

			dummyUnits+=i;
		}

		for(String s: grades){

			log("Grade dummy " + s);

		}
		log("sems total units is " + dummyUnits);
		log("sems credit units is " + sems.getTotalPointsInteger());

		String all = String.format("%.2f", Double.valueOf((sems.getTotalPointsInteger()+totalPoints)/ (sems.getTotalUnitsInteger()+totalCredits)));
		overall.setText(all);
		
		all = String.format("%.2f", Double.valueOf(sems.getTotalPointsInteger()/sems.getTotalUnitsInteger()));
		semester.setText(all);
		}
	

	
	private void refill(Semester sem) {

		String[] courses = sem.getCourses().split("----");
		String[] grades = sem.getGrades().split("----");
		String[] units = sem.getUnits().split("----");
		final ArrayList<RowView> rows = new ArrayList<RowView>();
		RowView row;
		for (int i = 0; i < courses.length; i++) {


			row = new RowView(getApplicationContext(), i);
			row.getCourse().setText(courses[i]);

			row.getGrade().setSelection(returnGrade(grades[i]));
			row.getGrade().setOnItemSelectedListener(this);
			int u = 0;
			try {

				u = Integer.parseInt(units[i]);
			} catch (IllegalArgumentException | IllegalStateException e) {

			}

			row.getUnitLoad().setSelection(u);
			row.getUnitLoad().setOnItemSelectedListener(this);
			rows.add(row);

		}

		final Handler h = new Handler();

		h.postDelayed(new Runnable(){
			@Override
			public void run(){

				if(index < rows.size()){
					layout.addView(rows.get(index));
					h.postDelayed(this, 500);
				}
				else{

					calculate();
				}
				index++;
			}

		}, 500);
	}

		 

		
		private int returnGrade(String g){
			int result = 0;
			switch(g){
			
			case "A":
			result = 1;
			
			break;
			
			case "B":
				
			result = 2;
			
			break;
			
			case "C":
			result = 3;
			break;
			case "D":
				result = 4;
				
				 break;
			
			case "E":
				result = 5;
				break;
				
			case "M":
				result = 6;
				break;
			
			case "P":
				result = 7;
				break;
				
				default:
					result = 0;
				break;}

			return result;
	  	
		}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.what_if, menu);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		((TextView) arg0.getChildAt(0)).setTextColor(getApplicationContext().getResources().getColor(R.color.realblack));
calculate();
		
	}
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

}
