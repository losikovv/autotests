package instamart.api.objects.v1;

import instamart.api.objects.BaseObject;
import instamart.api.objects.v2.Payment;

import java.util.List;

public class Order extends BaseObject {

    private Integer id;
    private String number;
    private Integer total;
    private Integer item_total;
    private Integer ship_total;
    private String state;
    private Integer adjustment_total;
    private Object user_id;
    private String created_at;
    private String updated_at;
    private Object completed_at;
    private Integer payment_total;
    private Object shipment_state;
    private String payment_state;
    private String email;
    private String token;
    private String currency;
    private Boolean closing_docs_required;
    private Object replacement_policy;
    private Object customer_comment;
    private Boolean is_first_order;
    private Object dispatcher_comment;
    private Object ship_address;
    private List<Payment> payments = null;
    private List<Shipment> shipments = null;
    private List<Adjustment> adjustments = null;

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

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getItem_total() {
        return item_total;
    }

    public void setItem_total(Integer item_total) {
        this.item_total = item_total;
    }

    public Integer getShip_total() {
        return ship_total;
    }

    public void setShip_total(Integer ship_total) {
        this.ship_total = ship_total;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getAdjustment_total() {
        return adjustment_total;
    }

    public void setAdjustment_total(Integer adjustment_total) {
        this.adjustment_total = adjustment_total;
    }

    public Object getUser_id() {
        return user_id;
    }

    public void setUser_id(Object user_id) {
        this.user_id = user_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public Object getCompleted_at() {
        return completed_at;
    }

    public void setCompleted_at(Object completed_at) {
        this.completed_at = completed_at;
    }

    public Integer getPayment_total() {
        return payment_total;
    }

    public void setPayment_total(Integer payment_total) {
        this.payment_total = payment_total;
    }

    public Object getShipment_state() {
        return shipment_state;
    }

    public void setShipment_state(Object shipment_state) {
        this.shipment_state = shipment_state;
    }

    public String getPayment_state() {
        return payment_state;
    }

    public void setPayment_state(String payment_state) {
        this.payment_state = payment_state;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Boolean getClosing_docs_required() {
        return closing_docs_required;
    }

    public void setClosing_docs_required(Boolean closing_docs_required) {
        this.closing_docs_required = closing_docs_required;
    }

    public Object getReplacement_policy() {
        return replacement_policy;
    }

    public void setReplacement_policy(Object replacement_policy) {
        this.replacement_policy = replacement_policy;
    }

    public Object getCustomer_comment() {
        return customer_comment;
    }

    public void setCustomer_comment(Object customer_comment) {
        this.customer_comment = customer_comment;
    }

    public Boolean getIs_first_order() {
        return is_first_order;
    }

    public void setIs_first_order(Boolean is_first_order) {
        this.is_first_order = is_first_order;
    }

    public Object getDispatcher_comment() {
        return dispatcher_comment;
    }

    public void setDispatcher_comment(Object dispatcher_comment) {
        this.dispatcher_comment = dispatcher_comment;
    }

    public Object getShip_address() {
        return ship_address;
    }

    public void setShip_address(Object ship_address) {
        this.ship_address = ship_address;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public List<Shipment> getShipments() {
        return shipments;
    }

    public void setShipments(List<Shipment> shipments) {
        this.shipments = shipments;
    }

    public List<Adjustment> getAdjustments() {
        return adjustments;
    }

    public void setAdjustments(List<Adjustment> adjustments) {
        this.adjustments = adjustments;
    }

}
