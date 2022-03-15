package ru.instamart.api.endpoint;

/**
 * sbermarket.tech/api/v2/ endpoints
 */
public final class ApiV2EndPoints {

    public static final String AB_TESTS = "v2/ab_tests";
    public static final String ADDRESSES = "v2/addresses";
    public static final String ADS = "v2/ads";
    public static final String ADS_IMAGES = "v2/ads-images";
    public static final String APP_CONFIGURATION = "v2/app_configuration";
    public static final String BONUS_CARDS = "v2/bonus_cards";
    public static final String BONUS_PROGRAMS = "v2/bonus_programs";
    public static final String CATEGORIES = "v2/categories";
    public static final String CITIES = "v2/cities";
    public static final String COMPANY_DOCUMENTS = "v2/company_documents";
    public static final String CREDIT_CARDS = "v2/credit_cards";
    public static final String DEEPLINKS = "v2/deeplinks";
    public static final String DELIVERY_AVAILABILITY = "v2/delivery_availability";
    public static final String DEPARTMENTS = "v2/departments";
    public static final String FEATURE_FLAGS = "v2/feature_flags";
    public static final String INSTACOIN_ACCOUNT = "v2/instacoin_account";
    public static final String LEGAL_ENTITY = "v2/legal_entity";
    public static final String LINE_ITEMS = "v2/line_items";
    public static final String NOTIFICATIONS = "v2/notifications";
    public static final String ONBOARDING_PAGES = "v2/onboarding_pages";
    public static final String ORDERS = "v2/orders";
    public static final String PASSWORDS = "v2/passwords";
    public static final String PAYMENT_TOOLS = "v2/payment_tools";
    public static final String PAYMENT_TOOL_TYPES = "v2/payment_tool_types";
    public static final String PAYMENT_TOOLS_WITH_TYPES = "v2/payment_tools_with_types";
    public static final String PHONES = "v2/phones";
    public static final String PHONE_CONFIRMATIONS = "v2/phone_confirmations";
    public static final String PRODUCTS = "v2/products";
    public static final String PROFILE = "v2/profile";
    public static final String PURCHASED_PRODUCTS = "v2/purchased_products";
    public static final String REPLACEMENT_POLICIES = "v2/replacement_policies";
    public static final String RETAILERS = "v2/retailers";
    public static final String REVIEWABLE_SHIPMENT = "v2/reviewable_shipment";
    public static final String SHIPPING_METHODS = "v2/shipping_methods";
    public static final String SIMPLE_ADS = "v2/simple-ads";
    public static final String STORES = "v2/stores";
    public static final String TAXONS = "v2/taxons";
    public static final String USERS = "v2/users";

    public static final class Addresses {
        public static final String BY_ID = "v2/addresses/{id}";
    }

    public static final class AuthProviders {
        public static final String AUTH_PARAMS = "v2/auth_providers/{provider}/auth_params";
        public static final String AVAILABLE_FOR_ATTACH = "v2/auth_providers/available_for_attach";
        public static final String SESSIONS = "v2/auth_providers/{provider}/sessions";
    }

    public static final class BonusCards {
        public static final String BY_ID = "v2/bonus_cards/{bonusCardId}";
    }

    public static final class CreditCards {
        public static final String BY_ID = "v2/credit_cards/{creditCardId}";
    }

    public static final class Departments {
        public static final String BY_ID = "v2/departments/{id}";
    }

    public static final class ExternalAnalytics {
        public static final String DEVICE_IDENTIFICATION = "v2/external_analytics/device_identification";
    }

    public static final class ExternalPartners {
        public static final String SERVICES = "v2/external_partners/services";

        public static final class Banners {
            public static final String SBER_PRIME = "v2/external_partners/banners/sberprime";
        }
    }

    public static final class FavoritesList {
        public static final String ITEMS = "v2/favorites_list/items";
        public static final String PRODUCTS_SKU = "v2/favorites_list/products_sku";
    }

    public static final class LineItems {
        public static final String BY_ID = "v2/line_items/{productId}";
    }

    public static final class Onboarding {
        public static final String ONBOARDING_V2_PAGES = "v2/onboarding/v2_pages";
    }

    public static final class Orders {
        public static final String BY_NUMBER = "v2/orders/{orderNumber}";
        public static final String CANCELLATIONS = "v2/orders/{orderNumber}/cancellations";
        public static final String COMPLETION = "v2/orders/{orderNumber}/completion";
        public static final String CURRENT = "v2/orders/current";
        public static final String LINE_ITEMS = "v2/orders/{orderNumber}/line_items";
        public static final String LINE_ITEM_CANCELLATIONS = "v2/orders/{orderNumber}/line_item_cancellations";
        public static final String LINE_ITEM_REPLACEMENTS = "v2/orders/{orderNumber}/line_item_replacements";
        public static final String MERGE_STATUS = "v2/orders/{orderNumber}/merge_status";
        public static final String PAYMENT_TOOLS_WITH_TYPES = "v2/orders/{orderNumber}/payment_tools_with_types";
        public static final String PREVIOUS = "v2/orders/previous";
        public static final String PROMOTIONS = "v2/orders/{orderNumber}/promotions";
        public static final String PROMOTION_CODE = "v2/orders/{orderNumber}/promotions/{promoCode}";
        public static final String PROMOTION_LIMITS = "v2/orders/{orderNumber}/promotion_limits";
        public static final String REVIEW_ISSUES = "v2/orders/{orderNumber}/review_issues";
        public static final String SHIPMENTS = "v2/orders/{orderNumber}/shipments";
        public static final String SHIP_ADDRESS = "v2/orders/{orderNumber}/ship_address";
        public static final String SHIP_ADDRESS_CHANGE = "v2/orders/{orderNumber}/ship_address_change";
        public static final String SPASIBO_INFO = "v2/orders/{orderNumber}/spasibo_info";
        public static final String TRANSFER_METHOD = "v2/orders/{orderNumber}/transfer_method";
        public static final String UNRATED = "v2/orders/unrated";

