package ru.instamart.api.endpoint;

public final class ApiV2EndPoints {

    public static final String ADDRESSES = "v2/addresses";
    public static final String BONUS_CARDS = "v2/bonus_cards";
    public static final String BONUS_PROGRAMS = "v2/bonus_programs";
    public static final String CATEGORIES = "v2/categories?sid={sid}";
    public static final String DEPARTMENTS = "v2/departments?sid={sid}&offers_limit={numberOfProductsFromEachDepartment}";
    public static final String LINE_ITEMS = "v2/line_items";
    public static final String ONBOARDING_PAGES = "v2/onboarding_pages";
    public static final String ORDERS = "v2/orders";
    public static final String PASSWORDS = "v2/passwords";
    public static final String PAYMENT_TOOLS = "v2/payment_tools";
    public static final String PAYMENT_TOOL_TYPES = "v2/payment_tool_types";
    public static final String PHONE_CONFIRMATIONS = "v2/phone_confirmations?phone={encryptedPhoneNumber}";
    public static final String PRODUCTS = "v2/products?sid={sid}&q={query}";
    public static final String PURCHASED_PRODUCTS = "v2/purchased_products?sid={sid}";
    public static final String RETAILERS = "v2/retailers";
    public static final String SHIPPING_METHODS = "v2/shipping_methods?sid={sid}";
    public static final String STORES = "v2/stores";
    public static final String TAXONS = "v2/taxons?sid={sid}";
    public static final String USERS = "v2/users";
    public static final String DELIVERY_AVAILABILITY = "v2/delivery_availability?lat={lat}&lon={lon}";

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

    public static final class Departments {
        public static final String BY_ID = "v2/departments/{id}?sid={sid}";
    }

    public static final class FavoritesList {
        public static final String ITEMS = "v2/favorites_list/items";
        public static final String PRODUCTS_SKU = "v2/favorites_list/products_sku";

        public static final class Items {
            public static final String BY_SID = "v2/favorites_list/items?sid={sid}";
        }
    }

    public static final class LineItems {
        public static final String BY_ID = "v2/line_items/{productId}";
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
        public static final String UNRATED = "v2/orders/unrated";
        public static final String PROMOTIONS = "v2/orders/{orderNumber}/promotions";
        public static final String PROMOTION_CODE = "v2/orders/{orderNumber}/promotions/{promoCode}";
    }

    public static final class Passwords {
        public static final String RESET = "v2/passwords/reset";
    }

    public static final class PhoneConfirmations {
        public static final String PHONE_NUMBER =
                "v2/phone_confirmations/{phoneNumber}?" +
                        "phone_confirmation_code={phoneConfirmationCode}&" +
                        "promo_terms_accepted={promoTermsAccepted}";
    }

    public static final class Products {
        public static final String BY_ID = "v2/products/{productId}";
        public static final String BY_SID = "v2/products/?sid={sid}";
    }

    public static final class Promotions {
        public static final String PROMO_PRODUCTS = "v2/promotions/{promo_id}/promo_products?sid={sid}";
        public static final String REFERRAL_PROGRAM = "v2/promotions/referral_program";
    }

    public static final class Retailers {
        public static final String BY_ID = "v2/retailers/{id}";
    }

    public static final class Recs{
        public static final String PERSONAL = "v2/recs/personal";
    }
    public static final class SimpleRecs{
        public static final String PERSONAL = "v2/simple-recs/personal";
    }

    public static final class Searches {
        public static final String SUGGESTIONS = "v2/searches/suggestions?sid={sid}";

        public static final class Suggestions {
            public static final String BY_QUERY = "v2/searches/suggestions?sid={sid}&q={query}";
        }
    }

    public static final class Shipments {
        public static final String SHIPPING_RATES = "v2/shipments/{shipmentNumber}/shipping_rates";
    }

    public static final class Stores {
        public static final String BY_SID = "v2/stores/{sid}";
        public static final String HEALTH_CHECK = "v2/stores/{sid}/healthcheck";
        public static final String PROMOTION_CARDS = "v2/stores/{sid}/promotion_cards";
    }

    public static final class Taxons {
        public static final String BY_ID = "v2/taxons/{taxonId}?sid={sid}";
    }

    public static final class Users {
        public static final String BY_EMAIL = "v2/users/{email}";
    }

    public static final class ExternalPartners {
        public static final class Banners {
            public static final String SBER_PRIME = "v2/external_partners/banners/sberprime?store_id={sid}";
        }
    }
}
