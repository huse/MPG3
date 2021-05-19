package com.kpr.hus.mpg4;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import CustomListView.Model;
import CustomListView.ModelArrayAdapter;
import CustomListView.SwipingActivity;
import CustomListView.SwipingActivity.ViewHolder;

/**
 * A fragment representing a single step in a wizard. The fragment shows a dummy title indicating
 * the page number, along with some dummy text.
 *

 */
public class ThirdFragment extends Fragment {
    private SwipingActivity.ViewHolder viewHolder2;
    private View view2;
    /**
     * The argument key for the page number this fragment represents.
     */
    double truncateDouble(double number, int numDigits) {
        double result = number;
        String arg = "" + number;
        int idx = arg.indexOf('.');
        if (idx != -1) {
            if (arg.length() > idx + numDigits) {
                arg = arg.substring(0, idx + numDigits + 1);
                result = Double.parseDouble(arg);
            }
        }
        return result;
    }
//    private AdView mAdView;
    List<Data> list;
    MySQLiteHelper db3;
    ListView listView;
    //ArrayAdapter<String> adapter;
    ModelArrayAdapter adapter;
    Button bt3Calculate, bt3Send;
    EditText et3MPG, et3Gallon;
    TextView tv3Mile, tv3Km;

    String a, b, c, d;
    View.OnTouchListener gestureListener;
    private ViewHolder viewHolder;
    boolean bCycling = false;

    public void sets(String a, String b, String c, String d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public String gets() {
        return "Distance you can go by your fuel: " +
                "" +
                "" +" Your MpG = " + et3MPG.getText() + "  Fuel in your tank, Gallon = " + et3Gallon.getText() + "    The Distance you can go - in Mile = "
                + tv3Mile.getText() + "    The Distance you can go - in Km = " + tv3Km.getText();
    }

        public static final String ARG_PAGE = "page";

    /**
     * The fragment's page number, which is set to the argument value for {@link #ARG_PAGE}.
     */
    private int mPageNumber;

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static ThirdFragment newInstance(int pageNumber) {
        ThirdFragment fragment = new ThirdFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ThirdFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
    }
    // Button bt1Calculate = new View.findViewById(R.id.button);
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.
        final ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_third, container, false);
        Support.colorBackChange(rootView,0,255,117,50,155,255,117,50);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        bt3Calculate = (Button) rootView.findViewById(R.id.button5);
        bt3Send = (Button) rootView.findViewById(R.id.button6);
        et3MPG = (EditText) rootView.findViewById(R.id.editText5);
        et3Gallon = (EditText) rootView.findViewById(R.id.editText6);
        tv3Mile = (TextView) rootView.findViewById(R.id.textView8);
        tv3Km = (TextView) rootView.findViewById(R.id.textView10);
        db3 = new MySQLiteHelper(getActivity().getBaseContext(),"third");
        listView = (ListView)rootView.findViewById(R.id.listView3);
//        mAdView = (AdView) rootView.findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

        updateingListView();
        bt3Calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Support.colorChange(v, "BLUE", "RED");
                    Double dd1 = Double.parseDouble(et3MPG.getText().toString());
                    Double dd2 = Double.parseDouble(et3Gallon.getText().toString());
                    Double dd;
                    dd = (dd2 * dd1);
                    Double ff = truncateDouble(dd, 2);
                    String ss;
                    ss = ff.toString();
                    tv3Mile.setText(ss + " ");

