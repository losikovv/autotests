package ru.instamart.api.endpoint;

/**
 * sbermarket.tech/admin/ endpoints (без /api/)
 */
public final class AdminEndpoints {

    public static final String CITIES = "cities";
    public static final String MANUFACTURERS = "manufacturers";
    public static final String PAGES = "pages";
    public static final String STORES = "stores";
    public static final String SHIPPING_METHODS = "shipping_methods";

    public static final class Cities {
        public static final String BY_SLUG = "cities/{citySlug}";
    }

    public static final class Pages {
        public static final String BY_ID = "pages/{pageId}";
    }

    public static final class Stores {
        public static final String BY_ID = "stores/{storeId}";
    }

    public static final class Manufacturers {
        public static final String BY_ID = "manufacturers/{id}";
    }
}
