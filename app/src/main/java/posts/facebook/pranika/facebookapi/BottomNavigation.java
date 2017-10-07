package posts.facebook.pranika.facebookapi;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class BottomNavigation extends AppCompatActivity {
    TextView textView;
    private FirebaseAuth mauth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);
        mauth=FirebaseAuth.getInstance();
       // textView= (TextView) findViewById(R.id.welcome);
        Bundle bundle=getIntent().getExtras();


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
                        Intent intent2=new Intent(getApplicationContext(),AuthAffectedPatients.class);
                        startActivity(intent2);
                        break;
                    case R.id.graph:
                        Intent intent3=new Intent(getApplicationContext(),WeekGraph.class);
                        startActivity(intent3);




                }






                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(getApplicationContext(),DoctorSignUp.class);
        startActivity(intent);
    }
}
