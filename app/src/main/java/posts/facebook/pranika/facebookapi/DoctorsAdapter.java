package posts.facebook.pranika.facebookapi;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.holder.AnimateViewHolder;

/**
 * Created by nikhiljain on 10/14/17.
 */

public class DoctorsAdapter extends RecyclerView.Adapter<DoctorsAdapter.ViewHolder> {
    Context context;
    List<Map<String, ?>> feedlist;
    DoctorsAdapter.OnItemClickListner onItemClickListner;


    public DoctorsAdapter(Context context1, List<Map<String, ?>> feedList) {
        context=context1;

        feedlist=feedList;
    }
    public interface OnItemClickListner{
        public void onEdit(View view, int position);
        public void onDelete(View view, int position);

    }
    public void setOnItemClickListner(DoctorsAdapter.OnItemClickListner onItemClickListner)
    {
        this.onItemClickListner = onItemClickListner;

    }

    @Override
    public DoctorsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v= LayoutInflater.from(parent.getContext()).inflate(R.layout.doctors,parent,false);
        DoctorsAdapter.ViewHolder viewHolder=new DoctorsAdapter.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DoctorsAdapter.ViewHolder holder, int position) {

        Log.d("bindlist",feedlist.toString());

        HashMap feed= (HashMap) feedlist.get(position);

        holder.name.setText(feed.get("name").toString());
        holder.email.setText(feed.get("email").toString());

        holder.specialization.setText(feed.get("specialization").toString());

    }

    @Override
    public int getItemCount() {
        return feedlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements AnimateViewHolder {


        @BindView(R.id.edit) Button edit;
        @BindView(R.id.delete) Button delete;
        public ImageView imageView;
        public TextView name;
        public TextView email;
        public TextView specialization;

        public Button button;


        public ViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this,itemView);
            imageView=(ImageView) itemView.findViewById(R.id.image1);
            //  button= (Button) itemView.findViewById(R.id.level);

            name =(TextView) itemView.findViewById(R.id.name);
            email=(TextView) itemView.findViewById(R.id.email);
            specialization=(TextView) itemView.findViewById(R.id. specialisation);


          edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              onItemClickListner.onEdit(v,getAdapterPosition());
            }
        });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     onItemClickListner.onDelete(v,getAdapterPosition());
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
