package com.kpr.hus.mpg3;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
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

//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import CustomListView.Model;
import CustomListView.ModelArrayAdapter;
import CustomListView.SwipingActivity;

/**
 * A fragment representing a single step in a wizard. The fragment shows a dummy title indicating
 * the page number, along with some dummy text.
 *
 * <p>This class is used by the {@link CardFlipActivity} and {@link
 * ScreenSlideActivity} samples.</p>
 */
public class ForthFragment extends Fragment {
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
    public void implicitSendText(View v){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, "Message");
        intent.setType("text/plain");
        startActivity(intent);
    }
//    private AdView mAdView;
    List<Data> list;
    MySQLiteHelper db4;
    ListView listView;
    ModelArrayAdapter adapter;
    View.OnTouchListener gestureListener;
    Button bt4Calculate, bt4Send;
    EditText et4km100, et4Liter;
    TextView tv4Km, tv4Mile;
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
                " " +"Liter per 100Km = " + et4km100.getText() + "   Fuel in your tank, Liter = " + et4Liter.getText() + "    The Distance you can go - in Km = "
                + tv4Km.getText() + "    The Distance you can go - in Mile = " + tv4Mile.getText();
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
    // Button bt1Calculate = new View.findViewById(R.id.button);
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_forth, container, false);
        Support.colorBackChange(rootView,0,150,150,255,155,150,150,255);
       // getActionBar().setDisplayHomeAsUpEnabled(true);

        bt4Calculate = (Button) rootView.findViewById(R.id.button5);
        bt4Send = (Button) rootView.findViewById(R.id.button6);
        et4km100 = (EditText) rootView.findViewById(R.id.editText5);
        et4Liter = (EditText) rootView.findViewById(R.id.editText6);
        tv4Km  = (TextView) rootView.findViewById(R.id.textView8);
        tv4Mile = (TextView) rootView.findViewById(R.id.textView10);
        db4 = new MySQLiteHelper(getActivity().getBaseContext(),"forth");
        listView = (ListView)rootView.findViewById(R.id.listView4);
//        mAdView = (AdView) rootView.findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

        updateingListView();


        bt4Calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Support.colorChange(v, "BLUE", "RED");
                    Double dd1 = Double.parseDouble(et4km100.getText().toString());
                    Double dd2 = Double.parseDouble(et4Liter.getText().toString());
                    Double dd;
                    dd = (dd2 * 100) / dd1;
                    Double ff = truncateDouble(dd, 2);
                    String ss;
                    ss = ff.toString();
                    tv4Km.setText(ss + " ");

                    //Double dk1 = dd1 / 1.609344;
                    //Double dk2 = dd2 / 3.785411784;
                    Double dkk = dd / 1.609344;
                    Double ff2 = truncateDouble(dkk, 2);
                    String sk = ff2.toString();
                    tv4Mile.setText(sk + " ");
                    sets(et4km100.getText().toString(), et4Liter.getText().toString(),
                            ss, sk);
                    InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    mgr.hideSoftInputFromWindow(et4Liter.getWindowToken(), 0);
                    mgr.hideSoftInputFromWindow(et4km100.getWindowToken(), 0);

                } catch (Exception e) {
                    tv4Mile.setText("invalid");
                    tv4Km.setText("invalid");

                }
                Calendar c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH) + 1;
                int year = c.get(Calendar.YEAR);

                db4.addBook(new Data(month + "/" + day + "/" + year, et4km100.getText().toString(), et4Liter.getText().toString(), tv4Km.getText().toString(), tv4Mile.getText().toString()));

                updateingListView();
            }
        });



        bt4Send.setOnClickListener(new View.OnClickListener() {
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
       // Toast.makeText(this, "Calculation Distance Metric", Toast.LENGTH_SHORT).show();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int pos, long id) {
                if (viewHolder2 != null) {
                    view2.setBackgroundColor(listView.getSolidColor());
                    viewHolder2.icon.setVisibility(View.GONE);
                    Log.d("Deselected", viewHolder2.position + "");
                }

                final SwipingActivity.ViewHolder viewHolder;
                view2 = arg1;
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
                        db4.deleteBook(list.get(poss));
                      // viewHolder.text.setText("");
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
/*        final SoftKeyboardStateWatcher softKeyboardStateWatcher = new SoftKeyboardStateWatcher(rootView);
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

    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return mPageNumber;
    }
    public ArrayList<Model> getData()
    {
        list = db4.getAllBooks();

        ArrayList<Model> models = new ArrayList();

        for(int a=0;a<list.size();a++)
        {
            Model m = new Model(list.get(a).toString());
            models.add(m);
        }
        return models;
    }
    public void updateingListView() {

        adapter = new ModelArrayAdapter(getActivity(), getData(),gestureListener);
        listView.setAdapter(adapter);
        // adapter=new MySimpleArrayAdapter(getActivity().getBaseContext(), rr, list);



        // check if the adapter has changed
        adapter.notifyDataSetChanged();
    }

}
