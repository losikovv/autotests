package ru.instamart.api.endpoint;

public class AuthorizationServiceEndpoints {

    public static class Realm {
        public static final String REALM = "realms";

        public static final String REALM_FULL = "realms/{realm}";

        public static class Policy {
            public static final String POLICY = "realms/{baseRealm}/policy";
        }
    }

    public static class Permissions {
        public static final String PERMISSION = "permissions";
    }

    public static class Authorization {
        public static final String AUTHORIZATION = "authorized-permissions";
    }
}

