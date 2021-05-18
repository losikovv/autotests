package ru.instamart.kraken.testdata.pagesdata;

public class AddressDetailsData {

    private String kind;
    private String apartment;
    private String floor;
    private boolean elevator;
    private String entrance;
    private String domofon;
    private String comments;

    public AddressDetailsData(String kind, String apartment, String floor, boolean elevator, String entrance, String domofon, String comments) {
        this.kind = kind;
        this.apartment = apartment;
        this.floor = floor;
        this.elevator = elevator;
        this.entrance = entrance;
        this.domofon = domofon;
        this.comments = comments;
    }

    public AddressDetailsData(String comments) {
        this.comments = comments;
    }

    // Setters

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public void setEntrance(String entrance) {
        this.entrance = entrance;
    }

    public void setDomofon(String domofonCode) {
        this.domofon = domofonCode;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public void setElevator(boolean elevator) {
        this.elevator = elevator;
    }

    // Getters

    public String getCommentaries() {
        return comments;
    }

    public String getApartment() {
        return apartment;
    }

    public String getFloor() {
        return floor;
    }

    public String getEntrance() {
        return entrance;
    }

    public String getDomofon() {
        return domofon;
    }

    public String getType() {
        return kind;
    }

    public boolean isElevatorAvailable() {
        return elevator;
    }
}
