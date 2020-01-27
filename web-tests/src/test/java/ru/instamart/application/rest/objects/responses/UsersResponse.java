package ru.instamart.application.rest.objects.responses;

import ru.instamart.application.rest.objects.User;

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
