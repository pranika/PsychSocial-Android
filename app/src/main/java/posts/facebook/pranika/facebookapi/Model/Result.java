
package posts.facebook.pranika.facebookapi.Model;

import java.util.List;
import com.squareup.moshi.Json;

public class Result {

    @Json(name = "taxonomies")
    private List<Taxonomy> taxonomies = null;
    @Json(name = "addresses")
    private List<Address> addresses = null;
    @Json(name = "created_epoch")
    private Integer createdEpoch;
    @Json(name = "identifiers")
    private List<Object> identifiers = null;
    @Json(name = "other_names")
    private List<Object> otherNames = null;
    @Json(name = "number")
    private Integer number;
    @Json(name = "last_updated_epoch")
    private Integer lastUpdatedEpoch;
    @Json(name = "basic")
    private Basic basic;
    @Json(name = "enumeration_type")
    private String enumerationType;

    public List<Taxonomy> getTaxonomies() {
        return taxonomies;
    }

    public void setTaxonomies(List<Taxonomy> taxonomies) {
        this.taxonomies = taxonomies;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public Integer getCreatedEpoch() {
        return createdEpoch;
    }

    public void setCreatedEpoch(Integer createdEpoch) {
        this.createdEpoch = createdEpoch;
    }

    public List<Object> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(List<Object> identifiers) {
        this.identifiers = identifiers;
    }

    public List<Object> getOtherNames() {
        return otherNames;
    }

    public void setOtherNames(List<Object> otherNames) {
        this.otherNames = otherNames;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getLastUpdatedEpoch() {
        return lastUpdatedEpoch;
    }

    public void setLastUpdatedEpoch(Integer lastUpdatedEpoch) {
        this.lastUpdatedEpoch = lastUpdatedEpoch;
    }

    public Basic getBasic() {
        return basic;
    }

    public void setBasic(Basic basic) {
        this.basic = basic;
    }

    public String getEnumerationType() {
        return enumerationType;
    }

    public void setEnumerationType(String enumerationType) {
        this.enumerationType = enumerationType;
    }

}
