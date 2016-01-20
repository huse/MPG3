package com.kpr.hus.mpg3;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import CustomListView.Model;
import CustomListView.ModelArrayAdapter;
import CustomListView.SwipingActivity;


public class SecondFragment extends Fragment {
    private SwipingActivity.ViewHolder viewHolder2;
    private View view2;

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
    List<Data> list;
    MySQLiteHelper db2;
    ListView listView;
    View.OnTouchListener gestureListener;
    ModelArrayAdapter adapter;

    Button bt2Calculate, bt2Send;
    EditText et1, et2, etKM, etLiter,et2Price;
    TextView tvKM100, tvMPG;
    String a, b, c, d;

    public void sets(String a, String b, String c, String d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public String gets() {
        return "Km = " + etKM.getText() + "   Liter = " + etLiter.getText() + "    Liter per 100 Km = "
                + tvKM100.getText() + "    Mile per Gallon = " + tvMPG.getText()+ "    Price = " +et2Price.getText();
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

        bt2Calculate = (Button) v.findViewById(R.id.button3);
        bt2Send = (Button) v.findViewById(R.id.button4);
        etKM = (EditText) v.findViewById(R.id.editText2Km);
        etLiter = (EditText) v.findViewById(R.id.editText2Liter);
        et2Price = (EditText) v.findViewById(R.id.editText2Price);
        tvKM100 = (TextView) v.findViewById(R.id.textView2KM);
        tvMPG = (TextView) v.findViewById(R.id.textView2MPG);
        db2 = new MySQLiteHelper(getActivity().getBaseContext(),"second");

        listView = (ListView)v.findViewById(R.id.listView2);
        mAdView = (AdView) v.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        updateingListView();

        bt2Calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Support.colorChange(v, "BLUE", "RED");
                    Double dd1 = Double.parseDouble(etKM.getText().toString());
                    Double dd2 = Double.parseDouble(etLiter.getText().toString());
                    Double dd;
                    dd = (dd2 / dd1) * 100;
                    Double ff = truncateDouble(dd, 2);
                    String ss;
                    ss = ff.toString();
                    tvKM100.setText(ss);

                    Double dk1 = dd1 / 1.609344;
                    Double dk2 = dd2 / 3.785411784;
                    Double dkk = dk1 / dk2;
                    Double ff2 = truncateDouble(dkk, 2);
                    String sk = ff2.toString();
                    tvMPG.setText(sk);
                    sets(etKM.getText().toString(), etLiter.getText().toString(),
                            ss, sk);
                    InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    mgr.hideSoftInputFromWindow(etLiter.getWindowToken(), 0);
                    mgr.hideSoftInputFromWindow(etKM.getWindowToken(), 0);

                } catch (Exception e) {
                    tvMPG.setText("invalid");
                    tvKM100.setText("invalid");

                }
                Calendar c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH) + 1;
                int year = c.get(Calendar.YEAR);

                db2.addBook(new Data(month + "/" + day + "/" + year, etKM.getText().toString(), etLiter.getText().toString(), et2Price.getText().toString(), tvKM100.getText().toString()));

                updateingListView();
            }
        });


        bt2Send.setOnClickListener(new View.OnClickListener() {
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
        //Toast.makeText(this, "Fuel efficiency Metric", Toast.LENGTH_SHORT).show();
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
                        db2.deleteBook(list.get(poss));
                        viewHolder.text.setText("");
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
        final SoftKeyboardStateWatcher softKeyboardStateWatcher = new SoftKeyboardStateWatcher(v);
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

        return v;
    }
    public ArrayList<Model> getData()
    {
        list = db2.getAllBooks();

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
