package ru.instamart.api.objects.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class MetaV2 extends BaseObject {
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
