package ru.instamart.api.endpoint;

/**
 * sbermarket.tech/api/ endpoints (без указания версии)
 */
public final class ApiV1Endpoints {

    public static final String AVAILABLE_PAYMENT_TOOLS = "available_payment_tools";
    public static final String API_CLIENTS = "api_clients";
    public static final String CHECKOUT = "checkout";
    public static final String COMPANIES = "companies";
    public static final String COMPANY_EMPLOYEES = "company_employees";
    public static final String COMPANY_MANAGERS = "company_managers";
    public static final String COMPANY_PRESENCE = "company_presence?inn={inn}";
    public static final String COMPANY_SALES_CONTRACT = "company_sales_contracts";
    public static final String COMPENSATION_PROMOTIONS = "compensation_promotions";
    public static final String DELIVERY_WINDOW_KINDS = "delivery_window_kinds";
    public static final String LEGAL_ENTITY = "legal_entity?inn={inn}";
    public static final String LINE_ITEMS = "line_items";
    public static final String MARKETING_SAMPLES = "marketing_samples";
    public static final String MULTIRETAILER_ORDER = "multiretailer_order";
    public static final String OPERATIONAL_ZONES = "operational_zones";
    public static final String ORDERS = "orders?page={pageNumber}";
    public static final String PHONE_CONFIRMATIONS = "phone_confirmations";
    public static final String PROMOTION_CARD_CATEGORIES = "promotion_card_categories";
    public static final String PROMOTION_CARDS = "promotion_cards";
    public static final String PROMOTIONS = "promotions";
    public static final String REPLACEMENT_POLICIES = "replacement_policies";
    public static final String RETAILER_AGREEMENTS = "retailer_agreements";
    public static final String RETAILER_CONTRACT_TYPES = "retailer_contract_types";
    public static final String RETAILERS = "retailers";
    public static final String RETAILER_POSITIONS = "retailer_positions";
    public static final String SHIPPING_METHOD_KINDS = "shipping_method_kinds";
    public static final String SHIPPING_METHODS = "shipping_methods";
    public static final String SHIPPING_POLICIES = "shipping_policies";
    public static final String SHOPPING_CONTEXT = "shopping_context";
    public static final String SHOPPING_SESSION = "shopping_session";
    public static final String STORES = "stores";
    public static final String TOKENS = "tokens";
    public static final String USERS = "users";
    public static final String USER_SESSIONS = "user_sessions";

    /**
     * sbermarket.tech/api/admin/ endpoints (с /api/)
     */
    public static final class Admin {
        public static final String APP_CONFIG = "admin/app_config";
        public static final String CITIES = "admin/cities";
        public static final String COMPANY_SETTINGS = "admin/company_settings";
        public static final String CURRENCIES = "admin/currencies";
        public static final String FAQ_GROUPS = "admin/faq_groups";
        public static final String FEATURE_SETTINGS = "admin/feature_settings";
        public static final String GENERAL_SETTINGS = "admin/general_settings";
        public static final String MAIN_NAVIGATION = "admin/main_navigation";
        public static final String MOBILE_CONFIGS = "admin/mobile_configs";
        public static final String LOGISTIC_DENSITIES = "admin/logistic_densities";
        public static final String OPERATIONAL_ZONES = "admin/operational_zones";
        public static final String PAYMENT_METHODS = "admin/payment_methods";
        public static final String SHIPMENTS = "admin/shipments.json";
        public static final String SHIPMENT = "admin/shipments/{uuid}";
        public static final String SHIPPING_CATEGORIES = "admin/shipping_categories";
        public static final String SHIPPING_METHOD_KINDS = "admin/shipping_method_kinds";
        public static final String SHIPPING_METHODS = "admin/shipping_methods";
        public static final String SMS_SETTINGS = "admin/sms_settings";
        public static final String TENANTS = "admin/tenants";
        public static final String USER_PERMISSIONS = "admin/user_permissions";
        public static final String USERS = "admin/users";

