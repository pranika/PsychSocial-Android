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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.recyclerview.animators.holder.AnimateViewHolder;

/**
 * Created by nikhiljain on 8/5/17.
 */

public class WeekAdapter extends RecyclerView.Adapter<WeekAdapter.ViewHolder> {
    Context context;
    List<Map<String, ?>> feedlist;
    int position;
    FeedAdapter.OnItemClickListner onItemClickListner;

    public WeekAdapter(Context context1, List<Map<String, ?>> feedList) {
        context=context1;
        feedlist=feedList;
    }
    public interface OnItemClickListner{
        public void onClick(View view,int position);

    }
    public void setOnItemClickListner(FeedAdapter.OnItemClickListner onItemClickListner)
    {
        this.onItemClickListner = onItemClickListner;

    }

    @Override
    public int getItemViewType(int position) {

        return super.getItemViewType(position);
    }

    @Override
    public WeekAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v= LayoutInflater.from(parent.getContext()).inflate(R.layout.weeklist,parent,false);
        WeekAdapter.ViewHolder viewHolder=new WeekAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(WeekAdapter.ViewHolder holder, int position) {
        HashMap feed=(HashMap)feedlist.get(position);

        String feed1="";
        holder.name.setText((String)feed.get("name"));
        holder.email.setText((String)feed.get("email"));
        holder.gender.setText((String)feed.get("gender"));

        holder.create_time.setText((String)feed.get("createdtime"));

        if(feed.get("message").equals("") )
        {
            holder.messagetext.setVisibility(View.INVISIBLE);
            holder.message.setVisibility(View.INVISIBLE);

        }
        else{
            holder.messagetext.setVisibility(View.VISIBLE);
            holder.message.setVisibility(View.VISIBLE);
            holder.message.setText((String)feed.get("message"));

        }
        if(feed.get("story").equals("") )
        {
            holder.feedtext.setVisibility(View.INVISIBLE);
            holder.feed.setVisibility(View.INVISIBLE);

        }
        else{
            holder.feedtext.setVisibility(View.VISIBLE);
            holder.feed.setVisibility(View.VISIBLE);

            holder.feed.setText((String)feed.get("story"));

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
            button= (Button) itemView.findViewById(R.id.showmoreweek);
            imageView=(ImageView) itemView.findViewById(R.id.image1);

            feedtext= (TextView) itemView.findViewById(R.id.feedtextweek);
            messagetext= (TextView) itemView.findViewById(R.id.messagetextweek);
            feed=(TextView) itemView.findViewById(R.id.feedweek);
            message=(TextView) itemView.findViewById(R.id.messageweek);

            name =(TextView) itemView.findViewById(R.id.nameweek);
            email=(TextView) itemView.findViewById(R.id.emailweek);
            gender=(TextView) itemView.findViewById(R.id.genderweek);
         //   age_range=(TextView) itemView.findViewById(R.id.age_rangeweek);

            create_time=(TextView) itemView.findViewById(R.id.created_timeweek);
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
