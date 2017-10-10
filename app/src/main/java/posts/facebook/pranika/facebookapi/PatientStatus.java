package posts.facebook.pranika.facebookapi;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PatientStatus extends AppCompatActivity {


    String type = "";
    RadioButton level;
    Button button;

    String app_url = "http://192.168.1.10:3000/update_status";
    int selectedid=0;
    private RadioGroup group;
    Button update;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_status);
        group= (RadioGroup) findViewById(R.id.radio);

        button= (Button) findViewById(R.id.update);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle data = getIntent().getExtras();
                final String patient_id = data.getString("patient_id");


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
                        params.put("status", type);

                        return params;
                    }
                };
                MySingleton.getmInstance(PatientStatus.this).addToRequestQue(stringRequest);
            }
        });



    }

        public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();
        selectedid =group.getCheckedRadioButtonId();


        switch (view.getId()) {
            case R.id.one:

                if (checked)
                    level = (RadioButton) findViewById(selectedid);
                    type = level.getText().toString().trim();

                break;
            case R.id.two:
                if (checked)
                    level = (RadioButton) findViewById(selectedid);
                    type = level.getText().toString().trim();
                break;
            case R.id.three:
                if (checked)
                    level = (RadioButton) findViewById(selectedid);
                    type = level.getText().toString().trim();
                break;
            case R.id.four:
                if (checked)
                    level = (RadioButton) findViewById(selectedid);
                type = level.getText().toString().trim();
                break;
            case R.id.five:
                if (checked)
                    level = (RadioButton) findViewById(selectedid);
                type = level.getText().toString().trim();
                break;
            case R.id.six:
                if (checked)
                    level = (RadioButton) findViewById(selectedid);
                type = level.getText().toString().trim();
                break;
            case R.id.seven:
                if (checked)
                    level = (RadioButton) findViewById(selectedid);
                type = level.getText().toString().trim();
                break;
            case R.id.eight:
                if (checked)
                    level = (RadioButton) findViewById(selectedid);
                type = level.getText().toString().trim();
                break;
            case R.id.nine:
                if (checked)
                    level = (RadioButton) findViewById(selectedid);
                type = level.getText().toString().trim();
                break;
            case R.id.ten:
                if (checked)
                    level = (RadioButton) findViewById(selectedid);
                type = level.getText().toString().trim();
                break;

        }
    }

}