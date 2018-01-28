package posts.facebook.pranika.facebookapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.design.widget.Snackbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class PhoneVerification extends BaseFragment {

    @BindView(R.id.one)
    TextView one;
    @BindView(R.id.two)
    TextView two;
    @BindView(R.id.three)
    TextView three;
    @BindView(R.id.four)
    TextView four;

    @BindView(R.id.verify)
    Button button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v=inflater.inflate(R.layout.activity_phone_verification2,container,false);
        ButterKnife.bind(this.getActivity());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   String phoneno=
                if(one.getText()!=null && two.getText()!=null && three.getText()!=null && four.getText()!=null){

                    String verify_url="https://api.authy.com/protected/json/phones" +
                            "/verification/check?phone_number=111-111-111&country_code=1&verification_code=1234";
                    JSONObject json = new JSONObject();
                    try {
                        json.put("via","call");
                       // json.put("phone_number",phoneno);
                        json.put("country_code",1);
                        json.put("locale","es");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

                    RequestBody body = RequestBody.create(JSON, json.toString());

                    okhttp3.Request request = new okhttp3.Request.Builder()
                            .url(verify_url)
                            .post(body)
                            .header("X-Authy-API-Key","BIfUI7CSRLDXDY3js4cREGIH425L4CFG")
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.d("response", e.toString());
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, final okhttp3.Response response) throws IOException {

                            Log.d("phone verify",response.body().string());

                            PhoneVerification.this.getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

//
                                }
                            });




                        }
                    });



                }
                else
                    Toast.makeText(getActivity(),"Enter the verification code first",Toast.LENGTH_LONG).show();
            }
        });




        return v;
    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_phone_verification2);
//
//        ButterKnife.bind(this);
//

//    }

}
