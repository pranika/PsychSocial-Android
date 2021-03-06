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
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import posts.facebook.pranika.facebookapi.DaggerApp.DaggerApplication;


public class RegisterdUsers extends BaseFragment {

    TextView textView;
    private Firebase ref;
    String url="";
    private FirebaseAuth mauth;
    List<Map<String,?>> patientlist;
    RecyclerView recyclerView;
    PatientsAdapter patientsAdapter;
    OnLevelClicklistner mlistner;
    private RecyclerView.LayoutManager mLayoutManager;
    String userid="";
    List<Map<String,?>> feedList;

        Context mContext;

    public interface OnLevelClicklistner {
        void showLevelActivity(HashMap feed,int position,List<Map<String,?>> feedList);
    }
    public RegisterdUsers() {

        feedList = new ArrayList<Map<String,?>>();

    }
    public void setContext(Context context){mContext = context;}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        // Inflate the layout for this fragment
        View rootview= inflater.inflate(R.layout.fragment_registerd_users, container, false);
        url = "http://"+((DaggerApplication)this.getActivity().getApplication()).getIpaddress()+"/Patients/getPatients";
        patientlist=new ArrayList<Map<String,?>>();
        textView= (TextView) rootview.findViewById(R.id.textpatient);
        recyclerView= (RecyclerView) rootview.findViewById(R.id.patientsrecycler_view);
        try {
            mlistner = (OnLevelClicklistner) rootview.getContext();
        }catch(Exception e){

        }
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
        AlphaInAnimationAdapter alpha = new AlphaInAnimationAdapter(patientsAdapter);
        ScaleInAnimationAdapter scale = new ScaleInAnimationAdapter(patientsAdapter);
        alpha.setDuration(2000);
        alpha.setInterpolator(new OvershootInterpolator());
        scale.setFirstOnly(false);



        mauth=FirebaseAuth.getInstance(myApp);
        userid=mauth.getCurrentUser().getUid().toString();

        //****************************okhttp****************************************

        JSONObject json = new JSONObject();
        try {

            json.put("doctorid",userid);



        } catch (JSONException e) {
            e.printStackTrace();
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
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                String resbody = response.body().string();

                try {


                    final JSONArray facebookdata = new JSONArray(resbody);
                    int n = facebookdata.length();
                    for (int i = 0; i < n; ++i) {

                        final JSONObject fbuser = facebookdata.getJSONObject(i);

                        HashMap map=new HashMap();
                        map.put("name",fbuser.getString("name"));
                        map.put("email",fbuser.getString("email"));
                        map.put("sex",fbuser.getString("sex"));
                        map.put("age_range",fbuser.getString("age_range"));
                        map.put("usn",fbuser.getString("usn"));

                        patientlist.add(map);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                RegisterdUsers.this.getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        patientsAdapter = new PatientsAdapter(getActivity(), patientlist);
                        recyclerView.setAdapter(patientsAdapter);
                        recyclerView.setAdapter(new AlphaInAnimationAdapter(patientsAdapter));
                        recyclerView.setAdapter(new ScaleInAnimationAdapter(patientsAdapter));
                        patientsAdapter.setOnItemClickListner(new PatientsAdapter.OnItemClickListner() {

                            @Override
                            public void onClick(View view, int position) {
                                HashMap feed=new HashMap(position);
                                mlistner.showLevelActivity(feed,position,feedList);


                            }
                        });

                    }
                });

            }
        });

     return rootview;
    }


}
