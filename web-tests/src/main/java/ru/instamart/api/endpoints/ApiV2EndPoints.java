package ru.instamart.api.endpoints;

public final class ApiV2EndPoints {
    public static final String RETAILERS = "v2/retailers";
    public static final String ORDERS = "v2/orders";
    public static final String DEPARTMENTS = "v2/departments?sid={sid}&offers_limit={numberOfProductsFromEachDepartment}";
    public static final String LINE_ITEMS = "v2/line_items";
    public static final String PAYMENT_TOOLS = "v2/payment_tools";
    public static final String PAYMENT_TOOL_TYPES = "v2/payment_tool_types";
    public static final String SHIPPING_METHODS = "v2/shipping_methods?sid={sid}";
    public static final String ONBOARDING_PAGES = "v2/onboarding_pages";
    public static final String PRODUCTS = "v2/products?sid={sid}&q={query}";
    public static final String TAXONS = "v2/taxons?sid={sid}";
    public static final String ADDRESSES = "v2/addresses";
    public static final String CATEGORIES = "v2/categories?sid={sid}";
    public static final String SESSIONS = "v2/sessions";
    public static final String PURCHASED_PRODUCTS = "v2/purchased_products?sid={sid}";

    public static final class Users {
        public static final String USERS = "v2/users";
        public static final String USERS_EMAIL = "v2/users/{email}";
    }

    public static final class Session {
        public static final String TOKEN = "v2/sessions/{token}";
        public static final String USER = "v2/sessions/{token}/user";
    }

    public static final class Reset {
        public static final String RESET = "v2/passwords/reset";
        public static final String RESET_PASSWORD = "v2/passwords";
    }

    public static final class Searches {
        public static final String SUGGESTIONS = "v2/searches/suggestions?sid={sid}&q={query}";
    }

    public static final class FavoritesList {
        public static final String ITEMS_SID = "v2/favorites_list/items?sid={sid}";
        public static final String PRODUCTS_SKU = "v2/favorites_list/products_sku";
        public static final String ITEMS = "v2/favorites_list/items";
    }

    public static final class Stores {
        public static final String COORDINATES = "v2/stores";
        public static final String SID = "v2/stores/{sid}";
        public static final String PROMOTION_CARDS = "v2/stores/{sid}/promotion_cards";
        public static final String HEALTH_CHECK = "v2/stores/{sid}/healthcheck";
    }

    public static final class Taxons {
        public static final String ID = "v2/taxons/{taxonId}?sid={sid}";
    }

    public static final class Products {
        public static final String ID = "v2/products/{productId}";
    }

    public static final class Orders {
        public static final String SHIPMENTS = "v2/orders/{orderNumber}/shipments";
        public static final String LINE_ITEMS = "v2/orders/{orderNumber}/line_items";
        public static final String SHIP_ADDRESS_CHANGE = "v2/orders/{orderNumber}/ship_address_change";
        public static final String SHIP_ADDRESS = "v2/orders/{orderNumber}/ship_address";
        public static final String CURRENT = "v2/orders/current";
        public static final String NUMBER = "v2/orders/{orderNumber}";
        public static final String COMPLETION = "v2/orders/{orderNumber}/completion";
        public static final String CANCELLATIONS = "v2/orders/{orderNumber}/cancellations?reason={reason}";
        public static final String STATUS = "v2/orders?status={status}&page={page}";
        public static final String UNRATED = "v2/orders/unrated";
    }

    public static final class Shipments {
        public static final String SHIPPING_RATES = "v2/shipments/{shipmentNumber}/shipping_rates";
    }

    public static final class LineItems {
        public static final String ID = "v2/line_items/{productId}";
    }

    public static final class Promotions {
        public static final String REFERRAL_PROGRAM = "v2/promotions/referral_program";
    }

    public static final class AuthProviders {
        public static final String SESSIONS = "v2/auth_providers/{provider}/sessions";
    }

    public static final class Departments {
        public static final String ID = "v2/departments/{id}?sid={sid}";
    }

    public static final class Retailers {
        public static final String ID = "v2/retailers/{id}";
    }

    public static final class Addresses {
        public static final String ID = "v2/addresses/{id}";
    }
}
