package com.kpr.hus.mpg3;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FirstFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FirstFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirstFragment extends Fragment {
    //dESIGNED BY hOSEIN kAJEPOR
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
    public void implicitSendText(View v){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, "Message");
        intent.setType("text/plain");
        startActivity(intent);
    }
    List<Data> list;
    MySQLiteHelper db;
    ListView listView;
    ArrayAdapter<String> adapter;
    Button bt, bt2,bt7;
    EditText et1, et6, et3, et4;
    TextView tv1, tv2;
    String a, b, c, d;
    float historicX = Float.NaN, historicY = Float.NaN;
    static final int DELTA = 50;
    enum Direction {LEFT, RIGHT;}

    public void sets(String a, String b, String c, String d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public String gets() {
        return "Mile = " + a + "   Gallon = " + b + "    Mile per Gallon = "
                + c + "    Liter per 100 Km = " + d;
    }
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String ARG_PAGE = "page";

    /**
     * The fragment's page number, which is set to the argument value for {@link #ARG_PAGE}.
     */
    private int mPageNumber;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    // TODO: Rename and change types and number of parameters
    public static FirstFragment newInstance(int pageNumber) {
        FirstFragment fragment = new FirstFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public FirstFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_first, container, false);
       // v.setBackgroundColor(Color.BLACK);
//        View root = v.getRootView();
//        root.setBackgroundColor(Color.argb(155,55,255,255));
        Support.colorBackChange(v,0,55,255,255,155,55,255,255);
        bt = (Button) v.findViewById(R.id.button3);
        bt2 = (Button) v.findViewById(R.id.button4);
        bt7 = (Button) v.findViewById(R.id.button7);
        et3 = (EditText) v.findViewById(R.id.editText);
        et4 = (EditText) v.findViewById(R.id.editText2);
        et6 = (EditText) v.findViewById(R.id.editText6);
        tv1 = (TextView) v.findViewById(R.id.textView4);
        tv2 = (TextView) v.findViewById(R.id.textView6);
        db = new MySQLiteHelper(getActivity().getBaseContext());
        listView = (ListView)v.findViewById(R.id.listView);

        updateingListView();

       bt.setOnTouchListener(new View.OnTouchListener() {


           @Override
           public boolean onTouch(View v, MotionEvent event) {
               Support.colorChange(v, "BLUE", "RED");
               try {
                   Double dd1 = Double.parseDouble(et3.getText().toString());
                   Double dd2 = Double.parseDouble(et4.getText().toString());
                   Double dd;
                   dd = dd1 / dd2;
                   Double ff = truncateDouble(dd, 2);
                   String ss;
                   ss = ff.toString();
                   tv1.setText(ss + "   Mile per Gallon");

                   Double dk1 = dd1 * 1.609344;
                   Double dk2 = dd2 * 3.785411784;
                   Double dkk = (100.0 * dk2) / dk1;
                   Double ff2 = truncateDouble(dkk, 2);
                   String sk = ff2.toString();
                   tv2.setText(sk + "   Liter per 100 KM");
                   sets(et3.getText().toString(), et4.getText().toString(),
                           ss, sk);
                   InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                   mgr.hideSoftInputFromWindow(et4.getWindowToken(), 0);
                   mgr.hideSoftInputFromWindow(et3.getWindowToken(), 0);

               } catch (Exception e) {
                   tv2.setText("invalid");
                   tv1.setText("invalid");

               }

               return false;
           }
       });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("text/plain");
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, gets());
                startActivity(intent);
            }


        });

        bt7.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Calendar c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                // textView2.setText(month+"/"+day+"/"+year );
                // db.getAllBooks();
                db.addBook(new Data(et3.getText().toString(), et4.getText().toString(), month + "/" + day + "/" + year, et6.getText().toString(), "price"));


                updateingListView();
            }
        });
        listView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listView.setBackgroundColor(0xFF00FFFF);
                Log.d("HHHHHHHHHH", "position" + "");

             return true;
            }
        });
        listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView parentView, View childView, int position, long id) {
                listView.setBackgroundColor(0xFF00FFFF);
                Log.d("HHHHHHHHHH", position + "");
                //The above text variable has the text value of selected item
                // position will reflect the index of selected item
            }

            public void onNothingSelected(AdapterView parentView) {
            }
        });
/*        listView.setOnTouchListener(new View.OnTouchListener() {
            private int padding = 0;
            private int initialx = 0;
            private int currentx = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    padding = 0;
                    initialx = (int) event.getX();
                    currentx = (int) event.getX();
                    currentx = (int) event.getX();
                    padding = currentx - initialx;

                }

                if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                    padding = 0;
                    initialx = 0;
                    currentx = 0;
                }

                ////////////
                if (padding == 0) {
                    v.setBackgroundColor(0xFF00FFFF);
                    // v.setBackgroundColor(0xFFFF0000 );
                    Log.d("HHHHHHHHHH0000", MySimpleArrayAdapter.posoo + "");
                    FunctionDeleteRowWhenSlidingLeft(MySimpleArrayAdapter.posoo);
                }
                if (padding > 75) {

                    v.setBackgroundColor(0xFF00FF00);
                    Log.d("HHHHHHHHHH75", MySimpleArrayAdapter.posoo + "");
                    FunctionDeleteRowWhenSlidingRight(MySimpleArrayAdapter.posoo);
                }
                if (padding < -75) {


                }
                v.setPadding(padding, 0, 0, 0);

*//*                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        historicX = event.getX();
                        historicY = event.getY();
                        break;

                    case MotionEvent.ACTION_UP:
                        if (event.getX() - historicX < -DELTA) {
                            Log.d("HHHHHHHHHH",  "-DELTA");
                            FunctionDeleteRowWhenSlidingLeft();
                            return true;
                        } else if (event.getX() - historicX > DELTA) {
                            Log.d("HHHHHHHHHH",  "DELTA");
                            FunctionDeleteRowWhenSlidingRight();
                            return true;
                        }
                        break;
                    default:
                        return false;*//*
               // }
                return false;
            }
        });*/






        // Toast.makeText(this, "Fuel efficiency Imperial", Toast.LENGTH_SHORT).show();
        return v;
    }

    private void FunctionDeleteRowWhenSlidingRight(int pos) {
        db.deleteBook(list.get(pos));
        updateingListView();
    }

    private void FunctionDeleteRowWhenSlidingLeft(int pos) {

        db.deleteBook(list.get(pos));
        updateingListView();

    }

    public void updateingListView() {
        list = db.getAllBooks();

        String[] rr = new String[list.size()];
        //initiate array
        for(int i=0;i<list.size();i++){
            rr[i]= i+"";
        }
        //  Log.d("HHHHHHHHHH listsize",list.size()+"");

        adapter=new MySimpleArrayAdapter(getActivity().getBaseContext(), rr, list);


        listView.setAdapter(adapter);


        // next thing you have to do is check if your adapter has changed
        adapter.notifyDataSetChanged();
    }


}
