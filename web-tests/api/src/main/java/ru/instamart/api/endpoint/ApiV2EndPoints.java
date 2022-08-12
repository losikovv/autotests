package ru.instamart.api.endpoint;

/**
 * мобильные эндпоинты, которы раньше были с api/v2/
 */
public final class ApiV2EndPoints {

    public static final String AB_TESTS = "vab_tests";
    public static final String ADDRESSES = "addresses";
    public static final String ADS = "ads";
    public static final String ADS_IMAGES = "ads-images";
    public static final String APP_CONFIGURATION = "app_configuration";
    public static final String BONUS_CARDS = "bonus_cards";
    public static final String BONUS_PROGRAMS = "bonus_programs";
    public static final String CATEGORIES = "categories";
    public static final String CITIES = "cities";
    public static final String COMPANY_DOCUMENTS = "company_documents";
    public static final String COMPANY_PRESENCE = "company_presence";
    public static final String CREDIT_CARDS = "credit_cards";
    public static final String CURRENT_TIME = "current_time";
    public static final String DEEPLINKS = "deeplinks";
    public static final String DELIVERY_AVAILABILITY = "delivery_availability";
    public static final String DEPARTMENTS = "departments";
    public static final String FEATURE_FLAGS = "feature_flags";
    public static final String INSTACOIN_ACCOUNT = "instacoin_account";
    public static final String LEGAL_ENTITY = "legal_entity";
    public static final String LOYALTIES = "loyalties";
    public static final String LINE_ITEMS = "line_items";
    public static final String NOTIFICATIONS = "notifications";
    public static final String ONBOARDING_PAGES = "onboarding_pages";
    public static final String ORDERS = "orders";
    public static final String PASSWORDS = "passwords";
    public static final String PAYMENT_TOOLS = "payment_tools";
    public static final String PAYMENT_TOOL_TYPES = "payment_tool_types";
    public static final String PAYMENT_TOOLS_WITH_TYPES = "payment_tools_with_types";
    public static final String PHONES = "phones";
    public static final String PHONE_CONFIRMATIONS = "phone_confirmations";
    public static final String PRODUCTS = "products";
    public static final String PRODUCT_FEEDBACKS = "product_feedbacks";
    public static final String PROFILE = "profile";
    public static final String PURCHASED_PRODUCTS = "purchased_products";
    public static final String REPLACEMENT_POLICIES = "replacement_policies";
    public static final String RETAILERS = "retailers";
    public static final String REVIEWABLE_SHIPMENT = "reviewable_shipment";
    public static final String SHIPPING_METHODS = "shipping_methods";
    public static final String SIMPLE_ADS = "simple-ads";
    public static final String STORES = "stores";
    public static final String TAXONS = "taxons";
    public static final String USERS = "users";

    public static final class Addresses {
        public static final String BY_ID = "addresses/{id}";
    }

    public static final class AuthProviders {
        public static final String AUTH_PARAMS = "auth_providers/{provider}/auth_params";
        public static final String AVAILABLE_FOR_ATTACH = "auth_providers/available_for_attach";
        public static final String SESSIONS = "auth_providers/{provider}/sessions";
    }

    public static final class BonusCards {
        public static final String BY_ID = "bonus_cards/{bonusCardId}";
    }

    public static final class CreditCards {
        public static final String BY_ID = "credit_cards/{creditCardId}";
    }

    public static final class Departments {
        public static final String BY_ID = "departments/{id}";
    }

    public static final class ExternalAnalytics {
        public static final String DEVICE_IDENTIFICATION = "external_analytics/device_identification";
    }

    public static final class ExternalPartners {
        public static final String SERVICES = "external_partners/services";

        public static final class Banners {
            public static final String SBER_PRIME = "external_partners/banners/sberprime";
        }
    }

    public static final class FeatureFlags {
        public static final String BY_NAME = "feature_flags/{name}";
    }

    public static final class FavoritesList {
        public static final String ITEMS = "favorites_list/items";
        public static final String PRODUCTS_SKU = "favorites_list/products_sku";
    }

    public static final class Loyalties {
        public static final String SBER_LOYALTY_INFO = "loyalties/sber_loyalty_info";
    }

    public static final class LineItems {
        public static final String BY_ID = "line_items/{productId}";
    }

    public static final class Onboarding {
        public static final String ONBOARDING_V2_PAGES = "onboarding/v2_pages";
    }

    public static final class Orders {
        public static final String BY_NUMBER = "orders/{orderNumber}";
        public static final String CANCELLATIONS = "orders/{orderNumber}/cancellations";
        public static final String COMPANY = "orders/{orderNumber}/company";
        public static final String COMPLETION = "orders/{orderNumber}/completion";
        public static final String CURRENT = "orders/current";
        public static final String LINE_ITEMS = "orders/{orderNumber}/line_items";
        public static final String LINE_ITEM_CANCELLATIONS = "orders/{orderNumber}/line_item_cancellations";
        public static final String LINE_ITEM_REPLACEMENTS = "orders/{orderNumber}/line_item_replacements";
        public static final String MERGE_STATUS = "orders/{orderNumber}/merge_status";
        public static final String PAYMENT_TOOLS_WITH_TYPES = "orders/{orderNumber}/payment_tools_with_types";
        public static final String PREVIOUS = "orders/previous";
        public static final String PROMOTIONS = "orders/{orderNumber}/promotions";
        public static final String PROMOTION_CODE = "orders/{orderNumber}/promotions/{promoCode}";
        public static final String PROMOTION_LIMITS = "orders/{orderNumber}/promotion_limits";
        public static final String REVIEW_ISSUES = "orders/{orderNumber}/review_issues";
        public static final String SHIPMENTS = "orders/{orderNumber}/shipments";
        public static final String SHIP_ADDRESS = "orders/{orderNumber}/ship_address";
        public static final String SHIP_ADDRESS_CHANGE = "orders/{orderNumber}/ship_address_change";
        public static final String SPASIBO_INFO = "orders/{orderNumber}/spasibo_info";
        public static final String TRANSFER_METHOD = "orders/{orderNumber}/transfer_method";
        public static final String UNRATED = "orders/unrated";

