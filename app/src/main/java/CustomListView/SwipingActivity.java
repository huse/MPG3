package CustomListView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.kpr.hus.mpg3.R;

import java.util.ArrayList;

/**
 * Created by f1 on 1/4/2016.
 */
public class SwipingActivity extends Activity {

    public View.OnTouchListener gestureListener;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      /*  ListView lv = (ListView)findViewById(R.id.listView);

        gestureListener = new View.OnTouchListener() {
            private int padding = 0;
            private int initialx = 0;
            private int currentx = 0;
            private  ViewHolder viewHolder;
            public boolean onTouch(View v, MotionEvent event) {
                if ( event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    padding = 0;
                    initialx = (int) event.getX();
                    currentx = (int) event.getX();
                    viewHolder = ((ViewHolder) v.getTag());
                }
                if ( event.getAction() == MotionEvent.ACTION_MOVE)
                {
                    currentx = (int) event.getX();
                    padding = currentx - initialx;
                }

                if ( event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL)
                {
                    padding = 0;
                    initialx = 0;
                    currentx = 0;
                }

                if(viewHolder != null)
                {
                    if(padding == 0)
                    {
                        v.setBackgroundColor(0xFF000000 );
                    }
                    if(padding > 75)
                    {
                        viewHolder.setRunning(true);
                    }
                    if(padding < -75)
                    {
                        viewHolder.setRunning(false);
                    }
                    v.setBackgroundColor(viewHolder.getColor());
                    viewHolder.icon.setImageResource(viewHolder.getImageId());
                    v.setPadding(padding, 0,0, 0);
                }
                return true;
            }
        };*/

       // ModelArrayAdapter adapter = new ModelArrayAdapter(this, getData(),gestureListener);
       // lv.setAdapter(adapter);
    }

    public ArrayList<Model> getData()
    {
        ArrayList<Model> models = new ArrayList<Model>();
        for(int a=0;a<10;a++)
        {
            Model m = new Model(String.format("Item %d", a));
            models.add(m);
        }
        return models;
    }

    public static class ViewHolder {
        protected TextView text;
        public ImageView icon;
        protected CheckBox checkbox;
        protected int position;
        protected Model model;
        private int color;
        private int imageid;
        public boolean running;

        public ViewHolder()
        {
            position = 0;
            imageid = R.drawable.bullet_go;
            color = 0xFFFFFFFF;
            running = false;
        }

        public int getColor() {
            return color;
        }
        public int getImageId() {
            return imageid;
        }
        public void setRunning(boolean running) {
            model.setRuning(running);
            if(running)
            {
                color = 0xFFffffb6;
                imageid = R.drawable.recycle_512;
            }
            else
            {
                imageid = R.drawable.bullet_go;
                color = 0xFFFFFFFF;
            }
        }
    }
}

