package ru.instamart.api.responses.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.instamart.api.responses.BaseResponseObject;
import ru.instamart.api.objects.v2.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UserResponse extends BaseResponseObject {
    private User user;
    @JsonProperty(value = "csrf_token")
    private String csrfToken;
}
