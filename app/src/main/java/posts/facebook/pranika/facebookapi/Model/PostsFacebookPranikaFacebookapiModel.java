
package posts.facebook.pranika.facebookapi.Model;

import java.util.List;
import com.squareup.moshi.Json;

public class PostsFacebookPranikaFacebookapiModel {

    @Json(name = "result_count")
    private Integer resultCount;
    @Json(name = "results")
    private List<Result> results = null;

    public Integer getResultCount() {
        return resultCount;
    }

    public void setResultCount(Integer resultCount) {
        this.resultCount = resultCount;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

}
