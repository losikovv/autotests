package ru.instamart.api.endpoint;

public class ApiV3Endpoints {

    public static final String STORES = "v3/stores";
    public static final String SETUP_INFO = "v3/setup_info";

    public static final class Stores {
        public static final String DELIVERY = "v3/stores?shipping_method=delivery";
        public static final String PICKUP_FROM_STORE = "v3/stores?shipping_method=pickup_from_store";
        public static final String CLOSEST_SHIPPING_OPTIONS = "v3/stores?include=closest_shipping_options";
        public static final String RETAILER_ID = "v3/stores?retailer_id=metro";
    }

    public static final class SetupInfo {
        public static final String STORES = "v3/setup_info/stores";

    }

    public static final class OrderOptions {
        public static final String DELIVERY = "v3/order_options/delivery";
        public static final String PICKUP_FROM_STORE = "v3/order_options/pickup_from_store";
    }

    public static final class Orders {
        public static final String DELIVERY = "v3/orders/delivery";
        public static final String PICKUP_FROM_STORE = "v3/orders/pickup_from_store";
        public static final String ORDER_BY_UUID = "v3/orders/{UUID}?include=contact,shipping,shipping_crew,packages,payments";
        public  static final String CANCEL ="v3/orders/{UUID}/status";
    }
}
