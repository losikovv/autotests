package ru.instamart.api.endpoint;

public class RisExporterEndpoints {
    public static final String AUTHENTICATION_TOKEN = "authentication/token";

    public static class Stores {
        public static class Catalog {
            public static final String CATEGORIES = "stores/{sid}/catalog/categories";
        }
    }
}
