package posts.facebook.pranika.facebookapi;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.FirebaseApp;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import posts.facebook.pranika.facebookapi.DaggerApp.DaggerApplication;

/**
 * Created by nikhiljain on 11/19/17.
 */

public class BaseFragment extends Fragment {
    @Inject
    public SharedPreferences pref;
    @Inject
    public Context context;
    @Inject
    public OkHttpClient client;
    @Inject
    public FirebaseApp myApp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((DaggerApplication)getActivity().getApplication()).getAppComponent().inject(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
