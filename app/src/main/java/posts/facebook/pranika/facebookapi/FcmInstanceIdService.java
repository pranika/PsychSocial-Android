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

/**
 * Created by nikhiljain on 9/3/17.
 */

public class FcmInstanceIdService extends FirebaseInstanceIdService {
    FirebaseAuth mauth;
    String docid="";
    String recent_token="";
    String app_url="http://10.1.195.231:3000/update_token";
    @Override
    public void onTokenRefresh() {
        mauth= FirebaseAuth.getInstance();

        recent_token= FirebaseInstanceId.getInstance().getToken();
        System.out.println("recent token"+recent_token);
        SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences(getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(getString(R.string.FCM_TOKEN),recent_token);
        editor.commit();
        if(mauth!=null){

            if(mauth.getCurrentUser().getUid().toString() !=null) {

                docid = mauth.getCurrentUser().getUid().toString();

                //********************************************

                StringRequest stringRequest = new StringRequest(Request.Method.POST, app_url,
                        new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(FcmInstanceIdService.this,"not responding",Toast.LENGTH_LONG).show();
                        System.out.println("no responding");

                    }
                })

                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("docid", docid);
                        params.put("token", recent_token);

//                                       params.put("professorid",professorid);

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

