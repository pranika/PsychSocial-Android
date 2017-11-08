package posts.facebook.pranika.facebookapi;

/**
 * Created by nikhiljain on 9/2/17.
 */

public class UpdateValues {

    String url;



    public String getPatientid() {
        return patientid;
    }

    public void setPatientid(String patientid) {
        this.patientid = patientid;
    }

    String patientid;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCase_history() {
        return case_history;
    }

    public void setCase_history(String case_history) {
        this.case_history = case_history;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    String case_history;
    String level;

}
