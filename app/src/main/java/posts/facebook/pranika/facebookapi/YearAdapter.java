package posts.facebook.pranika.facebookapi;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.recyclerview.animators.holder.AnimateViewHolder;

/**
 * Created by nikhiljain on 8/8/17.
 */

public class YearAdapter extends RecyclerView.Adapter<YearAdapter.ViewHolder> {
        Context context;
        List<Map<String, ?>> feedlist;
        YearAdapter.OnItemClickListner onItemClickListner;

public YearAdapter(Context context1, List<Map<String, ?>> feedList) {
        context=context1;
        feedlist=feedList;
        }
public interface OnItemClickListner{
    public void onClick(View view, int position);

}
    public void setOnItemClickListner(YearAdapter.OnItemClickListner onItemClickListner)
    {
        this.onItemClickListner = onItemClickListner;

    }
    @Override
    public YearAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v= LayoutInflater.from(parent.getContext()).inflate(R.layout.yearlist,parent,false);
        YearAdapter.ViewHolder viewHolder=new YearAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(YearAdapter.ViewHolder holder, int position) {
        HashMap feed=(HashMap)feedlist.get(position);
        String feed1="";
        holder.name.setText((String)feed.get("name"));
        holder.email.setText((String)feed.get("email"));
        holder.gender.setText((String)feed.get("gender"));
        //holder.age_range.setText((String)feed.get("age_range"));


        holder.create_time.setText((String)feed.get("createdtime"));
        if(!feed.get("post_image").equals(""))
            Picasso.with(context).load(feed.get("post_image").toString()).into(holder.imageView);
        if(feed.get("message").equals("") )
        {
            holder.messagetext.setVisibility(View.INVISIBLE);
            holder.message.setVisibility(View.INVISIBLE);

        }
        else{
            holder.messagetext.setVisibility(View.VISIBLE);
            holder.message.setVisibility(View.VISIBLE);
            int length =  feed.get("message").toString().length();
            if(length>15 &&length<30)
            {
                holder.message.setText((String)feed.get("message").toString().substring(0,length-10).concat("......"));

            }
            if(length>30 &&length<40)
            {
                holder.message.setText((String)feed.get("message").toString().substring(0,length-15).concat("......"));
            }
            if(length>40 &&length<60)
            {
                holder.message.setText((String)feed.get("message").toString().substring(0,length-25).concat("......"));
            }
            if(length>60 &&length<80)
            {
                holder.message.setText((String)feed.get("message").toString().substring(0,length-45).concat("......"));
            }
            if(length>80 &&length<100)
            {
                holder.message.setText((String)feed.get("message").toString().substring(0,length-55).concat("......"));
            }
            if(length>100 &&length<120)
            {
                holder.message.setText((String)feed.get("message").toString().substring(0,length-65).concat("......"));
            }
            if(length>120 &&length<140)
            {
                holder.message.setText((String)feed.get("message").toString().substring(0,length-75).concat("......"));
            }
            if(length>140 &&length<180)
            {
                holder.message.setText((String)feed.get("message").toString().substring(0,length-85).concat("......"));
            }
            if(length>180 &&length<200)
            {
                holder.message.setText((String)feed.get("message").toString().substring(0,length-95).concat("......"));
            }
            else
                holder.message.setText((String) feed.get("message"));


        }
        if(feed.get("story").equals("") )
        {
            holder.feedtext.setVisibility(View.INVISIBLE);
            holder.feed.setVisibility(View.INVISIBLE);

        }
        else{
            holder.feedtext.setVisibility(View.VISIBLE);
            holder.feed.setVisibility(View.VISIBLE);
            int length =  feed.get("message").toString().length();
            if(length>15 &&length<30)
            {
            holder.feed.setText((String)feed.get("story").toString().substring(0,length-10).concat("......"));
            }
            if(length>30 &&length<40)
            {
                holder.feed.setText((String)feed.get("story").toString().substring(0,length-15).concat("......"));
            }
            if(length>40 &&length<60)
            {
                holder.feed.setText((String)feed.get("story").toString().substring(0,length-25).concat("......"));
            }
            if(length>60 &&length<80)
            {
                holder.feed.setText((String)feed.get("story").toString().substring(0,length-45).concat("......"));
            }
            if(length>80 &&length<100)
            {
                holder.feed.setText((String)feed.get("story").toString().substring(0,length-55).concat("......"));
            }
            if(length>100 &&length<120)
            {
                holder.feed.setText((String)feed.get("story").toString().substring(0,length-65).concat("......"));
            }
            if(length>120 &&length<140)
            {
                holder.feed.setText((String)feed.get("story").toString().substring(0,length-75).concat("......"));
            }
            if(length>140 &&length<180)
            {
                holder.feed.setText((String)feed.get("story").toString().substring(0,length-85).concat("......"));
            }
            if(length>180 &&length<200)
            {
                holder.feed.setText((String)feed.get("story").toString().substring(0,length-95).concat("......"));
            }
            else {
                holder.feed.setText((String) feed.get("story"));
            }
        }
    }

    @Override
    public int getItemCount() {
        return feedlist.size();
    }

public class ViewHolder extends RecyclerView.ViewHolder implements AnimateViewHolder {


    private AnimateViewHolder viewHolder;
    public ImageView imageView;
    public TextView name;
    public TextView email;
    public TextView gender;
    public TextView feedtext;
    public TextView feed;
    public TextView messagetext;
    public TextView message;
    public TextView age_range;
    public TextView create_time;
    public Button button;
    public ViewHolder(View itemView) {
        super(itemView);
        button= (Button) itemView.findViewById(R.id.showmoreyear);
        imageView=(ImageView) itemView.findViewById(R.id.image1);


        feedtext= (TextView) itemView.findViewById(R.id.feedtextyear);
        messagetext= (TextView) itemView.findViewById(R.id.messagetextyear);

        feed=(TextView) itemView.findViewById(R.id.feedyear);
        message=(TextView) itemView.findViewById(R.id.messageyear);

        name =(TextView) itemView.findViewById(R.id.nameyear);
        email=(TextView) itemView.findViewById(R.id.emailyear);
        gender=(TextView) itemView.findViewById(R.id.genderyear);
       // age_range=(TextView) itemView.findViewById(R.id.age_rangeyear);

        create_time=(TextView) itemView.findViewById(R.id.created_timeyear);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListner.onClick(v,getAdapterPosition());
            }
        });


    }

    @Override
    public void preAnimateAddImpl(RecyclerView.ViewHolder holder) {

    }

    @Override
    public void preAnimateRemoveImpl(RecyclerView.ViewHolder holder) {
        ViewCompat.setTranslationY(itemView, -itemView.getHeight() * 0.3f);
        ViewCompat.setAlpha(itemView, 0);
    }

    @Override
    public void animateAddImpl(RecyclerView.ViewHolder holder, ViewPropertyAnimatorListener listener) {
        ViewCompat.animate(itemView)
                .translationY(0)
                .alpha(1)
                .setDuration(300)
                .setListener(listener)
                .start();
    }

    @Override
    public void animateRemoveImpl(RecyclerView.ViewHolder holder, ViewPropertyAnimatorListener listener) {
        ViewCompat.animate(itemView)
                .translationY(-itemView.getHeight() * 0.3f)
                .alpha(0)
                .setDuration(300)
                .setListener(listener)
                .start();
    }
}
}
