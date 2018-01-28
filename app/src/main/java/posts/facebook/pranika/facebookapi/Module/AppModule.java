package posts.facebook.pranika.facebookapi.Module;

import android.content.Context;
import android.content.SharedPreferences;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.readystatesoftware.chuck.ChuckInterceptor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import posts.facebook.pranika.facebookapi.DaggerApp.DaggerApplication;
import retrofit2.Retrofit;

/**
 * Created by nikhiljain on 11/16/17.
 */

@Module
public class AppModule {

    private final DaggerApplication application;

    public AppModule(DaggerApplication app){

        application=app;
    }

    @Provides
    @Singleton
    Context providesContext(){
        return  application;
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPrefences(Context context){

        return application.getSharedPreferences("MY_PREFERENCES",context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    OkHttpClient provideokhttpClient(Context context){

        return new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .addInterceptor(new ChuckInterceptor(context))
                .build();
    }

    @Provides
    @Singleton
    Retrofit providesRetrofitClient(OkHttpClient client){

        return new Retrofit.Builder().
                client(client).
                addConverterFactory(MoshiConverterFactory.create())
    }

    @Provides
    @Singleton
    FirebaseApp providesFirebaseApp(Context context){

        FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
                .setDatabaseUrl("https://facebookdepressionapi.firebaseio.com/")
                .setApiKey("AIzaSyD-1l4YOU3I09rZlSURCOxBcvQL65fCaFY")
                .setApplicationId("facebookdepressionapi").build();

     FirebaseApp myApp = com.google.firebase.FirebaseApp.initializeApp(context,firebaseOptions,
                "DoctorApp");
        return myApp;

    }




}
