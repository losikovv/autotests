package ru.instamart.k8s.rails_response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.k8s.rails_response.model.Retailer;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class GetRetailerResponse extends BaseObject {

    @JsonProperty("GetRetailerResponse")
    private List<Retailer> getRetailerResponse;
}