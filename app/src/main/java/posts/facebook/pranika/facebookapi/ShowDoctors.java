package posts.facebook.pranika.facebookapi;


import android.content.Intent;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
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

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import posts.facebook.pranika.facebookapi.DaggerApp.DaggerApplication;

public class ShowDoctors extends BaseActivity{
    TextView textView;

    RecyclerView recyclerView;
    DoctorsAdapter doctorsAdapter;
    RegisterdUsers.OnLevelClicklistner mlistner;
    List<Map<String,?>> patientlist;
    List<Map<String,?>> doctorlist;
    List<String> selectedPatients;
    PatientDialogFragment dialog;
    private FirebaseAuth mauth;
    DoctorDialogFragment docdialog;
    String url2="",url3="",url="",url4="";
    String docid="";
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show_doctors);

        url2 = "http://"+((DaggerApplication)this.getApplication()).getIpaddress()+"/Patients/getPatients";
        url3 = "http://"+((DaggerApplication)this.getApplication()).getIpaddress()+"/Doctors/getDoctors";
        url = "http://"+((DaggerApplication)this.getApplication()).getIpaddress()+"/Patients/updatePatient";
        url4="http://"+((DaggerApplication)this.getApplication()).getIpaddress()+"/Doctors/";
        mauth= FirebaseAuth.getInstance();
        patientlist=new ArrayList<Map<String,?>>();
        doctorlist=new ArrayList<>();
        selectedPatients=new ArrayList<>();

        textView = (TextView) findViewById(R.id.textpatient);
        recyclerView = (RecyclerView) findViewById(R.id.patientsrecycler_view);

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new SlideInLeftAnimator());
        recyclerView.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));
        recyclerView.getItemAnimator().setAddDuration(2000);
        recyclerView.getItemAnimator().setRemoveDuration(2000);
        recyclerView.getItemAnimator().setMoveDuration(2000);
        recyclerView.getItemAnimator().setChangeDuration(2000);
        recyclerView.setHasFixedSize(true);

        SlideInLeftAnimator animator = new SlideInLeftAnimator();
        animator.setInterpolator(new OvershootInterpolator());
        recyclerView.setItemAnimator(animator);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        AlphaInAnimationAdapter alpha = new AlphaInAnimationAdapter(doctorsAdapter);
        ScaleInAnimationAdapter scale = new ScaleInAnimationAdapter(doctorsAdapter);
        alpha.setDuration(2000);
        alpha.setInterpolator(new OvershootInterpolator());
        scale.setFirstOnly(false);

        Intent intent = getIntent();
        final  List<Map<String,?>> doctorList =  (List<Map<String,?>>) intent.getSerializableExtra("doctorslist");

        doctorsAdapter = new DoctorsAdapter(getApplicationContext(), doctorList);
        recyclerView.setAdapter(doctorsAdapter);
        recyclerView.setAdapter(new AlphaInAnimationAdapter(doctorsAdapter));
        recyclerView.setAdapter(new ScaleInAnimationAdapter(doctorsAdapter));
        doctorsAdapter.setOnItemClickListner(new DoctorsAdapter.OnItemClickListner() {
            @Override
            public void onEdit(View view, int position) {

                HashMap doctor= (HashMap) doctorList.get(position);
                Intent intent = new Intent(getApplicationContext(), UpdateDoctor.class);
                intent.putExtra("doctor", (Serializable) doctor);
                startActivity(intent);
            }

            @Override
            public void onDelete(View view, final int position) {

                HashMap doctor= (HashMap) doctorList.get(position);
                docid= (String) doctor.get("doctorid");


                //***********okhttp****************************



                JSONObject json = new JSONObject();
                try {
                    json.put("doctorid",docid);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

                RequestBody body = RequestBody.create(JSON, json.toString());


                okhttp3.Request request1 = new okhttp3.Request.Builder()
                        .url(url2)

                        .post(body)
                        .build();

                client.newCall(request1).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("response", e.toString());
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, okhttp3.Response response) throws IOException {
                        String resbody = response.body().string();
                        Log.d("response", response.toString());
                        Log.d("responsebody", resbody);

                        try {
                            // final JSONObject obj = new JSONObject(response);

                            final JSONArray facebookdata = new JSONArray(resbody);
                            int n = facebookdata.length();
                            for (int i = 0; i < n; ++i) {

                                final JSONObject fbuser = facebookdata.getJSONObject(i);

                                HashMap map=new HashMap();
                                map.put("name",fbuser.getString("name"));
                                map.put("patientid",fbuser.getString("id"));
                                patientlist.add(map);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        ShowDoctors.this.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                dialog = new PatientDialogFragment();


                                Bundle args = new Bundle();
                                args.putSerializable("patientlist", (Serializable) patientlist);
                                dialog.setArguments(args);
                                dialog.show(getSupportFragmentManager(), "PatientDialogFragment");

                                dialog.setOnPatientClickListner(new PatientDialogFragment.OnPatientClickListner() {
                                    @Override
                                    public void sendPatients(List<String> Patients) {



                                        selectedPatients=Patients;
                                        dialog.dismiss();

                                        //***********okhttp****************************

                                        JSONObject json1 = new JSONObject();

                                        String orgid=mauth.getCurrentUser().getUid();
                                        try {
                                            json1.put("organizationid",orgid);


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        final MediaType JSON2 = MediaType.parse("application/json; charset=utf-8");

                                        RequestBody body1 = RequestBody.create(JSON2, json1.toString());


                                        okhttp3.Request request3 = new okhttp3.Request.Builder()
                                                .url(url3)

                                                .post(body1)
                                                .build();

                                        client.newCall(request3).enqueue(new Callback() {
                                                                             @Override
                                                                             public void onFailure(Call call, IOException e) {
                                                                                 Log.d("response", e.toString());
                                                                                 e.printStackTrace();
                                                                             }

                                                                             @Override
                                                                             public void onResponse(Call call, okhttp3.Response response) throws IOException {
                                                                                 String resbody = response.body().string();
                                                                                 Log.d("response", response.toString());
                                                                                 Log.d("responsebody", resbody);

                                                                                 try {
                                                                                     // final JSONObject obj = new JSONObject(response);

                                                                                     final JSONArray facebookdata = new JSONArray(resbody);
                                                                                     int n = facebookdata.length();
                                                                                     for (int i = 0; i < n; ++i) {

                                                                                         final JSONObject fbuser = facebookdata.getJSONObject(i);

                                                                                         HashMap map = new HashMap();
                                                                                         map.put("name", fbuser.getString("name"));
                                                                                         map.put("doctorid", fbuser.getString("id"));
                                                                                         doctorlist.add(map);

                                                                                     }

                                                                                 } catch (JSONException e) {
                                                                                     e.printStackTrace();
                                                                                 }


                                                                                 ShowDoctors.this.runOnUiThread(new Runnable() {

                                                                                     @Override
                                                                                     public void run() {

                                                                                       docdialog = new DoctorDialogFragment();


                                                                                         Bundle args = new Bundle();
                                                                                         args.putSerializable("doctorlist", (Serializable) doctorlist);
                                                                                         docdialog.setArguments(args);
                                                                                         docdialog.show(getSupportFragmentManager(), "DoctorDialogFragment");
                                                                                         docdialog.setOnDoctorClickListner(new DoctorDialogFragment.OnDoctorClickListner() {
                                                                                             @Override
                                                                                             public void sendDoctors(final String doctorid) {

                                                                                                 JSONArray list = new JSONArray(selectedPatients);


                                                                                                 JSONObject json = new JSONObject();
                                                                                                 try {
                                                                                                     json.put("doctorid",doctorid);
                                                                                                     json.put("patientslist",list.toString());


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
                                                                                                         // ****************okhttp********delete doctor*********************


                                                                                                         String url5 = url4+docid+"";

                                                                                                         okhttp3.Request request = new okhttp3.Request.Builder()
                                                                                                                                .url(url5)
                                                                                                                                .delete()
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



                                                                                                                                ShowDoctors.this.runOnUiThread(new Runnable() {

                                                                                                                                    @Override
                                                                                                                                    public void run() {

                                                                                                                                        doctorList.remove(position);
                                                                                                                                        doctorsAdapter.notifyItemRemoved(position);
                                                                                                                                         }
                                                                                                                                   });
                                                                                                                              }
                                                                                                                        });
                                                                                                       //  ****************************************************


                                                                                                     }
                                                                                                 });

                                                                                             }

                                                                                             @Override
                                                                                             public void backPressed() {

                                                                                             }
                                                                                         });



                                                                                     }
                                                                                 });
                                                                             }
                                                                         });
                                     //************************************************

                                    }

                                    @Override
                                    public void backPressed() {
                                       Intent intent1=new Intent(getApplicationContext(),BottomNavigationOrganization.class);
                                        startActivity(intent1);

                                    }
                                });


                            }
                        });
                    }
                });


            }
        });

    }

}
