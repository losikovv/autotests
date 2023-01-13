package ru.instamart.api.response.self_fee;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.self_fee.ResultItem;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class RegistryV1Response {

    @JsonProperty("result")
    private List<ResultItem> result;

    @JsonProperty("total")
    private Integer total;
}