package instamart.api.shopper;

public class ShopperApiEndpoints {
    static final String shopper = "shopper";
    static final String sessions = "sessions";
    static final String assemblies = "assemblies";
    static final String routes = "routes";

    public static class Shoppers {
        static final String shipments = "shopper/shipments";
        static final String assemblies = "shopper/assemblies";
        static final String operation_shifts = "shopper/operation_shifts";
    }

    public static class Assemblies {
        static final String id = "assemblies/{id}";
        static final String items = "assemblies/{assemblyId}/items/{itemId}";
    }

    public static class Shipments {
        static final String id = "shipments/{id}";
    }

    public static class Stores {
        static final String offers = "stores/{id}/offers?q={q}}";
    }

    public static class Helpdesk {
        static final String tickets = "helpdesk/tickets?shipment_id={id}";
    }
}
