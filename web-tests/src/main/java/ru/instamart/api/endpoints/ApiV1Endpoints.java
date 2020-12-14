package instamart.api.endpoints;

public final class ApiV1Endpoints {
    public static final String RETAILERS = "retailers";
    public static final String USER_SESSIONS = "user_sessions";
    public static final String TOKENS = "tokens";
    public static final String STORES = "stores";
    public static final String OPERATIONAL_ZONES = "operational_zones";
    public static final String ORDERS = "/orders?page=119647"; //119647 - last page 10.12.20
    public static final String LINE_ITEMS = "line_items?shipment_number={shipmentNumber}";


    public static final class Retailers {
        public static final String ID = "retailers/{retailerId}";
        public static final String STORES = "retailers/{retailerId}/stores";
        public static final String EANS = "retailers/{retailerId}/eans";
    }

    public static final class Stores {
        public static final String OFFERS = "stores/{storeUuid}/offers?q[name]={offerName}&q[retailer_sku]={offerRetailerSku}";
        public static final String UUID = "stores/{storeUuid}";
    }

    public static final class OperationalZones {
        public static final String ID = "operational_zones/{operationalZoneId}";
    }

    public static final class Shoppers {
        public static final String MARKETING_SAMPLE_ITEMS = "shoppers/marketing_sample_items?shipment[uuid]={shipmentUuid}";
        public static final String ORDER_AVAILABLE_PAYMENT_TOOLS = "shoppers/order_available_payment_tools?order_id={orderNumber}";
    }

    public static final class Offers {
        public static final String UUID = "offers/{offerUuid}";
    }

    public static final class Orders {
        public static final String NUMBER = "orders/{orderNumber}";
    }

    public static final class Shipments {
        public static final class Products {
            public static final String PREREPLACEMENTS = "shipments/{shipmentNumber}/products/{productSku}/prereplacements";
        }
        public static final String NUMBER = "shipments/{shipmentNumber}";
        public static final String OFFERS = "shipments/{shipmentNumber}/offers";
    }
}