        public static final class Cities {
            public static final String CITY = "admin/cities/{cityId}";
            public static final String LOCK = "admin/cities/{cityId}/lock";
        }

        public static final class Countries {
            public static final String STATES = "admin/countries/{countryId}/states";
            public static final String STATE = "admin/countries/{countryId}/states/{stateId}";
        }

        public static final class Dictionaries {
            public static final String API_CLIENTS = "admin/dictionaries/api_clients";
            public static final String PAYMENT_METHODS = "admin/dictionaries/payment_methods";
            public static final String PAYMENT_STATES = "admin/dictionaries/payment_states";
            public static final String SHIPMENT_COMBINED_STATES = "admin/dictionaries/shipment_combined_states";
            public static final String TENANTS = "admin/dictionaries/tenants";
        }

        public static final class FaqGroups {
            public static final String FAQ_GROUP = "admin/faq_groups/{faqGroupId}";
            public static final String UPDATE_POSITIONS = "admin/faq_groups/update_positions";

            public static final class FaqGroup {
                public static final String FAQS = "admin/faq_groups/{faqGroupId}/faqs";
                public static final String FAQ = "admin/faq_groups/{faqGroupId}/faqs/{faqId}";
                public static final String UPDATE_POSITIONS = "admin/faq_groups/{faqGroupId}/faqs/update_positions";
            }
        }

        public static final class Hde {
            public static final String TICKETS = "admin/hde/tickets";
        }

        public static final class ExternalPartners {
            public static final String SUBSCRIPTION = "admin/external_partners/subscriptions/{subscriptionId}";
        }

        public static final class OperationalZones {
            public static final String BY_ID = "admin/operational_zones/{operationalZoneID}";
            public static final String DISPATCH_SETTING = "admin/operational_zones/{operationalZoneID}/dispatch_setting";
        }

        public static final class Orders {

            public static final class Number {
                public static final String ADJUSTMENTS = "admin/orders/{orderNumber}/adjustments";
                public static final String CANCELLATIONS = "admin/orders/{orderNumber}/cancellations";
                public static final String DELIVERY_WINDOWS = "admin/orders/{orderNumber}/delivery_windows";
                public static final String SIDEBAR = "admin/orders/{orderNumber}/sidebar";

                public static final class Adjustments {
                    public static final String CLOSE = "admin/orders/{orderNumber}/adjustments/close";
                    public static final String ID = "admin/orders/{orderNumber}/adjustments/{adjustmentId}";
                    public static final String OPEN = "admin/orders/{orderNumber}/adjustments/open";
                    public static final String TOGGLE_STATE = "admin/orders/{orderNumber}/adjustments/{adjustmentId}/toggle_state";
                }
            }
        }

        public static final class PromotionDetails {
            public static final class PromotionCode {
                public static final String PROMOTION_CODE_DETAILS = "admin/promotion_details/promotion_code/promotion_code_details";
            }

        }

        public static final class Promotions {
            public static final String PROMOTION = "admin/promotions/{promotionId}";
        }

        public static final class Shipments {
            public static final String DOCUMENTS = "admin/shipments/{shipmentNumber}/documents/{document}.pdf";
            public static final String LEFTOVERS = "admin/shipments/{shipmentUUID}/leftovers";
            public static final String REDISPATCH = "admin/shipments/{shipmentUUID}/redispatch";
            public static final String SHIPMENT_RETURNS = "admin/shipments/{shipmentUUID}/shipment_returns";
            public static final String SHIPMENT_RETURN = "admin/shipments/{shipmentUUID}/shipment_returns/{shipmentReturnUUID}";
        }

        public static final class StoreLabels {
            public static final String STORE_LABEL = "admin/store_labels/{storeLabelId}";
            public static final String STORE_LABELS = "admin/store_labels";
        }

        public static final class Users {
            public static final String BY_ID = "admin/users/{userId}";
        }

        public static final class PaymentMethods{
            public static final String UPDATE_POSITIONS = "admin/payment_methods/update_positions";
        }
    }

