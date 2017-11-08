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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class OrganizationSignUp extends AppCompatActivity {
    private static final String USER_CREATION_SUCCESS = "Successfully created user";
    private static final String USER_CREATION_ERROR = "User creation error";

    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;

    List<Map<String,?>> doctorList;
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
    String url = "http://192.168.1.21:1337/Organization";
    String url1 = "http://192.168.1.21:1337/Doctors/getDoctors";
    FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_sign_up);
        doctorList=new ArrayList<Map<String, ?>>();
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

                        SharedPreferences.Editor editor1 = sharedPreferences.edit();
                        editor1.putString("signuporgid",userid);
                        editor1.commit();

                        org.setId(userid);
                        org.setName(nametext);
                        org.setEmail(useremailET.getText().toString());
                        org.setFcm_token(token);
                        //****************************okhttp****************************************

                        OkHttpClient client = new OkHttpClient.Builder().build();


                        JSONObject json = new JSONObject();
                        try {

                            json.put("organizationid",org.getId());
                            json.put("email",org.getEmail());
                            json.put("name",org.getName());


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

                        RequestBody body = RequestBody.create(JSON, json.toString());

                        okhttp3.Request request = new okhttp3.Request.Builder()
                                .url(url)
                                .post(body)
                                .build();

                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.d("response", e.toString());
                                e.printStackTrace();
                            }

                            @Override
                            public void onResponse(Call call, okhttp3.Response response) throws IOException {
//                            Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                                Log.d("response", response.toString());
                                Log.d("response", response.body().string());

                              OrganizationSignUp.this.runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                       intent=new Intent(getApplicationContext(),DoctorSignUp.class);
                                        startActivity(intent);
                                    }
                                });

                            }
                        });

                        //****************************okhttp ned****************************************

                        sharedpreferences = getApplicationContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("orgid",userid);
                        editor.commit();

                        //**************************************************************************

                    }


                }


            });

        }


        return userid;

    }
}
