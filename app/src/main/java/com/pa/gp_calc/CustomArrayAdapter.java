package com.pa.gp_calc;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Larry on 4/14/2016.
 */
public class CustomArrayAdapter<T> extends ArrayAdapter<T> {
    public CustomArrayAdapter(Context context, T[] objects) {
        super(context, android.R.layout.simple_spinner_item, objects);
    }



    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent){

        View view  = super.getView(position, convertView, parent);

        TextView text = (TextView) view.findViewById(android.R.id.text1);
        text.setTextColor(getContext().getResources().getColor(R.color.white));
        return view;
    }
}