    public static final class ApiClients {
        public static final String BY_ID = "api_clients/{apiClientId}";
    }

    public static final class Checkout {
        public static final String COMPLETE = "checkout/complete";
    }

    /**
     * Страница компании в админке
     */
    public static final class Company {
        public static final String BY_ID = "companies/{companyID}";
        public static final String BY_INN = "companies?inn={inn}";

        public static final class PaymentAccount {
            public static final String REFRESH = "companies/{companyID}/payment_account/refresh";
        }

        public static final class SecurityCode {
            public static final String REFRESH = "companies/{companyID}/security_code/refresh";
        }
    }

    public static final class CompanyEmployees {
        public static final String BY_ID = "company_employees/{employeeID}";
    }

    public static final class CompanyManagers {
        public static final String BY_ID = "company_managers/{managerID}";
    }

    public static final class CompanySalesContracts {
        public static final String ARCHIVE = "company_sales_contracts/{contractID}/archive";
        public static final String BY_ID = "company_sales_contracts/{contractID}";
    }

    public static final class DeliveryWindows {
        public static final String BY_ID = "delivery_windows/{deliveryWindowId}";
    }

    public static final class ExternalPartners {
        public static final String SERVICES = "external_partners/services";

        public static final class Banners {
            public static final String SBER_PRIME = "external_partners/banners/sberprime";
        }

    }

    public static final class Imports {
        public static final String BRAND_FILES = "imports/brands_files";
        public static final String EANS_FILES = "imports/eans_files";
        public static final String FILTERS_FILES = "imports/filters_files";
        public static final String MASTER_CATEGORIES_FILES = "imports/master_categories_files";
        public static final String MASTER_CATEGORY_ATTRIBUTES_FILES = "imports/master_category_attributes_files";
        public static final String OFFERS_FILES = "imports/offers_files";
        public static final String OFFERS_STOCKS_FILES = "imports/offers_stocks_files";
        public static final String PAGE_METAS_FILES = "imports/page_metas_files";
        public static final String PRICES_FILES = "imports/prices_files";
        public static final String PRODUCTS_IMAGES_ARCHIVES = "imports/products_images_archives";
        public static final String PRODUCTS_FILES = "imports/products_files";
        public static final String TAXONS_FILES = "imports/taxons_files";
        public static final String TAXONS_IMAGES_FILES = "imports/taxons_images_files";
    }

    public static final class LineItems {
        public static final String BY_ID = "line_items/{lineItemId}";
    }

    public static final class Next {
        public static final String APP_CONFIG = "next/app_config";
        public static final String DYNAMIC_ROUTE_TYPE = "next/dynamic_route_type";
        public static final String REFERRAL_PROGRAM = "next/referral_program";
        public static final String RETAILER_PROMO_CARDS = "next/retailer_promo_cards";
        public static final String RETAILER_WELCOME_BANNER = "next/retailer_welcome_banner";
        public static final String SESSION = "next/session";
        public static final String TRACKERS = "next/trackers";

        public static final class Page {
            public static final String SERVER = "next/page/server";
            public static final String FOOTER = "next/page/footer";
            public static final String BROWSER_HEAD = "next/page/browser_head";
            public static final String FLASHES = "next/page/flashes";
        }

        public static final class Seo {
            public static final class Products {
                public static final String PERMALINK = "next/seo/products/{permalink}";
                public static final String REDIRECT_PARAMS = "next/seo/products/{permalink}/redirect_params";
            }
        }
    }

    public static final class MarketingSamples {
        public static final String BY_ID = "marketing_samples/{sampleId}";
    }

    public static final class Offers {
        public static final String UUID = "offers/{offerUuid}";
    }

    public static final class OperationalZones {
        public static final String ID = "operational_zones/{operationalZoneId}";
    }

    public static final class Orders {
        public static final String NUMBER = "orders/{orderNumber}";

