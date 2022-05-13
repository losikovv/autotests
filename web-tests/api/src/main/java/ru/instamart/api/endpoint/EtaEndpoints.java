package ru.instamart.api.endpoint;

public class EtaEndpoints {

    public static final class Service {
        public static final String PARAMETERS = "v1/service/parameters";
    }

    public static final class Retailers {
        public static final String PARAMETERS = "v1/retailers/{retailerId}/parameters";
    }

    public static final class Stores {
        public static final String PARAMETERS = "v1/stores/{storeId}/parameters";
    }
}
