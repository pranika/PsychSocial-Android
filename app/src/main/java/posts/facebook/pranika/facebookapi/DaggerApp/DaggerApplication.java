package posts.facebook.pranika.facebookapi.DaggerApp;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.firebase.client.Firebase;

import io.branch.referral.Branch;
import posts.facebook.pranika.facebookapi.Component.AppComponent;
import posts.facebook.pranika.facebookapi.Component.DaggerAppComponent;
import posts.facebook.pranika.facebookapi.Module.AppModule;

/**
 * Created by nikhiljain on 11/16/17.
 */

public class DaggerApplication extends Application {

    AppComponent appComponent;
    static String ipaddress="10.1.245.214:1337";


    public String getIpaddress() {
        return ipaddress;
    }




    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
        Stetho.initializeWithDefaults(this);
        Branch.enableLogging();
        // Initialize the Branch object
        Branch.getAutoInstance(this);

        appComponent= DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        appComponent.inject(this);


    }
    public AppComponent getAppComponent(){
        return appComponent;
    }
}
