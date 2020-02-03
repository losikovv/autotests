package ru.instamart.application.rest;

final class EndPoints {
    static final String retailers = "v2/retailers";
    static final String retailersV1 = "retailers";
    static final String stores = "v2/stores";
    static final String users = "v2/users";
    static final String sessions = "v2/sessions";
    static final String orders = "v2/orders";
    static final String departments = "v2/departments?sid={sid}&offers_limit={limit}";
    static final String line_items = "v2/line_items";
    static final String payment_tools = "v2/payment_tools";
    static final String payment_tool_types = "v2/payment_tool_types";
    static final String shipping_methods = "v2/shipping_methods?sid={sid}";

    static final class Stores {
        static final String byCoordinates = "v2/stores?lat={lat}&lon={lon}";
        static final String sid = "v2/stores/{sid}";
    }

    static final class Products {
        static final String id = "v2/products/{id}";
    }

    static final class Users {
        static final String email = "v2/users/{email}";
    }

    static final class Orders {
        static final String shipments = "v2/orders/{number}/shipments";
        static final String ship_address_change = "v2/orders/{number}/ship_address_change";
        static final String current = "v2/orders/current";
        static final String number = "v2/orders/{number}";
        static final String completion = "v2/orders/{number}/completion";
        static final String cancellations = "v2/orders/{number}/cancellations?reason=test";
    }

    static final class Shipments {
        static final String shipping_rates = "v2/shipments/{number}/shipping_rates";
    }
}
