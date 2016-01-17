package com.kpr.hus.mpg3;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A fragment representing a single step in a wizard. The fragment shows a dummy title indicating
 * the page number, along with some dummy text.
 *
 * <p>This class is used by the {@link CardFlipActivity} and {@link
 * ScreenSlideActivity} samples.</p>
 */
public class ForthFragment extends Fragment {
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

    Button bt5, bt6;
    EditText et1, et2, et5, et6;
    TextView tv1, tv2;
    String a, b, c, d;

    public void sets(String a, String b, String c, String d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public String gets() {
        return "Distance you can go by your fuel:" +
                "" +
                "" +
                " " +"Liter per 100Km = " + a + "   Fuel in your tank, Liter = " + b + "    The Distance you can go - in Km = "
                + c + "    The Distance you can go - in Mile = " + d;
    }
    public static final String ARG_PAGE = "page";

    /**
     * The fragment's page number, which is set to the argument value for {@link #ARG_PAGE}.
     */
    private int mPageNumber;

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static ForthFragment newInstance(int pageNumber) {
        ForthFragment fragment = new ForthFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ForthFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
    }
    // Button bt = new View.findViewById(R.id.button);
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_forth, container, false);
        Support.colorBackChange(rootView,0,150,150,255,155,150,150,255);
       // getActionBar().setDisplayHomeAsUpEnabled(true);

        bt5 = (Button) rootView.findViewById(R.id.button5);
        bt6 = (Button) rootView.findViewById(R.id.button6);
        et5 = (EditText) rootView.findViewById(R.id.editText5);
        et6 = (EditText) rootView.findViewById(R.id.editText6);
        tv1 = (TextView) rootView.findViewById(R.id.textView8);
        tv2 = (TextView) rootView.findViewById(R.id.textView10);
        //  tvMPG.setTextSize(20);
        // tvKM100.setTextSize(20)


        bt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Double dd1 = Double.parseDouble(et5.getText().toString());
                    Double dd2 = Double.parseDouble(et6.getText().toString());
                    Double dd;
                    dd =  (dd2*100)/ dd1;
                    Double ff = truncateDouble(dd, 2);
                    String ss;
                    ss = ff.toString();
                    tv1.setText(ss + " ");

                    //Double dk1 = dd1 / 1.609344;
                    //Double dk2 = dd2 / 3.785411784;
                    Double dkk = dd/1.609344;
                    Double ff2 = truncateDouble(dkk, 2);
                    String sk = ff2.toString();
                    tv2.setText(sk + " ");
                    sets(et5.getText().toString(), et6.getText().toString(),
                            ss, sk);
                    InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    mgr.hideSoftInputFromWindow(et6.getWindowToken(), 0);
                    mgr.hideSoftInputFromWindow(et5.getWindowToken(), 0);

                } catch (Exception e) {
                    tv2.setText("invalid");
                    tv1.setText("invalid");

                }

            }
        });



        bt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("text/plain");
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, gets());
                startActivity(intent);
            }


        });
       // Toast.makeText(this, "Calculation Distance Metric", Toast.LENGTH_SHORT).show();

        return rootView;
    }

    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return mPageNumber;
    }
}
