package com.pa.gp_calc;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Helper {

String message;
Context context;
	
public Helper(String message, Context context){
this.message = message;
this.context = context;
	
}


public void Welcomer(){

SharedPreferences prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE);

int count = prefs.getInt("opened", 0);

if(!(count>2)){

SharedPreferences.Editor editPref = prefs.edit();
editPref.putInt("opened", count+1);
editPref.commit();

Toast.makeText(context, message, Toast.LENGTH_LONG).show();

}
	
	
}



public void show(){

AlertDialog.Builder build = new AlertDialog.Builder(context);
LayoutInflater inf = LayoutInflater.from(context);
View view = inf.inflate(R.layout.helper, null);
build.setView(view);
TextView txt = (TextView) view.findViewById(R.id.helpText);
txt.setText(message); 
build.setPositiveButton("Ok" ,new DialogInterface.OnClickListener(){
	@Override
	public void onClick(DialogInterface dialog, int id){
		
		dialog.cancel();
	}
});


AlertDialog shower = build.create();
shower.show();

}
}
