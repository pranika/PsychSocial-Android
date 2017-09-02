package posts.facebook.pranika.facebookapi;

/**
 * Created by nikhiljain on 7/21/17.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeedData {

    List<Map<String,?>> feedList;

    public List<Map<String, ?>> getFeedList() {
        return feedList;
    }

    public int getSize(){
        return feedList.size();
    }

    public HashMap getItem(int i){
        if (i >=0 && i < feedList.size()){
            return (HashMap) feedList.get(i);
        } else return null;
    }

    public FeedData(){
        feedList = new ArrayList<Map<String,?>>();
    }


    public HashMap createFeed(String id, String story, String create_time, String message) {
        HashMap feed = new HashMap();
        feed.put("story",story);
        feed.put("create_time", create_time);
        feed.put("message", message);
        return feed;
    }
}