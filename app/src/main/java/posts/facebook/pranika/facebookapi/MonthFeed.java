package posts.facebook.pranika.facebookapi;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonthFeed extends AppCompatActivity {

    String url="http://192.168.69.120:3000/showfeedsmonth";
    List<Map<String,?>> feedList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_feed);
                MonthFragment fragment = new MonthFragment();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.commit();
//        feedList=new ArrayList<Map<String, ?>>();
//        final RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
//        StringRequest stringRequest=new StringRequest(Request.Method.GET, url,
//
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        Log.d("feed",response);
//                        try {
//                            final JSONObject obj = new JSONObject(response);
//
//                            final JSONArray facebookdata = obj.getJSONArray("month feed");
//                            int n = facebookdata.length();
//                            for (int i = 0; i < n; ++i) {
//
//                                final JSONObject fbuser = facebookdata.getJSONObject(i);
//
//                                Log.d("feeddata",fbuser.toString());
//
//                                HashMap feed1 = new HashMap();
//
//                                feed1.put("id",fbuser.getString("_id"));
//                                feed1.put("email",fbuser.getString("email"));
//                                feed1.put("name",fbuser.getString("name"));
//                                feed1.put("gender",fbuser.getString("gender"));
//                                feed1.put("age_range",fbuser.getString("age_range"));
//                                feed1.put("createdtime",fbuser.getString("createdtime"));
//                                feed1.put("story",fbuser.optString("story"));
//                                feed1.put("message",fbuser.optString("message"));
//
//                                feedList.add(feed1);
//
//
//                            }
//                            Log.d("list",feedList.toString());
//
//
//                        }
//                        catch (Exception e)
//                        {}
//
//
//
//                        requestQueue.stop();
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_LONG);
//                error.printStackTrace();
//                requestQueue.stop();
//
//            }
//        });
//        requestQueue.add(stringRequest);

    }
}
