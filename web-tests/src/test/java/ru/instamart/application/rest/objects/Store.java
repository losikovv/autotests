package ru.instamart.application.rest.objects;

import java.util.List;
import java.util.StringJoiner;

public class Store extends BaseObject {

    private Integer id;
    private String name;
    private NextDelivery next_delivery;
    private List<List<Zone>> zones = null;
    private String uuid;
    private Retailer retailer;
    private Address location;
    private List<Service> services = null;
    private List<OperationalTime> operational_times = null;

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

    public NextDelivery getNext_delivery() {
        return next_delivery;
    }

    public void setNext_delivery(NextDelivery next_delivery) {
        this.next_delivery = next_delivery;
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

    public Address getLocation() {
        return location;
    }

    public void setLocation(Address location) {
        this.location = location;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public List<OperationalTime> getOperational_times() {
        return operational_times;
    }

    public void setOperational_times(List<OperationalTime> operational_times) {
        this.operational_times = operational_times;
    }

    @Override
    public String toString() {
        return new StringJoiner(
                ", ",
                "",
                "")
                .add(getRetailer().getName())
                .add(getLocation().getCity())
                .add(getLocation().getStreet())
                .add("sid: " + id)
                .toString();
    }

}
