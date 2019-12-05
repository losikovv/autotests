package ru.instamart.application.rest.objects;

public class Location extends BaseObject {

    private Integer id;
    private String full_address;
    private String city;
    private String street;
    private String building;
    private Double lat;
    private Double lon;

    /**
     * No args constructor for use in serialization
     *
     */
    public Location() {
    }

    /**
     *
     * @param city
     * @param street
     * @param lon
     * @param id
     * @param full_address
     * @param building
     * @param lat
     */
    public Location(Integer id, String full_address, String city, String street, String building, Double lat, Double lon) {
        super();
        this.id = id;
        this.full_address = full_address;
        this.city = city;
        this.street = street;
        this.building = building;
        this.lat = lat;
        this.lon = lon;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

}
