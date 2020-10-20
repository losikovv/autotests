package instamart.api.objects.v2;

import instamart.api.objects.BaseObject;

public class User extends BaseObject {

    private String email;

    /**
     * No args constructor for use in serialization
     *
     */
    public User() {
    }

    /**
     *
     * @param email
     */
    public User(String email) {
        super();
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
