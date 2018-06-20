package posts.facebook.pranika.facebookapi.DaggerApp;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.firebase.client.Firebase;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import io.branch.referral.Branch;
import posts.facebook.pranika.facebookapi.Component.AppComponent;
import posts.facebook.pranika.facebookapi.Component.DaggerAppComponent;
import posts.facebook.pranika.facebookapi.Module.AppModule;

/**
 * Created by nikhiljain on 11/16/17.
 */

public class DaggerApplication extends Application {

    AppComponent appComponent;
    static String ipaddress="128.230.247.131/s";

    //static String ipaddress="128.230.153.45:1337";
    public String getIpaddress() {
        return ipaddress;
    }


    public static RefWatcher getRefWatcher(Context context) {
        DaggerApplication application = (DaggerApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
        Stetho.initializeWithDefaults(this);



        refWatcher = LeakCanary.install(this);
        // Normal app init code...

        appComponent= DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        appComponent.inject(this);


    }
    public AppComponent getAppComponent(){
        return appComponent;
    }
}
