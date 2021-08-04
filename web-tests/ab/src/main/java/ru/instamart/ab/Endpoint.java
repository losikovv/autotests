package ru.instamart.ab;

public final class Endpoint {

    public static final String OBTAIN = "/users/jwt/social/obtain";
    public static final String AUTHORS = "/ab-tests/authors";
    public static final String USER_GROUPS = "/users-groups";
    public static final String AB_TESTS = "/ab-tests?limit=1000&offset=0&status=2";

    private Endpoint() {}
}
