package posts.facebook.pranika.facebookapi;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;



public class UpdatePatientStatusFragment extends BaseFragment {

    String type = "";
    RadioButton level;
    Button button;


    int selectedid=0;
    private RadioGroup group;
    Button update;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String app_url = "http://192.168.1.7:3000/update_status";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public UpdatePatientStatusFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdatePatientStatusFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdatePatientStatusFragment newInstance(String param1, String param2) {
        UpdatePatientStatusFragment fragment = new UpdatePatientStatusFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater,container,savedInstanceState);
        View view= inflater.inflate(R.layout.fragment_update_patient_status, container, false);

        group= (RadioGroup) view.findViewById(R.id.radio);

        button= (Button) view.findViewById(R.id.update);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String patient_id = pref.getString("patient_id","");

                StringRequest stringRequest = new StringRequest(Request.Method.POST, app_url,
                        new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })

                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();

                        selectedid = group.getCheckedRadioButtonId();
                        level = (RadioButton) getView().findViewById(selectedid);
                        type = level.getText().toString().trim();

                        params.put("patient_id", patient_id);
                        params.put("status", type);

                        return params;
                    }
                };
                MySingleton.getmInstance(getActivity()).addToRequestQue(stringRequest);
                Intent intent=new Intent(getActivity(),BottomNavigation.class);
                startActivity(intent);
            }
        });

        return view;
    }









    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

}
