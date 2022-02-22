package ru.instamart.api.endpoint;

public final class ShopperAppEndpoints {

    public static final String ASSEMBLIES = "assemblies";
    public static final String CANCEL_REASONS = "cancel_reasons";
    public static final String CLARIFY_REASONS = "clarify_reasons";
    public static final String CURRENT_APP_VERSION = "current_app_version";
    public static final String MARS_TOKEN = "mars_token";
    public static final String NEXT_UNCOMPLETED_ROUTE = "next_uncompleted_route";
    public static final String RETURN_REASONS = "return_reasons";
    public static final String ROUTES = "routes";
    public static final String SESSIONS = "sessions";
    public static final String SHOPPER = "shopper";

    public static class Shopper {
        public static final String ASSEMBLIES = "shopper/assemblies";
        public static final String NOTIFICATIONS = "shopper/notifications";
        public static final String OPERATION_SHIFTS = "shopper/operation_shifts";
        public static final String SHIPMENTS = "shopper/shipments";
        public static final String SHIPMENT = "shopper/shipment";

        public static class Notifications{
            public static final String BY_ID = "shopper/notifications/{id}";
        }
        public static class Shipments{
            public static final String ACTIVE = "shopper/shipments/active";
        }
    }

    public static class ShopperDriver {
        public static class Shipments {
            public static final String ACTIVE = "/shopper_driver/shipments/active";
        }
    }

    public static class Driver {
        public static final String SHIPMENTS = "driver/shipments";
    }

    public static class Packer {
        public static final String ASSEMBLIES = "packer/assemblies";
        public static final String SHIPMENTS = "packer/shipments";
    }

    public static class Assemblies {
        public static final String APPROVE = "assemblies/{assemblyId}/approve";
        public static final String FINISH_ASSEMBLING = "assemblies/{assemblyId}/finish_assembling";
        public static final String ID = "assemblies/{assemblyId}";
        public static final String ITEMS = "assemblies/{assemblyId}/items";
        public static final String LIFEPAY = "assemblies/{assemblyId}/lifepay";
        public static final String PACKAGE_SETS = "assemblies/{assemblyId}/package_sets";
        public static final String PACKER = "assemblies/{assemblyId}/packer";
        public static final String PAUSE = "assemblies/{assemblyId}/pause";
        public static final String PURCHASE = "assemblies/{assemblyId}/purchase";
        public static final String RECEIPTS = "assemblies/{assemblyId}/receipts";
        public static final String SHIP = "assemblies/{assemblyId}/ship";
        public static final String START_PACKAGING = "assemblies/{assemblyId}/start_packaging";
        public static final String START_PAYMENT_VERIFICATION = "assemblies/{assemblyId}/start_payment_verification";
        public static final String START_PURCHASING = "assemblies/{assemblyId}/start_purchasing";
        public static final String SUSPEND = "assemblies/{assemblyId}/suspend";
        //todo
        public static final String APPROVE_NEED_REVIEW_ITEMS = "assemblies/{assemblyId}/approve_need_review_items";
        public static final String TROLLEYS = "assemblies/{assemblyId}/trolleys";

        public static class Items {
            public static final String ID = "assemblies/{assemblyId}/items/{itemId}";
        }
    }

    public static class AssemblyItems {
        public static final String APPROVE = "assembly_items/{itemId}/approve";
        public static final String CANCELLATIONS = "assembly_items/{itemId}/cancellations";
        public static final String CLARIFICATIONS = "assembly_items/{itemId}/clarifications";
        public static final String LOG_NOT_FOUND_EANS = "assembly_items/{itemId}/log_not_found_eans";
        public static final String PREREPLACEMENTS = "assembly_items/{itemId}/prereplacements";
        public static final String REPLACEMENTS = "assembly_items/{itemId}/replacements";
    }

    public static class Replacements {
        public static final String ADDITIONAL_ITEMS = "replacements/{replacementId}/additional_items";
        public static final String ID = "replacements/{replacementId}";
    }

    public static class Shipments {
        public static final String ID = "shipments/{shipmentId}";
        public static final String MARKETING_SAMPLE_ITEMS = "shipments/{shipmentId}/marketing_sample_items";
        public static final String STOCKS = "shipments/{shipmentId}/stocks";
    }

    public static class Stores {
        public static final String OFFERS = "stores/{shopperSid}/offers?q={query}";
    }

    public static class Helpdesk {
        public static final String TICKETS = "helpdesk/tickets";
    }

    public static class Auth {
        public static final String REFRESH = "auth/refresh";

        public static class Partners {
            public static final String CODE = "auth/partners/code";
            public static final String LOGIN = "auth/partners/login";
            public static final String REFRESH = "auth/partners/refresh";
        }

        public static class Testing {
            public static final String OTP = "api/v1/auth/testing/otp";
        }
    }

    public static class Otps {
        public static final String AUTHORIZATIONS = "otps/authorizations";
        public static final String TOKENS = "otps/tokens";

    }

    public static class Orders{
        public static final String IMPORTS = "orders/{orderNumber}/imports";
    }

    public static class Scango{
        public static class Assemblies{
            public static final String CONFIG = "scango/assemblies/{assemblyId}/config";
        }
    }
}
