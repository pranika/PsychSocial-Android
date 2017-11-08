package posts.facebook.pranika.facebookapi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class OrganizationLogin extends AppCompatActivity {

    private EditText email;
    public static final String MyPREFERENCES = "MyPrefs" ;
    private EditText password;
    private Button loginbutton;
    private FirebaseAuth mauth;
    String userid="";
    String url= "http://192.168.1.21:1337/Doctors/getDoctors";
    List<Map<String,?>> doctorList;
    SharedPreferences sharedpreferences;
    private FirebaseAuth.AuthStateListener authStateListener;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        doctorList=new ArrayList<Map<String, ?>>();
        mauth=FirebaseAuth.getInstance();


        email= (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.pwd);
        loginbutton = (Button) findViewById(R.id.login);


        authStateListener=new FirebaseAuth.AuthStateListener(){

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){

                if(firebaseAuth.getCurrentUser()!=null)
                {

                    sharedpreferences = getApplicationContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("orgid",firebaseAuth.getCurrentUser().getUid());
                    editor.commit();

                    //*****************************************************************************

                    OkHttpClient client = new OkHttpClient.Builder().build();


                    JSONObject json = new JSONObject();
                    try {

                       SharedPreferences sharedpreferences = getApplicationContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                       final String orgid=sharedpreferences.getString("orgid","");
                     //   final String signuporgid=sharedpreferences.getString("signuporgid","");
                        json.put("organizationid",orgid);


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
                            Log.d("responsemessage", response.toString());
                            String resbody = response.body().string();
                            Log.d("responsebody", resbody);

                            try {
                                        final JSONArray obj = new JSONArray(resbody);

                                        int n = obj.length();
                                        for (int i = 0; i < n; ++i) {

                                            final JSONObject fbuser = obj.getJSONObject(i);
                                            HashMap doctor = new HashMap();

                                            doctor.put("orgid",fbuser.getString("organization"));
                                            doctor.put("email",fbuser.getString("email"));
                                            doctor.put("name",fbuser.getString("name"));
                                            doctor.put("specialization",fbuser.getString("specialization"));
                                            doctor.put("doctortype",fbuser.getString("doctortype"));
                                            doctorList.add(doctor);

                                            if(doctorList.size()==0)

                                            {
                                                intent = new Intent(getApplicationContext(), DoctorSignUp.class);

                                                startActivity(intent);


                                            }
                                            else if(doctorList.size()!=0)

                                            {
                                                intent = new Intent(getApplicationContext(), ShowDoctors.class);
                                                intent.putExtra("doctorslist", (Serializable) doctorList);
                                                startActivity(intent);

                                            }


                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                        }
                    });




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
            Toast.makeText(getApplicationContext(),"Fields empty",Toast.LENGTH_LONG);

        }
        else {
            mauth.signInWithEmailAndPassword(emailtext, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (!task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "LOGIN FAILED", Toast.LENGTH_LONG);
                    }
                    else
                    {
                        sharedpreferences = getApplicationContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("orgid",userid);
                        editor.commit();
                        Toast.makeText(getApplicationContext(), "LOGIN SUCCESS", Toast.LENGTH_LONG);



                        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {

                                    @Override
                                    public void onResponse(String response) {


                                        try {
                                           // final JSONObject obj = new JSONObject(response);

                                            final JSONArray facebookdata = new JSONArray(response);
                                            int n = facebookdata.length();
                                            for (int i = 0; i < n; ++i) {

                                                final JSONObject fbuser = facebookdata.getJSONObject(i);
                                                HashMap doctor = new HashMap();

                                                doctor.put("orgid",fbuser.getString("organizations"));
                                                doctor.put("email",fbuser.getString("email"));
                                                doctor.put("name",fbuser.getString("name"));
                                                doctor.put("specialization",fbuser.getString("specialization"));
                                                doctor.put("doctortype",fbuser.getString("doctortype"));
                                                doctorList.add(doctor);
                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                System.out.println("no responding");

                            }
                        })

                        {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<String, String>();

                                params.put("organizationid",userid);

                                return params;
                            }
                        };
                        MySingleton.getmInstance(getApplicationContext()).addToRequestQue(stringRequest2);


                        //**************************************************************************

                        if(doctorList.size()==0)

                        {
                            intent = new Intent(getApplicationContext(), DoctorSignUp.class);

                            startActivity(intent);


                        }
                        else

                        {
                            intent = new Intent(getApplicationContext(), ShowDoctors.class);
                            intent.putExtra("doctorslist", (Serializable) doctorList);
                            startActivity(intent);

                        }


                    }

                }
            });
        }
    }
}
