
package posts.facebook.pranika.facebookapi.Model;

import com.squareup.moshi.Json;

public class Address {

    @Json(name = "city")
    private String city;
    @Json(name = "address_2")
    private String address2;
    @Json(name = "telephone_number")
    private String telephoneNumber;
    @Json(name = "fax_number")
    private String faxNumber;
    @Json(name = "state")
    private String state;
    @Json(name = "postal_code")
    private String postalCode;
    @Json(name = "address_1")
    private String address1;
    @Json(name = "country_code")
    private String countryCode;
    @Json(name = "country_name")
    private String countryName;
    @Json(name = "address_type")
    private String addressType;
    @Json(name = "address_purpose")
    private String addressPurpose;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getAddressPurpose() {
        return addressPurpose;
    }

    public void setAddressPurpose(String addressPurpose) {
        this.addressPurpose = addressPurpose;
    }

}
