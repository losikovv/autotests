package instamart.api.objects.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Meta extends BaseObject {
    @JsonProperty(value = "current_page")
    private Integer currentPage;
    @JsonProperty(value = "next_page")
    private Integer nextPage;
    @JsonProperty(value = "previous_page")
    private Integer previousPage;
    @JsonProperty(value = "total_pages")
    private Integer totalPages;
    @JsonProperty(value = "per_page")
    private Integer perPage;
    @JsonProperty(value = "total_count")
    private Integer totalCount;
}
