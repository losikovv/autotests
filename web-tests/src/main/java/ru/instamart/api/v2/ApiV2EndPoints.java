package instamart.api.v2;

final class ApiV2EndPoints {
    static final String retailers = "v2/retailers";
    static final String retailers_v1 = "retailers";
    static final String stores = "v2/stores";
    static final String products = "v2/products";
    static final String users = "v2/users";
    static final String sessions = "v2/sessions";
    static final String orders = "v2/orders";
    static final String departments = "v2/departments?sid={sid}&offers_limit={limit}";
    static final String line_items = "v2/line_items";
    static final String payment_tools = "v2/payment_tools";
    static final String payment_tool_types = "v2/payment_tool_types";
    static final String shipping_methods = "v2/shipping_methods?sid={sid}";
    static final String search_suggestions = "v2/searches/suggestions?sid={sid}&q={q}";
    static final String favorites_list = "v2/favorites_list/items?sid={sid}";
    static final String onboarding_pages = "v2/onboarding_pages";

    static final class Retailers {
        static final String stores = "retailers/{id}/stores";
    }

    static final class Stores {
        static final String coordinates = "v2/stores?lat={lat}&lon={lon}";
        static final String sid = "v2/stores/{sid}";
        static final String promotion_cards = "v2/stores/{sid}/promotion_cards";
    }

    static final class Taxons {
        static final String id = "v2/taxons/{id}?sid={sid}";
        static final String sid = "v2/taxons/?sid={sid}";
    }

    static final class Products {
        static final String id = "v2/products/{id}";
        static final String sid = "v2/products?sid={sid}&q={q}";
    }

    static final class Users {
        static final String email = "v2/users/{email}";
    }

    static final class Orders {
        static final String shipments = "v2/orders/{number}/shipments";
        static final String line_items = "v2/orders/{number}/line_items";
        static final String ship_address_change = "v2/orders/{number}/ship_address_change";
        static final String current = "v2/orders/current";
        static final String number = "v2/orders/{number}";
        static final String completion = "v2/orders/{number}/completion";
        static final String cancellations = "v2/orders/{number}/cancellations?reason=test";
        static final String statusActive = "v2/orders?status=active&page={p}";
        static final String statusComplete = "v2/orders?status=complete&page={p}";
        static final String statusCanceled = "v2/orders?status=canceled&page={p}";
        static final String unrated = "v2/orders/unrated";
    }

    static final class Shipments {
        static final String shipping_rates = "v2/shipments/{number}/shipping_rates";
    }

    static final class LineItems {
        static final String id = "v2/line_items/{id}";
    }

    static final class Promotions {
        static final String referral_program = "v2/promotions/referral_program";
    }

    static final class AuthProviders {
        static final String sessions = "v2/auth_providers/{provider}/sessions";
    }
}
