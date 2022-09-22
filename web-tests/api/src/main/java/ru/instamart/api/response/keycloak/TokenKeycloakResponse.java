package ru.instamart.api.response.keycloak;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class TokenKeycloakResponse extends BaseResponseObject {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("expires_in")
    private String expiresIn;
    @JsonProperty("refresh_expires_in")
    private String refreshExpiresIn;
    @JsonProperty("token_type")
    private String token_type;
    @JsonProperty("not-before-policy")
    private String notBeforePolicy;
    private String scope;
}
