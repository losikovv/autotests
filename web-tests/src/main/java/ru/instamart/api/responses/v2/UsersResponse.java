package instamart.api.responses.v2;

import instamart.api.responses.BaseResponseObject;
import instamart.api.objects.v2.User;

public class UsersResponse extends BaseResponseObject {

    private User user;

    /**
     * No args constructor for use in serialization
     *
     */
    public UsersResponse() {
    }

    /**
     *
     * @param user
     */
    public UsersResponse(User user) {
        super();
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
