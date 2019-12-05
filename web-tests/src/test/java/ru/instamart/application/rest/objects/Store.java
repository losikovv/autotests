package ru.instamart.application.rest.objects;

import java.util.List;

public class Store extends BaseObject {

    private Integer id;
    private String name;
    private List<List<Zone>> zones = null;
    private String uuid;
    private Retailer retailer;
    private Location location;
    private List<Service> services = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public Store() {
    }

    /**
     *
     * @param retailer
     * @param name
     * @param location
     * @param id
     * @param services
     * @param zones
     * @param uuid
     */
    public Store(Integer id, String name, List<List<Zone>> zones, String uuid, Retailer retailer, Location location, List<Service> services) {
        super();
        this.id = id;
        this.name = name;
        this.zones = zones;
        this.uuid = uuid;
        this.retailer = retailer;
        this.location = location;
        this.services = services;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<List<Zone>> getZones() {
        return zones;
    }

    public void setZones(List<List<Zone>> zones) {
        this.zones = zones;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Retailer getRetailer() {
        return retailer;
    }

    public void setRetailer(Retailer retailer) {
        this.retailer = retailer;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

}
