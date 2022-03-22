package ru.instamart.api.request.v1;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;

public class NextV1Request extends ApiV1RequestBase {

    public static class AppConfig {
        @Step("{method} /" + ApiV1Endpoints.Next.APP_CONFIG)
        public static Response GET() {
            return givenWithSpec()
                    .get(ApiV1Endpoints.Next.APP_CONFIG);
        }
    }

    public static class DynamicRouteType {
        @Step("{method} /" + ApiV1Endpoints.Next.DYNAMIC_ROUTE_TYPE)
        public static Response GET(String path) {
            return givenWithSpec()
                    .queryParam("path", path)
                    .get(ApiV1Endpoints.Next.DYNAMIC_ROUTE_TYPE);
        }
    }

    public static class Server {
        @Step("{method} /" + ApiV1Endpoints.Next.Page.SERVER)
        public static Response GET(String path) {
            return givenWithSpec()
                    .queryParam("path", path)
                    .get(ApiV1Endpoints.Next.Page.SERVER);
        }
    }

    public static class Footer {
        @Step("{method} /" + ApiV1Endpoints.Next.Page.FOOTER)
        public static Response GET() {
            return givenWithSpec()
                    .get(ApiV1Endpoints.Next.Page.FOOTER);
        }
    }

    public static class BrowserHead {
        @Step("{method} /" + ApiV1Endpoints.Next.Page.BROWSER_HEAD)
        public static Response GET() {
            return givenWithSpec()
                    .get(ApiV1Endpoints.Next.Page.BROWSER_HEAD);
        }
    }

    public static class Flashes {
        @Step("{method} /" + ApiV1Endpoints.Next.Page.FLASHES)
        public static Response GET() {
            return givenWithSpec()
                    .get(ApiV1Endpoints.Next.Page.FLASHES);
        }
    }

    public static class ReferralProgram {
        @Step("{method} /" + ApiV1Endpoints.Next.REFERRAL_PROGRAM)
        public static Response GET(String promocode) {
            return givenWithSpec()
                    .queryParam("promocode", promocode)
                    .get(ApiV1Endpoints.Next.REFERRAL_PROGRAM);
        }
    }

    public static class RetailerPromoCards {
        @Step("{method} /" + ApiV1Endpoints.Next.RETAILER_PROMO_CARDS)
        public static Response GET(Long retailerId) {
            return givenWithSpec()
                    .queryParam("retailer_id", retailerId)
                    .get(ApiV1Endpoints.Next.RETAILER_PROMO_CARDS);
        }
    }

    public static class RetailerWelcomeBanners {
        @Step("{method} /" + ApiV1Endpoints.Next.RETAILER_WELCOME_BANNER)
        public static Response GET(Long retailerId, String welcomeBannerSlug) {
            return givenWithSpec()
                    .queryParam("retailer_id", retailerId)
                    .queryParam("wb", welcomeBannerSlug)
                    .get(ApiV1Endpoints.Next.RETAILER_WELCOME_BANNER);
        }
    }

    public static class Permalink {
        @Step("{method} /" + ApiV1Endpoints.Next.Seo.Products.PERMALINK)
        public static Response GET(String permalink) {
            return givenWithSpec()
                    .get(ApiV1Endpoints.Next.Seo.Products.PERMALINK, permalink);
        }
    }

    public static class RedirectParams {
        @Step("{method} /" + ApiV1Endpoints.Next.Seo.Products.REDIRECT_PARAMS)
        public static Response GET(String permalink) {
            return givenWithSpec()
                    .get(ApiV1Endpoints.Next.Seo.Products.REDIRECT_PARAMS, permalink);
        }
    }

    public static class Session {
        @Step("{method} /" + ApiV1Endpoints.Next.SESSION)
        public static Response GET(String path) {
            return givenWithSpec()
                    .queryParam("path", path)
                    .get(ApiV1Endpoints.Next.SESSION);
        }
    }

    public static class Trackers {
        @Step("{method} /" + ApiV1Endpoints.Next.TRACKERS)
        public static Response GET() {
            return givenWithSpec()
                    .get(ApiV1Endpoints.Next.TRACKERS);
        }
    }
}