        public static final class TransferMethod {
            public static final String ANALYZE = "v2/orders/{orderNumber}/transfer_method/analyze";
            public static final String LOSSES = "v2/orders/{orderNumber}/transfer_method/losses";
        }
    }

    public static final class Passwords {
        public static final String RESET = "v2/passwords/reset";
    }

    public static final class PaymentsSber {
        public static final String CREDIT_CARD_AUTHORIZATIONS = "v2/payments/sber/credit_card_authorizations";

        public static final class CreditCardAuthorizations {
            public static final String FINISH = "v2/payments/sber/credit_card_authorizations/finish";
        }
    }

    public static final class PhoneConfirmations {
        public static final String PHONE_NUMBER = "v2/phone_confirmations/{phoneNumber}";
    }

    public static final class Phones {
        public static final String BY_ID = "v2/phones/{phoneId}";
    }

    public static final class Products {
        public static final String BY_ID = "v2/products/{productId}";
    }

    public static final class Promotions {
        public static final String FREE_DELIVERY = "v2/promotions/free_delivery";
        public static final String PROMO_PRODUCTS = "v2/promotions/{promo_id}/promo_products";
        public static final String REFERRAL_PROGRAM = "v2/promotions/referral_program";
    }

    public static final class Recs {
        public static final String PERSONAL = "v2/recs/personal";
    }

    public static final class Retailers {
        public static final String BY_ID = "v2/retailers/{id}";
    }

    public static final class Searches {
        public static final String SUGGESTIONS = "v2/searches/suggestions";
    }

    public static final class Sessions {
        public static final String BY_TOKEN = "v2/sessions/{token}";
        public static final String USER = "v2/sessions/{token}/user";
    }

    public static final class Shipments {
        public static final String ACTIVE = "v2/shipments/active";
        public static final String ASSEMBLY_ITEMS = "v2/shipments/{shipmentNumber}/assembly_items";
        public static final String CANCELLATIONS = "v2/shipments/{shipmentNumber}/cancellations";
        public static final String CLONES = "v2/shipments/{shipmentNumber}/clones";
        public static final String DELIVERY_WINDOWS = "v2/shipments/{shipmentId}/delivery_windows/";
        public static final String LINE_ITEMS = "v2/shipments/{shipmentNumber}/line_items";
        public static final String LINE_ITEM_CANCELLATIONS = "v2/shipments/{shipmentNumber}/line_item_cancellations";
        public static final String LINE_ITEM_REPLACEMENTS = "v2/shipments/{shipmentNumber}/line_item_replacements";
        public static final String MERGE = "v2/shipments/{shipmentNumber}/merge";
        public static final String REVIEWS = "v2/shipments/{shipmentNumber}/reviews";
        public static final String REVIEW_ISSUES = "v2/shipments/{shipmentNumber}/review_issues";
        public static final String REVIEW_WINDOW_CLOSE = "v2/shipments/{shipmentNumber}/review_window_close";
        public static final String SERVICE_RATE = "v2/shipments/{shipmentNumber}/service_rate";
        public static final String SHIPMENTS = "v2/shipments/{shipmentNumber}";
        public static final String SHIPPING_RATES = "v2/shipments/{shipmentNumber}/shipping_rates";
        public static final String STATE = "v2/shipments/{shipmentNumber}/state";

        public static final class LineItems {
            public static final String MERGE = "v2/shipments/{shipmentNumber}/line_items/merge";
        }
    }

    public static final class SimpleRecs {
        public static final String COMPLEMENTARY = "v2/simple-recs/complementary";
        public static final String PERSONAL = "v2/simple-recs/personal";
        public static final String SUBSTITUTE = "v2/simple-recs/substitute";
    }

    public static final class Stores {
        public static final String BY_SID = "v2/stores/{sid}";
        public static final String FOR_MAP = "v2/stores/for_map";
        public static final String HEALTH_CHECK = "v2/stores/{sid}/healthcheck";
        public static final String NEXT_DELIVERIES = "v2/stores/{sid}/next_deliveries";
        public static final String PROMOTION_CARDS = "v2/stores/{sid}/promotion_cards";
    }

    public static final class Taxons {
        public static final String BY_ID = "v2/taxons/{taxonId}";
    }

    public static final class Users {
        public static final String BY_EMAIL = "v2/users/{email}";
        public static final String REFERRAL_PROGRAM = "v2/users/{email}/referral_program";
    }
}
