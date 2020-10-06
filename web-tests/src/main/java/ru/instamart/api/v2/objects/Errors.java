package instamart.api.v2.objects;

import instamart.api.common.BaseObject;

public class Errors extends BaseObject {

    private String email;
    private String password;
    private String fullname;
    private String shipments;
    private String base;
    private String payments;

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

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getPayments() {
        return payments;
    }

    public void setPayments(String payments) {
        this.payments = payments;
    }
}
