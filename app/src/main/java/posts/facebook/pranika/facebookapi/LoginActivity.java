package posts.facebook.pranika.facebookapi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends BaseActivity {

    private EditText email;
    private EditText password;
    private Button loginbutton;
    private FirebaseAuth mauth;



    private FirebaseAuth.AuthStateListener authStateListener;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mauth=FirebaseAuth.getInstance(myApp);
        email= (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.pwd);
        loginbutton = (Button) findViewById(R.id.login);

        final String orgid=pref.getString("orgid","");

        authStateListener=new FirebaseAuth.AuthStateListener(){

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){


                if(firebaseAuth.getCurrentUser()!=null)
                {
//                         if(orgid!=null){
//                             pref.edit().putString("orgid",orgid);
//                        intent=new Intent(getApplicationContext(),BottomNavigationOrganization.class);
//
//                        startActivity(intent);
//
//
//                    }
//                    else if(orgid==null) {
//

                        intent = new Intent(getApplicationContext(), BottomNavigation.class);
                        startActivity(new Intent(getApplicationContext(), BottomNavigation.class));
//                    }
//
                }
            }
        };
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startSignIn();

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mauth.addAuthStateListener(authStateListener);
    }

    private void startSignIn()
    {

        String emailtext = email.getText().toString();
        String pwd = password.getText().toString();

        if(TextUtils.isEmpty(emailtext) || TextUtils.isEmpty(pwd))
        {
            Toast.makeText(getApplicationContext(),"Fields empty",Toast.LENGTH_LONG).show();

        }
        else {
            mauth.signInWithEmailAndPassword(emailtext, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (!task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "LOGIN FAILED", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "LOGIN SUCCESS", Toast.LENGTH_LONG).show();
                        intent = new Intent(getApplicationContext(),BottomNavigation.class);
                        startActivity(new Intent(getApplicationContext(),BottomNavigation.class));


                    }

                }
            });
        }
    }
}
