
package com.pa.gp_calc;

import android.content.Context;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

public class Semester implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int level;
	String Name;
	int Session;
	ArrayList<String> courses;
	ArrayList<String> grades;
	ArrayList<Integer> units;
	double gp;
	int Semester;
	int Ul;
	String position; 
	int id = 0;
	int uid = 1;
	
	int As = 0;
			int Bs = 0;
			int Cs = 0;
			int  Ds = 0;
			int  Es = 0;
			int Fs = 0;
	public Semester(int level, String Name, int Ul, int Session, int Semester, ArrayList<String> courses, ArrayList<String>Grades, ArrayList<Integer> units){
	
	this.level = level;
	log("rceived level " + level);
	
	this.Name = Name;
	log("received name " + Name);
	this.Ul = Ul;
	
	log("received ul"+ Ul);
	this.Session = Session;
	
	log("recieved session" + Session);
	this.courses = courses;
	
	log("received courses " + courses.get(0));
	this.grades = Grades;
	
	
	log("received grade " + grades.get(0));
	this.units = units;
	
	this.Semester = Semester;
	log("received units " + units.get(0));
	this.Session = Session;
	}
	
	public int getSemester(){
		return this.Semester;
	}
	public int getUid(){
	
		return this.uid;
		
	}

	public boolean validLists(){

		if(grades.size() < 1) return false;
		if(units.size() < 1) return false;
		return true;
	}
	public int getSession(){
		return this.Session;
	}
	
	public void setId(int id){
		this.id=id;
	}
	 
	public int Ul(){
		return this.Ul;
	}
	
	public int Level(){
		
		return this.level;
	}
	
	
	public String Name(){
		return this.Name;
	}
	
	public int Session(){
		return this.Session;
		
	}
	
	
	public void save(Context ctx){ 
	SemesterDatabase db = new SemesterDatabase(ctx);
	
	db.addSemester(this); 
	try {
		this.finalize();
	} catch (Throwable e) {
		 
	}
	}
	public String arrayToString(ArrayList<String> string){
	
		StringBuilder buil = new StringBuilder();
		for(String str: string){ 
			buil.append(str);
			buil.append("----");
			log("this course is" +str);
		}
		
		return buil.toString();
	}
	 
	public String getCourses(){

		return arrayToString(this.courses);
	}
	public  String  getGrades(){

		return arrayToString(this.grades);
		
	}
	
	
	public void setSemester(int semester){
		this.Semester = semester;
	}
	
	
	public String getUnits(){
		StringBuilder build = new StringBuilder();
		for(int i: units){
			
		build.append(""+i);
		build.append("----");
		
			
		} 
		return build.toString();
		 
	}
	
	public void setLevel(int level){
		
		this.level= level;
	}

	public int getTotalUnitsInteger(){
		int result = 0;
		for(int i : units){

			result+=i;
		}
		return result;
	}

	public int getTotalPointsInteger(){
		int result = 0;
		for(int i = 0; i < grades.size(); i++){
			result+= valueOf(grades.get(i)) * units.get(i);
		}

		return result;

	}
	
	public void setSession(int session){
		this.Session = session;
	}
	
	
	public void setTul(int tul){
		this.Ul = tul;
	}
	 
	
	public void setUser(String user){
		this.Name = user;
	}
	
	public void setCourses(ArrayList<String> cour){
		this.courses = cour;
	}
	
	public int getAs(){
		return this.As;
	}
	
	public int getBs(){
		return this.Bs;
	}
	
	public int getCs(){
		return this.Cs;
	}
	
	public int getDs(){
		return this.Ds;
	}
	
	public int getEs(){
		return this.Es;
	}
	public void setGrades(ArrayList<String> gra){
		this.grades = gra;
	}
	
	public void setUls(ArrayList<Integer> u){
		this.units = u;
	}
	
	public int getId(){
		return this.id;
	}
	
	public void setGrade(String grade, int i){
		
		 grades.add(i, grade);
	}
	
	
	public void setCourse(String course, int i){
		
		courses.add(i, course);
	}
	
	
	public double getGp(){
		calculate();	
		return this.gp;
		
	}
	
	private void log(String message){
		
		Log.d("Semester", message);
	}
	
	public boolean verification(){
		
	boolean feedback = true;
	int totalUn = 0;	
	for(int i = 0; i < units.size(); i++){
		
		totalUn+=units.get(i);
		log("Total units is now " + totalUn);
		log("total UL " + Ul);
	
	}
	
	if(totalUn> Ul){
		
	feedback = false;
	} 
	return feedback;
	}
	public void calculate(){
	
	double tempGp = 0;
	for(int i =0; i<units.size(); i++){
		 log("grades here is"+grades.get(i) + " and unit is" + units.get(i));
	tempGp+= valueOf(grades.get(i)) * units.get(i);
	 log("grades here is"+grades.get(i) + " and unit is" + units.get(i));
	// log("therefore added "+ valueOf(grades.get(i)) * units.get(i));
	}
	
	this.gp =  (tempGp/Ul);
	log("gp is"+gp);
	}



	
	
	
	private int valueOf(String g){
		log("verifying " + g);
	int gr = 0;
	
	switch(g){
	
	case "A":
		gr= 5;
		As+=1;
		break;
		
		
	case "B":
		gr= 4;
		Bs+=1;
		break;
		
	case "C":
		Cs+=1;
		gr= 3;
		
		break;
		
	case "D":
		gr = 2;
		Ds+=1;
		break;
		
	case "E":
		gr = 1;
		Es+=1;
		break;
		
	default:
		gr = 0;
		break;
	}
		log("The grade chekced is " + gr);
		return gr;
	}
	
	
	
	
}
