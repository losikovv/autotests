package instamart.api.responses.v2;

import instamart.api.responses.BaseResponseObject;
import instamart.api.objects.v2.User;

public class UserResponse extends BaseResponseObject {

    private User user;
    private String csrf_token;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCsrf_token() {
        return csrf_token;
    }

    public void setCsrf_token(String csrf_token) {
        this.csrf_token = csrf_token;
    }

}
