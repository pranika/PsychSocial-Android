package posts.facebook.pranika.facebookapi;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Feeds extends AppCompatActivity {
    Button button,button1;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeds);
        button= (Button) findViewById(R.id.show);
        button1= (Button) findViewById(R.id.show1);
        textView= (TextView) findViewById(R.id.text);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(),SingleFeed.class);
                startActivity(intent);



            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                Intent intent=new Intent(getApplicationContext(),MonthFeed.class);
                startActivity(intent);

            }
        });
    }
}
