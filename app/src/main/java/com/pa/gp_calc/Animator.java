package com.pa.gp_calc;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Larry on 10/1/2015.
 */
public class Animator {

    private int delay=200;

    private  int normalColor= R.color.black;
    private  int animColor = R.color.twhite;

    private static Animator instance;

    private Animator(){

    }

    public static Animator getInstance(){

        if(instance==null) instance = new Animator();

        return instance;
    }




    public void animateView(final View v, final Context context){

        v.setBackgroundColor(context.getResources().getColor(animColor));

        Handler h = new Handler();

        h.postDelayed(new Runnable(){
            @Override
            public void run(){
                v.setBackgroundColor(context.getResources().getColor(normalColor));

            }
        }, delay);



    }


    public void animateButton(final ImageView view, final int normalImage, int animatedImage, final Context context){

        Handler h = new Handler();
        view.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), animatedImage));

        Runnable run = new Runnable(){
            @Override
            public void run(){
                view.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), normalImage));
            }
        };
        h.postDelayed(run,delay);

    }

    public  void setNormalColor(int i){
        this.normalColor  = i;
    }

    public  void setDelay(int dela){
       this.delay = dela;
    }


    public void setAnimColor(int animColor) {
        this.animColor = animColor;
    }
}
