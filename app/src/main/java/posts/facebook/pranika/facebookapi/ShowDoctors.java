package posts.facebook.pranika.facebookapi;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class ShowDoctors extends AppCompatActivity {
    TextView textView;

    RecyclerView recyclerView;
    DoctorsAdapter doctorsAdapter;
    RegisterdUsers.OnLevelClicklistner mlistner;
    private RecyclerView.LayoutManager mLayoutManager;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_doctors);

        textView = (TextView) findViewById(R.id.textpatient);
        recyclerView = (RecyclerView) findViewById(R.id.patientsrecycler_view);

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
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        AlphaInAnimationAdapter alpha = new AlphaInAnimationAdapter(doctorsAdapter);
        ScaleInAnimationAdapter scale = new ScaleInAnimationAdapter(doctorsAdapter);
        alpha.setDuration(2000);
        alpha.setInterpolator(new OvershootInterpolator());
        scale.setFirstOnly(false);

        Intent intent = getIntent();
        List<Map<String,?>> doctorList =  (List<Map<String,?>>) intent.getSerializableExtra("doctorslist");

        doctorsAdapter = new DoctorsAdapter(getApplicationContext(), doctorList);
        recyclerView.setAdapter(doctorsAdapter);
        recyclerView.setAdapter(new AlphaInAnimationAdapter(doctorsAdapter));
        recyclerView.setAdapter(new ScaleInAnimationAdapter(doctorsAdapter));

    }
}
