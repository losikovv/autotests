package ru.instamart.api.model.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProductsFilterParams {
    private Integer sid;
    @JsonProperty("q")
    private String query;
    private Object tid;
    private Integer page;
    @JsonProperty("per_page")
    private Integer perPage;
    private String sort;
    @JsonProperty("filter[discounted][]")
    private Integer discountedFilter;
    @JsonProperty("filter[brand][]")
    private Integer brandFilter;
    @JsonProperty("filter[country][]")
    private Integer countryFilter;
}
