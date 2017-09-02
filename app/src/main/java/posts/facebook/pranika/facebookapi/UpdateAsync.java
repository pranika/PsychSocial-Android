package posts.facebook.pranika.facebookapi;

import android.os.AsyncTask;

/**
 * Created by nikhiljain on 9/2/17.
 */

public class UpdateAsync extends AsyncTask<UpdateValues,String, UpdateValues> {
    @Override
    protected UpdateValues doInBackground(UpdateValues... params) {

        UpdateValues updateValues=params[0];
        String url=updateValues.getUrl();
        String case_history=updateValues.getCase_history();
        String level=updateValues.getLevel();
        System.out.println("url"+url+"casehistory"+case_history+"level"+level);



        return null;
    }
}
