package instamart.api.endpoints;

public class ApiV3Endpoints {
    public static final String STORES = "v3/stores?shipping_method={shippingMethod}";

    public static class OrderOptions {
        public static final String PICKUP_FROM_STORE = "v3/order_options/pickup_from_store";
    }
}
