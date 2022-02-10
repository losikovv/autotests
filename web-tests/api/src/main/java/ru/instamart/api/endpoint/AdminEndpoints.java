package ru.instamart.api.endpoint;

/**
 * sbermarket.tech/admin/ endpoints (без /api/)
 */
public final class AdminEndpoints {

    public static final String BRAND = "brands/{permalink}";
    public static final String BRANDS = "brands";

    public static final String CITIES = "cities";
    public static final String CITY = "cities/{citySlug}";

    public static final String GENERAL_SETTINGS = "general_settings";

    public static final String MANUFACTURER = "manufacturers/{manufacturerId}";
    public static final String MANUFACTURERS = "manufacturers";

    public static final String MANUFACTURING_COUNTRIES = "manufacturing_countries";
    public static final String MANUFACTURING_COUNTRY = "manufacturing_countries/{countryPermalink}";

    public static final String OPTION_TYPE = "option_types/{optionTypeId}";
    public static final String OPTION_TYPES = "option_types";
    public static final String OPTION_VALUE = "option_values/{optionValueId}";

    public static final String PAGE = "pages/{pageId}";
    public static final String PAGES = "pages";

    public static final String PROPERTIES = "properties";
    public static final String PROPERTY = "properties/{propertyId}";

    public static final String SHIPMENTS = "shipments.json";
    public static final String SHIPPING_METHODS = "shipping_methods";

    public static final String STORE = "stores/{storeId}";
    public static final String STORES = "stores";

    public static final String TAXONOMIES = "taxonomies";
    public static final String TAXONOMY = "taxonomies/{taxonomyId}";
}
