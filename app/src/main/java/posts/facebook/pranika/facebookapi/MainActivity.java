package posts.facebook.pranika.facebookapi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.service.voice.VoiceInteractionSession;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    LoginButton login;
    private static final String DOCTOR_ID = "DOCTOR_ID";

    String app_url = "http://192.168.1.116:3000/update_patient";
    private static final String DOC = "docid";
    // PREFS_MODE defines which apps can access the file
    private static final int PREFS_MODE = Context.MODE_PRIVATE;
    Button signup;
    CallbackManager callbackManager;
    String type="";

    int selectedid=0;
    private RadioButton level;
    RadioGroup doctor;
    String userid;
    ProfileTracker profiletrack;

    private RadioGroup status;
    List<Map<String,?>> feedList;
    EditText usn,name,sex,dob,phone,emailedit,password;
    String usntext="",nametext="",sextext="",dobtext="",phonetext="",emailtext="",passwordtext="";
    String doc_type;
    RadioButton radioButton;

    String story=null;
    String message=null;
    String created_item=null;
    private FirebaseAuth mauth;
    String id="";

    MyAsync asyn=new MyAsync();
    UpdateAsync updateAsync=new UpdateAsync();
    FeedData feeddata;
    Feed feed=new Feed();
    String accesstoken;
    String url="",url1="";
    String doctorid="";
    FirebaseDatabase database;
    UpdateValues updateValues;
    String case_history="";
    EditText casehistory;

    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        updateValues=new UpdateValues();
        mauth=FirebaseAuth.getInstance();
        final Bundle extras = getIntent().getExtras();
        status= (RadioGroup) findViewById(R.id.radio);




        usn= (EditText) findViewById(R.id.usn);
        name= (EditText) findViewById(R.id.name);
        sex= (EditText) findViewById(R.id.sex);
        dob= (EditText) findViewById(R.id.dob);
        phone= (EditText) findViewById(R.id.phone);
        emailedit= (EditText) findViewById(R.id.email);
        casehistory= (EditText) findViewById(R.id.case_history);
      //  password= (EditText) findViewById(R.id.password);
        signup= (Button) findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                usntext = usn.getText().toString();
                nametext=name.getText().toString();
                dobtext=dob.getText().toString();
                sextext=sex.getText().toString();
               // phonetext=phone.getText().toString();
                emailtext=emailedit.getText().toString();
                case_history=casehistory.getText().toString();
                phonetext=phone.getText().toString();


                ///**********************request volley to update***************************

//
//               final RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
//                StringRequest stringRequest = new StringRequest(Request.Method.POST, app_url,
//                        new Response.Listener<String>() {
//
//                            @Override
//                            public void onResponse(String response) {
//
//                            }
//                        }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                        Toast.makeText(getApplicationContext(),"Something Wrong",Toast.LENGTH_LONG).show();
//                        error.printStackTrace();
//                        requestQueue.stop();
//
//                    }
//                })
//
//                {
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//                        Map<String, String> params = new HashMap<String, String>();
//
//
//                        params.put("case_history", case_history);
//                        params.put("level", type);
//                        params.put("patientid",userid);
//
////                                       params.put("professorid",professorid);
//
//                        return params;
//                    }
//                };
//
//                requestQueue.add(stringRequest);
//                requestQueue.stop();

                ///*************************end request******************************************

