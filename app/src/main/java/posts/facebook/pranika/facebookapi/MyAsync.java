package posts.facebook.pranika.facebookapi;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;
import com.mongodb.MongoWriteException;

import static com.mongodb.client.model.Filters.eq;


/**
 * Created by nikhiljain on 7/20/17.
 */

public class MyAsync extends AsyncTask<List<Map<String,?>>,String, List<Map<String,?>>> {
    FirebaseAuth mauth;




    @Override
    protected List<Map<String, ?>> doInBackground(List<Map<String, ?>>... feeds) {

        mauth=FirebaseAuth.getInstance();
        String docid=mauth.getCurrentUser().getUid().toString();
        Log.d("doctor",docid.toString());

        List<Map<String, ?>> feed_objs = feeds[0];
        MongoClientURI uri = new MongoClientURI( "mongodb://service.arunlogistics.com:27017/facebookapi");
        MongoClient mongoClient = new MongoClient(uri);
        MongoDatabase db = mongoClient.getDatabase(uri.getDatabase());
        MongoCollection collection = db.getCollection("feed");
        MongoCollection patients = db.getCollection("patients");


            for (Map<String, ?> feeditem : feed_objs) {

                String story= feeditem.get("story").toString();
                String message= feeditem.get("message").toString();
                String email=feeditem.get("email").toString();
                String name=feeditem.get("name").toString();
                String gender=feeditem.get("gender").toString();
                String agerange=feeditem.get("birthday").toString();
                String accesstoken=feeditem.get("accesstoken").toString();
                String userid= feeditem.get("userid").toString();
                String id= (String) feeditem.get("id");
                String createdtime= (String) feeditem.get("create_time");
                String casehistory= (String) feeditem.get("case_history");
                String status= (String) feeditem.get("status");

                Document documentMapDetail = new Document();
                documentMapDetail.put("_id", id);
                documentMapDetail.put("email",email);
                documentMapDetail.put("name",name);
                documentMapDetail.put("gender",gender);
                documentMapDetail.put("age_range",agerange);

                documentMapDetail.put("userid", userid);
                documentMapDetail.put("createdtime",createdtime);
                documentMapDetail.put("story",story);
                documentMapDetail.put("message",message);
                documentMapDetail.put("email_flag",0);
                Document documentMapPatient = new Document();

                documentMapPatient.put("_id", userid );
                documentMapPatient.put("doctorid",docid);
                documentMapPatient.put("case_history",casehistory);
                documentMapPatient.put("level",status);

                documentMapPatient.put("email",email);
                documentMapPatient.put("name",name);
                documentMapPatient.put("gender",gender);
                documentMapPatient.put("age_range",agerange);
                documentMapPatient.put("accesstoken",accesstoken);


//             try {
//
//                    collection.insertOne(documentMapDetail);
//
//
//                  //  Log.d("document",documentMapPatient.toString());
//                }
//
//             catch(MongoWriteException e) {
//             }
                try {
                    patients.insertOne(documentMapPatient);
                  //  Log.d("document",documentMapPatient.toString());
                }

                catch(MongoWriteException e) {
                }
            }
        return null;

    }

    @Override
    protected void onPostExecute(List<Map<String, ?>> maps) {
        super.onPostExecute(maps);


    }
}
