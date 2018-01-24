package posts.facebook.pranika.facebookapi;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.google.firebase.FirebaseApp;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import posts.facebook.pranika.facebookapi.DaggerApp.DaggerApplication;

/**
 * Created by nikhiljain on 11/16/17.
 */

public class BaseActivity extends AppCompatActivity {
    @Inject
    public SharedPreferences pref;
    @Inject
    public Context context;
    @Inject
    public OkHttpClient client;

    @Inject
    public FirebaseApp myApp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((DaggerApplication)getApplication()).getAppComponent().inject(this);
    }
}
