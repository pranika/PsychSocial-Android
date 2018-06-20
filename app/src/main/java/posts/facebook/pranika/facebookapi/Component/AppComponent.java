package posts.facebook.pranika.facebookapi.Component;

import javax.inject.Singleton;

import dagger.Component;
import posts.facebook.pranika.facebookapi.BaseActivity;
import posts.facebook.pranika.facebookapi.BaseFragment;
import posts.facebook.pranika.facebookapi.DaggerApp.DaggerApplication;
import posts.facebook.pranika.facebookapi.DoctorSignUp;
import posts.facebook.pranika.facebookapi.FcmInstanceIdService;
import posts.facebook.pranika.facebookapi.Module.AppModule;
import posts.facebook.pranika.facebookapi.NonNPISignUp;
//import posts.facebook.pranika.facebookapi.data.DoctorDataSource;
//import posts.facebook.pranika.facebookapi.data.DoctorSignUpNonNPIDataSource;

/**
 * Created by nikhiljain on 11/6/17.
 */


@Singleton @Component(modules={AppModule.class})
public interface AppComponent {

    void inject(DaggerApplication application);
    void inject(BaseActivity baseActivity);
    void inject(FcmInstanceIdService fcmInstanceIdService);
    void inject(BaseFragment fragment);
//    void inject(DoctorDataSource doctorDataSource);
//    void inject(DoctorSignUpNonNPIDataSource doctorSignUpNonNPIDataSource);

}
