package ru.instamart.api.objects.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShoppersBackend extends BaseObject {
    @JsonProperty(value = "base_url")
    private String baseUrl;
    @JsonProperty(value = "client_jwt")
    private String clientJwt;
    @JsonProperty(value = "client_id")
    private String clientId;
}
