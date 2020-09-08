package ru.instamart.application.rest.objects;

import java.util.List;
import java.util.StringJoiner;

public class Store extends BaseObject {

    private Integer id;
    private String name;
    private NextDelivery next_delivery;
    private List<List<Zone>> zones = null;
    private String uuid;
    private Boolean express_delivery;
    private Double min_order_amount;
    private Double min_first_order_amount;
    private Double min_order_amount_pickup;
    private Double min_first_order_amount_pickup;
    private Boolean available_for_pickup;
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

    public Boolean getExpress_delivery() {
        return express_delivery;
    }

    public void setExpress_delivery(Boolean express_delivery) {
        this.express_delivery = express_delivery;
    }

    public Double getMin_order_amount() {
        return min_order_amount;
    }

    public void setMin_order_amount(Double min_order_amount) {
        this.min_order_amount = min_order_amount;
    }

    public Double getMin_first_order_amount() {
        return min_first_order_amount;
    }

    public void setMin_first_order_amount(Double min_first_order_amount) {
        this.min_first_order_amount = min_first_order_amount;
    }

    public Double getMin_order_amount_pickup() {
        return min_order_amount_pickup;
    }

    public void setMin_order_amount_pickup(Double min_order_amount_pickup) {
        this.min_order_amount_pickup = min_order_amount_pickup;
    }

    public Double getMin_first_order_amount_pickup() {
        return min_first_order_amount_pickup;
    }

    public void setMin_first_order_amount_pickup(Double min_first_order_amount_pickup) {
        this.min_first_order_amount_pickup = min_first_order_amount_pickup;
    }

    public Boolean getAvailable_for_pickup() {
        return available_for_pickup;
    }

    public void setAvailable_for_pickup(Boolean available_for_pickup) {
        this.available_for_pickup = available_for_pickup;
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
