package com.pa.gp_calc;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class simpleAdapter  extends ArrayAdapter<String> {
		
	private final Activity context;
	private final ArrayList<String> titles;
	private ArrayList<String> details;
	
	
	
	static public class ViewHolder{
		public TextView detail;
		public TextView title;
	}
	
		
		public simpleAdapter(Activity context, ArrayList<String> titles, ArrayList<String> details){
			super(context, R.layout.list_layout, titles);
			this.context = context;
			this.titles = titles;
			this.details = details;
	}
		
		
	
	 
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		View rowView = convertView;
		if(rowView == null){
			log("set row view");
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.list_layout, null);
			
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.title = (TextView) rowView.findViewById(R.id.gtitle);
			viewHolder.detail= (TextView) rowView.findViewById(R.id.gdetail);
			log("set view holder elements");
			rowView.setTag(viewHolder);
		}
		
		log("view holder set");
		
		ViewHolder holder = (ViewHolder) rowView.getTag();
		
		log("gotten holder tag");
		 log("text is not null + " + titles.get(0));
		 holder.title.setText(titles.get(position));
		 log("text is not null + " + details.get(0));
		 holder.detail.setText(details.get(position));
		 
		 log("set all views");
		return rowView;
		
		
	}
	
	 
	
	
	private void log(String message){
		
		 Log.d("simple adapter", message);
	}
}