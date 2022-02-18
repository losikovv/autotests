package ru.instamart.api.endpoint;

/**
 * sbermarket.tech/api/ endpoints (без указания версии)
 */
public final class ApiV1Endpoints {

    public static final String COMPANIES = "companies";
    public static final String COMPANY_EMPLOYEES = "company_employees";
    public static final String COMPANY_MANAGERS = "company_managers";
    public static final String COMPANY_PRESENCE = "company_presence?inn={inn}";
    public static final String COMPANY_SALES_CONTRACT = "company_sales_contracts";
    public static final String LEGAL_ENTITY = "legal_entity?inn={inn}";
    public static final String LINE_ITEMS = "line_items";
    public static final String MARKETING_SAMPLES = "marketing_samples";
    public static final String MULTIRETAILER_ORDER = "multiretailer_order";
    public static final String OPERATIONAL_ZONES = "operational_zones";
    public static final String ORDERS = "orders?page={pageNumber}";
    public static final String PHONE_CONFIRMATIONS = "phone_confirmations";
    public static final String RETAILERS = "retailers";
    public static final String RETAILER_POSITIONS = "retailer_positions";
    public static final String SHIPPING_METHODS = "shipping_methods";
    public static final String SHIPPING_POLICIES = "shipping_policies";
    public static final String SHOPPING_CONTEXT = "shopping_context";
    public static final String SHOPPING_SESSION = "shopping_session";
    public static final String STORES = "stores";
    public static final String TOKENS = "tokens";
    public static final String USER_SESSIONS = "user_sessions";

    /**
     * sbermarket.tech/api/admin/ endpoints (с /api/)
     */
    public static final class Admin {
        public static final String APP_CONFIG = "admin/app_config";
        public static final String MAIN_NAVIGATION = "admin/main_navigation";
        public static final String OPERATIONAL_ZONES = "admin/operational_zones";
        public static final String USER_PERMISSIONS = "admin/user_permissions";
        public static final String SHIPMENTS = "admin/shipments.json";

        public static final class Dictionaries {
            public static final String API_CLIENTS = "admin/dictionaries/api_clients";
            public static final String PAYMENT_METHODS = "admin/dictionaries/payment_methods";
            public static final String PAYMENT_STATES = "admin/dictionaries/payment_states";
            public static final String SHIPMENT_COMBINED_STATES = "admin/dictionaries/shipment_combined_states";
            public static final String TENANTS = "admin/dictionaries/tenants";
        }

        public static final class OperationalZones {
            public static final String BY_ID = "admin/operational_zones/{operationalZoneID}";
            public static final String DISPATCH_SETTING = "admin/operational_zones/{operationalZoneID}/dispatch_setting";
        }

        public static final class Shipments {
            public static final String LEFTOVERS = "admin/shipments/{shipmentUUID}/leftovers";
        }
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
            public static final String LINE_ITEMS = "orders/{orderNumber}/line_items";
            public static final String MERGE_STATUS = "orders/{orderNumber}/merge_status";
        }
    }

    public static final class PhoneConfirmations {
        public static final String BY_NUMBER = "phone_confirmations/{phoneNumber}";
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

        public static final String CALCULATORS = "shipping_methods/pricers/{ruleId}/calculators";
        public static final String RULES = "shipping_methods/pricers/{ruleId}/rules";

        public static final class Calculator {
            public static final String RULE_ID = "shipping_methods/pricers/calculators/{ruleId}";
        }

        public static final class MarketingPricers {
            public static final String MARKETING_PRICERS = "shipping_methods/{methodId}/marketing_pricers";
            public static final String RULE_ID = "shipping_methods/marketing_pricers/{ruleId}";
        }

        public static final class NominalPricers {
            public static final String NOMINAL_COST_PRICERS = "shipping_methods/{methodId}/nominal_cost_pricers";
            public static final String RULE_ID = "shipping_methods/nominal_cost_pricers/{ruleId}";
        }

        public static final class Rules {
            public static final String RULE_ID = "shipping_methods/pricers/rules/{ruleId}";
        }
    }

    public static final class ShippingPolicies {
        public static final String ID = "shipping_policies/{id}";

        public static final class ShippingPolicyRules {
            public static final String ID = "shipping_policies/{id}/shipping_policy_rules/{rulesId}";
        }
    }

    public static final class Shoppers {
        public static final String MARKETING_SAMPLE_ITEMS = "shoppers/marketing_sample_items?shipment[uuid]={shipmentUuid}";
        public static final String ORDER_AVAILABLE_PAYMENT_TOOLS = "shoppers/order_available_payment_tools?order_id={orderNumber}";
    }

    public static final class Stores {
        public static final String OFFERS = "stores/{storeUuid}/offers?q[name]={offerName}&q[retailer_sku]={offerRetailerSku}";
        public static final String UUID = "stores/{storeUuid}";

        public static final class StoreId {

            public static final String NEXT_DELIVERIES = "stores/{storeId}/next_deliveries";
            public static final String SEARCH_SUGGESTIONS = "stores/{storeId}/search_suggestions";

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
