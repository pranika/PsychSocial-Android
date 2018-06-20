package posts.facebook.pranika.facebookapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.design.widget.Snackbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.AuthResult;
import com.mongodb.util.JSON;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import posts.facebook.pranika.facebookapi.DaggerApp.DaggerApplication;

public class PhoneVerification extends BaseActivity {
    private static final String USER_CREATION_SUCCESS = "Successfully created user";
    private static final String USER_CREATION_ERROR = "User creation error";

    @BindView(R.id.one)
    EditText one;
    @BindView(R.id.two)
    EditText two;
    @BindView(R.id.three)
    EditText three;
    @BindView(R.id.four)
    EditText four;

    @BindView(R.id.verify)
    Button button;
    boolean messagevalue = false;
    FirebaseAuth mAuth,orgauth;
    String url="";
    String userid = "",orgid="";
    Intent intent;
    Doctor doc;
    JSONObject json1;

    //    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        super.onCreateView(inflater, container, savedInstanceState);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verification2);

        //  View v=inflater.inflate(R.layout.activity_phone_verification2,container,false);
//        one= (EditText) v.findViewById(R.id.one);
//        two= (EditText) v.findViewById(R.id.two);
//        three= (EditText) v.findViewById(R.id.three);
//        four= (EditText) v.findViewById(R.id.four);
//        button= (Button) v.findViewById(R.id.button);
        url = "http://"+((DaggerApplication)this.getApplication()).getIpaddress()+"/Doctors";

        doc = new Doctor();

        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance(myApp);
        orgauth=FirebaseAuth.getInstance();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneno = "";
                if (pref != null) {
                    phoneno = pref.getString("phoneno", "not found");
                }
                if (one.getText() != null && two.getText() != null && three.getText() != null && four.getText() != null) {

                    final String result = new StringBuilder(4).append(one.getText().toString()).append(two.getText().toString())
                            .append(three.getText().toString()).append(four.getText().toString()).toString();
                    int resultparse = Integer.parseInt(result);
                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG);
                    System.out.println("verification" + resultparse);
                    String verify_url = "https://api.authy.com/protected/json/phones" +
                            "/verification/check?phone_number=315-949-8821&country_code=1&verification_code=" + result + "";


                    okhttp3.Request request = new okhttp3.Request.Builder()
                            .url(verify_url)
                            .get()
                            .header("X-Authy-API-Key", "BIfUI7CSRLDXDY3js4cREGIH425L4CFG")
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.d("response", e.toString());
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, final okhttp3.Response response) throws IOException {

                            //Log.d("phone verify",response.body().string());


                            try {
                                json1 = new JSONObject(response.body().string());
                                messagevalue = Boolean.parseBoolean(json1.getString("success"));


                            } catch (Exception e) {
                            }


                            PhoneVerification.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    if (messagevalue == true)
                                    {
                                        try {
                                            Toast.makeText(getApplicationContext(), json1.getString("message"), Toast.LENGTH_LONG).show();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        createUser();

                                    } else
                                        try {
                                            Toast.makeText(getApplicationContext(), json1.getString("message"), Toast.LENGTH_LONG).show();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
//
                                }
                            });


                        }
                    });


                } else
                    Toast.makeText(getApplicationContext(), "Enter the verification code first", Toast.LENGTH_LONG).show();
            }
        });


        //  return v;
    }


    public void createUser() {

        final String nametext = pref.getString("name","");
        final String useremailET = pref.getString("email","");
        final String passwordET = pref.getString("password","");
        Log.d("name", nametext);
        if (!(TextUtils.isEmpty(useremailET) || TextUtils.isEmpty(passwordET))) {

            mAuth.createUserWithEmailAndPassword(useremailET, passwordET).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (!task.isSuccessful()) {

                        Snackbar snackbar = Snackbar.make(button, USER_CREATION_ERROR, Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    } else {
                      //  Toast.makeText(getApplicationContext(), "WELCOME " + nametext, Toast.LENGTH_LONG).show();
                        Snackbar snackbar = Snackbar.make(button, USER_CREATION_SUCCESS, Snackbar.LENGTH_SHORT);
                        snackbar.show();
                        userid = mAuth.getCurrentUser().getUid();

                        if(orgauth!=null){

                        orgid=orgauth.getCurrentUser().getUid();
                        }

                        pref.edit().putString("orgid", orgid).commit();


                        String token = pref.getString(getString(R.string.FCM_TOKEN), "");
                        doc.setId(userid);
                        doc.setName(nametext);
                        doc.setEmail(pref.getString("email",""));

                        doc.setSpecialisation(pref.getString("specialization",""));
                        doc.setOrganizationId(orgid);
                        doc.setFcm_token(token);

                        //********************************okhttp********************************


                        JSONObject json = new JSONObject();
                        try {

                            json.put("doctorid", userid);
                            json.put("organization", orgid);
                            json.put("email", doc.getEmail());
                            json.put("name", doc.getName());
                            json.put("specialization", doc.getSpecialisation());
                            json.put("status", "Active");



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
                                Log.d("response", response.toString());
                                Log.d("response", response.body().string());

                                PhoneVerification.this.runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {

                                        mAuth.signOut();

                                        if (orgid == null) {


                                            intent = new Intent(getApplicationContext(), BottomNavigation.class);

                                            startActivity(intent);
                                        } else
                                            intent = new Intent(getApplicationContext(), BottomNavigationOrganization.class);

                                        startActivity(intent);


                                    }
                                });

                            }
                        });


                    }

                }


            });

        }


    }
}