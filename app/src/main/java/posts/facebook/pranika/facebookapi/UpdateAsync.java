package posts.facebook.pranika.facebookapi;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by nikhiljain on 9/2/17.
 */

public class UpdateAsync extends AsyncTask<UpdateValues,String, UpdateValues> {

    private Context context;

    public UpdateAsync(Context context) { this.context = context; }
    @Override
    protected UpdateValues doInBackground(UpdateValues... params) {

        UpdateValues updateValues=params[0];


        String response = "";

        try
        {
            String url=updateValues.getUrl();
            final String case_history=updateValues.getCase_history();
            final String level=updateValues.getLevel();
            final String patientid=updateValues.getPatientid();
            System.out.println("url"+url+"casehistory"+case_history+"level"+level);
            URL connecturl=new URL(url);
            HttpURLConnection conn= (HttpURLConnection) connecturl.openConnection();


            conn.setReadTimeout(1000000000);
            conn.setConnectTimeout(1500000000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            HashMap map = new HashMap<>();
            map.put("case_history",case_history);
            map.put("level", level);
            map.put("patientid", patientid);
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getQuery(map));
            writer.flush();
            writer.close();
            os.close();
            int responseCode=conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
            }
            else {
                response="";
                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();

            }


        }
        catch(Exception e)
        {

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
