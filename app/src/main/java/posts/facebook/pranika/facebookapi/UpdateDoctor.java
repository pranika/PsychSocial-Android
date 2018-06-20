package posts.facebook.pranika.facebookapi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import posts.facebook.pranika.facebookapi.DaggerApp.DaggerApplication;

public class UpdateDoctor extends AppCompatActivity {

    @BindView(R.id.name)
    EditText nametext;
    @BindView(R.id.email)
    EditText emailtext;

    @BindView(R.id.specialisation)
    EditText specializationtext;
    @BindView(R.id.update)
    Button updatebutton;
    String url="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_doctor);

        ButterKnife.bind(this);
        url="http://"+((DaggerApplication)this.getApplication()).getIpaddress()+"/Doctors/";

        Intent intent = getIntent();
        final HashMap doctor = (HashMap) intent.getSerializableExtra("doctor");
        nametext.setText(doctor.get("name").toString());
        emailtext.setText(doctor.get("email").toString());

        specializationtext.setText(doctor.get("specialization").toString());
        updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String doctorid=doctor.get("doctorid").toString();
                String name=nametext.getText().toString();
                String email=emailtext.getText().toString();

                String specialization=specializationtext.getText().toString();

                OkHttpClient client = new OkHttpClient.Builder().build();


                JSONObject json = new JSONObject();
                try {

                    json.put("name",name);
                    json.put("email",email);

                    json.put("specialization",specialization);
                    json.put("doctorid",doctorid);



                } catch (JSONException e) {
                    e.printStackTrace();
                }

                final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

                RequestBody body = RequestBody.create(JSON, json.toString());
                String url1 = url+doctorid;
                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url(url1)
                        .put(body)
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

                        UpdateDoctor.this.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                Intent intent1=new Intent(getApplicationContext(),BottomNavigationOrganization.class);
                                startActivity(intent1);


                            }
                        });

                    }
                });


            }
        });

    }
}
