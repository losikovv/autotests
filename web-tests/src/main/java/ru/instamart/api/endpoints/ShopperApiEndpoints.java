package instamart.api.endpoints;

public class ShopperApiEndpoints {

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
        public static final String id = "assemblies/{id}";
        public static final String items = "assemblies/{assemblyId}/items/{itemId}";
    }

    public static class AssemblyItems {
        public static final String prereplacements = "assembly_items/{id}/prereplacements";
    }

    public static class Shipments {
        public static final String id = "shipments/{id}";
        public static final String stocks = "shipments/{id}/stocks";
    }

    public static class Stores {
        public static final String offers = "stores/{id}/offers?q={q}";
    }

    public static class Helpdesk {
        public static final String tickets = "helpdesk/tickets?shipment_id={id}";
    }
}
