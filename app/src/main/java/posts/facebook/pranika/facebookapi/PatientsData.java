package posts.facebook.pranika.facebookapi;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by nikhiljain on 8/6/17.
 */

public class PatientsData extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
