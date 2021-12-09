package ru.instamart.api.endpoint;

public final class AdminEndpoints {
    public static final String CITIES = "cities";
    public static final String PAGES = "pages";
    public static final String STORES = "stores";

    public static final class Cities {
        public static final String BY_SLUG = "cities/{citySlug}";
    }

    public static final class Pages {
        public static final String BY_ID = "pages/{pageId}";
    }

    public static final class Stores {
        public static final String BY_ID = "stores/{storeId}";
    }
}
