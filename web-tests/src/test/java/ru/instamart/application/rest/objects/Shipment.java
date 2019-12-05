package ru.instamart.application.rest.objects;

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

    /**
     * No args constructor for use in serialization
     *
     */
    public Shipment() {
    }

    /**
     *
     * @param alerts
     * @param next_deliveries
     * @param number
     * @param total
     * @param promotions
     * @param cost
     * @param retailer
     * @param item_total
     * @param delivery_window
     * @param id
     * @param state
     * @param line_items
     */
    public Shipment(Integer id, String number, Double cost, Double item_total, Double total, String state, List<Alert> alerts, List<Promotion> promotions, List<NextDelivery> next_deliveries, List<LineItem> line_items, DeliveryWindow delivery_window, Retailer retailer) {
        super();
        this.id = id;
        this.number = number;
        this.cost = cost;
        this.item_total = item_total;
        this.total = total;
        this.state = state;
        this.alerts = alerts;
        this.promotions = promotions;
        this.next_deliveries = next_deliveries;
        this.line_items = line_items;
        this.delivery_window = delivery_window;
        this.retailer = retailer;
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

    public Object getDelivery_window() {
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

}
