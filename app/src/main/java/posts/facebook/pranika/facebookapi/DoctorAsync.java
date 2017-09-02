package posts.facebook.pranika.facebookapi;

import android.os.AsyncTask;
import android.util.Log;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.List;
import java.util.Map;

/**
 * Created by nikhiljain on 8/8/17.
 */

public class DoctorAsync extends AsyncTask<Doctor,String, Doctor> {



    @Override
    protected Doctor doInBackground(Doctor... params) {
        Doctor doc=params[0];

                        MongoClientURI uri = new MongoClientURI( "mongodb://192.168.1.116:27017/facebookapi");
                        MongoClient mongoClient = new MongoClient(uri);
                        MongoDatabase db = mongoClient.getDatabase(uri.getDatabase());
                        MongoCollection doctors = db.getCollection("doctors");
                        Document documentMapDetail = new Document();
                        documentMapDetail.put("_id",doc.getId());
                        documentMapDetail.put("email",doc.getEmail());
                        documentMapDetail.put("password",doc.getPassword());
                        documentMapDetail.put("name",doc.getName());
                        //documentMapDetail.put("age_range",agerange);

                        try {
                            doctors.insertOne(documentMapDetail);
                            //  Log.d("document",documentMapPatient.toString());
                        }

                        catch(MongoWriteException e) {
                        }

        Log.d("doctors",params.toString());

        return null;
    }
}
