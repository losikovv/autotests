package ru.instamart.application.rest;

final class EndPoints {
    static final String sessions = "sessions";
    static final String orders = "orders";
    static final String departments = "departments?sid={sid}&offers_limit={limit}";
    static final String line_items = "line_items";
    static final String stores = "stores?lat={lat}&lon={lon}";

    static final class Orders {
        static final String shipments = "orders/{number}/shipments";
        static final String ship_address_change = "orders/{number}/ship_address_change";
        static final String current = "orders/current";
        static final String number = "orders/{number}";
        static final String completion = "orders/{number}/completion";
        static final String cancellations = "orders/{number}/cancellations?reason=test";
    }
    static final class Shipments {
        static final String shipping_rates = "shipments/{number}/shipping_rates";
    }
}
