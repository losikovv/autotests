package ru.instamart.api.endpoint;

public class ApiV3Endpoints {
    public static final String CURRENT_TIME = "v3/current_time";
    public static final String FEATURE_FLAG = "feature_flags/{featureName}";
    public static final String FEATURE_FLAGS = "feature_flags";
    public static final String MARKETING_CATEGORIES = "marketing_categories";
    public static final String MARKETING_CATEGORY = "marketing_categories/{marketingCategoryId}";
    public static final String NOTIFICATIONS = "v3/notifications";
    public static final String ORDER = "v3/orders/{UUID}";
    public static final String SETUP_INFO = "v3/setup_info";
    public static final String STORES = "v3/stores";

    public static final class AppConfigurations {
        public static final String COMPANY_DETAILS = "app_configurations/company_details";
    }

    public static final class Checkout {
        public static final String ORDER = "v3/checkout/orders/{orderNumber}";
        public static final String REPLACEMENT_POLICIES = "v3/checkout/replacement_policies";

        public static final class Orders {
            public static final String COMPLETION = "v3/checkout/orders/{orderNumber}/completion";
            public static final String INITIALIZATION = "v3/checkout/orders/{orderNumber}/initialization";
            public static final String PAYMENT_TOOLS = "v3/checkout/orders/{orderNumber}/payment_tools";
            public static final String PROMOTIONS = "v3/checkout/orders/{orderNumber}/promotions";
            public static final String VALIDATION = "v3/checkout/orders/{orderNumber}/validation";

            public static final class Promotions {
                public static final String REMOVE = "v3/checkout/orders/{orderNumber}/promotions/{promocode}/remove";
            }
        }


        public static final class Shipments {
            public static final String SHIPPING_RATES = "v3/checkout/shipments/{shipmentNumber}/shipping_rates";
        }
    }

    public static final class OrderOptions {
        public static final String DELIVERY = "v3/order_options/delivery";
        public static final String PICKUP_FROM_STORE = "v3/order_options/pickup_from_store";
    }

    public static final class Orders {
        public static final String CANCEL = "v3/orders/{UUID}/status";
        public static final String DELIVERY = "v3/orders/delivery";
        public static final String PICKUP_FROM_STORE = "v3/orders/pickup_from_store";
    }

    public static final class SetupInfo {
        public static final String STORES = "v3/setup_info/stores";
    }

    public static final class Stores {
        public static final String AGGREGATING_CATEGORIES = "stores/{sid}/aggregating_categories";
        public static final String CATEGORIES = "stores/{sid}/categories";
        public static final String CATEGORY = "stores/{sid}/categories/{categoryId}";
        public static final String DEPARTMENT = "stores/{sid}/departments/{departmentId}";
        public static final String DEPARTMENTS = "stores/{sid}/departments";
        public static final String PRODUCTS = "stores/{sid}/products";

        public static final class FavoritesList {
            public static final String ITEM = "stores/{sid}/favorites_list/items/{retailerSku}";
            public static final String ITEMS = "stores/{sid}/favorites_list/items";
        }

        public static final class MarketingCategories {
            public static final String PRODUCTS = "stores/{sid}/marketing_categories/{marketingCategoryId}/products";
        }
    }
}
