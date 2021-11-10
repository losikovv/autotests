package ru.instamart.api.endpoint;

public final class ApiV2EndPoints {

    public static final String AB_TESTS = "v2/ab_tests";
    public static final String ADDRESSES = "v2/addresses";
    public static final String ADS = "v2/ads";
    public static final String ADS_IMAGES = "v2/ads-images?image_path={image}";
    public static final String APP_CONFIGURATION = "v2/app_configuration";
    public static final String BONUS_CARDS = "v2/bonus_cards";
    public static final String BONUS_PROGRAMS = "v2/bonus_programs";
    public static final String CATEGORIES = "v2/categories?sid={sid}";
    public static final String COMPANY_DOCUMENTS = "v2/company_documents";
    public static final String CREDIT_CARDS = "v2/credit_cards";
    public static final String DEEPLINKS = "v2/deeplinks";
    public static final String DEPARTMENTS = "v2/departments?sid={sid}&offers_limit={numberOfProductsFromEachDepartment}";
    public static final String FEATURE_FLAGS = "v2/feature_flags";
    public static final String LEGAL_ENTITY = "v2/legal_entity";
    public static final String LINE_ITEMS = "v2/line_items";
    public static final String ONBOARDING_PAGES = "v2/onboarding_pages";
    public static final String ORDERS = "v2/orders";
    public static final String PASSWORDS = "v2/passwords";
    public static final String PAYMENT_TOOLS = "v2/payment_tools";
    public static final String PAYMENT_TOOL_TYPES = "v2/payment_tool_types";
    public static final String PAYMENT_TOOL_TYPES_WITH_ORDER_NUMBER = "v2/payment_tool_types?order_number={orderNumber}";
    public static final String PHONES = "v2/phones";
    public static final String PHONE_CONFIRMATIONS = "v2/phone_confirmations";
    public static final String PRODUCTS = "v2/products";
    public static final String PROFILE = "v2/profile";
    public static final String PURCHASED_PRODUCTS = "v2/purchased_products?sid={sid}";
    public static final String REPLACEMENT_POLICIES = "v2/replacement_policies";
    public static final String RETAILERS = "v2/retailers";
    public static final String REVIEWABLE_SHIPMENT = "v2/reviewable_shipment";
    public static final String SIMPLE_ADS = "v2/simple-ads";
    public static final String SHIPPING_METHODS = "v2/shipping_methods?sid={sid}";
    public static final String STORES = "v2/stores";
    public static final String TAXONS = "v2/taxons?sid={sid}";
    public static final String USERS = "v2/users";
    public static final String DELIVERY_AVAILABILITY = "v2/delivery_availability?lat={lat}&lon={lon}";

    public static final class AbTests {
        public static final String DEVICE_ID = "v2/ab_tests?device_id={deviceId}";
    }

    public static final class Addresses {
        public static final String BY_ID = "v2/addresses/{id}";
    }

    public static final class AuthProviders {
        public static final String SESSIONS = "v2/auth_providers/{provider}/sessions";
    }

    public static final class BonusCards {
        public static final String WITH_PARAMS =
                "v2/bonus_cards?bonus_card[bonus_program_id]={bonusProgramId}&bonus_card[number]={bonusCardNumber}";
        public static final String BY_ID = "v2/bonus_cards/{bonusCardId}";
    }

    public static final class CreditCards {
        public static final String BY_ID = "v2/credit_cards/{creditCardId}";
    }

    public static final class Deeplinks {
        public static final String BY_URL = "v2/deeplinks?web_url={webUrl}";
    }


    public static final class Departments {
        public static final String BY_ID = "v2/departments/{id}?sid={sid}";
    }

    public static final class ExternalAnalytics {
        public static final String DEVICE_IDENTIFICATION = "v2/external_analytics/device_identification";
    }

    public static final class ExternalPartners {
        public static final class Banners {
            public static final String SBER_PRIME = "v2/external_partners/banners/sberprime?store_id={sid}";
        }
    }

    public static final class FavoritesList {
        public static final String ITEMS = "v2/favorites_list/items";
        public static final String PRODUCTS_SKU = "v2/favorites_list/products_sku";

        public static final class Items {
            public static final String BY_SID = "v2/favorites_list/items?sid={sid}";
        }
    }

    public static final class LegalEntity {
        public static final String BY_INN = "v2/legal_entity?inn={inn}";
    }

    public static final class LineItems {
        public static final String BY_ID = "v2/line_items/{productId}";
    }

    public static final class Onboarding {
        public static final String ONBOARDING_V2_PAGES = "v2/onboarding/v2_pages";
    }

