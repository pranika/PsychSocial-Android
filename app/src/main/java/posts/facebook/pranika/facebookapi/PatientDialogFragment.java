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
 * Created by nikhiljain on 11/12/17.
 */

public class PatientDialogFragment extends DialogFragment {

    List<String> patientname; List<String> patientid;

    List<HashMap<String,?>> patientlist;
    private static final String TITLE_KEY = "title";

    List<String> selectedPatients;
    OnPatientClickListner onPatientClickListner;
    private String mTitle="Patients";



    void setOnPatientClickListner(OnPatientClickListner monPatientClickListner){

        onPatientClickListner=monPatientClickListner;
    }
public static PatientDialogFragment newInstance(String title){

    PatientDialogFragment p=new PatientDialogFragment();
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

        patientname = new ArrayList<>();
        patientid = new ArrayList<>();


        // Where we track the selected items

        selectedPatients=new ArrayList<>();
        patientlist=new ArrayList<>();

        Bundle args = getArguments();


            patientlist= (List<HashMap<String, ?>>) args.getSerializable("patientlist");
            for(HashMap map : patientlist){

                patientname.add((String) map.get("name"));
                patientid.add((String) map.get("patientid"));
            }


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Set the dialog title
        builder.setTitle(mTitle)
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setMultiChoiceItems(patientname.toArray(new CharSequence[patientname.size()]), null,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which,
                                                boolean isChecked) {
                                if (isChecked) {
                                    // If the user checked the item, add it to the selected items
                                    selectedPatients.add((String) patientid.get(which));
                                } else if (selectedPatients.contains(which)) {
                                    // Else, if the item is already in the array, remove it
                                    selectedPatients.remove(Integer.valueOf(which));
                                }
                            }
                        })
                // Set the action buttons
                .setPositiveButton("Select", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        onPatientClickListner.sendPatients(selectedPatients);


                    }
                })
                .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {



                       onPatientClickListner.backPressed();


                    }
                });

        return builder.create();
    }
    interface OnPatientClickListner{

        public void sendPatients(List<String> Patients);
        public void backPressed();

    }

}
