package instamart.api.objects.v1;

import instamart.api.objects.BaseObject;
import instamart.api.objects.v2.Retailer;
import instamart.api.objects.v2.ShippingMethod;

import java.util.List;

public class Shipment extends BaseObject {

    private Integer id;
    private String number;
    private Double cost;
    private Integer total_weight;
    private Double item_total;
    private Integer item_count;
    private Object shipped_at;
    private String state;
    private String uuid;
    private String order_number;
    private Object dispatcher_comment;
    private Integer store_id;
    private String store_uuid;
    private Double adjustment_total;
    private String payment_state;
    private Boolean is_online_payment;
    private String tenant_id;
    private Object driver_phone;
    private ShippingMethod shipping_method;
    private Object delivery_window;
    private Retailer retailer;
    private Object retailer_card;
    private ShippingCategory shipping_category;
    private List<Object> assembly_notes = null;
    private List<Object> shipment_changes = null;
    private List<Object> shipping_team_members = null;

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

    public Integer getTotal_weight() {
        return total_weight;
    }

    public void setTotal_weight(Integer total_weight) {
        this.total_weight = total_weight;
    }

    public Double getItem_total() {
        return item_total;
    }

    public void setItem_total(Double item_total) {
        this.item_total = item_total;
    }

    public Integer getItem_count() {
        return item_count;
    }

    public void setItem_count(Integer item_count) {
        this.item_count = item_count;
    }

    public Object getShipped_at() {
        return shipped_at;
    }

    public void setShipped_at(Object shipped_at) {
        this.shipped_at = shipped_at;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public Object getDispatcher_comment() {
        return dispatcher_comment;
    }

    public void setDispatcher_comment(Object dispatcher_comment) {
        this.dispatcher_comment = dispatcher_comment;
    }

    public Integer getStore_id() {
        return store_id;
    }

    public void setStore_id(Integer store_id) {
        this.store_id = store_id;
    }

    public String getStore_uuid() {
        return store_uuid;
    }

    public void setStore_uuid(String store_uuid) {
        this.store_uuid = store_uuid;
    }

    public Double getAdjustment_total() {
        return adjustment_total;
    }

    public void setAdjustment_total(Double adjustment_total) {
        this.adjustment_total = adjustment_total;
    }

    public String getPayment_state() {
        return payment_state;
    }

    public void setPayment_state(String payment_state) {
        this.payment_state = payment_state;
    }

    public Boolean getIs_online_payment() {
        return is_online_payment;
    }

    public void setIs_online_payment(Boolean is_online_payment) {
        this.is_online_payment = is_online_payment;
    }

    public String getTenant_id() {
        return tenant_id;
    }

    public void setTenant_id(String tenant_id) {
        this.tenant_id = tenant_id;
    }

    public Object getDriver_phone() {
        return driver_phone;
    }

    public void setDriver_phone(Object driver_phone) {
        this.driver_phone = driver_phone;
    }

    public ShippingMethod getShipping_method() {
        return shipping_method;
    }

    public void setShipping_method(ShippingMethod shipping_method) {
        this.shipping_method = shipping_method;
    }

    public Object getDelivery_window() {
        return delivery_window;
    }

    public void setDelivery_window(Object delivery_window) {
        this.delivery_window = delivery_window;
    }

    public Retailer getRetailer() {
        return retailer;
    }

    public void setRetailer(Retailer retailer) {
        this.retailer = retailer;
    }

    public Object getRetailer_card() {
        return retailer_card;
    }

    public void setRetailer_card(Object retailer_card) {
        this.retailer_card = retailer_card;
    }

    public ShippingCategory getShipping_category() {
        return shipping_category;
    }

    public void setShipping_category(ShippingCategory shipping_category) {
        this.shipping_category = shipping_category;
    }

    public List<Object> getAssembly_notes() {
        return assembly_notes;
    }

    public void setAssembly_notes(List<Object> assembly_notes) {
        this.assembly_notes = assembly_notes;
    }

    public List<Object> getShipment_changes() {
        return shipment_changes;
    }

    public void setShipment_changes(List<Object> shipment_changes) {
        this.shipment_changes = shipment_changes;
    }

    public List<Object> getShipping_team_members() {
        return shipping_team_members;
    }

    public void setShipping_team_members(List<Object> shipping_team_members) {
        this.shipping_team_members = shipping_team_members;
    }

}
