package posts.facebook.pranika.facebookapi;

/**
 * Created by nikhiljain on 7/20/17.
 */

public class Feed {


    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    String created_time,story,message;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;




}
