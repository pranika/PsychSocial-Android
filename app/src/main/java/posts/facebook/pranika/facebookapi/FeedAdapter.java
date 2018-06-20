package posts.facebook.pranika.facebookapi;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
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
import java.util.StringTokenizer;

import jp.wasabeef.recyclerview.animators.holder.AnimateViewHolder;

/**
 * Created by nikhiljain on 8/4/17.
 */

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {
    OnItemClickListner onItemClickListner;

    Context context;
    List<Map<String, ?>> feedlist;

    public FeedAdapter(Context context1, List<Map<String, ?>> feedList) {
        context=context1;
        feedlist=feedList;
    }
    public interface OnItemClickListner{
        public void onClick(View view,int position);

    }
    public void setOnItemClickListner(OnItemClickListner onItemClickListner)
    {
        this.onItemClickListner = onItemClickListner;

    }

    @Override
    public FeedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v= LayoutInflater.from(parent.getContext()).inflate(R.layout.feedlist,parent,false);
        ViewHolder viewHolder=new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HashMap feed=(HashMap)feedlist.get(position);
        String feed1="";
        holder.name.setText((String)feed.get("name"));
        holder.email.setText((String)feed.get("email"));
        holder.gender.setText((String)feed.get("gender"));
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
//        try{
//            holder.feed.setText((String)feed.get("story"));
//          //  holder.messagetext.setVisibility(View.INVISIBLE);
//
//
//        }catch (Exception e)
//        {
//
//           // holder.feedtext.setVisibility(View.INVISIBLE);
//            //holder.feed.setVisibility(View.INVISIBLE);
//        }
//       // holder.age_range.setText((String)feed.get("age_range"));
//        try{
//
//
//            holder.message.setText((String)feed.get("message"));
//           // holder.feedtext.setVisibility(View.INVISIBLE);
//
//        }catch (Exception e)
//        {
//
//           // holder.messagetext.setVisibility(View.INVISIBLE);
//            //holder.message.setVisibility(View.INVISIBLE);
//        }


        holder.create_time.setText((String)feed.get("createdtime"));
        if(!feed.get("post_image").equals(""))
            Picasso.with(context).load(feed.get("post_image").toString()).into(holder.imageView);
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
            button= (Button) itemView.findViewById(R.id.showmore);
            imageView=(ImageView) itemView.findViewById(R.id.image1);
            feedtext= (TextView) itemView.findViewById(R.id.feedtext);
            messagetext= (TextView) itemView.findViewById(R.id.messagetext);

            name =(TextView) itemView.findViewById(R.id.name);
            email=(TextView) itemView.findViewById(R.id.email);
            gender=(TextView) itemView.findViewById(R.id.gender);
            age_range=(TextView) itemView.findViewById(R.id.age_range);
            feed=(TextView) itemView.findViewById(R.id.feed);
            message=(TextView) itemView.findViewById(R.id.message);
            create_time=(TextView) itemView.findViewById(R.id.created_time);
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