        public static final class Number {
            public static final String COMPENSATIONS = "orders/{orderNumber}/compensations";
            public static final String LINE_ITEMS = "orders/{orderNumber}/line_items";
            public static final String MERGE_STATUS = "orders/{orderNumber}/merge_status";
            public static final String PAYMENTS = "orders/{orderNumber}/payments";
            public static final String PAYMENT_TOOLS = "orders/{orderNumber}/payment_tools";
            public static final String SHIPMENT = "orders/{orderNumber}/shipments/{shipmentNumber}";

            public static final class Compensations {
                public static final String NEW = "orders/{orderNumber}/compensations/new";
                public static final String ID = "orders/{orderNumber}/compensations/{compensationId}";
            }

            public static final class Payments {
                public static final String PURCHASE = "orders/{orderNumber}/payments/{paymentId}/purchase";
                public static final String ID = "orders/{orderNumber}/payments/{paymentId}";
            }
        }
    }

    public static final class PhoneConfirmations {
        public static final String BY_NUMBER = "phone_confirmations/{phoneNumber}";
    }

    public static final class Products {
        public static final String OFFERS = "products/{permalink}/offers";
    }

    public static final class PromotionCards {
        public static final String ID = "promotion_cards/{promotionCardId}";
        public static final String UPDATE_POSITIONS = "promotion_cards/update_positions";
    }

    public static final class Promotions {
        public static final String FREE_DELIVERY = "promotions/free_delivery";
    }

    public static final class Proxy {
        public static final class Eta {
            public static final class Retailers {
                public static final String ID = "proxy/eta/retailers/{retailerId}";
            }
        }
    }

    public static final class Retailers {
        public static final String EANS = "retailers/{retailerId}/eans";
        public static final String ID = "retailers/{retailerId}";
        public static final String SHIPPING_POLICIES = "retailers/{slug}/shipping_policies";
        public static final String SLUG = "retailers/{slug}";
        public static final String STORES = "retailers/{retailerId}/stores";
    }

    public static final class Shipments {
        public static final String NUMBER = "shipments/{shipmentNumber}";
        public static final String OFFERS = "shipments/{shipmentNumber}/offers";
        public static final String SHIPPING_RATES = "shipments/{shipmentNumber}/shipping_rates?{date}";

        public static final class Products {
            public static final String PREREPLACEMENTS = "shipments/{shipmentNumber}/products/{productSku}/prereplacements";
        }
    }

    public static final class ShippingMethods {
        public static final String ID = "shipping_methods/{id}";

        public static final class MarketingPricers {
            public static final String MARKETING_PRICERS = "shipping_methods/{methodId}/marketing_pricers";
            public static final String RULE_ID = "shipping_methods/marketing_pricers/{ruleId}";
        }

        public static final class NominalPricers {
            public static final String NOMINAL_COST_PRICERS = "shipping_methods/{methodId}/nominal_cost_pricers";
            public static final String RULE_ID = "shipping_methods/nominal_cost_pricers/{ruleId}";
        }

        public static final class Pricers {
            public static final String CALCULATORS = "shipping_methods/pricers/{ruleId}/calculators";
            public static final String CALCULATOR_TYPES = "shipping_methods/pricers/calculator_types";
            public static final String RULES = "shipping_methods/pricers/{ruleId}/rules";
            public static final String RULE_TYPES = "shipping_methods/pricers/rule_types";

            public static final class Calculators {
                public static final String RULE_ID = "shipping_methods/pricers/calculators/{ruleId}";
            }

            public static final class Rules {
                public static final String RULE_ID = "shipping_methods/pricers/rules/{ruleId}";
            }
        }
    }

    public static final class ShippingPolicies {
        public static final String ID = "shipping_policies/{id}";
        public static final String SHIPPING_POLICY_RULES = "shipping_policies/{id}/shipping_policy_rules";

        public static final class ShippingPolicyRules {
            public static final String ID = "shipping_policies/{id}/shipping_policy_rules/{rulesId}";
        }
    }

