package ru.instamart.application.rest.objects;

public class Address extends BaseObject {

    private Integer id;
    private String first_name;
    private String last_name;
    private String full_address;
    private String city;
    private String street;
    private String building;
    private String block;
    private String entrance;
    private String floor;
    private String apartment;
    private String comments;
    private Double lat;
    private Double lon;
    private String kind;
    private String door_phone;

    /**
     * No args constructor for use in serialization
     *
     */
    public Address() {
    }

    public Address(String city, String street, String building, Double lat, Double lon) {
        super();
        this.city = city;
        this.street = street;
        this.building = building;
        this.lat = lat;
        this.lon = lon;
    }

    public Address(Location location) {
        super();
        this.city = location.getCity();
        this.street = location.getStreet();
        this.building = location.getBuilding();
        this.lat = location.getLat();
        this.lon = location.getLon();
    }

    public void setCoordinates(Zone zone) {
        this.lat = zone.getLat();
        this.lon = zone.getLon();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public Object getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFull_address() {
        return full_address;
    }

    public void setFull_address(String full_address) {
        this.full_address = full_address;
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

    public Object getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public Object getEntrance() {
        return entrance;
    }

    public void setEntrance(String entrance) {
        this.entrance = entrance;
    }

    public Object getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public Object getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public Object getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Object getDoor_phone() {
        return door_phone;
    }

    public void setDoor_phone(String door_phone) {
        this.door_phone = door_phone;
    }

}
