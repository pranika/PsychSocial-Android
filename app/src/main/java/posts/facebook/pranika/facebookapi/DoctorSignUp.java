package posts.facebook.pranika.facebookapi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;

import okhttp3.RequestBody;
import posts.facebook.pranika.facebookapi.DaggerApp.DaggerApplication;


public class DoctorSignUp extends BaseActivity {
    private static final String USER_CREATION_SUCCESS = "Successfully created user";
    private static final String USER_CREATION_ERROR = "User creation error";

    String userid = "";
    EditText useremailET,specialisation;
    public static final String MyPREFERENCES = "MyPrefs" ;
    EditText passwordET,name;

    String doctype;
    Doctor doc;

    Button login,createButton,logout;
    Intent intent;
    String url="",url2;
    FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_sign_up);
        url = "http://"+((DaggerApplication)this.getApplication()).getIpaddress()+"/Doctors";
        doc = new Doctor();

        useremailET = (EditText) findViewById(R.id.edit_text_email);
        passwordET = (EditText) findViewById(R.id.edit_text_password);
        specialisation = (EditText) findViewById(R.id.edit_text_specialisation);
        createButton = (Button) findViewById(R.id.signup);
        name = (EditText) findViewById(R.id.edit_text_name);
        login = (Button) findViewById(R.id.login);

        mAuth = FirebaseAuth.getInstance(myApp);
        logout = (Button) findViewById(R.id.logout);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // createUser();

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {

                   mAuth.signOut();

                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent1=new Intent(getApplicationContext(),FrontPage.class);
        startActivity(intent1);

    }

    public void onRadioButtonClicked(View view) {
            // Is the button now checked?
            boolean checked = ((RadioButton) view).isChecked();

            // Check which radio button was clicked
            switch(view.getId()) {
                case R.id.radio_physican:
                    if (checked)
                        doctype="Primary Physician";


                        break;
                case R.id.radio_coordinator:
                    if (checked)
                        doctype ="Coordinator";
                        break;
            }
        }


//    public void createUser() {
//
//        final String nametext = name.getText().toString();
//        Log.d("name",nametext);
//        if (!(TextUtils.isEmpty(useremailET.getText().toString()) || TextUtils.isEmpty(passwordET.getText().toString()))) {
//
//            mAuth.createUserWithEmailAndPassword(useremailET.getText().toString(), passwordET.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//
//                    if (!task.isSuccessful()) {
//
//                        Snackbar snackbar = Snackbar.make(useremailET, USER_CREATION_ERROR, Snackbar.LENGTH_SHORT);
//                        snackbar.show();
//                    } else {
//                        Toast.makeText(getApplicationContext(),"WELCOME "+nametext,Toast.LENGTH_LONG).show();
//                        Snackbar snackbar = Snackbar.make(useremailET, USER_CREATION_SUCCESS, Snackbar.LENGTH_SHORT);
//                        snackbar.show();
//                        userid = mAuth.getCurrentUser().getUid();
//
//                        //SharedPreferences   sharedpreferences = getApplicationContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
//                        final String orgid=pref.getString("orgid","");
//
//
//                        pref.edit().putString("orgid",orgid).commit();
//
//                        SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences(getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
//                        String token=sharedPreferences.getString(getString(R.string.FCM_TOKEN),"");
//                        doc.setId(userid);
//                        doc.setName(nametext);
//                        doc.setEmail(useremailET.getText().toString());
//
//                        doc.setSpecialisation(specialisation.getText().toString());
//                        doc.setDoctype(doctype);
//                        doc.setOrganizationId(orgid);
//                        doc.setFcm_token(token);
//
//                        //********************************okhttp********************************
//
//
//                        JSONObject json = new JSONObject();
//                        try {
//
//                            json.put("doctorid",userid);
//                            json.put("organization",orgid);
//                            json.put("email",doc.getEmail());
//                            json.put("name",doc.getName());
//                            json.put("specialization",doc.getSpecialisation());
//                            json.put("status","Active");
//                            json.put("doctortype",doctype);
//
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//
//                        RequestBody body = RequestBody.create(JSON, json.toString());
//
//                        okhttp3.Request request = new okhttp3.Request.Builder()
//                                .url(url)
//                                .post(body)
//                                .build();
//
//                        client.newCall(request).enqueue(new Callback() {
//                            @Override
//                            public void onFailure(Call call, IOException e) {
//                                Log.d("response", e.toString());
//                                e.printStackTrace();
//                            }
//
//                            @Override
//                            public void onResponse(Call call, okhttp3.Response response) throws IOException {
//                                Log.d("response", response.toString());
//                                Log.d("response", response.body().string());
//
//                                DoctorSignUp.this.runOnUiThread(new Runnable() {
//
//                                    @Override
//                                    public void run() {
//
//                                        mAuth.signOut();
//
//                                        if(orgid==null){
//
//
//
//                                            intent=new Intent(getApplicationContext(),BottomNavigation.class);
//
//                                            startActivity(intent);
//                                        }
//                                        else
//                                            intent=new Intent(getApplicationContext(),BottomNavigationOrganization.class);
//
//                                        startActivity(intent);
//
//
//
//                                    }
//                                });
//
//                            }
//                        });
//
//
//
//                    }
//
//                }
//
//
//            });
//
//        }
//



 //   }
}
