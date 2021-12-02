package ru.instamart.api.rails_response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.rails_response.model.GetRetailerResponseItem;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class GetRetailerResponse extends BaseResponseObject {

    @JsonProperty("GetRetailerResponse")
    private List<GetRetailerResponseItem> getRetailerResponse;
}