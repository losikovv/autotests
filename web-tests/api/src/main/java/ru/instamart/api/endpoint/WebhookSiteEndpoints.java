package ru.instamart.api.endpoint;

public class WebhookSiteEndpoints {
    public static final String TOKEN = "/token";

    public static final class Token {
        public static final String TOKEN = "/token/{token}";
        public static final String REQUESTS = "/token/{token}/requests";
    }

}
