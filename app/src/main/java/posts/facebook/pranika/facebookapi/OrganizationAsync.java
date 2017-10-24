package posts.facebook.pranika.facebookapi;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

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
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by nikhiljain on 10/14/17.
 */

public class OrganizationAsync extends AsyncTask<Organization,String, Organization> {

        String url = "http://10.1.232.254:3000/insert_organization";

private Context context;

public OrganizationAsync(Context context) { this.context = context; }


@Override
protected Organization doInBackground(Organization... params) {
        Organization doc = params[0];


        try {
        String response = "";

final String _id = doc.getId();
final String email = doc.getEmail();
final String password = doc.getPassword();
final String name = doc.getName();
final String fcm_token=doc.getFcm_token();
        // System.out.println("url"+url+"casehistory"+case_history+"level"+level);
        URL connecturl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) connecturl.openConnection();


        conn.setReadTimeout(1000000000);
        conn.setConnectTimeout(1500000000);
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);

        HashMap map = new HashMap<>();
        map.put("_id", _id);
        map.put("email", email);
        map.put("password", password);
        map.put("name", name);
        map.put("fcm_token", fcm_token);
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

        // conn.connect();
        // return response;


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