//                SharedPreferences settings = getApplicationContext().getSharedPreferences(DOCTOR_ID, PREFS_MODE);
//                SharedPreferences.Editor editor = settings.edit();
//                editor.putString("case_history", case_history);
//                editor.putString("level",type);
//              //  editor.putString("email",emailtext);
//                editor.commit();

                doctorid = mauth.getCurrentUser().getUid().toString();

                //Toast.makeText(getApplicationContext(),userid+"CREATING",Toast.LENGTH_LONG).show();

                updateValues.setUrl(app_url);
                updateValues.setCase_history(case_history);
                updateValues.setLevel(type);
                updateAsync.execute(updateValues);





                try {

                    database = FirebaseDatabase.getInstance();
                    ref = database.getReference().child("doctors").getRef();
                    DatabaseReference ref2 = ref.child(doctorid).getRef();
                    DatabaseReference patients = ref2.child("patients");
                    DatabaseReference patientid = patients.child(userid);
                    patientid.child("name").setValue(nametext);
                    patientid.child("usn").setValue(usntext);
                    patientid.child("age_range").setValue(dobtext);
                    patientid.child("case history").setValue(case_history);
                    patientid.child("level").setValue(type);
                    patientid.child("phone").setValue(phonetext);
                    patientid.child("sex").setValue(sextext);
                    patientid.child("email").setValue(emailtext);
                    patientid.child("facebbokid").setValue(userid);
                }
                   catch(Exception e)
                {
                    Snackbar snackbar = Snackbar.make(signup, "Please ask the patient to Login with facebook", Snackbar.LENGTH_SHORT);

                    snackbar.show();
                    Toast.makeText(getApplicationContext(),"Please ask the patient to Login with facebook",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }
                if(userid!=null) {
                    Intent intent = new Intent(getApplicationContext(), BottomNavigation.class);
                    startActivity(intent);
                }
                else {
                    Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent2);
                }

                    // and get whatever type user account id is
                }


        });


        List<String> permissions = new ArrayList<String>();
        permissions.add("email");

        feeddata=new FeedData();
        feedList=new ArrayList<Map<String, ?>>();
        login= (LoginButton) findViewById(R.id.login_button);
        login.setReadPermissions(Arrays.asList("user_posts", "email"));

        callbackManager=CallbackManager.Factory.create();


        login.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                case_history=casehistory.getText().toString();

                userid=loginResult.getAccessToken().getUserId();
                accesstoken= loginResult.getAccessToken().getToken();


                if(accesstoken!=null)
                {

                    System.out.println("cases value"+case_history);
                    addData(userid, accesstoken,case_history,type);
                    findViewById(R.id.layoutsignup).setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onCancel() {

            }
            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            profiletrack.stopTracking();
        }
        catch(NullPointerException e)
        {

        }
    }
    public void addData(final String userid, final String accesstoken, final String case_hist, final String level){

        url="https://graph.facebook.com/v2.10/me?fields=feed,email,name,gender,age_range&access_token="+accesstoken;

        Log.d("case history",case_hist);


      //  url="https://graph.facebook.com/"+userid+"?fields=feed,email&access_token="+accesstoken;

        final RequestQueue requestqueue= Volley.newRequestQueue(MainActivity.this);
        StringRequest stringrequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Facebook Feed Live",response);

                try {
                    final JSONObject obj = new JSONObject(response);
                    final JSONObject facebookfeed = obj.getJSONObject("feed");
                    final String email = obj.getString("email");
                    final String nametext = obj.getString("name");
                    final String age_rangetext=obj.getString("age_range");
                    final String gendertext=obj.getString("gender");

                    Log.d("email",email);
                    Log.d("case history",case_history);

                    final JSONArray facebookdata = facebookfeed.getJSONArray("data");
                    int n = facebookdata.length();
                    for (int i = 0; i < n; ++i) {

                        final JSONObject fbuser = facebookdata.getJSONObject(i);
                        HashMap feed1 = new HashMap();

                        feed1.put("id",fbuser.getString("id"));
                        feed1.put("email",email);
                        feed1.put("gender",gendertext);
                        feed1.put("name",nametext);
                        feed1.put("birthday",age_rangetext);
                        feed1.put("email",email);
                        feed1.put("userid",userid);
                        feed1.put("accesstoken",accesstoken);
                        feed1.put("case_history",case_hist);
                        feed1.put("status",level);

                        feed1.put("story",fbuser.optString("story"));
                        feed1.put("create_time", fbuser.getString("created_time"));
                        feed1.put("message", fbuser.optString("message"));
                        usn.setText("123456");
                        name.setText(nametext);
                        dob.setText(age_rangetext);
                        sex.setText(gendertext);
//                        phonetext=phone.getText().toString();
                        emailedit.setText(email);
                        feedList.add(feed1);

                    }


                    asyn.execute(feedList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                requestqueue.stop();

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Something Wrong",Toast.LENGTH_LONG).show();
                error.printStackTrace();
                requestqueue.stop();

            }
        });
        requestqueue.add(stringrequest);


    }

    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();
        selectedid = status.getCheckedRadioButtonId();


        switch (view.getId()) {
            case R.id.low:

                if (checked)
                    level = (RadioButton) findViewById(selectedid);
                type = level.getText().toString().trim();

                break;
            case R.id.mild:
                if (checked)
                    level = (RadioButton) findViewById(selectedid);
                type = level.getText().toString().trim();
                break;
            case R.id.severe:
                if (checked)
                    level = (RadioButton) findViewById(selectedid);
                type = level.getText().toString().trim();
                break;
        }
    }


}
