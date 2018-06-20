package posts.facebook.pranika.facebookapi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class BottomNavigation extends BaseActivity {
    TextView textView;
    private FirebaseAuth mauth;
    String patientid="",selfpatientid="";
    String consistency="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        Intent intent = getIntent();
        String action = intent.getAction();
        Uri data = intent.getData();
        Log.d("selfpatientid","self"+data);
       // Log.d("substitute","subsitute"+patientid);
        if(data!=null){

            patientid=  data.getQueryParameter("patientid");
            selfpatientid=data.getQueryParameter("selfpatientid");

        }


        mauth=FirebaseAuth.getInstance();



        BottomNavigationView bottomNavigationView= (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.registered:
                      //  Toast.makeText(getApplicationContext(), "Registered", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.displaypatients:
                       // Toast.makeText(getApplicationContext(), "Registered", Toast.LENGTH_SHORT).show();
                        Intent intent1=new Intent(getApplicationContext(),RegisteredActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.affected:

                        //Toast.makeText(getApplicationContext(), "self patient"+selfpatientid, Toast.LENGTH_SHORT).show();


                        if(patientid!="" || selfpatientid!="") {


                            //***********************Auth Affected Patients********************
                        Intent intent2=new Intent(getApplicationContext(),AuthAffectedPatients.class);

                        SharedPreferences.Editor pEdit = pref.edit();

                        pEdit.putString("substitute_patientid",patientid);
                        pEdit.putString("self_patientid",selfpatientid);
//                        Toast.makeText(getApplicationContext(),"Substitute Patient ID"+patientid,Toast.LENGTH_LONG).show();
//                        Toast.makeText(getApplicationContext(),"Self Patient ID"+selfpatientid,Toast.LENGTH_LONG).show();
                        pEdit.commit();

                            intent2.putExtra("patientid",patientid);
                            intent2.putExtra("selfpatientid",selfpatientid);

                        startActivity(intent2);

                            //*******************Auth Affected Patients End****************************

                        }else{
                            Intent intent2=new Intent(getApplicationContext(),AffectedPatients.class);
                            startActivity(intent2);

                        }




                        break;
                    case R.id.graph:
                        SharedPreferences.Editor pEdit1 = pref.edit();
                        Intent intent3=new Intent(getApplicationContext(),WeekGraph.class);

                        pEdit1.putString("substitute_patientid",patientid);
                        pEdit1.putString("self_patientid",selfpatientid);
                        pEdit1.commit();

                        intent3.putExtra("patientid",patientid);
                        intent3.putExtra("selfpatientid",selfpatientid);

                        startActivity(intent3);
          }
           return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(getApplicationContext(),DoctorSignUp.class);
        startActivity(intent);
    }
}
