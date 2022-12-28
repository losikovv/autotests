package ru.instamart.api.endpoint;

public final class SelfFreeEndpoints {

    public class V1 {
        public static final String REGISTRY = "v1/registry";
    }

    public class V3 {
        public static final String UPLOAD = "v3/upload";

        public class Upload {
            public static final String BY_ID = "v3/upload/{id}";
        }

    }

}
