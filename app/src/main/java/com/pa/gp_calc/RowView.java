package com.pa.gp_calc;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by Larry on 4/14/2016.
 */
public class RowView extends RelativeLayout {

    private EditText course;
    private Spinner unitload;
    private Spinner grade;
    private Context context;
    int index;

    public RowView(Context context, int index){
        super(context);
        this.index = index;
        this.context = context;
        this.course = new EditText(context);
        this.unitload = new Spinner(context);
        this.grade = new Spinner(context);

        LayoutParams clp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(clp);

        LayoutParams courselp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        courselp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

        course.setHint("Course " + index);
        course.setLayoutParams(courselp);
        course.setTextColor(context.getResources().getColor(R.color.realblack));


        this.addView(course);

        unitload = new Spinner(context);
        String[] ulArray = getResources().getStringArray(R.array.unitload);
        ArrayAdapter<String> unit = new CustomArrayAdapter<String>(context,   ulArray);
        LayoutParams unitParam = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        unitParam.addRule(RelativeLayout.CENTER_HORIZONTAL);
        unitload.setAdapter(unit);
        unitload.setLayoutParams(unitParam);
        unitload.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                  ((TextView) adapterView.getChildAt(0)).setTextColor(getContext().getResources().getColor(R.color.realblack));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        this.addView(unitload);

        grade = new Spinner(context);
        String[] grArray = getResources().getStringArray(R.array.grades);
        ArrayAdapter<String> gradeAdapter = new CustomArrayAdapter<String>(context,   grArray);
        LayoutParams gradeParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        gradeParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        grade.setLayoutParams(gradeParams);
        grade.setAdapter(gradeAdapter);
        grade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView) adapterView.getChildAt(0)).setTextColor(getContext().getResources().getColor(R.color.black));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        this.addView(grade);
}
/*
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Animation anim = AnimationUtils.loadAnimation(context, R.anim.fade_in);
        anim.setFillAfter(true);
        this.startAnimation(anim);
    } */

    public void setIndex(int i){
        this.index = i;
        this.course.setHint("Course " + i);
    }

    public Spinner getGrade(){

        return grade;
    }

    public Spinner getUnitLoad(){

        return unitload;
    }
    public EditText getCourse(){

        return course;
    }
}
