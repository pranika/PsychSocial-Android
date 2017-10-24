package posts.facebook.pranika.facebookapi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrganizationSignUp extends AppCompatActivity {
    private static final String USER_CREATION_SUCCESS = "Successfully created user";
    private static final String USER_CREATION_ERROR = "User creation error";
    private static final String EMAIL_INVALID = "email is invalid :";
    private static final String Organization_ID = "Organization_ID";
    // PREFS_MODE defines which apps can access the file
    private static final int PREFS_MODE = Context.MODE_PRIVATE;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    private static final String COURSE = "course";
    private static final String USERID = "userid";
    String userid = "";
    EditText useremailET;
    EditText passwordET,name;
    OrganizationAsync asyn=new OrganizationAsync(this);
    String orgid="";
    FirebaseDatabase database;
    Organization org;
    DatabaseReference ref;
    Button login,createButton,logout;
    Intent intent;

    FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_sign_up);
        org=new Organization();
        useremailET = (EditText) findViewById(R.id.edit_text_email);
        passwordET = (EditText) findViewById(R.id.edit_text_password);
        createButton = (Button) findViewById(R.id.signup);
        name = (EditText) findViewById(R.id.edit_text_name);
        login = (Button) findViewById(R.id.login);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference().child("Organizations");
        mAuth = FirebaseAuth.getInstance();
        logout= (Button) findViewById(R.id.logout);
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(firebaseAuth.getCurrentUser() == null)
                {

                }

            }

        };
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                orgid =createUser();

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {

                    Intent intent = new Intent(getApplicationContext(), OrganizationLogin.class);
                    startActivity(intent);
                }

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Snackbar snackbar = Snackbar.make(useremailET, "User is Logged Out", Snackbar.LENGTH_LONG);
                snackbar.show();

            }
        });
    }

    public String createUser() {

        final String nametext = name.getText().toString();
        Log.d("name",nametext);
        if (!(TextUtils.isEmpty(useremailET.getText().toString()) || TextUtils.isEmpty(passwordET.getText().toString()))) {

            mAuth.createUserWithEmailAndPassword(useremailET.getText().toString(), passwordET.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (!task.isSuccessful()) {

                        Snackbar snackbar = Snackbar.make(useremailET, USER_CREATION_ERROR, Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    } else {
                        Toast.makeText(getApplicationContext(),"WELCOME "+nametext,Toast.LENGTH_LONG).show();
                        Snackbar snackbar = Snackbar.make(useremailET, USER_CREATION_SUCCESS, Snackbar.LENGTH_SHORT);
                        snackbar.show();
                        userid = mAuth.getCurrentUser().getUid();

                        SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences(getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
                        String token=sharedPreferences.getString(getString(R.string.FCM_TOKEN),"");

                        org.setId(userid);
                        org.setName(nametext);
                        org.setEmail(useremailET.getText().toString());
                        org.setPassword(passwordET.getText().toString());
                        org.setFcm_token(token);
                        asyn.execute(org);

                        DatabaseReference userdb = ref.child(userid);
                        userdb.child("id").setValue(userid);
                        userdb.child("email").setValue(useremailET.getText().toString());
                        userdb.child("password").setValue(passwordET.getText().toString());
                        userdb.child("name").setValue(nametext);
                        sharedpreferences = getApplicationContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("orgid",userid);
                        editor.commit();

                        intent=new Intent(getApplicationContext(),ShowDoctors.class);

                        intent.putExtra("orgid",userid);
                        startActivity(intent);


                    }


                }


            });

        }


        return userid;

    }
}