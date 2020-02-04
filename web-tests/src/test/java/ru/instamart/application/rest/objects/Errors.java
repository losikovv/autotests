package ru.instamart.application.rest.objects;

public class Errors extends BaseObject {

    private String email;
    private String password;
    private String fullname;
    private String shipments;

    /**
     * No args constructor for use in serialization
     *
     */
    public Errors() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getShipments() {
        return shipments;
    }

    public void setShipments(String shipments) {
        this.shipments = shipments;
    }
}
