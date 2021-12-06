package ru.instamart.api.rails_response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.rails_response.model.Offer;
import ru.instamart.api.response.BaseResponseObject;

@EqualsAndHashCode(callSuper = true)
@Data
public class OfferResponse extends BaseResponseObject {

    @JsonProperty("offer")
    private Offer offer;
}