                    //Double dk1 = dd1 / 1.609344;
                    //Double dk2 = dd2 / 3.785411784;
                    Double dkk = 1.609344 * dd;
                    Double ff2 = truncateDouble(dkk, 2);
                    String sk = ff2.toString();
                    tv3Km.setText(sk + " ");
                    sets(et3MPG.getText().toString(), et3Gallon.getText().toString(),
                            ss, sk);
                    //for finish keypad after press button.
                    InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    mgr.hideSoftInputFromWindow(et3Gallon.getWindowToken(), 0);
                    mgr.hideSoftInputFromWindow(et3MPG.getWindowToken(), 0);

                } catch (Exception e) {
                    tv3Km.setText("invalid");
                    tv3Mile.setText("invalid");

                }

                Calendar c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH) + 1;
                int year = c.get(Calendar.YEAR);

                // textView2.setText(month+"/"+day+"/"+year );
                // db1.getAllBooks();
                db3.addBook(new Data(month + "/" + day + "/" + year, et3MPG.getText().toString(), et3Gallon.getText().toString(), tv3Mile.getText().toString(), tv3Km.getText().toString()));


                updateingListView();

            }
        });


        bt3Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Support.colorChange(v, "BLUE", "RED");
                Intent intent = new Intent();
                intent.setType("text/plain");
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, gets());
                startActivity(intent);
            }


        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int pos, long id) {
                if(viewHolder2!=null){
                    view2.setBackgroundColor(listView.getSolidColor());
                    viewHolder2.icon.setVisibility(View.GONE);
                    Log.d("Deselected", viewHolder2.position + "");
                }

                final SwipingActivity.ViewHolder viewHolder;
                view2=arg1;
                viewHolder2 = ((SwipingActivity.ViewHolder) arg1.getTag());
                viewHolder = ((SwipingActivity.ViewHolder) arg1.getTag());
                //Using background color
                int color = Color.TRANSPARENT;
                Drawable background = arg1.getBackground();
                if (background instanceof ColorDrawable) {
                    color = ((ColorDrawable) background).getColor();
                }
                final View substitute;
                substitute = arg1;

                if (color != 0xFFFF5556) {


                    Support.colorBackChange2(arg1, 200, 0, 144, 250, 200, 255, 76, 54);
                    //arg1.setBackgroundColor(0xFFFF5556);
                    viewHolder.icon.setImageResource(R.drawable.recycle_512);
                    viewHolder.icon.setVisibility(View.VISIBLE);
                    Log.d("Selected", viewHolder.position + "");
                } else {
                    arg1.setBackgroundColor(listView.getSolidColor());
                    viewHolder.icon.setVisibility(View.GONE);
                    Log.d("Deselected", viewHolder.position + "");
                }
                final int poss = pos;
                viewHolder.icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        substitute.setBackgroundColor(0xFFB1B1B1);
                        viewHolder.icon.setVisibility(View.GONE);
                        db3.deleteBook(list.get(poss));
//                        viewHolder.text.setText("");
                        viewHolder.text2.setText("Deleted");
                        viewHolder.text3.setText("");
                        viewHolder.text4.setText("");
                        viewHolder.text5.setText("");
                        viewHolder.text6.setText("");
                        Log.d("List", list.get(poss) + "");
                        Log.d("positon", poss + "");
                        updateingListView();
                    }


                });

            }

        });
        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            private SwipingActivity.ViewHolder viewHolder;

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int pos, long id) {
                // cyclingButt( arg1,  viewHolder);
                viewHolder = ((ViewHolder) arg1.getTag());
                int color = Color.TRANSPARENT;
                Drawable background = arg1.getBackground();
                if (background instanceof ColorDrawable)
                    color = ((ColorDrawable) background).getColor();
                if (color == 0xFFFF5556) {
                    arg1.setBackgroundColor(Color.argb(155, 255, 117, 50));
                    viewHolder.icon.setVisibility(View.GONE);
                }
                final int poss = pos;


            }

        });