        public static final class TransferMethod {
            public static final String ANALYZE = "orders/{orderNumber}/transfer_method/analyze";
            public static final String LOSSES = "orders/{orderNumber}/transfer_method/losses";
        }
    }

    public static final class Passwords {
        public static final String RESET = "passwords/reset";
    }

    public static final class PaymentsSber {
        public static final String CREDIT_CARD_AUTHORIZATIONS = "payments/sber/credit_card_authorizations";

        public static final class CreditCardAuthorizations {
            public static final String FINISH = "payments/sber/credit_card_authorizations/finish";
        }
    }

    public static final class ProductFeedbacks {
        public static final String BY_ID = "product_feedbacks/{id}";
        public static final String CAN_POST_FEEDBACK = "product_feedbacks/can_post_feedback";
        public static final String ACTUAL_FEEDBACK = "product_feedbacks/actual_feedback";
    }

    public static final class PhoneConfirmations {
        public static final String PHONE_NUMBER = "phone_confirmations/{phoneNumber}";
    }

    public static final class Phones {
        public static final String BY_ID = "phones/{phoneId}";
    }

    public static final class Products {
        public static final String BY_ID = "products/{productId}";
    }

    public static final class Promotions {
        public static final String FREE_DELIVERY = "promotions/free_delivery";
        public static final String PROMO_PRODUCTS = "promotions/{promo_id}/promo_products";
        public static final String REFERRAL_PROGRAM = "promotions/referral_program";
    }

    public static final class Recs {
        public static final String PERSONAL = "recs/personal";
    }

    public static final class Retailers {
        public static final String BY_ID = "retailers/{id}";
    }

    public static final class Searches {
        public static final String SUGGESTIONS = "searches/suggestions";

        public static final class Suggestions {
            public static final String TOP_PHRASES = "searches/suggestions/top_phrases";
        }
    }

    public static final class SeparateReviews {
        public static final String SHIPMENT_REVIEW_RATES = "separate_reviews/shipment_review_rates";
        public static final String REVIEWABLE_SHIPMENT = "separate_reviews/reviewable_shipment";
    }

    public static final class Sessions {
        public static final String BY_TOKEN = "sessions/{token}";
        public static final String USER = "sessions/{token}/user";
    }

    public static final class Shipments {
        public static final String ACTIVE = "shipments/active";
        public static final String ASSEMBLY_ITEMS = "shipments/{shipmentNumber}/assembly_items";
        public static final String CANCELLATIONS = "shipments/{shipmentNumber}/cancellations";
        public static final String CLONES = "shipments/{shipmentNumber}/clones";
        public static final String DELIVERY_WINDOWS = "shipments/{shipmentId}/delivery_windows/";
        public static final String LINE_ITEMS = "shipments/{shipmentNumber}/line_items";
        public static final String LINE_ITEM_CANCELLATIONS = "shipments/{shipmentNumber}/line_item_cancellations";
        public static final String LINE_ITEM_REPLACEMENTS = "shipments/{shipmentNumber}/line_item_replacements";
        public static final String MERGE = "shipments/{shipmentNumber}/merge";
        public static final String REVIEWS = "shipments/{shipmentNumber}/reviews";
        public static final String REVIEW_ISSUES = "shipments/{shipmentNumber}/review_issues";
        public static final String REVIEW_WINDOW_CLOSE = "shipments/{shipmentNumber}/review_window_close";
        public static final String SERVICE_RATE = "shipments/{shipmentNumber}/service_rate";
        public static final String SHIPMENTS = "shipments/{shipmentNumber}";
        public static final String SHIPPING_RATES = "shipments/{shipmentNumber}/shipping_rates";
        public static final String STATE = "shipments/{shipmentNumber}/state";

        public static final class LineItems {
            public static final String MERGE = "shipments/{shipmentNumber}/line_items/merge";
        }
    }

    public static final class SimpleRecs {
        public static final String COMPLEMENTARY = "simple-recs/complementary";
        public static final String PERSONAL = "simple-recs/personal";
        public static final String SUBSTITUTE = "simple-recs/substitute";
    }

    public static final class Stores {
        public static final String BY_SID = "stores/{sid}";
        public static final String FOR_MAP = "stores/for_map";
        public static final String HEALTH_CHECK = "stores/{sid}/healthcheck";
        public static final String NEXT_DELIVERIES = "stores/{sid}/next_deliveries";
        public static final String PROMOTION_CARDS = "stores/{sid}/promotion_cards";
    }

    public static final class Taxons {
        public static final String BY_ID = "taxons/{taxonId}";
    }

    public static final class User {
        public static final String COMPANIES = "user/companies";
        public static final String COMPANY_EMPLOYEES = "user/company_employees";
        public static final String EXIST = "user/companies/exist";
    }

    public static final class Users {
        public static final String BY_EMAIL = "users/{email}";
        public static final String REFERRAL_PROGRAM = "users/{email}/referral_program";
    }
}
