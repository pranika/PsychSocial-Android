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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MonthFragment extends Fragment {

    String url="http://192.168.1.10:3000/showfeedsmonth";
    List<Map<String,?>> feedList;
    RecyclerView recyclerView;
    Context context;
    FeedAdapter feedAdapter;
//    static MovieData movieData = new MovieData();
    OnClicklistner mlistner;
    private RecyclerView.LayoutManager mLayoutManager;


    public MonthFragment() {
    //    context=getContext();


    }
    public interface OnClicklistner {
        void showActivity(HashMap feed,int position,List<Map<String,?>> feedList);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_month, container, false);
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

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("feed",response);
                        try {
                            final JSONObject obj = new JSONObject(response);

                            final JSONArray facebookdata = obj.getJSONArray("month feed");
                            int n = facebookdata.length();
                            for (int i = 0; i < n; ++i) {

                                final JSONObject fbuser = facebookdata.getJSONObject(i);

                                Log.d("feeddata",fbuser.toString());

                                HashMap feed1 = new HashMap();


                                feed1.put("id",fbuser.getString("_id"));


                                feed1.put("email",fbuser.getString("email"));
                                feed1.put("name",fbuser.getString("name"));
                                feed1.put("gender",fbuser.getString("gender"));
                                feed1.put("age_range",fbuser.getString("age_range"));
                                feed1.put("createdtime",fbuser.getString("createdtime"));
                                feed1.put("story",fbuser.optString("story"));
                                feed1.put("message",fbuser.optString("message"));

                                feedList.add(feed1);


                            }

                            feedAdapter=new FeedAdapter(getActivity(),feedList);
                            recyclerView.setAdapter(feedAdapter);
                            recyclerView.setAdapter(new AlphaInAnimationAdapter(feedAdapter));
                            recyclerView.setAdapter(new ScaleInAnimationAdapter(feedAdapter));



                            Log.d("list",feedList.toString());
                            feedAdapter.setOnItemClickListner(new FeedAdapter.OnItemClickListner() {

                                @Override
                                public void onClick(View view, int position) {

                                    HashMap feed=new HashMap(position);
                                  //  Log.d("hashmap",feed.get("createdtime").toString());
                                    mlistner.showActivity(feed,position,feedList);


                                }
                            });


                        }
                        catch (Exception e)
                        {}





                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Something went wrong",Toast.LENGTH_LONG);
                error.printStackTrace();


            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);




    }


}
