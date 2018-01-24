package posts.facebook.pranika.facebookapi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FrontPage extends AppCompatActivity {

    @BindView(R.id.organization)
    TextView organization;
    @BindView(R.id.doctor)
    TextView doctor;
    @BindView(R.id.loginorganization)
    TextView loginorg;
    @BindView(R.id.logindoctor)
    TextView logindoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);
        ButterKnife.bind(this);
    }

    public void ontextclick(View view) {

        Intent intent;

        switch (view.getId()){
            case R.id.organization:

                intent=new Intent(getApplicationContext(),OrganizationSignUp.class);
                startActivity(intent);

                break;
            case R.id.doctor:

                intent=new Intent(getApplicationContext(),DoctorSignUp.class);
                startActivity(intent);

                break;

            case R.id.loginorganization:

                intent=new Intent(getApplicationContext(),OrganizationLogin.class);
                startActivity(intent);

                break;

            case R.id.logindoctor:

                intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);

                break;



        }

    }
}
