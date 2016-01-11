package com.kpr.hus.mpg3;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerTitleStrip;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SecondFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SecondFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecondFragment extends Fragment {
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

    Button bt, bt2;
    EditText et1, et2, et3, et4;
    TextView tv1, tv2;
    String a, b, c, d;

    public void sets(String a, String b, String c, String d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public String gets() {
        return "Km = " + a + "   Liter = " + b + "    Liter per 100 Km = "
                + c + "    Mile per Gallon = " + d;
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
    public static SecondFragment newInstance(int pageNumber) {
        SecondFragment fragment = new SecondFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, 5);
        fragment.setArguments(args);

        return fragment;
    }

    public SecondFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
/*        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_second, container, false);
        Support.colorBackChange(v,0,255,55,255,155,155,200,255);
        //getActionBar().setDisplayHomeAsUpEnabled(true);

        bt = (Button) v.findViewById(R.id.button3);
        bt2 = (Button) v.findViewById(R.id.button4);
        et3 = (EditText) v.findViewById(R.id.editText);
        et4 = (EditText) v.findViewById(R.id.editText2);
        tv1 = (TextView) v.findViewById(R.id.textView4);
        tv2 = (TextView) v.findViewById(R.id.textView6);



        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Double dd1 = Double.parseDouble(et3.getText().toString());
                    Double dd2 = Double.parseDouble(et4.getText().toString());
                    Double dd;
                    dd =  (dd2/ dd1)*100;
                    Double ff = truncateDouble(dd, 2);
                    String ss;
                    ss = ff.toString();
                    tv1.setText(ss + "   Liter per 100 KM");

                    Double dk1 = dd1 / 1.609344;
                    Double dk2 = dd2 / 3.785411784;
                    Double dkk = dk1 / dk2;
                    Double ff2 = truncateDouble(dkk, 2);
                    String sk = ff2.toString();
                    tv2.setText(sk + "   Mile per Gallon");
                    sets(et3.getText().toString(), et4.getText().toString(),
                            ss, sk);
                    InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    mgr.hideSoftInputFromWindow(et4.getWindowToken(), 0);
                    mgr.hideSoftInputFromWindow(et3.getWindowToken(), 0);

                } catch (Exception e) {
                    tv2.setText("invalid");
                    tv1.setText("invalid");

                }

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
        //Toast.makeText(this, "Fuel efficiency Metric", Toast.LENGTH_SHORT).show();


        return v;
    }




}
