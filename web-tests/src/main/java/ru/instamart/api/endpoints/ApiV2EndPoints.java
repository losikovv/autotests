package instamart.api.endpoints;

public final class ApiV2EndPoints {
    public static final String retailers = "v2/retailers";
    public static final String retailers_v1 = "retailers";
    static final String stores = "v2/stores";
    static final String products = "v2/products";
    public static final String users = "v2/users";
    public static final String sessions = "v2/sessions";
    public static final String orders = "v2/orders";
    public static final String departments = "v2/departments?sid={sid}&offers_limit={limit}";
    public static final String line_items = "v2/line_items";
    public static final String payment_tools = "v2/payment_tools";
    static final String payment_tool_types = "v2/payment_tool_types";
    public static final String shipping_methods = "v2/shipping_methods?sid={sid}";
    public static final String search_suggestions = "v2/searches/suggestions?sid={sid}&q={q}";
    public static final String favorites_list = "v2/favorites_list/items?sid={sid}";
    public static final String onboarding_pages = "v2/onboarding_pages";

    public static final class Retailers {
        public static final String stores = "retailers/{id}/stores";
    }

    public static final class Stores {
        public static final String coordinates = "v2/stores?lat={lat}&lon={lon}";
        public static final String sid = "v2/stores/{sid}";
        public static final String promotion_cards = "v2/stores/{sid}/promotion_cards";
    }

    public static final class Taxons {
        public static final String id = "v2/taxons/{id}?sid={sid}";
        public static final String sid = "v2/taxons/?sid={sid}";
    }

    public static final class Products {
        public static final String id = "v2/products/{id}";
        public static final String sid = "v2/products?sid={sid}&q={q}";
    }

    static final class Users {
        static final String email = "v2/users/{email}";
    }

    public static final class Orders {
        public static final String shipments = "v2/orders/{number}/shipments";
        public static final String line_items = "v2/orders/{number}/line_items";
        public static final String ship_address_change = "v2/orders/{number}/ship_address_change";
        public static final String current = "v2/orders/current";
        public static final String number = "v2/orders/{number}";
        public static final String completion = "v2/orders/{number}/completion";
        public static final String cancellations = "v2/orders/{number}/cancellations?reason=test";
        public static final String statusActive = "v2/orders?status=active&page={p}";
        static final String statusComplete = "v2/orders?status=complete&page={p}";
        static final String statusCanceled = "v2/orders?status=canceled&page={p}";
        public static final String unrated = "v2/orders/unrated";
    }

    public static final class Shipments {
        public static final String shipping_rates = "v2/shipments/{number}/shipping_rates";
    }

    public static final class LineItems {
        public static final String id = "v2/line_items/{id}";
    }

    public static final class Promotions {
        public static final String referral_program = "v2/promotions/referral_program";
    }

    public static final class AuthProviders {
        public static final String sessions = "v2/auth_providers/{provider}/sessions";
    }
}
