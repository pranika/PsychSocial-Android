package posts.facebook.pranika.facebookapi;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

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
 * Created by nikhiljain on 8/8/17.
 */

public class DoctorAsync extends AsyncTask<Doctor,String, Doctor> {

    String url = "http://192.168.1.10:3000/insert_doctor";

    private Context context;

    public DoctorAsync(Context context) { this.context = context; }


    @Override
    protected Doctor doInBackground(Doctor... params) {
        Doctor doc = params[0];


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

    // ************************** UI DOCTOR UPDATE***************

//
//                        MongoClientURI uri = new MongoClientURI( "mongodb://192.168.1.116:27017/facebookapi");
//                        MongoClient mongoClient = new MongoClient(uri);
//                        MongoDatabase db = mongoClient.getDatabase(uri.getDatabase());
//                        MongoCollection doctors = db.getCollection("doctors");
//                        Document documentMapDetail = new Document();
//                        documentMapDetail.put("_id",doc.getId());
//                        documentMapDetail.put("email",doc.getEmail());
//                        documentMapDetail.put("password",doc.getPassword());
//                        documentMapDetail.put("name",doc.getName());
//                        //documentMapDetail.put("age_range",agerange);
//
//                        try {
//                            doctors.insertOne(documentMapDetail);
//                            //  Log.d("document",documentMapPatient.toString());
//                        }
//
//                        catch(MongoWriteException e) {
//                        }
    //********************END ************************************

//        Log.d("doctors",params.toString());
//
//        return null;
//}

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
