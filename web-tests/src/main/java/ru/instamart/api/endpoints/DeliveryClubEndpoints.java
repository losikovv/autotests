package ru.instamart.api.endpoints;

public class DeliveryClubEndpoints {

    public static final class Authentication {
        public static final String TOKEN = "delivery_club/v1/authentication/token";
    }

    public static final class Stores {
        public static final String STOCK = "delivery_club/v1/stores/{sid}/stock";
        public static final String SLOTS = "delivery_club/v1/stores/{sid}/slots";
        public static final String NOTIFICATIONS = "delivery_club/v1/stores/{sid}/notifications";
        public static final String ZONES = "delivery_club/v1/stores/{sid}/zones";
        public static final String ORDERS = "delivery_club/v1/stores/{sid}/orders";


        public static final class Orders {
            public static final String BY_NUMBER = "delivery_club/v1/stores/{sid}/orders/{orderNumber}";
            public static final String STATUS = "delivery_club/v1/stores/{sid}/orders/{orderNumber}/status";
        }

        public static final class Catalog {
            public static final String CATEGORIES = "delivery_club/v1/stores/{sid}/catalog/categories";
            public static final String PRODUCTS = "delivery_club/v1/stores/{sid}/catalog/products";
        }

        public static final class Slots {
            public static final String AVAILABLE = "delivery_club/v1/stores/{sid}/slots/available";
        }
    }
}
