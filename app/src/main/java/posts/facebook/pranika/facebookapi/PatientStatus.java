package posts.facebook.pranika.facebookapi;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PatientStatus extends AppCompatActivity {

    ImageButton low,mild,high,update;
    String status="";
    String app_url="http://10.1.193.91:3000/update_status";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_status);
        low= (ImageButton) findViewById(R.id.low);
        mild= (ImageButton) findViewById(R.id.mild);
        high= (ImageButton) findViewById(R.id.high);
       // update= (Button) findViewById(R.id.update);

        //update.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {









                //********************************************







       // });


    }

    public void onButtonClicked(View view) {

        switch (view.getId()) {
            case R.id.low:

                status= "low";
                break;
            case R.id.mild:
                status="medium";

                break;
            case R.id.high:
               status="high";
                break;
        }

        Bundle data=getIntent().getExtras();
        final String patient_id=data.getString("patient_id");

        //********************************************

        StringRequest stringRequest = new StringRequest(Request.Method.POST, app_url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("patient_id", patient_id);
                params.put("status",status);

//                                       params.put("professorid",professorid);

                return params;
            }
        };
        MySingleton.getmInstance(PatientStatus.this).addToRequestQue(stringRequest);






    }
}
