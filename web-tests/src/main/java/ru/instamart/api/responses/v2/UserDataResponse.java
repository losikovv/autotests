package instamart.api.responses.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import instamart.api.objects.v2.User;

public final class UserDataResponse {

    @JsonProperty(value = "user")
    private User user;

    public User getUser() {
        return user;
    }
}
