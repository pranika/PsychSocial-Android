package posts.facebook.pranika.facebookapi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AffectedPatients extends BaseActivity implements AffectedPatientsFragment.OnItemClickListner {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affected_patients);



        AffectedPatientsFragment fragment = new AffectedPatientsFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.framesingle, fragment);
        fragmentTransaction.commit();

    }

    @Override
    public void showFeeds(HashMap feed, int position, List<Map<String, ?>> feedList) {
        HashMap currentfeed = null;
        //if (position >= 0 && position < feedList.size()){
        currentfeed=  (HashMap) feedList.get(position);
        String selfpatientid=  currentfeed.get("userid").toString();
        //}
        Intent intent=new Intent(getApplicationContext(),AuthAffectedPatients.class);
        //                        Intent intent2=new Intent(getApplicationContext(),AuthAffectedPatients.class);

                        SharedPreferences.Editor pEdit = pref.edit();

                       // pEdit.putString("substitute_patientid",patientid);
                        pEdit.putString("self_patientid",selfpatientid);
//                        Toast.makeText(getApplicationContext(),"Substitute Patient ID"+patientid,Toast.LENGTH_LONG).show();
//                        Toast.makeText(getApplicationContext(),"Self Patient ID"+selfpatientid,Toast.LENGTH_LONG).show();
                        pEdit.commit();

                        intent.putExtra("selfpatientid",selfpatientid);


        startActivity(intent);

    }

}