*/
        /*gestureListener = new View.OnTouchListener() {
            private int padding = 0;
            private int initialx = 0;
            private int currentx = 0;
            private SwipingActivity.ViewHolder viewHolder;
            public boolean onTouch(View v, MotionEvent event) {
                if ( event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    padding = 0;
                    initialx = (int) event.getX();
                    currentx = (int) event.getX();
                    viewHolder = ((ViewHolder) v.getTag());
                    Log.v("ACTION_DOWN", "currentx: " + currentx);
                    Log.v("ACTION_DOWN", "PADDING: " + padding);
                    Log.v("ACTION_DOWN", "initialx: " + initialx);
                }
                if ( event.getAction() == MotionEvent.ACTION_MOVE)
                {
                    currentx = (int) event.getX();
                    padding = currentx - initialx;
                    Log.v("ACTION_MOVE", "currentx: " + currentx);
                    Log.v("ACTION_MOVE", "PADDING: " + padding);
                    Log.v("ACTION_MOVE", "initialx: " + initialx);
                }

                if ( event.getAction() == MotionEvent.ACTION_UP ||
                        event.getAction() == MotionEvent.ACTION_CANCEL)
                {
                    padding = 0;
                    initialx = 0;
                    currentx = 0;
                    Log.v("ACTION_UP", "currentx: " + currentx);
                    Log.v("ACTION_UP", "PADDING: " + padding);
                    Log.v("ACTION_UP", "initialx: " + initialx);
                }

                if(viewHolder != null)
                {int color = Color.TRANSPARENT;
                    Drawable background = v.getBackground();
                    if (background instanceof ColorDrawable)
                        color = ((ColorDrawable) background).getColor();
                    if(padding == 0 && color!=0xFFFF5556)
                    {

                        v.setBackgroundColor(0xFFFF5556 );
                        viewHolder.icon.setImageResource(R.drawable.recycle_512);
                        viewHolder.icon.setVisibility(View.VISIBLE);
*//*
                        viewHolder.icon.setVisibility(View.INVISIBLE);

                        viewHolder.icon.setVisibility(View.GONE);*//*
                        if(viewHolder.running)
                            v.setBackgroundColor(0xFF058805);

                    }
                    if(padding == 0 && color==0xFFFF5556)
                    {

                        v.setBackgroundColor(Color.argb(155, 255, 117, 50));

                       // viewHolder.icon.setVisibility(View.VISIBLE);

                       // viewHolder.icon.setVisibility(View.INVISIBLE);

                        viewHolder.icon.setVisibility(View.GONE);
                        if(viewHolder.running)
                            v.setBackgroundColor(0xFF058805);

                    }
                    if(padding > 15) {
                        viewHolder.running = true;
                        v.setBackgroundColor(0xFF00FF00 );
                      //  viewHolder.icon.setImageResource(R.drawable.clock_running);
                    }
                    if(padding < -15) {
                        viewHolder.running = false;
                        v.setBackgroundColor(0xFFFF0000);
                       // viewHolder.icon.setImageResource(R.drawable.bullet_green);
                    }
                    v.setPadding(padding, 0,0, 0);
                }
                viewHolder.icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db3.deleteBook(list.get(0));
                        updateingListView();
                    }


                });
                return true;
            }
        };*/
/*        updateingListView();
        final SoftKeyboardStateWatcher softKeyboardStateWatcher = new SoftKeyboardStateWatcher(rootView);
        // Add listener
        softKeyboardStateWatcher.addSoftKeyboardStateListener(new SoftKeyboardStateWatcher.SoftKeyboardStateListener() {
            @Override
            public void onSoftKeyboardOpened(int keyboardHeightInPx) {
                Log.d("hhhhhh", "keyboard opened");
                mAdView.setVisibility(View.GONE);
            }

            @Override
            public void onSoftKeyboardClosed() {
                Log.d("hhhhhh", "keyboard Closed");
                mAdView.setVisibility(View.VISIBLE);
            }
        });*/
        return rootView;
    }
/*    public boolean cyclingButt(View arg1,ViewHolder viewHolder){
        int color = Color.TRANSPARENT;
        Drawable background = arg1.getBackground();
        if (background instanceof ColorDrawable)
            color = ((ColorDrawable) background).getColor();
        if(color==Color.argb(155, 255, 117, 50)) {
            arg1.setBackgroundColor(0xAA305500);
            viewHolder = ((ViewHolder) arg1.getTag());
            viewHolder.icon.setImageResource(R.drawable.recycle_512);

            bCycling=false;
        }
        else{
            arg1.setBackgroundColor(Color.argb(155, 255, 117, 50));

            viewHolder = ((ViewHolder) arg1.getTag());
            viewHolder.icon.setImageResource(R.drawable.bullet_go);
            bCycling=true;
        }
        return bCycling;

    }*/
    public ArrayList<Model> getData()
    {
        list = db3.getAllBooks();

        ArrayList<Model> models = new ArrayList();

        for(int a=0;a<list.size();a++)
        {
            Model m = new Model(list.get(a).toString());
            models.add(m);
        }
        return models;
    }
    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return mPageNumber;
    }

    private void FunctionDeleteRowWhenSlidingRight(int pos) {
        db3.deleteBook(list.get(pos));
        updateingListView();
    }

    private void FunctionDeleteRowWhenSlidingLeft(int pos) {

        db3.deleteBook(list.get(pos));
        updateingListView();

    }

    public void updateingListView() {

        adapter = new ModelArrayAdapter(getActivity(), getData(),gestureListener);
        listView.setAdapter(adapter);
       // adapter=new MySimpleArrayAdapter(getActivity().getBaseContext(), rr, list);



        // check if the adapter has changed
        adapter.notifyDataSetChanged();
    }
}
