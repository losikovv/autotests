package ru.instamart.api.endpoint;

public final class SelfFreeEndpoints {

    public static class V1 {
        public static final String REGISTRY = "v1/registry";
        public static final String FILE = "v1/file";

        public static class Registry {
            public static final String FISCALIZE = "v1/registry/{id}/fiscalize";
            public static final String RECEIPT = "v1/registry/{id}/receipt";
            public static final String CANCELLATION = "v1/registry/{id}/cancellation";

            public static class Payroll {
                public static final String SBER = "v1/registry/{id}/payroll/sber";
            }
        }

        public static class File {
            public static final String BY_ID = "v1/file/{uuid}";
        }
    }

    public static class V2 {
        public static class Registry {
            public static final String ACCEPT = "v2/registry/accept";
        }
    }

    public static class V3 {
        public static final String REGISTRY = "v3/registry";
        public static final String UPLOAD = "v3/upload";

        public static class Upload {
            public static final String BY_ID = "v3/upload/{id}";
        }

        public static class Registry {
            public static final String RECEIPT = "v3/registry/{id}/receipt";
        }

    }

}
