package com.kpr.hus.mpg3;

/**
 * Created by f1 on 10/27/2015.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MySimpleArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;
    List vv;

    public MySimpleArrayAdapter(Context context, String[] values, List vv) {



        super(context, -1, values);
        this.context = context;
        this.values = values;
        this.vv=vv;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.textView11);
        textView.setText(vv.get(position).toString());




        return rowView;
    }

}
