package posts.facebook.pranika.facebookapi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Flowable;
//import okhttp3.Call;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;


import okhttp3.RequestBody;
import posts.facebook.pranika.facebookapi.DaggerApp.DaggerApplication;
import posts.facebook.pranika.facebookapi.Model.Result;
//import posts.facebook.pranika.facebookapi.data.DoctorDataSource;
import retrofit2.Response;


public class DoctorSignUp extends BaseActivity {
//    private static final String USER_CREATION_SUCCESS = "Successfully created user";
//    private static final String USER_CREATION_ERROR = "User creation error";
    String phoneno="",address_text="";

    String userid = "";
    EditText useremailET;
    TextView specialisation;
    public static final String MyPREFERENCES = "MyPrefs" ;
    EditText passwordET;
    TextView name;

    EditText editnpi;
    @BindView(R.id.edit_text_phoneno)
    EditText phone;
    @BindView(R.id.edit_text_address)
    EditText address;

    String npinumber="";

    Button search;

    String doctype;
    Doctor doc;

    Button login,createButton,logout;
    Intent intent;
    String url="",url2="";
    FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_sign_up);
        ButterKnife.bind(this);


        useremailET = (EditText) findViewById(R.id.edit_text_email);
        passwordET = (EditText) findViewById(R.id.edit_text_password);

        specialisation = (TextView) findViewById(R.id.edit_text_specialisation);
        createButton = (Button) findViewById(R.id.signup);
        search=(Button)findViewById(R.id.search);
        name = (TextView) findViewById(R.id.edit_text_name);
        editnpi= (EditText) findViewById(R.id.edit_npi_number);
        login = (Button) findViewById(R.id.login);

        mAuth = FirebaseAuth.getInstance(myApp);
        logout = (Button) findViewById(R.id.logout);
        final String[] specialization = {""},name_text={""};
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editnpi !=null){

                    npinumber=editnpi.getText().toString();

                  //  getData();
                    url2="https://npiregistry.cms.hhs.gov/api?number="+npinumber;

                    //**********************api call*******************************
                    okhttp3.Request request = new okhttp3.Request.Builder()
                                .url(url2)
                                .get()
                                .build();



                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.d("response", e.toString());
                                e.printStackTrace();
                            }

                            @Override
                            public void onResponse(Call call, okhttp3.Response response) throws IOException {

                                try {

                                    String resbody = response.body().string();

                                    final JSONObject obj = new JSONObject(resbody);

                                    final JSONArray doc_details = obj.getJSONArray("results");
                                    int n = doc_details.length();

                                    for (int i = 0; i < n; ++i) {
                                        final JSONArray doctor = doc_details.getJSONObject(i).getJSONArray("taxonomies");
                                        int length=doctor.length();
                                      final JSONObject doctor_basic = doc_details.getJSONObject(i).getJSONObject("basic");

                                        final JSONArray doctor_address = doc_details.getJSONObject(i).getJSONArray("addresses");
                                        int addresslength=doctor_address.length();
                                        HashMap feed1 = new HashMap();
                                        for(int j=0;j<length;j++) {


                                            final JSONObject doc_taxonomy = doctor.getJSONObject(i);

                                            Log.d("feeddata", doc_taxonomy.toString());



                                            feed1.put("specialization", doc_taxonomy.getString("desc"));
                                                specialization[0] = doc_taxonomy.getString("desc");
                                        }



                                            Log.d("feeddata", doctor_basic.toString());



                                            feed1.put("name", doctor_basic.getString("first_name")+" "+doctor_basic.getString("last_name"));
                                            name_text[0] =  doctor_basic.getString("first_name")+" "+doctor_basic.getString("last_name");

                                            for(int l=0;l<addresslength;l++){

                                            final JSONObject address = doctor_address.getJSONObject(i);

                                                if(address.get("address_purpose").equals("LOCATION"))
                                                {
                                                    feed1.put("address",address.get("address_1")+" "+address.get("postal_code"));
                                                    feed1.put("city",address.get("city"));
                                                    feed1.put("state",address.get("state"));
                                                    feed1.put("telephone_number",address.get("telephone_number"));
                                                    phoneno=address.get("telephone_number").toString();
                                                    address_text=address.get("address_1")+", "+address.get("postal_code")+", "+address.get("city")+", "+address.get("state");

                                                }
                                        //    }


//

                                        }
                                        DoctorSignUp.this.runOnUiThread(new Runnable() {
                                                                            @Override
                                                                            public void run() {

                                                                                name.setText(name_text[0]);
                                                                                specialisation.setText(specialization[0]);
                                                                                phone.setText(phoneno);
                                                                                address.setText(address_text);


                                                                            }
                                                                        });


                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                            }
                        });



                    //******************************************************************************


                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please enter NPI Number",Toast.LENGTH_LONG).show();}

               // createUser();

            }
        });
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verify_phoneno(phoneno);
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
        Intent intent1=new Intent(getApplicationContext(),HomePage.class);
        startActivity(intent1);

    }

    public void verify_phoneno(final String phoneno)  {


        String verify_url="https://api.authy.com/protected/json/phones/verification/start";
        JSONObject json = new JSONObject();
        try {

            json.put("via", "sms");
            json.put("phone_number", "315-949-8821");
            json.put("country_code", 1);
            json.put("locale", "es");
        }
        catch (Exception e){
        Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
        }


            final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

            RequestBody body = RequestBody.create(JSON, json.toString());


        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(verify_url)
                .post(body)
                .header("X-Authy-API-Key","BIfUI7CSRLDXDY3js4cREGIH425L4CFG")
                .build();

//******************************phone api call****************************
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("response", e.toString());
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final okhttp3.Response response) throws IOException {



              //  Log.d("phone verify",response.body().string());

                DoctorSignUp.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       // Toast.makeText(getApplicationContext(),response.body().toString(),Toast.LENGTH_LONG).show();
                        SharedPreferences.Editor pEdit = pref.edit();
                        pEdit.putString("phoneno",phoneno);
                        pEdit.putString("email",useremailET.getText().toString());
                        pEdit.putString("name",name.getText().toString());
                        pEdit.putString("password",passwordET.getText().toString());
                        pEdit.putString("specialization",specialisation.getText().toString());
                        pEdit.commit();
                        Intent intent=new Intent(getApplicationContext(),PhoneVerification.class);
                        startActivity(intent);
//                        PhoneVerification fragment = new PhoneVerification();
//                        FragmentManager fm = getSupportFragmentManager();
//                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
//                        fragmentTransaction.replace(R.id.framesingle, fragment);
//                        fragmentTransaction.commit();




                    }
                });




            }
        });

        //************************phone api call***************************

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
