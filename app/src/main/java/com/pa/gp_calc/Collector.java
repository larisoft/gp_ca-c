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
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Collector extends Activity {
	
	ArrayList<String>grades;
	ArrayList<String>courses;
	ArrayList<Integer>uls;   

	Spinner tul;
	Spinner session;
	Spinner level;
	Spinner Seme;
	TextView name;
	boolean cumulativeMode = false;
	boolean editMode = false;
	Button calculate;

	List<Spinner> allUL = new ArrayList<Spinner>();
	List<EditText> allCourses = new ArrayList<EditText>();
	List<Spinner> allGrades = new ArrayList<Spinner>();
	LinearLayout main_container;

	//we are adding new rows individually for performance reasons. This determines the interval at which this is done
	int add_interval = 500;

	//how many courses should be added automatically when activity loads
	int course_limit = 6;

	//how many rows have been automatically added
	int added_count = 0;

	//last added row
	int index = 1;
	int c = 0;
	LinearLayout layout;
	ImageView add_row, remove_row;
	boolean backMode = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_collector);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.header_collector);
	  
		grades = new ArrayList<String>();
		courses = new ArrayList<String>();
		uls = new ArrayList<Integer>();
		main_container = (LinearLayout) findViewById(R.id.main_container);
		layout = (LinearLayout) findViewById(R.id.rows);
		load();
	 
	if(getIntent().getBooleanExtra("back", false)){ 
		Semester backSem = (Semester) getIntent().getSerializableExtra("semester");
		backMode = true;
		refill(backSem);
	}
		
	else if(getIntent().getBooleanExtra("cumulative", false)){
		cumulativeMode = true;
		calculate.setText(" Add This Semester");
	}
	
	else if(getIntent().getBooleanExtra("edit", false)){
		Semester editSem = (Semester) getIntent().getSerializableExtra("semester"); 
		refill(editSem);
		editMode= true;
		calculate.setText("Save");
	}

		//set up automatic filling
		if(!editMode && !backMode) {
			final Handler h = new Handler();
			h.postDelayed(new Runnable() {
				@Override
				public void run() {

					add(add_row);
					added_count++;
					if (added_count < course_limit) {
						h.postDelayed(this, add_interval);
					}
				}
			}, add_interval);
		}
 
	}
	
	public void info(View v){
		
	new Helper(getResources().getString(R.string.collectorHelp), Collector.this).show();
	}
	
	private void refill(Semester sem){
	final ArrayList<RowView> rows = new ArrayList<RowView>();
	String[] courses = sem.getCourses().split("----");
	String[] grades = sem.getGrades().split("----");
		String[] units = sem.getUnits().split("----");

		RowView row;
	for(int i = 0; i < courses.length; i++){
		row = new RowView(getApplicationContext(), i);
		row.getCourse().setText(courses[i]);
		row.getGrade().setSelection(returnGrade(grades[i]));

	int u = 0;
	try{
    
	u = Integer.parseInt(units[i]);
	}
	catch(IllegalArgumentException | IllegalStateException e){
		
	}
	
	row.getUnitLoad().setSelection(u);

		rows.add(row);
	}

	tul.setSelection(sem.Ul());
	level.setSelection(sem.Level());
	Seme.setSelection(sem.getSemester());
	session.setSelection(sem.getSession());


		final Handler h2 = new Handler();

		h2.postDelayed(new Runnable(){
			@Override
			public void run(){
				if(c < rows.size()) {
					layout.addView(rows.get(c));
					c++;
					h2.postDelayed(this, 500);
				}

			}
		}, 500);

	}
	
	private int returnGrade(String g){
		log("Searching for " + g);
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
			
		case "F":
			result = 6;
			break;

			case "M":
			result = 7;
			break;
		
		case "P":
			result = 8;
			break;
			
			default:
				result = 0;
			break;}

		return result;
  	
	}




	//retrieve a row with course, unitload, and grade
	private RowView get_row(int index){
		return new RowView(getApplicationContext(), index);

	}



	private void load(){
 	tul = (Spinner) findViewById(R.id.totalUnitLoad);	
	level = (Spinner) findViewById(R.id.level);
	name = (TextView) findViewById(R.id.username);
	session = (Spinner) findViewById(R.id.session);
	Seme = (Spinner) findViewById(R.id.semester);

	
	name.setOnClickListener(new OnClickListener(){
		@Override
		public void onClick(View v){
		}
	});
	name.setText(getIntent().getStringExtra("username"));

	calculate = (Button) findViewById(R.id.calculate);
	if(cumulativeMode){
		calculate.setText("Add this Semester");
	}
	else if(editMode ){
		
		calculate.setText("Save");

	}
	
	calculate.setOnClickListener(new OnClickListener(){
	@Override
	public void onClick(View v){
		
		if(verify()){
		calculate();
		}
	}
	});
	
	if(Collector.this.getSharedPreferences("settings", Context.MODE_PRIVATE).getInt("opened", 0) <1){
	Helper help = new Helper("Tip!\n U can leave the course fields blank if you like. They will be filled with 'course1, course2...'", Collector.this);
	help.show();
	
	SharedPreferences pref = getSharedPreferences("settings", Context.MODE_PRIVATE);
	SharedPreferences.Editor editPref = pref.edit();
	
	editPref.putInt("opened", 1);
	
	editPref.commit();
	
	
	
	}

		add_row = (ImageView) findViewById(R.id.add_row);
		remove_row = (ImageView) findViewById(R.id.remove_row);


	}

	public void add(View v){

		Animator anim = Animator.getInstance();
		anim.setNormalColor(R.color.transparent);
		anim.setAnimColor(R.color.black);
		anim.animateView(v, getApplicationContext());

		layout.addView(get_row(index));
		layout.invalidate();
		main_container.invalidate();
		index++;

	}


	public void remove(View v){
		Animator anim = Animator.getInstance();
		anim.setNormalColor(R.color.transparent);
		anim.setAnimColor(R.color.black);
		anim.animateView(v, getApplicationContext());
		int count  = layout.getChildCount();
		if(count > 0){

			layout.removeViewAt(count-1);
			index--;
		}
	}
	
	@Override
	public void onBackPressed(){
	
	final AlertDialog.Builder build = new AlertDialog.Builder(Collector.this);
	LayoutInflater inf = LayoutInflater.from(Collector.this);
	View v = inf.inflate(R.layout.helper, null);
	TextView txt = (TextView) v.findViewById(R.id.helpText);
	txt.setText("Are you sure you want to cancel?");
	build.setView(v);
	build.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int id) {
			if (cumulativeMode) {
				Intent i = new Intent(getApplicationContext(), Cumulative.class);
				i.putExtra("requirePassword", false);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
				finish();
			} else {

				finish();
				overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
			}
		}
	});
	
	build.setNegativeButton("No", new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int id) {
			dialog.cancel();
		}
	});

	AlertDialog alert = build.create();
	alert.show();
	}
	Semester tester;
	private boolean verify(){
		log("verifying ");
		grades.clear();
			courses.clear();
			uls.clear();

		RowView row;

		for(int i =1, j= 0; i <=layout.getChildCount(); i++, j++) {

			row = (RowView) layout.getChildAt(j);
			log("row is " + row);
			if (row != null) {

				if (row.getCourse().getText().toString().length() < 1 && row.getGrade().getSelectedItemPosition() > 0) {

					courses.add("Course "+i);
				} else if(row.getCourse().getText().toString().length()>0) {
					courses.add(row.getCourse().getText().toString());
				}
				else {
					//these are empty entrie stop here
					break;
				}
				grades.add(row.getGrade().getSelectedItem().toString());
				log("added grade " + grades.get(j));

				log("added course" + courses.get(j));
				uls.add(row.getUnitLoad().getSelectedItemPosition());
				log("added unit" + uls.get(j));
			}

			log("Finished with  " + j  + " entries");

		}

		if(grades.size()==0 || uls.size()==0) {
			toast("Some rows are empty! fill all rows and remove rows you are not using");
			return false;
		}
		int tu = tul.getSelectedItemPosition();
		log("total unitload is"+ tu);

		int Sem = Seme.getSelectedItemPosition();

		String user = name.getText().toString();

		int sess = session.getSelectedItemPosition();

		int lev= level.getSelectedItemPosition();



	 tester = new Semester(lev, user, tu, sess, Sem , courses, grades, uls);
	if(tu==0){
		toast("Total unit load not selected!");
		return false;
		
	}
	else if(Sem==0 && cumulativeMode){
		
		toast("Semester not selected!");
		return false;
	}
	
	else if(lev == 0 && cumulativeMode){
		
		toast("level not selected!");
		return false;
	}
	
	else if (sess == 0 && cumulativeMode){
		toast("session not entered!");
		return false;
	}
	
	else if (!tester.verification()){
		toast("The sum of courses' unitloads is greater than the total Unitload. Cross check your entries for errors");

	tester = null;
		return false;
	}
	else if(!tester.validLists()){
		toast("Some rows are empty! fill all rows and remove rows you are not using");
		return false;
	}
	else if(tester.validLists()){

		log("passed all tests");
	}
	
	else{
		
		for(int i =1, j =0; i <=layout.getChildCount(); i++,j++){
			row = (RowView) layout.getChildAt(j);
			if(row!=null) {
				if (row.getUnitLoad().getSelectedItemPosition() == 0 && row.getGrade().getSelectedItemPosition() > 0 || row.getUnitLoad().getSelectedItemPosition() > 1 && row.getGrade().getSelectedItemPosition() == 0) {
					toast("Row " + i + " is not properly filled in");
					return false;
				}
			}
			 
			
		}
		
		
	}
		grades.clear();
		courses.clear();
		uls.clear();
	return true;
	}
	/*
	private void debug(){

	tul.setSelection(15);
	
	level.setSelection(1);
	
	for(int i = 0; i< 15; i ++){
		
	allCourses[i].setText("Debug");
	allGrades[i].setSelection(1);
	allUL[i].setSelection(1);
	
	}

	
	}*/
	
	
	private void toast(String message){
		
	Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
	}
