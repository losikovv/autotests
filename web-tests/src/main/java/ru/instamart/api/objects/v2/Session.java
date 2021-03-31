package instamart.api.objects.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Session extends BaseObject {
    @JsonProperty(value = "access_token")
    private String accessToken;
    @JsonProperty(value = "expires_at")
    private String expiresAt;
    @JsonProperty(value = "is_valid")
    private Boolean isValid;
}