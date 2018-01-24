package posts.facebook.pranika.facebookapi;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;


public class SingleFragment extends BaseFragment {





    TextView name,email,feed,createdtime,sex;

    public SingleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_single, container, false);

        name= (TextView) view.findViewById(R.id.name);
        email= (TextView) view.findViewById(R.id.email);
        feed= (TextView) view.findViewById(R.id.feed);
        sex= (TextView) view.findViewById(R.id.sex);
        createdtime= (TextView) view.findViewById(R.id.createdtime);
        String nametxt=pref.getString("name","");
        String emailtxt=pref.getString("email","");
        String feedtxt=pref.getString("feed","");
        String sextxt=pref.getString("gender","");
        String createdtxt=pref.getString("createdtime","");


        name.setText(nametxt);
        email.setText(emailtxt);
        feed.setText(feedtxt);
        sex.setText(sextxt);
        createdtime.setText(createdtxt);




//        final RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
//        StringRequest stringRequest=new StringRequest(Request.Method.GET, url,
//
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        Log.d("feedsingle",response);
//                        try {
//                            final JSONObject obj = new JSONObject(response);
//                            name.setText(obj.getString("name"));
//                            email.setText(obj.getString("email"));
//                            feed.setText(obj.getString("message"));
//                            sex.setText(obj.getString("gender"));
//                            createdtime.setText(obj.getString("createdtime"));
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
//                Toast.makeText(getActivity(),"Something went wrong",Toast.LENGTH_LONG);
//                error.printStackTrace();
//                requestQueue.stop();
//
//            }
//        });
//        requestQueue.add(stringRequest);
        return view;


    }




}
