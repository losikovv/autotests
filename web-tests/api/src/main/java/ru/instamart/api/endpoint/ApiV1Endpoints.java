package ru.instamart.api.endpoint;

public final class ApiV1Endpoints {

    public static final String LINE_ITEMS = "line_items?shipment_number={shipmentNumber}";
    public static final String OPERATIONAL_ZONES = "operational_zones";
    public static final String ORDERS = "orders?page={pageNumber}";
    public static final String RETAILERS = "retailers";
    public static final String STORES = "stores";
    public static final String TOKENS = "tokens";
    public static final String USER_SESSIONS = "user_sessions";
    public static final String COMPANY_PRESENCE = "company_presence?inn={inn}";
    public static final String LEGAL_ENTITY = "legal_entity?inn={inn}";
    public static final String COMPANY_SALES_CONTRACT = "company_sales_contracts";
    public static final String COMPANY_EMPLOYEES = "company_employees";
    public static final String COMPANY_MANAGERS = "company_managers";
    public static final String MARKETING_SAMPLES = "marketing_samples";
    public static final String COMPANIES = "companies";
    public static final String RETAILER_POSITION = "retailer_positions";



    public static final class DeliveryWindows {
        public static final String BY_ID = "delivery_windows/{deliveryWindowId}";
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
        public static final String STORES = "retailers/{retailerId}/stores";
        public static final String SLUG = "retailers/{slug}";
    }

    public static final class Shipments {
        public static final String NUMBER = "shipments/{shipmentNumber}";
        public static final String OFFERS = "shipments/{shipmentNumber}/offers";

        public static final class Products {
            public static final String PREREPLACEMENTS = "shipments/{shipmentNumber}/products/{productSku}/prereplacements";
        }
    }

    public static final class Shoppers {
        public static final String MARKETING_SAMPLE_ITEMS = "shoppers/marketing_sample_items?shipment[uuid]={shipmentUuid}";
        public static final String ORDER_AVAILABLE_PAYMENT_TOOLS = "shoppers/order_available_payment_tools?order_id={orderNumber}";
    }

    public static final class Stores {
        public static final String OFFERS = "stores/{storeUuid}/offers?q[name]={offerName}&q[retailer_sku]={offerRetailerSku}";
        public static final String UUID = "stores/{storeUuid}";

        public static final class DeliveryWindows {
            public static final String BY_DATE = "stores/{storeId}/delivery_windows?date={date}";
            public static final String GENERATE = "stores/{storeId}/delivery_windows/generate";
        }

    }

    public static final class User {
        public static final String COMPANIES = "user/companies";
        public static final String COMPANIES_EMPLOYEES = "user/company_employees?company_id={companyID}";

        public static final class Company {
            public static final String BY_ID = "user/companies/{companyID}";
            public static final String MANAGER = "user/companies/{companyID}/manager";
            public static final String EMPLOYEES = "user/companies/{companyID}/employees?per_page=10&page=1";
            public static final String PAYMENT_ACCOUNT = "user/companies/{companyID}/payment_account";

            public static final class PaymentAccount {
                public static final String REFRESH = "user/companies/{companyID}/payment_account/refresh";
            }
        }
    }

    /**Страница компании в админке*/
    public static final class Company {
        public static final String BY_INN = "companies?inn={inn}";
        public static final String BY_ID = "companies/{companyID}";

        public static final class PaymentAccount {
            public static final String REFRESH = "companies/{companyID}/payment_account/refresh";
        }

        public static final class SecurityCode {
            public static final String REFRESH = "companies/{companyID}/security_code/refresh";
        }
    }

    public static final class CompanySalesContracts {
        public static final String ARCHIVE = "company_sales_contracts/{contractID}/archive";
        public static final String BY_ID = "company_sales_contracts/{contractID}";
    }

    public static final class CompanyEmployees {
        public static final String BY_ID = "company_employees/{employeeID}";
    }

    public static final class CompanyManagers {
        public static final String BY_ID = "company_managers/{managerID}";
    }

    public static final class Admin {
        public static final String OPERATIONAL_ZONES = "admin/operational_zones";
    }
}
