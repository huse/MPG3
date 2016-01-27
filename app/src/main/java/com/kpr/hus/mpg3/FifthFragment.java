package com.kpr.hus.mpg3;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * A fragment representing a single step in a wizard. The fragment shows a dummy title indicating
 * the page number, along with some dummy text.
 *
 * <p>This class is used by the {@link CardFlipActivity} and {@link
 * ScreenSlideActivity} samples.</p>
 */
public class FifthFragment extends Fragment {
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
    private AdView mAdView;
    Button bt1, bt2,bt3,bt4;
    EditText et1, et2, et3, et4;
    TextView tv1, tv2,tv3,tv4;
    String a, b, c, d;

    public static final String ARG_PAGE = "page";

    /**
     * The fragment's page number, which is set to the argument value for {@link #ARG_PAGE}.
     */
    private int mPageNumber;

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static FifthFragment newInstance(int pageNumber) {
        FifthFragment fragment = new FifthFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public FifthFragment() {
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
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_fifth, container, false);
        Support.colorBackChange(rootView,0,33, 232, 123,155,33, 232, 123);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        bt1 = (Button) rootView.findViewById(R.id.button1);
        bt2 = (Button) rootView.findViewById(R.id.button2);
        bt3 = (Button) rootView.findViewById(R.id.button3);
        bt4 = (Button) rootView.findViewById(R.id.button4);
        et1 = (EditText) rootView.findViewById(R.id.editText1);
        et2 = (EditText) rootView.findViewById(R.id.editText2);
        et3 = (EditText) rootView.findViewById(R.id.editText3);
        et4 = (EditText) rootView.findViewById(R.id.editText4);
        tv1 = (TextView) rootView.findViewById(R.id.textView11);
        tv2 = (TextView) rootView.findViewById(R.id.textView12);
        tv3 = (TextView) rootView.findViewById(R.id.textView13);
        tv4 = (TextView) rootView.findViewById(R.id.textView14);
        mAdView = (AdView) rootView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Double dd1 = Double.parseDouble(et1.getText().toString());

                    Double dd;
                    dd =  (1.609344* dd1);
                    Double ff = truncateDouble(dd, 4);
                    String ss;
                    ss = ff.toString();
                    tv1.setText(ss + " ");


                    InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    mgr.hideSoftInputFromWindow(et1.getWindowToken(), 0);
                    mgr.hideSoftInputFromWindow(et2.getWindowToken(), 0);
                } catch (Exception e) {

                    tv1.setText("invalid");

                }

            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Double dd1 = Double.parseDouble(et2.getText().toString());

                    Double dd;
                    dd =  (dd1*3.785411784);
                    Double ff = truncateDouble(dd, 4);
                    String ss;
                    ss = ff.toString();
                    tv2.setText(ss + " ");




                    InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    mgr.hideSoftInputFromWindow(et1.getWindowToken(), 0);
                    mgr.hideSoftInputFromWindow(et2.getWindowToken(), 0);
                } catch (Exception e) {

                    tv2.setText("invalid");

                }

            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Double dd1 = Double.parseDouble(et3.getText().toString());

                    Double dd;
                    dd =  (dd1/1.609344);
                    Double ff = truncateDouble(dd, 4);
                    String ss;
                    ss = ff.toString();
                    tv3.setText(ss + " ");




                    InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    mgr.hideSoftInputFromWindow(et1.getWindowToken(), 0);
                    mgr.hideSoftInputFromWindow(et2.getWindowToken(), 0);
                } catch (Exception e) {

                    tv3.setText("invalid");

                }

            }
        });
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Double dd1 = Double.parseDouble(et4.getText().toString());

                    Double dd;
                    dd = (dd1 / 3.785411784);
                    Double ff = truncateDouble(dd, 4);
                    String ss;
                    ss = ff.toString();
                    tv4.setText(ss + " ");


                    InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    mgr.hideSoftInputFromWindow(et1.getWindowToken(), 0);
                    mgr.hideSoftInputFromWindow(et2.getWindowToken(), 0);
                } catch (Exception e) {

                    tv4.setText("invalid");

                }

            }
        });



       // Toast.makeText(this, "Converter", Toast.LENGTH_LONG).show();
       // final SoftKeyboardStateWatcher softKeyboardStateWatcher = new SoftKeyboardStateWatcher(rootView);
        // Add listener
/*        softKeyboardStateWatcher.addSoftKeyboardStateListener(new SoftKeyboardStateWatcher.SoftKeyboardStateListener() {
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
        this.getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        return rootView;
    }

    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return mPageNumber;
    }
}
