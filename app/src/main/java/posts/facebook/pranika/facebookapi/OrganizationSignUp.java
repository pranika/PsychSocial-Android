package posts.facebook.pranika.facebookapi;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
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


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;

import okhttp3.RequestBody;
import posts.facebook.pranika.facebookapi.DaggerApp.DaggerApplication;

public class OrganizationSignUp extends BaseActivity {
    private static final String USER_CREATION_SUCCESS = "Successfully created user";
    private static final String USER_CREATION_ERROR = "User creation error";


    List<Doctor> doctorList;
    String userid = "";
    String url="";
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_sign_up);
         url = "http://"+((DaggerApplication)this.getApplication()).getIpaddress()+"/Organization";
        doctorList=new ArrayList<Doctor>();
        org=new Organization();
        useremailET = (EditText) findViewById(R.id.edit_text_email);
        passwordET = (EditText) findViewById(R.id.edit_text_password);
        createButton = (Button) findViewById(R.id.signup);
        name = (EditText) findViewById(R.id.edit_text_name);
        login = (Button) findViewById(R.id.login);
        database = FirebaseDatabase.getInstance();

        mAuth = FirebaseAuth.getInstance();
        logout= (Button) findViewById(R.id.logout);

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

                        String token=pref.getString("fcm_token","");

                        pref.edit().putString("signuporgid",userid).commit();

                        org.setId(userid);
                        org.setName(nametext);
                        org.setEmail(useremailET.getText().toString());
                        org.setFcm_token(token);
                        //****************************okhttp****************************************

                        JSONObject json = new JSONObject();
                        try {

                            json.put("organizationid",org.getId());
                            json.put("email",org.getEmail());
                            json.put("name",org.getName());
                            json.put("fcm_token",org.getFcm_token());


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
                                        intent=new Intent(getApplicationContext(),BottomNavigationOrganization.class);
                                        startActivity(intent);
                                    }
                                });

                            }
                        });

                        //****************************shared pref****************************************

                        pref.edit().putString("orgid",userid).commit();
                        //**************************************************************************

                    }


                }


            });

        }


        return userid;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(getApplicationContext(),FrontPage.class);
        startActivity(intent);
    }
}