package posts.facebook.pranika.facebookapi;

/**
 * Created by nikhiljain on 10/14/17.
 */

public class Organization {
    public String getFcm_token() {
    return fcm_token;
}

        public void setFcm_token(String fcm_token) {
            this.fcm_token = fcm_token;
        }

        String fcm_token;
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        String id,name,email,password;
}
