package instamart.api.v2.objects;

import instamart.api.common.BaseObject;

import java.util.List;

public class Order extends BaseObject {

    private String number;
    private Double total;
    private Integer item_count;
    private Double item_total;
    private Double ship_total;
    private Double adjustment_total;
    private Double promo_total;
    private Object shipment_state;
    private Object payment_state;
    private String special_instructions;
    private String created_at;
    private String updated_at;
    private Object completed_at;
    private Address address;
    private Payment payment;
    private ReplacementPolicy replacement_policy;
    private String email;
    private String uuid;
    private List<Shipment> shipments = null;
    private List<Object> promotion_codes = null;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getItem_count() {
        return item_count;
    }

    public void setItem_count(Integer item_count) {
        this.item_count = item_count;
    }

    public Double getItem_total() {
        return item_total;
    }

    public void setItem_total(Double item_total) {
        this.item_total = item_total;
    }

    public Double getShip_total() {
        return ship_total;
    }

    public void setShip_total(Double ship_total) {
        this.ship_total = ship_total;
    }

    public Double getAdjustment_total() {
        return adjustment_total;
    }

    public void setAdjustment_total(Double adjustment_total) {
        this.adjustment_total = adjustment_total;
    }

    public Double getPromo_total() {
        return promo_total;
    }

    public void setPromo_total(Double promo_total) {
        this.promo_total = promo_total;
    }

    public Object getShipment_state() {
        return shipment_state;
    }

    public void setShipment_state(Object shipment_state) {
        this.shipment_state = shipment_state;
    }

    public Object getPayment_state() {
        return payment_state;
    }

    public void setPayment_state(Object payment_state) {
        this.payment_state = payment_state;
    }

    public String getSpecial_instructions() {
        return special_instructions;
    }

    public void setSpecial_instructions(String special_instructions) {
        this.special_instructions = special_instructions;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public ReplacementPolicy getReplacement_policy() {
        return replacement_policy;
    }

    public void setReplacement_policy(ReplacementPolicy replacement_policy) {
        this.replacement_policy = replacement_policy;
    }

    public List<Shipment> getShipments() {
        return shipments;
    }

    public void setShipments(List<Shipment> shipments) {
        this.shipments = shipments;
    }

    public List<Object> getPromotion_codes() {
        return promotion_codes;
    }

    public void setPromotion_codes(List<Object> promotion_codes) {
        this.promotion_codes = promotion_codes;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
