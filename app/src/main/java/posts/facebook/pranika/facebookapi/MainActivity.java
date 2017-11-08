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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity {

    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;

    LoginButton login;
    private static final String DOCTOR_ID = "DOCTOR_ID";


    public static final String MyPREFERENCES = "MyPrefs" ;
    private static final String DOC = "docid";
    // PREFS_MODE defines which apps can access the file
    private static final int PREFS_MODE = Context.MODE_PRIVATE;
    String url1 = "http://192.168.1.21:1337/Patients";
    String url_feeds = "http://192.168.1.21:3000/storefeeds";
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


    UpdateAsync updateAsync=new UpdateAsync(this);
    FeedData feeddata;
    Feed feed=new Feed();
    String accesstoken;

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
        spinner= (Spinner) findViewById(R.id.static_spinner);
        adapter=ArrayAdapter.createFromResource(this,R.array.spinner,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                type= (String) parent.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
  }
        });
        usn= (EditText) findViewById(R.id.usn);
        name= (EditText) findViewById(R.id.name);
        sex= (EditText) findViewById(R.id.sex);
        dob= (EditText) findViewById(R.id.dob);
        phone= (EditText) findViewById(R.id.phone);

        emailedit= (EditText) findViewById(R.id.email);
        casehistory= (EditText) findViewById(R.id.case_history);
        signup= (Button) findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                }
    //                   catch(Exception e)
//                {
//                    Snackbar snackbar = Snackbar.make(signup, "Please ask the patient to Login with facebook", Snackbar.LENGTH_SHORT);
//
//                    snackbar.show();
//                    Toast.makeText(getApplicationContext(),"Please ask the patient to Login with facebook",Toast.LENGTH_LONG).show();
//                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
//                    startActivity(intent);
//                }
                if(userid!=null) {
                    makeVolleyRequest(userid,accesstoken);
                    storefeeds();
                    Intent intent = new Intent(getApplicationContext(), BottomNavigation.class);
                    startActivity(intent);
                }
                else {
                    Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent2);
                }

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
                 accesstoken = loginResult.getAccessToken().getToken();


                if(accesstoken!=null)
                {

                    findViewById(R.id.layoutsignup).setVisibility(View.VISIBLE);
                    addData(userid, accesstoken,case_history,type);

                }
            }
            @Override
            public void onCancel() {


            }
            @Override
            public void onError(FacebookException error) {

                Log.d("facebook error",error.toString());

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

       String url="https://graph.facebook.com/v2.10/me?fields=feed,email,name,gender,age_range&access_token="+accesstoken;


        StringRequest stringrequest=new StringRequest(com.android.volley.Request.Method.GET, url, new Response.Listener<String>() {
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

                    name.setText(nametext);
                    dob.setText(age_rangetext);
                    sex.setText(gendertext);
                    emailedit.setText(email);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Something Wrong",Toast.LENGTH_LONG).show();
                error.printStackTrace();

            }
        });
        MySingleton.getmInstance(getApplicationContext()).addToRequestQue(stringrequest);

    }
    void makeVolleyRequest(String userid,String accesstoken){

    OkHttpClient client = new OkHttpClient.Builder().build();
    SharedPreferences   sharedpreferences = getApplicationContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    final String orgid=sharedpreferences.getString("orgid","");
    usntext = usn.getText().toString();
    nametext=name.getText().toString();
    dobtext=dob.getText().toString();
    sextext=sex.getText().toString();
    emailtext=emailedit.getText().toString();
    case_history=casehistory.getText().toString();
    phonetext=phone.getText().toString();
    doctorid = mauth.getCurrentUser().getUid().toString();

    JSONObject json = new JSONObject();
    try {
        json.put("patientid",userid);
        json.put("organization",orgid);
        json.put("doctor",doctorid);
        json.put("accesstoken",accesstoken);
        json.put("email",emailtext);

        json.put("name",nametext);
        json.put("case_history",case_history);
        json.put("level",type);
        json.put("phone",phonetext);
        json.put("sex",sextext);
        json.put("usn",usntext);
        json.put("age_range",dobtext);

    } catch (JSONException e) {
        e.printStackTrace();
    }

    final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    RequestBody body = RequestBody.create(JSON, json.toString());

    Request request = new Request.Builder()
            .url(url1)
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
        }
    });



}

void storefeeds(){

    //*******************************okhttp store feed*************************

    OkHttpClient client = new OkHttpClient.Builder().build();

    Request request = new Request.Builder()
            .url(url_feeds)
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
        }
    });

//**************************************************************************************************

}


}
