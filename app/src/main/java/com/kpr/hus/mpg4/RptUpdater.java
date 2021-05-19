package com.kpr.hus.mpg4;

import android.os.Handler;

/**
 * Created by f1 on 12/27/2015.
 */
class RptUpdater implements Runnable {
    private Handler repeatUpdateHandler = new Handler();
  //  And 2 vars which will state: is it increment or decrement? Only one set at a time.
public static int REP_DELAY=50;
    private boolean mAutoIncrement = false;
    private boolean mAutoDecrement = false;
   // And the present number value

    public int mValue;
    public void run() {
        if( mAutoIncrement ){
            increment();
            repeatUpdateHandler.postDelayed( new RptUpdater(), REP_DELAY );
        } else if( mAutoDecrement ){
            decrement();
            repeatUpdateHandler.postDelayed( new RptUpdater(), REP_DELAY );
        }
    }

    public void decrement(){
        mValue--;
        //value.setText( ""+mValue );
    }
    public void increment(){
        mValue--;
       // value.setText( ""+mValue );
    }
}