    public static final class Shoppers {
        public static final String MARKETING_SAMPLE_ITEMS = "shoppers/marketing_sample_items?shipment[uuid]={shipmentUuid}";
        public static final String ORDER_AVAILABLE_PAYMENT_TOOLS = "shoppers/order_available_payment_tools?order_id={orderNumber}";

        public static class Events {
            public static final String ASSEMBLY_APPROVED = "shoppers/events/assembly_approved";
            public static final String ASSEMBLY_CANCELLED = "shoppers/events/assembly_cancelled";
            public static final String ASSEMBLY_CREATED = "shoppers/events/assembly_created";
            public static final String ASSEMBLY_DESTROYED = "shoppers/events/assembly_destroyed";
            public static final String ASSEMBLY_PURCHASED = "shoppers/events/assembly_purchased";
            public static final String ASSEMBLY_SHIPPED = "shoppers/events/assembly_shipped";
            public static final String ASSEMBLY_SHIPPING_STARTED = "shoppers/events/assembly_shipping_started";
            public static final String ASSEMBLY_SHIPPING_STOPPED = "shoppers/events/assembly_shipping_stopped";
            public static final String ASSEMBLY_UPDATED = "shoppers/events/assembly_updated";
            public static final String LINE_ITEM_СANCELLED = "shoppers/events/line_item_cancelled";
            public static final String LINE_ITEM_СREATED = "shoppers/events/line_item_created";
            public static final String LINE_ITEM_REPLACED = "shoppers/events/line_item_replaced";
            public static final String LINE_ITEM_RESTORED = "shoppers/events/line_item_restored";
            public static final String LINE_ITEM_RETURNED = "shoppers/events/line_item_returned";
            public static final String LINE_ITEM_UPDATED = "shoppers/events/line_item_updated";
            public static final String ORDER_PAYMENT_TOOL_UPDATED = "shoppers/events/order_payment_tool_updated";
            public static final String RECEIPT_CREATED = "shoppers/events/receipt_created";
            public static final String STORE_HEALTHCHECK = "shoppers/events/store_healthcheck";
        }
    }

    public static final class Stores {
        public static final String NEXT_DELIVERIES = "stores/{storeId}/next_deliveries";
        public static final String OFFERS = "stores/{storeUuid}/offers?q[name]={offerName}&q[retailer_sku]={offerRetailerSku}";
        public static final String SEARCH_SUGGESTIONS = "stores/{storeId}/search_suggestions";
        public static final String UUID = "stores/{storeUuid}";
        public static final String SCHEDULE = "stores/{storeUuid}/store_schedule";
        public static final String ZONES = "stores/{storeId}/zones";
        public static final String STORE_ZONES = "stores/{storeId}/zones/{storeZoneId}";
        public static final String ZONE_FILES = "stores/{storeId}/zone_files";

        public static final class DeliveryWindows {
            public static final String BY_DATE = "stores/{storeId}/delivery_windows?date={date}";
            public static final String GENERATE = "stores/{storeId}/delivery_windows/generate";
        }

        public static final class Offers {
            public static final String SEARCH = "stores/{storeId}/offers/search";
        }

        public static final class Products {
            public static final String BY_PERMALINK = "stores/{storeId}/products/{permalink}";
        }
    }


    public static final class User {
        public static final String COMPANIES = "user/companies";
        public static final String COMPANIES_EMPLOYEES = "user/company_employees?company_id={companyID}";

        public static final class Company {
            public static final String BY_ID = "user/companies/{companyID}";
            public static final String EMPLOYEES = "user/companies/{companyID}/employees?per_page=10&page=1";
            public static final String MANAGER = "user/companies/{companyID}/manager";
            public static final String PAYMENT_ACCOUNT = "user/companies/{companyID}/payment_account";

            public static final class PaymentAccount {
                public static final String REFRESH = "user/companies/{companyID}/payment_account/refresh";
            }
        }
    }

    public static final class Users {

        public static final class Shipments {
            public static final String BY_NUMBER = "users/{userId}/shipments/{shipmentNumber}";
        }
    }
}
