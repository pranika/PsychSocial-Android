package posts.facebook.pranika.facebookapi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;

public class SignUpDecision extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_decision);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.npi:
                if (checked) {
                    Intent intent = new Intent(getApplicationContext(), DoctorSignUp.class);
                    startActivity(intent);
                }

                    break;
            case R.id.nonpi:
                if (checked)
                {
                    Intent intent = new Intent(getApplicationContext(), NonNPISignUp.class);
                    startActivity(intent);
                }

                    break;
        }
    }
}
