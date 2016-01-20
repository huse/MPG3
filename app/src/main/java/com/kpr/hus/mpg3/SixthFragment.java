package com.kpr.hus.mpg3;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class SixthFragment extends Fragment {
    /**
     * The argument key for the page number this fragment represents.
     */
    public static final String ARG_PAGE = "page";
    TextView tv1, tv2, tv3;
    /**
     * The fragment's page number, which is set to the argument value for {@link #ARG_PAGE}.
     */
    private int mPageNumber;
    private AdView mAdView;
    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static SixthFragment newInstance(int pageNumber) {
        SixthFragment fragment = new SixthFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public SixthFragment() {
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
                .inflate(R.layout.fragment_sixth, container, false);
        Support.colorBackChange(rootView,0,50, 150, 100,155,50, 150, 100);
        mAdView = (AdView) rootView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

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
        });
/*
        tvMPG = (TextView) rootView.findViewById(R.id.textView3);
        tvKM100 = (TextView) rootView.findViewById(R.id.textView4);
        tv3 = (TextView) rootView.findViewById(R.id.textView6);
        tvMPG.setText("All car manufacture companies release their products fuel efficiency in scale of MPG or Liter per 100Km, but those numbers are actually just an estimates or the ideal numbers that could be achieved only under some certain conditions such as good quality fuel, brand new tires without any attrition and erosion, flat road, and without any passengers, but not all of us have all those perfect parameters ! so it would be nice to have a better understanding  of our automobile fuel consuming to see if ours is the green one or not.");
        tvKM100.setText(" 1- Full the gas tank at the gas station. &#xA;\n" +
                "                          2- Reset the odometer(trip) number. &#xA;\n" +
                "                          3- Next time at the gas station note down the odometer number line. &#xD;\n" +
                "                          4- Full the gas tank and note down the amount of Gallon or Litter. &#xD;\n" +
                "                          5- Now you have all the numbers you need to calculate the accurate fuel efficiency of your car.");
        tv3.setText("you can save MPG or Liter/100Km to also calculate the distance you can drive");*/
        return rootView;
    }

    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return mPageNumber;
    }
}
