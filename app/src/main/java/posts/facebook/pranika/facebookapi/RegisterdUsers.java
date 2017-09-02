package posts.facebook.pranika.facebookapi;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;


public class RegisterdUsers extends Fragment {

    TextView textView;
    private Firebase ref;
    String url="";
    private FirebaseAuth mauth;
    RecyclerView recyclerView;
    PatientsAdapter patientsAdapter;
    OnLevelClicklistner mlistner;
    private RecyclerView.LayoutManager mLayoutManager;
    //RegisteredAdapter adapter;
    String userid="";
    List<Map<String,?>> feedList;
   // RegisteredUsersAdapter myFirebaseRecylerAdapter;
        Context mContext;


    public interface OnLevelClicklistner {
        void showLevelActivity(HashMap feed,int position,List<Map<String,?>> feedList);
    }
    public RegisterdUsers() {

        feedList = new ArrayList<Map<String,?>>();
       // adapter = null;

        // Required empty public constructor
    }
//
//    public void setAdapter(RegisteredAdapter mAdapter) {
//
//        adapter = mAdapter;
//    }

    public void setContext(Context context){mContext = context;}






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview= inflater.inflate(R.layout.fragment_registerd_users, container, false);
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


        mauth=FirebaseAuth.getInstance();
        userid=mauth.getCurrentUser().getUid().toString();
        ref=new Firebase
                ("https://facebookdepressionapi.firebaseio.com/doctors/"+userid+"/patients");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
            try {
                System.out.println("datasnapshot"+dataSnapshot.getValue());

                if (dataSnapshot.getValue().equals(null)) {

                   textView.setText("NO PATIENTS REGISTERED YET");
                    textView.setVisibility(View.VISIBLE);

                }

            }
            catch(Exception e)
            {

                textView.setText("NO PATIENTS REGISTERED YET");
                textView.setVisibility(View.VISIBLE);

            }


                try {

                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {


                        String latestKey = childSnapshot.getKey();
                        HashMap feed = (HashMap) childSnapshot.getValue();
                        Log.d("key", latestKey);
                        Log.d("feed", childSnapshot.getValue().toString());

//                        int insertPosition = 0;
//                        String id = (String) feed.get("id");
//                        for (int i = 0; i < feedList.size(); i++) {
//
//                            String mid = (String) feed.get("id");
//                            if (mid.equals(id)) {
//                                return;
//                            }
//                            if (mid.compareTo(id) < 0) {
//                                insertPosition = i + 1;
//                            } else {
//                                break;
//                            }
//
//                        }
                        feedList.add(feed);
                        Log.d("feedslist firbase", feedList.toString());
                        patientsAdapter = new PatientsAdapter(getActivity(), feedList, latestKey);
                        recyclerView.setAdapter(patientsAdapter);
                        recyclerView.setAdapter(new AlphaInAnimationAdapter(patientsAdapter));
                        recyclerView.setAdapter(new ScaleInAnimationAdapter(patientsAdapter));
                        patientsAdapter.setOnItemClickListner(new PatientsAdapter.OnItemClickListner() {

                            @Override
                            public void onClick(View view, int position) {

                                HashMap feed=new HashMap(position);
                                //  Log.d("hashmap",feed.get("createdtime").toString());
                                mlistner.showLevelActivity(feed,position,feedList);


                            }
                        });

                        Log.d("list", feedList.toString());


                    }
                }catch (Exception e)
                {}






            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });






        return rootview;
    }

    public void initializedatafromcloud()
    {
        //feedList.clear();




    }




}
