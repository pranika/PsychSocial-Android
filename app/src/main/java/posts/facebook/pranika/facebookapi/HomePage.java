package posts.facebook.pranika.facebookapi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomePage extends BaseActivity {
    @BindView(R.id.orgsignup)
    Button organization;
    @BindView(R.id.docsignup)
    Button doctor;
    @BindView(R.id.orglogin)
    Button loginorg;
    @BindView(R.id.doclogin)
    Button logindoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ButterKnife.bind(this);
    }

    public void ontextclick(View view) {

        Intent intent;

        switch (view.getId()){
            case R.id.orgsignup:

                intent=new Intent(getApplicationContext(),OrganizationSignUp.class);
                startActivity(intent);

                break;
            case R.id.docsignup:

                intent=new Intent(getApplicationContext(),SignUpDecision.class);
                startActivity(intent);

                break;

            case R.id.orglogin:

                intent=new Intent(getApplicationContext(),OrganizationLogin.class);
                startActivity(intent);

                break;

            case R.id.doclogin:

                intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);

                break;



        }

    }
}
