package posts.facebook.pranika.facebookapi;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabbedActivity extends AppCompatActivity implements MonthFragment.OnClicklistner,WeekFeed.OnWeekClicklistner,YearFeed.OnYearClicklistner{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_tabbed, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showActivity(HashMap feed, int position, List<Map<String,?>> feedList) {

        HashMap currentfeed = null;
        //if (position >= 0 && position < feedList.size()){
            currentfeed=  (HashMap) feedList.get(position);
        //}
        Intent intent=new Intent(getApplicationContext(),DetailActivity.class);
        intent.putExtra("hashmap",currentfeed);
        startActivity(intent);


      //  getSupportFragmentManager().beginTransaction().

              //  replace(R.id.detail, DetailFragment.newInstance(currentfeed)).addToBackStack("frame").commit();
    }

    @Override
    public void showWeekActivity(HashMap feed, int position, List<Map<String, ?>> feedList) {
        HashMap currentfeed = null;
        //if (position >= 0 && position < feedList.size()){
        currentfeed=  (HashMap) feedList.get(position);
        //}
        Intent intent=new Intent(getApplicationContext(),DetailWeekActivity.class);
        intent.putExtra("hashweek",currentfeed);
        startActivity(intent);

    }

    @Override
    public void showYearActivity(HashMap feed, int position, List<Map<String, ?>> feedList) {
        HashMap currentfeed = null;
        //if (position >= 0 && position < feedList.size()){
        currentfeed=  (HashMap) feedList.get(position);
        //}
        Intent intent=new Intent(getApplicationContext(),DetailYearActivity.class);
        intent.putExtra("hashyear",currentfeed);
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(getApplicationContext(),BottomNavigation.class);
        startActivity(intent);
    }
    /**
     * A placeholder fragment containing a simple view.
     */


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position)
            {
                case 0:
                    SingleFragment single=new SingleFragment();
                    return single;
                case 1:
                    MonthFragment month=new MonthFragment();
                    return month;
                case 2:
                    WeekFeed weekFeed=new WeekFeed();
                    return weekFeed;
                case 3:
                    YearFeed yearFeed=new YearFeed();
                    return yearFeed;


            }
            return null;
        }


        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SINGLE FEED";
                case 1:
                    return "MONTH FEED";
                case 2:
                    return "WEEK FEED";
                case 3:
                    return "YEAR FEED";

            }
            return null;
        }
    }
}
