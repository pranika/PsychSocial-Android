package posts.facebook.pranika.facebookapi;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;


public class DetailFragment extends Fragment {


    public TextView nameview;
    public TextView emailview;
    public TextView genderview;
    public TextView storyview;
    public TextView messageview;
    public TextView age_rangeview;
    public TextView create_timeview;


    static HashMap feed=new HashMap();


    public DetailFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static DetailFragment newInstance(HashMap oldfeed) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        feed=oldfeed;
        args.putSerializable("oldinstance",oldfeed);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(getArguments()!=null)
        {
            feed =(HashMap)getArguments().getSerializable("oldinstance");
            //  position = getArguments().getInt("textposition");
        }

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_detail, container, false);
        setHasOptionsMenu(true);
        String name = (String) feed.get("name");
        String email = (String) feed.get("email");
        String gender = (String) feed.get("gender");
        String age_range = (String) feed.get("age_range");
        String created_time = (String) feed.get("createdtime");



        nameview= (TextView) view.findViewById(R.id.name);
        emailview= (TextView) view.findViewById(R.id.email);
        storyview= (TextView) view.findViewById(R.id.feed);
        genderview= (TextView) view.findViewById(R.id.sex);
        create_timeview= (TextView) view.findViewById(R.id.createdtime);
        age_rangeview = (TextView) view.findViewById(R.id.age_range);
        messageview = (TextView) view.findViewById(R.id.message);
                try {

                    String story = (String) feed.get("story");
            if (story == "") {

                storyview.setText("No Feed");
            } else {
                storyview.setText(story);
            }
        }catch(Exception e)

        {
            storyview.setText("No Feed");
        }
        try{

            String message = (String) feed.get("message");
            if(message=="")
            {

                messageview.setText("No Message");
            }
            else
                messageview.setText(message);
        }
      catch(Exception e)
     {

         messageview.setText("No Message");
     }




        nameview.setText(name);
        emailview.setText(email);
        genderview.setText(gender);

        age_rangeview.setText(age_range);
//
        create_timeview.setText(created_time);



        return view;
        


    }






}
