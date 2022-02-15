package ru.instamart.api.response.v1.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.MetaV1;
import ru.instamart.api.response.BaseResponseObject;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public final class ShipmentsAdminV1Response extends BaseResponseObject {

    public List<Shipment> shipments;
    public MetaV1 meta;

    @Data
    public static class Shipment {
        public int id;
        public String uuid;
        public String number;
        public double total;
        public String state;
        public String state_name;
        @JsonProperty(value = "combined_state")
        public String combinedState;
        @JsonProperty(value = "current_stage")
        public String currentStage;
        @JsonProperty(value = "express_delivery")
        public boolean expressDelivery;
        @JsonProperty(value = "assembly_comment")
        public String assemblyComment;
        @JsonProperty(value = "vehicle_number")
        public Object vehicleNumber;
        @JsonProperty(value = "display_total_weight")
        public String displayTotalWeight;
        @JsonProperty(value = "delivery_window_date")
        public String deliveryWindowDate;
        @JsonProperty(value = "delivery_window_local_starts_at")
        public Date deliveryWindowLocalStartsAt;
        @JsonProperty(value = "delivery_window_local_ends_at")
        public Date deliveryWindowLocalEndsAt;
        @JsonProperty(value = "delivery_window_time_zone")
        public String deliveryWindowTimeZone;
        public RecentPayment recent_payment;
        public Urls urls;
        public Shopper shopper;
        public Driver driver;
        public Order order;
        public Retailer retailer;
        public Store store;
        public List<Payment> payments;
        @JsonProperty(value = "shipment_changes")
        public List<Object> shipmentChanges;
        @JsonProperty(value = "assigned_shopper")
        public Object assignedShopper;
        @JsonProperty(value = "assigned_driver")
        public Object assignedDriver;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class RecentPayment extends BaseResponseObject {
        @JsonProperty(value = "method_name")
        public String methodName;
        @JsonProperty(value = "hold_acquired")
        public boolean holdAcquired;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class Urls extends BaseResponseObject {
        @JsonProperty(value = "edit_admin_order")
        public String editAdminOrder;
        @JsonProperty(value = "admin_order_payments")
        public String adminOrderPayments;
        @JsonProperty(value = "admin_order_customer")
        public String adminOrderCustomer;
        @JsonProperty(value = "admin_order_delivery_windows")
        public String adminOrderDeliveryWindows;
        @JsonProperty(value = "xlsx_document_order")
        public String xlsxDocumentOrder;
        public String store;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class Shopper extends BaseResponseObject {
        public String login;
        public String name;
        public String phone;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class Driver extends BaseResponseObject {
        public String login;
        public String name;
        public String phone;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class Order extends BaseResponseObject {
        public int id;
        public String number;
        public String name;
        public String email;
        @JsonProperty(value = "payment_state")
        public String paymentState;
        @JsonProperty(value = "payment_state_name")
        public String paymentStateName;
        public boolean promo;
        public String platform;
        @JsonProperty(value = "company_document")
        public CompanyDocument companyDocument;
        @JsonProperty(value = "ship_address")
        public ShipAddress shipAddress;
        @JsonProperty(value = "order_promotions")
        public List<Object> orderPromotions;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class CompanyDocument extends BaseResponseObject {
        public String inn;
        public String name;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class ShipAddress extends BaseResponseObject {
        public String city;
        public String fullname;
        @JsonProperty(value = "phone_number")
        public String phoneNumber;
        public String address;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class Retailer extends BaseResponseObject {
        public int id;
        public String name;
        public String short_name;
        public String color;
        public String icon_url;
        public String logo_url;
        public String logo_background_color;
        public Appearance appearance;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class Appearance extends BaseResponseObject {
        @JsonProperty(value = "background_color")
        public String backgroundColor;
        @JsonProperty(value = "image_color")
        public String imageColor;
        @JsonProperty(value = "black_theme")
        public boolean blackTheme;
        @JsonProperty(value = "logo_image")
        public String logoImage;
        @JsonProperty(value = "side_image")
        public String sideImage;
        @JsonProperty(value = "mini_logo_image")
        public String miniLogoImage;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class Store extends BaseResponseObject {
        public String uuid;
        public Location location;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class Location extends BaseResponseObject {
        public StoreLocation store_location;
        public String city;
        public String street;
        public String building;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class StoreLocation extends BaseResponseObject {
        public String city;
        public String street;
        public String building;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class Payment extends BaseResponseObject {
        public double amount;
        @JsonProperty(value = "hold_acquired")
        public boolean holdAcquired;
        @JsonProperty(value = "payment_method")
        public PaymentMethod paymentMethod;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class PaymentMethod extends BaseResponseObject {
        public String name;
        public String description;
    }
}
