package ru.instamart.api.response.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.ShoppersBackendV1;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class TokensV1Response extends BaseResponseObject {
    @JsonProperty(value = "shoppers_backend")
    private ShoppersBackendV1 shoppersBackend;
}
