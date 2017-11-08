package posts.facebook.pranika.facebookapi;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.net.ssl.HttpsURLConnection;

/**
 * Created by nikhiljain on 7/20/17.
 */

public class MyAsync extends AsyncTask<List<Map<String,?>>,String, List<Map<String,?>>> {
    FirebaseAuth mauth;

    String url = "http://10.1.232.254:1337/Patients";
    private Context context;

    public MyAsync(Context context) { this.context = context; }


    @Override
    protected List<Map<String, ?>> doInBackground(List<Map<String, ?>>... feeds) {

        mauth=FirebaseAuth.getInstance();
        String docid=mauth.getCurrentUser().getUid().toString();
        Log.d("doctor",docid.toString());

        List<Map<String, ?>> feed_objs = feeds[0];
        try{
            String response = "";
            for (Map<String, ?> feeditem : feed_objs) {


                String email = feeditem.get("email").toString();
                String name = feeditem.get("name").toString();
                String gender = feeditem.get("gender").toString();
                String agerange = feeditem.get("birthday").toString();
                String accesstoken = feeditem.get("accesstoken").toString();
                String userid = feeditem.get("userid").toString();

                String casehistory = (String) feeditem.get("case_history");
                String status = (String) feeditem.get("status");



                URL connecturl = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) connecturl.openConnection();


                conn.setReadTimeout(1000000000);
                conn.setConnectTimeout(1500000000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                HashMap map = new HashMap<>();
                map.put("userid", userid);
                map.put("", userid);
                map.put("email", email);
                map.put("name", name);
                map.put("gender", gender);
                map.put("agerange", agerange);
                map.put("accesstoken", accesstoken);
                map.put("case", casehistory);
                map.put("status", status);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getQuery(map));
                writer.flush();
                writer.close();
                os.close();
                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    while ((line = br.readLine()) != null) {
                        response += line;
                    }
                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                } else {
                    response = "";
                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();

                }
            }

            } catch (Exception e) {

            e.printStackTrace();
        }
        return null;

    }


    private String getQuery(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (Map.Entry<String, String> entry : params.entrySet())
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }
}
