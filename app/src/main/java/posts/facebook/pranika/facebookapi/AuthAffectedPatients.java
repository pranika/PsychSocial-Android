package posts.facebook.pranika.facebookapi;

import android.content.Intent;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class AuthAffectedPatients extends AppCompatActivity {

    String url="http://192.168.1.116:3000/getafeedupdated";
    private FirebaseAuth mauth;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_affected_patients);
        textView= (TextView) findViewById(R.id.textview);
        mauth= FirebaseAuth.getInstance();

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("feed",response);
                        try {
                            final JSONObject obj = new JSONObject(response);


                            String doctor=obj.getString("doctorid").toString();

                            Log.d("doctor string",doctor);
                            String currentdoc=mauth.getCurrentUser().getUid().toString();

                            Log.d("doctor string 2",currentdoc);
                            if(doctor.equals(currentdoc))
                            {
                                Intent intent=new Intent(getApplicationContext(),TabbedActivity.class);
                                startActivity(intent);
                            }
                            else
                            {

                                textView.setText("NO AFFECTED PATIENTS LISTED");
                            }


                        }
                        catch (Exception e)
                        {}





                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_LONG);
                error.printStackTrace();


            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);





    }
}
