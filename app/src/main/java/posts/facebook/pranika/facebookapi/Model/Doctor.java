
package posts.facebook.pranika.facebookapi.Model;

import com.squareup.moshi.Json;

public class Doctor {

    @Json(name = "name")
    private String name;
    @Json(name = "email")
    private String email;
    @Json(name = "address")
    private String address;
    @Json(name = "phone")
    private String phone;
    @Json(name = "specialization")
    private String specialization;
    @Json(name = "createdAt")
    private String createdAt;
    @Json(name = "updatedAt")
    private String updatedAt;
    @Json(name = "id")
    private String id;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
