package instamart.api.shopper.objects;

import instamart.api.common.BaseObject;

public class Address extends BaseObject {

    private String fullName;
    private String phone;
    private String address1;
    private String comments;
    private String entrance;
    private String doorPhone;
    private String vatsPhone;
    private String floor;
    private Double lat;
    private Double lon;
    private Boolean deliveryToDoor;
    private String fullAddress;
    private String city;
    private String street;
    private String building;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public Object getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Object getEntrance() {
        return entrance;
    }

    public void setEntrance(String entrance) {
        this.entrance = entrance;
    }

    public Object getDoorPhone() {
        return doorPhone;
    }

    public void setDoorPhone(String doorPhone) {
        this.doorPhone = doorPhone;
    }

    public String getVatsPhone() {
        return vatsPhone;
    }

    public void setVatsPhone(String vatsPhone) {
        this.vatsPhone = vatsPhone;
    }

    public Object getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Boolean getDeliveryToDoor() {
        return deliveryToDoor;
    }

    public void setDeliveryToDoor(Boolean deliveryToDoor) {
        this.deliveryToDoor = deliveryToDoor;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

}
