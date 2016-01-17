package CustomListView;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kpr.hus.mpg3.R;

import java.util.ArrayList;
import java.util.regex.Pattern;

import CustomListView.SwipingActivity.ViewHolder;
/**
 * Created by hosen on 1/4/2016.
 */
public class ModelArrayAdapter extends ArrayAdapter<Model>
{
    private ArrayList<Model> allModelItemsArray;
    private Activity context;
    private LayoutInflater inflator;
    private View.OnTouchListener listener;


    public ModelArrayAdapter(Activity context, ArrayList<Model> list,View.OnTouchListener _listener) {
        super(context, R.layout.list_row, list);
        this.listener = _listener;
        this.context = context;
        this.allModelItemsArray = new ArrayList<Model>();

        this.allModelItemsArray.addAll(list);
     //   Collections.reverse(allModelItemsArray);
        inflator = context.getLayoutInflater();
    }
   // private Map<Integer, View> myViews = new HashMap<Integer, View>();
/*   @Override
   public Model getItem(int position) {
       return super.getItem(super.getCount() - position - 1);
   }*/
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;
        if(position > allModelItemsArray.size())
            return null;
        Model m = allModelItemsArray.get(position);
        final ViewHolder viewHolder = new ViewHolder();
        ViewHolder holder = null;
        if (convertView == null) {

        } else {
           // view = convertView;
           // holder = (ViewHolder) convertView.getTag();


        }

        view = inflator.inflate(R.layout.list_row, null);

        view.setTag(viewHolder);
        viewHolder.position = position;

       // holder = viewHolder;
       // myViews.put(position, view);
        viewHolder.position = position;
        viewHolder.text = (TextView) view.findViewById(R.id.label1);
        viewHolder.text2 = (TextView) view.findViewById(R.id.label2);
        viewHolder.text3 = (TextView) view.findViewById(R.id.label3);
        viewHolder.text4 = (TextView) view.findViewById(R.id.label4);
        viewHolder.text5 = (TextView) view.findViewById(R.id.label5);
        viewHolder.text6 = (TextView) view.findViewById(R.id.label6);
        viewHolder.icon = (ImageView) view.findViewById(R.id.icon);

        holder = ((ViewHolder) view.getTag());
        if(this.listener != null)
            view.setOnTouchListener(this.listener);

        holder.model = m;
        holder.position = position;

                // in this line I modified the output as several textView.
        String string=m.getName();
        String[] parts = string.split(Pattern.quote(",")); // Split on period.
        holder.text.setText(parts[0]);
        holder.text2.setText(parts[1]);
        holder.text3.setText(parts[2]);
        holder.text4.setText(parts[3]);
        holder.text5.setText(parts[4]);
        holder.text6.setText(parts[5]);
        return view;
    }
}
