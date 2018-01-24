package posts.facebook.pranika.facebookapi;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 * Use the {@link AffectedPatientsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AffectedPatientsFragment extends  BaseFragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String url="";
    private FirebaseAuth mauth;
    List<Map<String,?>> patientlist;
    RecyclerView recyclerView;
    AffectedPatientsAdapter patientsAdapter;
    OnItemClickListner mlistner;
    private RecyclerView.LayoutManager mLayoutManager;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public interface OnItemClickListner {
        void showFeeds(HashMap feed,int position,List<Map<String,?>> feedList);
    }



    public AffectedPatientsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AffectedPatientsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AffectedPatientsFragment newInstance(String param1, String param2) {
        AffectedPatientsFragment fragment = new AffectedPatientsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        // Inflate the layout for this fragment
        View rootview=inflater.inflate(R.layout.fragment_affected_patients, container, false);
        try {
            mlistner = (AffectedPatientsFragment.OnItemClickListner) rootview.getContext();
        }catch(Exception e){

        }

        url = "http://"+((DaggerApplication)this.getActivity().getApplication()).getIpaddress()+"/Patients/getAffectedPatients";
        patientlist=new ArrayList<Map<String,?>>();
        //textView= (TextView) rootview.findViewById(R.id.textpatient);
        recyclerView= (RecyclerView) rootview.findViewById(R.id.recycler_view_patients);

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



        mauth= FirebaseAuth.getInstance(myApp);
        String userid=mauth.getCurrentUser().getUid().toString();

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
                        map.put("userid",fbuser.getString("id"));
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

                AffectedPatientsFragment.this.getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        patientsAdapter = new AffectedPatientsAdapter(getActivity(), patientlist);
                        recyclerView.setAdapter(patientsAdapter);
                        recyclerView.setAdapter(new AlphaInAnimationAdapter(patientsAdapter));
                        recyclerView.setAdapter(new ScaleInAnimationAdapter(patientsAdapter));
                        patientsAdapter.setOnItemClickListner(new AffectedPatientsAdapter.OnItemClickListner() {
                            @Override
                            public void onClick(View view, int position) {

                                HashMap feed=new HashMap(position);
                                mlistner.showFeeds(feed,position,patientlist);


                            }
                        });

                    }
                });

            }
        });

        return rootview;



}






}
