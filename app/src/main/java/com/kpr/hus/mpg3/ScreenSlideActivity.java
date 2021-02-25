package com.kpr.hus.mpg3;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.core.app.NavUtils;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.legacy.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.PagerTitleStrip;
import androidx.viewpager.widget.ViewPager;



/**
 * Demonstrates a "screen-slide" animation using a {@link ViewPager}. Because {@link ViewPager}
 * automatically plays such an animation when calling {@link ViewPager#setCurrentItem(int)}, there
 * isn't any animation-specific code in this sample.
 *
 * <p>This sample shows a "next" button that advances the user to the next step in a wizard,
 * animating the current screen out (to the left) and the next screen in (from the right). The
 * reverse animation is played when the user presses the "previous" button.</p>
 *
 * @see ScreenSlidePageFragment
 */
public class ScreenSlideActivity extends FragmentActivity {
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES =6;
    public String title="";

    public void setTitle2(int p){
        PagerTitleStrip  pagerTitleStrip = (PagerTitleStrip) findViewById(R.id.pager_title_strip);
        switch (p) {
            case 0:
                title ="MPG";

              ////pagerTitleStrip.setBackgroundColor(Color.argb(255, 50, 50, 100));
                break;
            case 1:

                //Support.colorBackChange(pagerTitleStrip,0, 50, 50, 100,255, 50, 50, 100);
                pagerTitleStrip.setBackgroundColor(Color.argb(255, 50, 50, 100));
                title ="Liter/100KM";
                break;
            case 2:

               // Support.colorBackChange(pagerTitleStrip,0, 55,255,255,255,55,255,255);
                pagerTitleStrip.setBackgroundColor(Color.argb(255,155,200,255));
                title ="Distance Imperial";
                break;
            case 3:

               // Support.colorBackChange(pagerTitleStrip,0,255,117,50,255,255,117,50);
               pagerTitleStrip.setBackgroundColor(Color.argb(255,255,117,50));
                title ="Distance Metric";
                break;
            case 4:

               // Support.colorBackChange(pagerTitleStrip,0,150,150,255,255,150,150,255);
                pagerTitleStrip.setBackgroundColor(Color.argb(255,150,150,255));
                title ="Converter";
                break;
            case 5:
               // Support.colorBackChange(pagerTitleStrip,0,33, 232, 123,255,33, 232, 123);
                pagerTitleStrip.setBackgroundColor(Color.argb(255, 33, 232, 123));
                title ="Description";

                break;

default:
   // Support.colorBackChange(pagerTitleStrip,0,50, 150, 100,255,50, 150, 100);
    pagerTitleStrip.setBackgroundColor(Color.argb(255, 50, 150, 100));
        }

    }
    public String getTitle2(){
     /*  if (title=="Description"){
            PagerTitleStrip  pagerTitleStrip = (PagerTitleStrip) findViewById(R.id.pager_title_strip);
            pagerTitleStrip.setBackgroundColor(Color.argb(255, 50, 150, 100));
        }
        if (title=="Converter"){
            PagerTitleStrip  pagerTitleStrip = (PagerTitleStrip) findViewById(R.id.pager_title_strip);
            pagerTitleStrip.setBackgroundColor(Color.argb(255, 255, 50, 10));
        }*/

        return this.title;
    }
    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);
        setTitle2(0);

        //startActivity(new Intent(ScreenSlideActivity.this, FirstFragment.class));
        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());

        mPager.setAdapter(mPagerAdapter);
        //mPager.setPageTransformer(true, new DepthPageTransformer());
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {

                // When changing pages, reset the action bar actions since they are dependent
                // on which page is currently active. An alternative approach is to have each
                // fragment expose actions itself (rather than the activity exposing actions),
                // but for simplicity, the activity provides the actions in this sample.
                invalidateOptionsMenu();
            }
        });

    }
    public void onClick(View v){



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
      //  getMenuInflater().inflate(R.menu.activity_screen_slide, menu);

        menu.findItem(R.id.action_previous).setEnabled(mPager.getCurrentItem() > 0);

        // Add either a "next" or "finish" button to the action bar, depending on which page
        // is currently selected.
        MenuItem item = menu.add(Menu.NONE, R.id.action_next, Menu.NONE,
                (mPager.getCurrentItem() == mPagerAdapter.getCount()-1)
                        ? R.string.action_finish
                        : R.string.action_next);
        MenuItemCompat.setShowAsAction(item, MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Navigate "up" the demo structure to the launchpad activity.
                // See http://developer.android.com/design/patterns/navigation.html for more.
                NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
                return true;

            case R.id.action_previous:
                // Go to the previous step in the wizard. If there is no previous step,
                // setCurrentItem will do nothing.
                mPager.setCurrentItem(mPager.getCurrentItem() - 1);
                return true;

            case R.id.action_next:
                // Advance to the next step in the wizard. If there is no next step, setCurrentItem
                // will do nothing.
                mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A simple pager adapter that represents 5 {@link ScreenSlidePageFragment} objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentPagerAdapter{
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
       // public Fragment getItem(int position) { return FirstFragment.newInstance("a","v");  }
        public Fragment getItem(int position) {

            switch (position) {
                case 0:

                    return FirstFragment.newInstance(position);

                case 1:

                    return SecondFragment.newInstance(position);

                case 2:

                    return ThirdFragment.newInstance(position);
                case 3:

                    return ForthFragment.newInstance(position);
                case 4:

                    return FifthFragment.newInstance(position);
 /*               case 5:

                    return SixthFragment.newInstance(position);
*/
                default:
                    return SixthFragment.newInstance(position);
            }


              }
      //  public Fragment getItem(int position) {return ScreenSlidePageFragment.create(position);}
        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        //PUT TITLE FOR EACH FRAGMENT
        @Override
        public CharSequence getPageTitle(int position) {

           setTitle2(position);

            return getTitle2();
        }
    }

    private ScreenSlideActivity selectedFragment;

  /*  @Override
    public void onBackPressed() {

*//*
        this.finish();


        super.onBackPressed();*//*

    }*/


}
