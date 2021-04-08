package ru.instamart.api.responses.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.v2.UserV2;
import ru.instamart.api.responses.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class UserV2Response extends BaseResponseObject {
    private UserV2 user;
    @JsonProperty(value = "csrf_token")
    private String csrfToken;
}
