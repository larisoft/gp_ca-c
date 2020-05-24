package com.pa.gp_calc;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class SemesterDatabase extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	
	private static final String DATABASE_NAME = "SemesterDB";
	
	public SemesterDatabase(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);																
	}
	
	@Override
	public void onCreate(SQLiteDatabase db){
		String CREATE_BOOK_TABLE = "CREATE TABLE semesters (" + "_id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
	"user TEXT, " + "level TEXT, " + "session TEXT,"+
				" tul TEXT, sem Text, courses Text, grades Text,  uls Text)";
		db.execSQL(CREATE_BOOK_TABLE);
	}

	
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL("DROP TABLE IF EXISTS semesters"); 
		this.onCreate(db);
	}
	
	private static final String TABLE_MEMBERS =  "semesters";
	private static final String KEY_ID = "_id";
	private static final String KEY_NAME = "user";
	private static final String KEY_LEVEL = "level";
	private static final String KEY_SESSION = "session";
	private static final String KEY_TUL = "tul";
	private static final String KEY_SEMESTER = "sem";
	private static final String KEY_COURSES ="courses";
	private static final String KEY_GRADES = "grades"; 

	private static final String KEY_ULS = "uls";  
	
	private static final String[] COLUMNS ={KEY_ID, KEY_NAME, KEY_LEVEL, KEY_SESSION, KEY_TUL, KEY_SEMESTER, KEY_COURSES, KEY_GRADES, KEY_ULS};
	 
	
	public void addSemester(Semester asemester){
	//Log.d("addBook",  aCase.getTitle());
	
	SQLiteDatabase db = this.getWritableDatabase();
	
	ContentValues values = new ContentValues();
	values.put(KEY_NAME,  asemester.Name());
	values.put(KEY_LEVEL,  asemester.Level());
	values.put(KEY_SESSION, asemester.Session());
	
	values.put(KEY_TUL, asemester.Ul());
	values.put(KEY_SEMESTER, asemester.getSemester());
	values.put(KEY_COURSES, asemester.getCourses());

	values.put(KEY_GRADES, asemester.getGrades());
	values.put(KEY_ULS, asemester.getUnits());  
	db.insert(TABLE_MEMBERS,  null,  values);
	log("inserted");
	db.close();
	
	
	} 
	
	public Semester getSemester(int id){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_MEMBERS,  COLUMNS, "_id = ?",  new String[]{String.valueOf(id)},null,null,null,null);
		if(cursor!=null){
			cursor.moveToFirst();
		}
		
		 
		String name =  cursor.getString( 1);
		String level = cursor.getString( 2);
		String session = cursor.getString( 3);
		String tul = cursor.getString(4);
		String courses = cursor.getString(5);
		
		String sem = cursor.getString(6);
		String grades = cursor.getString(7);
		String uls = cursor.getString(8);
		String uid = cursor.getString(9);
		
		 
		
		log(name);
		log(level);
		log(session);
		log(tul);
		log(courses);
		log(grades);
		log(uls);
		log(uid);
		Semester aSem = new Semester(Integer.valueOf(level), name, Integer.valueOf(tul), Integer.valueOf(session), Integer.valueOf(sem), getArray(courses, false), getArray(grades, false), getunits(uls));
	//	Log.d("getCase("+id+")", aCase.getTitle());
		
		
		db.close();
		return aSem;
	}
	
	
	private  ArrayList<Integer> getunits(String s){
		
		ArrayList<Integer> output = new ArrayList<Integer>();
		
		String[] process = s.split("----");
		
		for(int i = 0; i <process.length; i++){
			
			output.add(Integer.valueOf(process[i])); 
			log("processing integer " + i + " is " + process[i]);
		}
		
		return output;
	}
	 
	private  ArrayList<String> getArray(String str, boolean num){
	
	ArrayList<String> output = new ArrayList<String>();
	
	String[] process = str.split("----");
	
	for(int i = 0; i <process.length; i++){
		
		output.add(process[i]); 
		log("process " + i + " is " + process[i]);
	}
	
	return output;
	}
	
	public ArrayList<Semester> getAllMembers(){
		ArrayList<Semester> Semesters = new ArrayList<Semester>();
		String query = "SELECT * FROM " + TABLE_MEMBERS;
		
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		
		log("about to query "+ TABLE_MEMBERS);
		if(cursor.moveToFirst()){
		  
		do{ log("getting entries");
			int id = Integer.valueOf(cursor.getString(0));
			String name =  cursor.getString( 1);
			String level = cursor.getString( 2);
			String session = cursor.getString( 3);
			String tul = cursor.getString(4);
			String sem = cursor.getString(5);
			String courses = cursor.getString(6);
			String grades = cursor.getString(7);
			String uls = cursor.getString(8);   
			
			log(name);
			log(level);
			log(session);
			log(tul);
			log(courses);
			log(grades);
			log(uls);
			Semester aSem = new Semester(Integer.valueOf(level), name, Integer.valueOf(tul), Integer.valueOf(session), Integer.valueOf(sem), getArray(courses, false), getArray(grades, false), getunits(uls));
		    aSem.setId(id);
			Semesters.add(aSem);
		}
		while(cursor.moveToNext());
		
	}
	  
		db.close();
		return Semesters; 
		}


	public void clearData(){
		String query = "DELETE FROM " + TABLE_MEMBERS;
		SQLiteDatabase db = this.getWritableDatabase();
 		db.execSQL(query);
 	}
	   
		 
	private void log(String message){
		
		Log.d("SemesterDatabase", message);
	}
	public int updateSemester(Semester amember, int id){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_NAME,  amember.Name());
		values.put(KEY_LEVEL,  amember.Level());
		values.put(KEY_SESSION, amember.Session());
		values.put(KEY_TUL, amember.Ul());
		values.put(KEY_COURSES, amember.getCourses());
		values.put(KEY_GRADES, amember.getGrades());
		values.put(KEY_ULS, amember.getUnits()); 
		
		int i = db.update(TABLE_MEMBERS,  values,  "_id = '" + id+"'" ,  null );
		
		//db.execSQL("UPDATE " + TABLE_CASES + " SET title = '"+aCase.getTitle() + ", summary = '"+aCase.getSummary() + "'");
		  
		db.close();
		return i;
	}
	
	 
	

}
