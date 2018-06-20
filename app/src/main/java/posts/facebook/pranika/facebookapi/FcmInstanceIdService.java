package posts.facebook.pranika.facebookapi;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import posts.facebook.pranika.facebookapi.DaggerApp.DaggerApplication;

/**
 * Created by nikhiljain on 9/3/17.
 */

public class FcmInstanceIdService extends FirebaseInstanceIdService {
    FirebaseAuth mauth;
    String orgid="";
    String recent_token="";
    String app_url="";

    @Inject
    SharedPreferences pref;
    @Override
    public void onTokenRefresh() {
        mauth= FirebaseAuth.getInstance();
        ((DaggerApplication)getApplication()).getAppComponent().inject(this);
        app_url="http://128.230.247.131/e/update_token";

        recent_token= FirebaseInstanceId.getInstance().getToken();
        System.out.println("recent token"+recent_token);

        pref.edit().putString("fcm_token",recent_token).commit();

        if(mauth.getCurrentUser()!=null){

            if(mauth.getCurrentUser().getUid().toString() !=null) {

                orgid = mauth.getCurrentUser().getUid().toString();

                //********************************************

                StringRequest stringRequest = new StringRequest(Request.Method.POST, app_url,
                        new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {



                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       // Toast.makeText(FcmInstanceIdService.this,"not responding",Toast.LENGTH_LONG).show();
                        System.out.println("no responding");

                    }
                })

                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("orgid", orgid);
                        params.put("token", recent_token);

                        return params;
                    }
                };
                MySingleton.getmInstance(FcmInstanceIdService.this).addToRequestQue(stringRequest);


            }
            else
            {}

        }
        else
        {}

        //********************************************



    }
}

