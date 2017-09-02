package posts.facebook.pranika.facebookapi;

/**
 * Created by nikhiljain on 8/7/17.
 */

public class FeedItem {

    String id;
    String phone;
    String name;
    String email;
    String facebooid;
    String sex;
    String usn;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFacebooid() {
        return facebooid;
    }

    public void setFacebooid(String facebooid) {
        this.facebooid = facebooid;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUsn() {
        return usn;
    }

    public void setUsn(String usn) {
        this.usn = usn;
    }

    public String getAge_range() {
        return age_range;
    }

    public void setAge_range(String age_range) {
        this.age_range = age_range;
    }

    String age_range;

}
