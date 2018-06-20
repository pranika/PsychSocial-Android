package posts.facebook.pranika.facebookapi;

import android.content.Intent;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

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
import okhttp3.RequestBody;
import posts.facebook.pranika.facebookapi.DaggerApp.DaggerApplication;

public class BottomNavigationOrganization extends BaseActivity{

    private FirebaseAuth mauth;

    List<Map<String,?>> doctorList;
    String url="";
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation_organization);
        url= "http://"+((DaggerApplication)this.getApplication()).getIpaddress()+"/Doctors/getDoctors";
        mauth= FirebaseAuth.getInstance();
        doctorList=new ArrayList<>();

        BottomNavigationView bottomNavigationView= (BottomNavigationView) findViewById(R.id.bottom_navigation_org);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.show_doc:

                        if(mauth.getCurrentUser().getUid()!=null){


                            pref.edit().putString("orgid",mauth.getCurrentUser().getUid()).commit();

                            String orgid=mauth.getCurrentUser().getUid();

                            JSONObject json = new JSONObject();
                            try {


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
//
                                    Log.d("responsemessage", response.toString());
                                    String resbody = response.body().string();
                                    Log.d("responsebody", resbody);

                                    try {
                                        if(!resbody.equals("[]")){
                                            final JSONArray obj = new JSONArray(resbody);

                                            int n = obj.length();
                                            for (int i = 0; i < n; ++i) {

                                                final JSONObject fbuser = obj.getJSONObject(i);
                                                HashMap doctor = new HashMap();

                                                doctor.put("orgid",fbuser.getString("organization"));
                                                doctor.put("doctorid", fbuser.getString("id"));
                                                doctor.put("email",fbuser.getString("email"));
                                                doctor.put("name",fbuser.getString("name"));
                                                doctor.put("specialization",fbuser.getString("specialization"));

                                                doctorList.add(doctor);

                                                BottomNavigationOrganization.this.runOnUiThread(new Runnable() {

                                                    @Override
                                                    public void run() {

                                                        intent = new Intent(getApplicationContext(), ShowDoctors.class);
                                                        intent.putExtra("doctorslist", (Serializable) doctorList);
                                                        startActivity(intent);


                                                    }});

                                            }

                                        }
                                        else{

                                            BottomNavigationOrganization.this.runOnUiThread(new Runnable() {

                                                @Override
                                                public void run() {

                                                    Log.d("doctorlist", String.valueOf(doctorList.size()));
                                                    intent = new Intent(getApplicationContext(), DoctorSignUp.class);

                                                    startActivity(intent);


                                                }});




                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                        else
                        {Intent intent=new Intent(getApplicationContext(),OrganizationLogin.class);
                        startActivity(intent);}

                        //*****************************************************************************

                        break;

                    case R.id.register_doc:
                        Intent intent1=new Intent(getApplicationContext(),DoctorSignUp.class);
                        startActivity(intent1);
                        break;

//                    case R.id.show_patients:
//                        Intent intent2=new Intent(getApplicationContext(),AuthAffectedPatients.class);
//                        startActivity(intent2);
//                        break;
//                    case R.id.graph:
//                        Intent intent3=new Intent(getApplicationContext(),WeekGraph.class);
//                        startActivity(intent3);
                }

                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(getApplicationContext(),OrganizationSignUp.class);
        startActivity(intent);
    }
}
