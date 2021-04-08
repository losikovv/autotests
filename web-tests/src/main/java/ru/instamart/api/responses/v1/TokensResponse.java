package ru.instamart.api.responses.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.instamart.api.objects.v1.ShoppersBackend;
import ru.instamart.api.responses.BaseResponseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class TokensResponse extends BaseResponseObject {
    @JsonProperty(value = "shoppers_backend")
    private ShoppersBackend shoppersBackend;
}
