package instamart.api.endpoints;

public class ShopperApiEndpoints {

    public static final String shopper = "shopper";
    public static final String sessions = "sessions";
    public static final String assemblies = "assemblies";
    public static final String routes = "routes";

    public static class Shoppers {
        public static final String shipments = "shopper/shipments";
        public static final String assemblies = "shopper/assemblies";
        public static final String operation_shifts = "shopper/operation_shifts";
    }

    public static class Assemblies {
        public static final String id = "assemblies/{id}";
        public static final String items = "assemblies/{assemblyId}/items/{itemId}";
    }

    public static class Shipments {
        public static final String id = "shipments/{id}";
    }

    public static class Stores {
        public static final String offers = "stores/{id}/offers?q={q}}";
    }

    public static class Helpdesk {
        public static final String tickets = "helpdesk/tickets?shipment_id={id}";
    }
}
