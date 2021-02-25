package com.kpr.hus.mpg3;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import androidx.viewpager.widget.PagerTitleStrip;
import android.view.View;

/**
 * Created by f1 on 12/26/2015.
 */
public class Support {
    public static void colorChange(View v,String color1,String color2){
        ColorDrawable[] color = {new ColorDrawable(Color.RED), new ColorDrawable(Color.rgb(0,232,123))};
        TransitionDrawable trans = new TransitionDrawable(color);
        //This will work also on old devices. The latest API says you have to use setBackground instead.
        v.setBackgroundDrawable(trans);
        trans.startTransition(10000);
    }
    public static void colorBackChange(View v,int a1,int r1,int g1,int b1, int a2, int r2, int g2, int b2 ){
        View root = v.getRootView();

        ColorDrawable[] color = {new ColorDrawable(Color.argb(a1,r1,g1,b1)), new ColorDrawable(Color.argb(a2, r2, g2, b2))};
        TransitionDrawable trans = new TransitionDrawable(color);
        //This will work also on old devices. The latest API says you have to use setBackground instead.
        //v.setBackgroundDrawable(trans);
        root.setBackgroundDrawable(trans);
        trans.startTransition(5000);
    }
    public static void colorBackChange2(View v,int a1,int r1,int g1,int b1, int a2, int r2, int g2, int b2 ){
        //View root = v.getRootView();

        ColorDrawable[] color = {new ColorDrawable(Color.argb(a1,r1,g1,b1)), new ColorDrawable(Color.argb(a2, r2, g2, b2))};
        TransitionDrawable trans = new TransitionDrawable(color);
        //This will work also on old devices. The latest API says you have to use setBackground instead.
        v.setBackgroundDrawable(trans);
       // root.setBackgroundDrawable(trans);
        trans.startTransition(1000);
    }
    public static void colorTitleChange(View v,PagerTitleStrip pagerTitleStrip,int a1,int r1,int g1,int b1, int a2, int r2, int g2, int b2 ){


        ColorDrawable[] color = {new ColorDrawable(Color.argb(a1,r1,g1,b1)), new ColorDrawable(Color.argb(a2,r2,g2,b2))};
        TransitionDrawable trans = new TransitionDrawable(color);
        //This will work also on old devices. The latest API says you have to use setBackground instead.
        //v.setBackgroundDrawable(trans);
       pagerTitleStrip.setBackgroundDrawable(trans);

        trans.startTransition(5000);
    }
}