private void log(String message){
	
	Log.d("Collector", message);
}
	private void calculate(){
		RowView row;

	for(int i =1, j= 0; i <=layout.getChildCount(); i++, j++) {

		row = (RowView) layout.getChildAt(j);
		log("row is " + row);
		if (row != null) {


			if (row.getCourse().getText().toString().length() < 1 && row.getGrade().getSelectedItemPosition() > 0) {

				courses.add("Course "+i);
			} else if(row.getCourse().getText().toString().length()>0) {
				courses.add(row.getCourse().getText().toString());
			}
			else {
				//these are empty entries stop here
				break;
			}
			grades.add(row.getGrade().getSelectedItem().toString());
			log("added grade " + grades.get(j));

			log("added course" + courses.get(j));
			uls.add(row.getUnitLoad().getSelectedItemPosition());
			log("added unit" + uls.get(j));
		}

			log("Finished with  " + j  + " entries");

	}
	int tu = tul.getSelectedItemPosition();
	log("total unitload is"+ tu);

	int Sem = Seme.getSelectedItemPosition();
	
	String user = name.getText().toString();
	
	int sess = session.getSelectedItemPosition();
	
	int lev= level.getSelectedItemPosition();
	
	 
	if(cumulativeMode){
		Semester s = new Semester(lev, user, tu, sess, Sem, courses, grades, uls);
		s.save(getApplicationContext());
		Intent i = new Intent(getApplicationContext(), Cumulative.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		i.putExtra("requirePassword", false);
		startActivity(i);
		finish();
	} else if (editMode){

		Semester edited = (Semester) getIntent().getSerializableExtra("semester");
		
		edited.setSemester(Sem);
		edited.setLevel(lev);
		edited.setSession(sess);
		edited.setTul(tu);
		edited.setUser(user);
		edited.setGrades(grades);
		edited.setCourses(courses);
		edited.setUls(uls);
		
		SemesterDatabase db = new SemesterDatabase(getApplicationContext());
		db.updateSemester(edited, edited.getId());
		
		Intent i = new Intent(getApplicationContext(), SemestersActivity.class);
		startActivity(i);
		finish();

		
	}
	else{
	Semester sem = new Semester(lev, user, tu, sess, Seme.getSelectedItemPosition(), courses, grades, uls);
	log("Sending " + sem.getGp());
	Intent i = new Intent(getApplicationContext(), SpokesMan.class);
	i.putExtra("semester", sem);
	startActivity(i);
	finish();
	}
	}
	
	  
	
 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.collector, menu);
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
