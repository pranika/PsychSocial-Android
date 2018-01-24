package posts.facebook.pranika.facebookapi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.mongodb.util.JSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import io.branch.indexing.BranchUniversalObject;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;
import io.branch.referral.util.LinkProperties;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import posts.facebook.pranika.facebookapi.DaggerApp.DaggerApplication;

public class AuthAffectedPatients extends BaseActivity {


    private FirebaseAuth mauth;
    TextView textView;
    String patientid="",selfpatientid="";
    String url="";
    String name="",email="",createdtime="",message="",story="",sex="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_affected_patients);
        url="http://"+((DaggerApplication)this.getApplication()).getIpaddress()+"/Feed/getafeedupdated";
        Bundle extras = getIntent().getExtras();


        if (extras != null) {
            patientid = extras.getString("patientid");
            selfpatientid = extras.getString("selfpatientid");

            // and get whatever type user account id is


            textView = (TextView) findViewById(R.id.textview);
            mauth = FirebaseAuth.getInstance(myApp);


            JSONObject json = new JSONObject();
            try {
                SharedPreferences.Editor pEdit = pref.edit();

                if (patientid!=null && selfpatientid==null) {


                    json.put("substitute_patient", patientid);
                    pEdit.putString("substitute_patient", patientid);
                    pEdit.putString("selfpatientid","");
                    pEdit.putString("patient_id",patientid);

                    getaffectedPatients(json,patientid);
                    selfpatientid=null;
                } else if (selfpatientid!=null && patientid==null) {
                    json.put("doctor", mauth.getCurrentUser().getUid());

                    json.put("selfpatientid", selfpatientid);
                    pEdit.putString("selfpatientid", selfpatientid);
                    pEdit.putString("patient_id",selfpatientid);
                    getaffectedPatients(json,selfpatientid);
                    pEdit.putString("substitute_patient","");
                    patientid=null;

                }
                else if (patientid.equals("") && selfpatientid.equals("")) {
                    textView.setText("NO AFFECTED PATIENTS LISTED");

                }
                pEdit.commit();


            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }


        }
        else
            textView.setText("NO AFFECTED PATIENTS LISTED");

    }

    void getaffectedPatients(JSONObject json, final String patient){


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
            public void onResponse(Call call, final okhttp3.Response response) throws IOException {
//
                if(response.body().toString().equals("NO AFFECTED PATIENTS FOUND")) {

                    AuthAffectedPatients.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            textView.setText("NO AFFECTED PATIENTS LISTED");

                        }
                    });
                }
                else {
                    try {

                        String resbody = response.body().string();

                        final JSONArray facebookdata = new JSONArray(resbody);

                        final JSONObject object = facebookdata.getJSONObject(0);
                        name = object.getString("name");
                        email = object.getString("email");
                        message = object.optString("message");
                        story = object.optString("story");
                        sex = object.getString("gender");
                        createdtime = object.getString("createdtime");





                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    AuthAffectedPatients.this.runOnUiThread(new Runnable() {


                        @Override
                        public void run() {
                            SharedPreferences.Editor pEdit = pref.edit();
                            pEdit.putString("name", name);
                            System.out.print("prefname"+pref.getString("name",""));
                            pEdit.putString("email", email);
                            if (!message.equals(""))
                                pEdit.putString("feed", message);
                            if (!story.equals(""))
                                pEdit.putString("feed", story);
                            if(!message.equals("") && !story.equals("")){

                                pEdit.putString("feed", "Story is "+story+"    Message is "+message);
                            }
                            pEdit.putString("createdtime", createdtime);
                            pEdit.putString("gender", sex);
                            pEdit.commit();
                            Intent intent = new Intent(getApplicationContext(), TabbedActivity.class);
                            intent.putExtra("patient",patient);

                            startActivity(intent);
                        }
                    });
//
                }


            }
        });

    }
}
