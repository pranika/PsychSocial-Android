package posts.facebook.pranika.facebookapi;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import posts.facebook.pranika.facebookapi.DaggerApp.DaggerApplication;


public class OrganizationLogin extends BaseActivity {

    private EditText email;

    private EditText password;
    private Button loginbutton;
    private FirebaseAuth mauth;

    String url= "";
    List<Map<String,?>> doctorList;

    private FirebaseAuth.AuthStateListener authStateListener;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_login);
        url= "http://"+((DaggerApplication)this.getApplication()).getIpaddress()+"/Doctors/getDoctors";

        doctorList=new ArrayList<Map<String, ?>>();
        mauth=FirebaseAuth.getInstance();


        email= (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.pwd);
        loginbutton = (Button) findViewById(R.id.login);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {

            pref.edit().putString("orgid",user.getUid()).commit();


            Toast.makeText(getApplicationContext(),"orag id"+user.getUid(),Toast.LENGTH_LONG).show();


            intent = new Intent(getApplicationContext(), BottomNavigationOrganization.class);
//
            startActivity(intent);
        } else {
            // No user is signed in
        }



        authStateListener=new FirebaseAuth.AuthStateListener(){

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){

                if(mauth.getCurrentUser()!=null)
                {


                    intent = new Intent(getApplicationContext(), BottomNavigationOrganization.class);
//
                    startActivity(intent);
                 }
                else{
                    findViewById(R.id.loginlayout).setVisibility(View.VISIBLE);

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
                        pref.edit().putString("orgid",mauth.getCurrentUser().getUid()).commit();

                        Toast.makeText(getApplicationContext(), "LOGIN SUCCESS", Toast.LENGTH_LONG).show();
                        intent = new Intent(getApplicationContext(), BottomNavigationOrganization.class);
                        startActivity(intent);

                    }

                }
            });
        }
    }
}
