package posts.facebook.pranika.facebookapi;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.HashMap;

public class DetailYearActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_year);
        Intent intent = getIntent();
        HashMap hashMap = (HashMap)intent.getSerializableExtra("hashyear");
//        Log.v("HashMapTest", hashMap.get("key").toString());

        getSupportFragmentManager().beginTransaction().

                replace(R.id.detailyear, DetailFragment.newInstance(hashMap)).commit();
    }

    @Override
    public void onBackPressed(){
        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            Intent intent=new Intent(getApplicationContext(),TabbedActivity.class);
            startActivity(intent);
            fm.popBackStack();
        } else {
            Log.i("MainActivity", "nothing on backstack, calling super");
            super.onBackPressed();
        }
    }
}