    public static final class Orders {
        public static final String BY_NUMBER = "v2/orders/{orderNumber}";
        public static final String CANCELLATIONS = "v2/orders/{orderNumber}/cancellations?reason={reason}";
        public static final String COMPLETION = "v2/orders/{orderNumber}/completion";
        public static final String CURRENT = "v2/orders/current";
        public static final String LINE_ITEMS = "v2/orders/{orderNumber}/line_items";
        public static final String SHIPMENTS = "v2/orders/{orderNumber}/shipments";
        public static final String SHIP_ADDRESS = "v2/orders/{orderNumber}/ship_address";
        public static final String SHIP_ADDRESS_CHANGE = "v2/orders/{orderNumber}/ship_address_change";
        public static final String STATUS = "v2/orders?status={status}&page={page}";
        public static final String PAGE = "v2/orders?page={page}";
        public static final String UNRATED = "v2/orders/unrated";
        public static final String PREVIOUS = "v2/orders/previous";
        public static final String PROMOTIONS = "v2/orders/{orderNumber}/promotions";
        public static final String PROMOTION_CODE = "v2/orders/{orderNumber}/promotions/{promoCode}";
        public static final String PROMOTION_LIMITS = "v2/orders/{orderNumber}/promotion_limits";
        public static final String LINE_ITEM_CANCELLATIONS = "v2/orders/{orderNumber}/line_item_cancellations";
        public static final String LINE_ITEM_REPLACEMENTS = "v2/orders/{orderNumber}/line_item_replacements";
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

    public static final class Phones {
        public static final String BY_ID = "v2/phones/{phoneId}";
    }

    public static final class PhoneConfirmations {
        public static final String PHONE_NUMBER =
                "v2/phone_confirmations/{phoneNumber}";

        public static final String PHONE_NUMBER_WITH_PARAM =
                "v2/phone_confirmations/{phoneNumber}?" +
                        "phone_confirmation_code={phoneConfirmationCode}&" +
                        "promo_terms_accepted={promoTermsAccepted}";
    }

    public static final class Products {
        public static final String BY_ID = "v2/products/{productId}";
    }

    public static final class Promotions {
        public static final String PROMO_PRODUCTS = "v2/promotions/{promo_id}/promo_products?sid={sid}";
        public static final String REFERRAL_PROGRAM = "v2/promotions/referral_program";
    }

    public static final class Retailers {
        public static final String BY_ID = "v2/retailers/{id}";
    }

    public static final class Recs {
        public static final String PERSONAL = "v2/recs/personal";
    }

    public static final class SimpleRecs {
        public static final String PERSONAL = "v2/simple-recs/personal";
    }

    public static final class Searches {
        public static final String SUGGESTIONS = "v2/searches/suggestions?sid={sid}";

        public static final class Suggestions {
            public static final String BY_QUERY = "v2/searches/suggestions?sid={sid}&q={query}";
        }
    }

    public static final class Sessions {
        public static final String BY_TOKEN = "v2/sessions/{token}";
        public static final String USER = "v2/sessions/{token}/user";
    }

    public static final class Shipments {
        public static final String SHIPMENTS = "v2/shipments/{shipmentNumber}";
        public static final String DELIVERY_WINDOWS = "v2/shipments/{shipmentId}/delivery_windows/?{date}";
        public static final String LINE_ITEMS = "v2/shipments/{shipmentNumber}/line_items";
        public static final String LINE_ITEM_CANCELLATIONS = "v2/shipments/{shipmentNumber}/line_item_cancellations";
        public static final String LINE_ITEM_REPLACEMENTS = "v2/shipments/{shipmentNumber}/line_item_replacements";
        public static final String SERVICE_RATE = "v2/shipments/{shipmentNumber}/service_rate?{deliveryWindowId}";
        public static final String SHIPPING_RATES = "v2/shipments/{shipmentNumber}/shipping_rates?{date}";
        public static final String STATE = "v2/shipments/{shipmentNumber}/state";
        public static final String REVIEW_ISSUES = "v2/shipments/{shipmentNumber}/review_issues";
        public static final String REVIEWS = "v2/shipments/{shipmentNumber}/reviews";
        public static final String CLONES = "v2/shipments/{shipmentNumber}/clones";
        public static final String ASSEMBLY_ITEMS = "v2/shipments/{shipmentNumber}/assembly_items";
        public static final String CANCELLATIONS = "v2/shipments/{shipmentNumber}/cancellations";
    }

    public static final class Stores {
        public static final String BY_SID = "v2/stores/{sid}";
        public static final String HEALTH_CHECK = "v2/stores/{sid}/healthcheck";
        public static final String NEXT_DELIVERIES = "v2/stores/{sid}/next_deliveries";
        public static final String PROMOTION_CARDS = "v2/stores/{sid}/promotion_cards";
    }

    public static final class Taxons {
        public static final String BY_ID = "v2/taxons/{taxonId}?sid={sid}";
    }

    public static final class Users {
        public static final String BY_EMAIL = "v2/users/{email}";
    }
}
