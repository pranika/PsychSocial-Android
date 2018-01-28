
package posts.facebook.pranika.facebookapi.Model;

import com.squareup.moshi.Json;

public class Taxonomy {

    @Json(name = "state")
    private String state;
    @Json(name = "code")
    private String code;
    @Json(name = "primary")
    private Boolean primary;
    @Json(name = "license")
    private String license;
    @Json(name = "desc")
    private String desc;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getPrimary() {
        return primary;
    }

    public void setPrimary(Boolean primary) {
        this.primary = primary;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
