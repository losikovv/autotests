package instamart.api.objects;

import java.util.List;

public class Shipment extends BaseObject {

    private Integer id;
    private String number;
    private Double cost;
    private Double item_total;
    private Double total;
    private String state;
    private List<Alert> alerts = null;
    private List<Promotion> promotions = null;
    private List<NextDelivery> next_deliveries = null;
    private List<LineItem> line_items = null;
    private DeliveryWindow delivery_window;
    private Retailer retailer;
    private String driver_name;
    private String driver_phone;
    private String external_partners_service;
    private List<Object> shipping_team_members = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public Shipment() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getItem_total() {
        return item_total;
    }

    public void setItem_total(Double item_total) {
        this.item_total = item_total;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<Alert> getAlerts() {
        return alerts;
    }

    public void setAlerts(List<Alert> alerts) {
        this.alerts = alerts;
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public List<NextDelivery> getNext_deliveries() {
        return next_deliveries;
    }

    public void setNext_deliveries(List<NextDelivery> next_deliveries) {
        this.next_deliveries = next_deliveries;
    }

    public List<LineItem> getLine_items() {
        return line_items;
    }

    public void setLine_items(List<LineItem> line_items) {
        this.line_items = line_items;
    }

    public DeliveryWindow getDelivery_window() {
        return delivery_window;
    }

    public void setDelivery_window(DeliveryWindow delivery_window) {
        this.delivery_window = delivery_window;
    }

    public Retailer getRetailer() {
        return retailer;
    }

    public void setRetailer(Retailer retailer) {
        this.retailer = retailer;
    }

    public String getDriver_name() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }

    public String getDriver_phone() {
        return driver_phone;
    }

    public void setDriver_phone(String driver_phone) {
        this.driver_phone = driver_phone;
    }

    public String getExternal_partners_service() {
        return external_partners_service;
    }

    public void setExternal_partners_service(String external_partners_service) {
        this.external_partners_service = external_partners_service;
    }

    public List<Object> getShipping_team_members() {
        return shipping_team_members;
    }

    public void setShipping_team_members(List<Object> shipping_team_members) {
        this.shipping_team_members = shipping_team_members;
    }
}
