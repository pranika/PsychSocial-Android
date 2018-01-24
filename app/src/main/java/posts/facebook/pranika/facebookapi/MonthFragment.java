package posts.facebook.pranika.facebookapi;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import posts.facebook.pranika.facebookapi.DaggerApp.DaggerApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MonthFragment extends BaseFragment {

    String url="";
    private FirebaseAuth mauth;
    List<Map<String,?>> feedList;
    RecyclerView recyclerView;
    Context context;
    FeedAdapter feedAdapter;

    OnClicklistner mlistner;
    private RecyclerView.LayoutManager mLayoutManager;


    public MonthFragment() {

    }
    public interface OnClicklistner {
        void showActivity(HashMap feed,int position,List<Map<String,?>> feedList);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_month, container, false);
        url="http://"+((DaggerApplication)this.getActivity().getApplication()).getIpaddress()+"/Feed/showfeedsmonth";
        mauth= FirebaseAuth.getInstance(myApp);
        try {
            mlistner = (OnClicklistner) v.getContext();
        }catch(Exception e){

        }

        recyclerView= (RecyclerView) v.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new SlideInLeftAnimator());
        recyclerView.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));
        recyclerView.getItemAnimator().setAddDuration(2000);
        recyclerView.getItemAnimator().setRemoveDuration(2000);
        recyclerView.getItemAnimator().setMoveDuration(2000);
        recyclerView.getItemAnimator().setChangeDuration(2000);
        recyclerView.setHasFixedSize(true);
        SlideInLeftAnimator animator = new SlideInLeftAnimator();
        animator.setInterpolator(new OvershootInterpolator());
        recyclerView.setItemAnimator(animator);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        AlphaInAnimationAdapter alpha = new AlphaInAnimationAdapter(feedAdapter);
        ScaleInAnimationAdapter scale = new ScaleInAnimationAdapter(feedAdapter);
        alpha.setDuration(2000);
        alpha.setInterpolator(new OvershootInterpolator());
        scale.setFirstOnly(false);

        feedList=new ArrayList<Map<String, ?>>();
        addData();

        return v;
    }
    public void addData()
    {

        String patientid=pref.getString("substitute_patient","");
        String selfpatientid=pref.getString("selfpatientid","");

        JSONObject json = new JSONObject();
        try {


            if(patientid!=""){
                json.put("substitute_patient",patientid);
               // pref.edit().putString("substitute_patient",patientid);
            }
            if(selfpatientid!=""){
                json.put("doctor",mauth.getCurrentUser().getUid());

                json.put("selfpatientid",selfpatientid);
            }


        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, json.toString());

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("response", e.toString());
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final okhttp3.Response response) throws IOException {

                    try {

                        String resbody = response.body().string();
                            final JSONObject obj = new JSONObject(resbody);

                            final JSONArray facebookdata = obj.getJSONArray("month feed");
                            int n = facebookdata.length();
                            for (int i = 0; i < n; ++i) {

                                final JSONObject fbuser = facebookdata.getJSONObject(i);

                                Log.d("feeddata", fbuser.toString());

                                HashMap feed1 = new HashMap();



                                feed1.put("email", fbuser.getString("email"));
                                feed1.put("name", fbuser.getString("name"));
                                feed1.put("gender", fbuser.getString("gender"));
                                feed1.put("age_range", fbuser.getString("age_range"));
                                feed1.put("createdtime", fbuser.getString("createdtime"));
                                feed1.put("story", fbuser.optString("story"));
                                feed1.put("message", fbuser.optString("message"));
                                feed1.put("detect_flag", fbuser.getString("detect_flag"));

                                feedList.add(feed1);
                            }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    MonthFragment.this.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            feedAdapter=new FeedAdapter(getActivity(),feedList);
                            recyclerView.setAdapter(feedAdapter);
                            recyclerView.setAdapter(new AlphaInAnimationAdapter(feedAdapter));
                            recyclerView.setAdapter(new ScaleInAnimationAdapter(feedAdapter));



                            Log.d("list",feedList.toString());
                            feedAdapter.setOnItemClickListner(new FeedAdapter.OnItemClickListner() {

                                @Override
                                public void onClick(View view, int position) {

                                    HashMap feed=new HashMap(position);

                                    mlistner.showActivity(feed,position,feedList);


                                }
                            });

                        }
                    });



            }
        });



    }


}
