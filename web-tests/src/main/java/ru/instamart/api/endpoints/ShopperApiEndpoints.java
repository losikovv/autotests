package instamart.api.endpoints;

public final class ShopperApiEndpoints {
    public static final String shopper = "shopper";
    public static final String sessions = "sessions";
    public static final String assemblies = "assemblies";
    public static final String routes = "routes";
    public static final String mars_token = "mars_token";
    public static final String current_app_version = "current_app_version";
    public static final String cancel_reasons = "cancel_reasons";
    public static final String clarify_reasons = "clarify_reasons";
    public static final String return_reasons = "return_reasons";

    public static class Shopper {
        public static final String shipments = "shopper/shipments";
        public static final String assemblies = "shopper/assemblies";
        public static final String operation_shifts = "shopper/operation_shifts";
    }

    public static class Driver {
        public static final String shipments = "driver/shipments";
    }

    public static class Packer {
        public static final String shipments = "packer/shipments";
        public static final String assemblies = "packer/assemblies";
    }

    public static class Assemblies {
        public static final String id = "assemblies/{assemblyId}";
        public static final String items = "assemblies/{assemblyId}/items";
        public static final String item_by_id = "assemblies/{assemblyId}/items/{itemId}";
        public static final String approve = "assemblies/{assemblyId}/approve";
        public static final String start_payment_verification = "assemblies/{assemblyId}/start_payment_verification";
        public static final String finish_assembling = "assemblies/{assemblyId}/finish_assembling";
        public static final String package_sets = "assemblies/{assemblyId}/package_sets";
        public static final String packer = "assemblies/{assemblyId}/packer";
        public static final String start_purchasing = "assemblies/{assemblyId}/start_purchasing";
        public static final String receipts = "assemblies/{assemblyId}/receipts";
        public static final String start_packaging = "assemblies/{assemblyId}/start_packaging";
        public static final String purchase = "assemblies/{assemblyId}/purchase";
        //todo
        public static final String pause = "assemblies/{assemblyId}/pause"; //patch
        public static final String suspend = "assemblies/{assemblyId}/suspend"; //patch
        public static final String ship = "assemblies/{assemblyId}/ship";
        public static final String trolleys = "assemblies/{assemblyId}/trolleys";
        public static final String approve_need_review_items = "assemblies/{assemblyId}/approve_need_review_items";
    }

    public static class AssemblyItems {
        public static final String approve = "assembly_items/{itemId}/approve";
        public static final String cancellations = "assembly_items/{itemId}/cancellations";
        public static final String replacements = "assembly_items/{itemId}/replacements";
        public static final String prereplacements = "assembly_items/{itemId}/prereplacements";
        public static final String clarifications = "assembly_items/{itemId}/clarifications";
    }

    public static class Replacements {
        public static final String id = "replacements/{replacementId}";
        public static final String additional_items = "replacements/{replacementId}/additional_items";
    }

    public static class Shipments {
        public static final String id = "shipments/{shipmentId}";
        public static final String stocks = "shipments/{shipmentId}/stocks";
        //todo
        public static final String marketing_sample_items = "shipments/{shipmentId}/marketing_sample_items";
    }

    public static class Stores {
        public static final String offers = "stores/{storeId}/offers?q={q}";
    }

    public static class Helpdesk {
        public static final String tickets = "helpdesk/tickets?shipment_id={shipmentId}";
    }
}
