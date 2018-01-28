
package posts.facebook.pranika.facebookapi.Model;

import com.squareup.moshi.Json;

public class Basic {

    @Json(name = "status")
    private String status;
    @Json(name = "credential")
    private String credential;
    @Json(name = "first_name")
    private String firstName;
    @Json(name = "last_name")
    private String lastName;
    @Json(name = "middle_name")
    private String middleName;
    @Json(name = "name")
    private String name;
    @Json(name = "gender")
    private String gender;
    @Json(name = "sole_proprietor")
    private String soleProprietor;
    @Json(name = "last_updated")
    private String lastUpdated;
    @Json(name = "enumeration_date")
    private String enumerationDate;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSoleProprietor() {
        return soleProprietor;
    }

    public void setSoleProprietor(String soleProprietor) {
        this.soleProprietor = soleProprietor;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getEnumerationDate() {
        return enumerationDate;
    }

    public void setEnumerationDate(String enumerationDate) {
        this.enumerationDate = enumerationDate;
    }

}
