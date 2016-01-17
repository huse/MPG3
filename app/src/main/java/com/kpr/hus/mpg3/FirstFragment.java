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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import CustomListView.Model;
import CustomListView.ModelArrayAdapter;
import CustomListView.SwipingActivity;

//dESIGNED BY hOSEIN kAJEPOR

public class FirstFragment extends Fragment {

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
    MySQLiteHelper db1;
    ListView listView;
    ModelArrayAdapter adapter;
    View.OnTouchListener gestureListener;
    Button bt, bt2,bt7;
    EditText et1, etPrice, etMile, etGallon;
    TextView tvMPG, tvKM100;
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

        etMile = (EditText) v.findViewById(R.id.editText);
        etGallon = (EditText) v.findViewById(R.id.editText2);
        etPrice = (EditText) v.findViewById(R.id.editText6);
        tvMPG = (TextView) v.findViewById(R.id.textView4);
        tvKM100 = (TextView) v.findViewById(R.id.textView6);
        db1 = new MySQLiteHelper(getActivity().getBaseContext(),"first");
        listView = (ListView)v.findViewById(R.id.listView);

        updateingListView();

       bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Support.colorChange(v, "BLUE", "RED");
                try {
                    Double dd1 = Double.parseDouble(etMile.getText().toString());
                    Double dd2 = Double.parseDouble(etGallon.getText().toString());
                    Double dd;
                    dd = dd1 / dd2;
                    Double ff = truncateDouble(dd, 2);
                    String ss;
                    ss = ff.toString();
                    tvMPG.setText(ss);

                    Double dk1 = dd1 * 1.609344;
                    Double dk2 = dd2 * 3.785411784;
                    Double dkk = (100.0 * dk2) / dk1;
                    Double ff2 = truncateDouble(dkk, 2);
                    String sk = ff2.toString();
                    tvKM100.setText(sk);
                    sets(etMile.getText().toString(), etGallon.getText().toString(),
                            ss, sk);
                    InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    mgr.hideSoftInputFromWindow(etGallon.getWindowToken(), 0);
                    mgr.hideSoftInputFromWindow(etMile.getWindowToken(), 0);

                } catch (Exception e) {
                    tvKM100.setText("invalid");
                    tvMPG.setText("invalid");

                }
                Calendar c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH) + 1;
                int year = c.get(Calendar.YEAR);

                db1.addBook(new Data(month + "/" + day + "/" + year, etMile.getText().toString(), etGallon.getText().toString(), etPrice.getText().toString(), tvMPG.getText().toString()));

                updateingListView();

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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int pos, long id) {

                final SwipingActivity.ViewHolder viewHolder;
                ;

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
                    arg1.setBackgroundColor(0xFFFF5556);
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
                        db1.deleteBook(list.get(poss));
                        viewHolder.text.setText("");
                        viewHolder.text2.setText("Deleted");
                        viewHolder.text3.setText("");
                        viewHolder.text4.setText("");
                        viewHolder.text5.setText("");
                        viewHolder.text6.setText("");
                        Log.d("List", list.get(poss) + "");
                        Log.d("positon", poss + "");
                    }


                });

            }

        });
        return v;
    }

    private void FunctionDeleteRowWhenSlidingRight(int pos) {
        db1.deleteBook(list.get(pos));
        updateingListView();
    }

    private void FunctionDeleteRowWhenSlidingLeft(int pos) {

        db1.deleteBook(list.get(pos));
        updateingListView();

    }

    public ArrayList<Model> getData()
    {
        list = db1.getAllBooks();

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
