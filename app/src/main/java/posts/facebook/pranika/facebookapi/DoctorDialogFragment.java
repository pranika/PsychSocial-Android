package posts.facebook.pranika.facebookapi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by nikhiljain on 11/14/17.
 */

public class DoctorDialogFragment extends DialogFragment {

    List<String> Doctorname; List<String> Doctorid;

    List<HashMap<String,?>> Doctorlist;
    private static final String TITLE_KEY = "title";
    Context mContext;
    String selectedDoctor="";
    DoctorDialogFragment.OnDoctorClickListner onDoctorClickListner;
    String Doctor=null;
    private String mTitle="Doctors";


    void setOnDoctorClickListner(DoctorDialogFragment.OnDoctorClickListner monDoctorClickListner){

        onDoctorClickListner=monDoctorClickListner;
    }
    public static DoctorDialogFragment newInstance(String title){

        DoctorDialogFragment p=new DoctorDialogFragment();
        Bundle args=new Bundle();
        args.putString(TITLE_KEY, title);
        p.setArguments(args);
        return p;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState==null) {
            //not restart
            Bundle args = getArguments();
            if (args==null) {
                throw new IllegalArgumentException("Bundle args required");
            }
            mTitle = args.getString(TITLE_KEY);
        } else {
            //restart
            mTitle = savedInstanceState.getString(TITLE_KEY);
        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TITLE_KEY, mTitle);
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Doctorname = new ArrayList<>();
        Doctorid = new ArrayList<>();


        // Where we track the selected items

       // selectedDoctors=new ArrayList<>();
        Doctorlist=new ArrayList<>();

        Bundle args = getArguments();


        Doctorlist= (List<HashMap<String, ?>>) args.getSerializable("doctorlist");
        for(HashMap map : Doctorlist){

            Doctorname.add((String) map.get("name"));
            Doctorid.add((String) map.get("doctorid"));
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Set the dialog title
        builder.setTitle(mTitle)

                .setSingleChoiceItems(Doctorname.toArray(new CharSequence[Doctorname.size()]),-1,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                selectedDoctor=Doctorid.get(which);

                            }
                        })

                .setPositiveButton("Select", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        onDoctorClickListner.sendDoctors(selectedDoctor);


                    }
                })
                .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {



                        onDoctorClickListner.backPressed();


                    }
                });

        return builder.create();
    }
    interface OnDoctorClickListner{

        public void sendDoctors(String docid);
        public void backPressed();

    }

}
