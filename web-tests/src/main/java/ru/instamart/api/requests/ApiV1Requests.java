package ru.instamart.api.requests;

import ru.instamart.api.endpoints.ApiV1Endpoints;
import ru.instamart.api.objects.v1.ShoppersBackendV1;
import ru.instamart.api.responses.v1.TokensV1Response;
import ru.instamart.core.testdata.UserManager;
import ru.instamart.ui.common.pagesdata.UserData;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;

import java.util.Map;

import static ru.instamart.api.requests.InstamartRequestsBase.givenApiV1;

/**
 * МЕТОДЫ ЗАПРОСОВ SPREE REST API (сайт)
 */
@SuppressWarnings("unchecked")
public class ApiV1Requests {
    private static Map<String, String> cookies;

    public static class UserSessions {
        /**
         * Авторизация в админке
         */
        @Step("{method} /" + ApiV1Endpoints.USER_SESSIONS)
        public static Response POST(String email, String password) {
            JSONObject requestParams = new JSONObject();
            JSONObject userParams = new JSONObject();
            requestParams.put("user", userParams);
            userParams.put("email", email);
            userParams.put("password", password);
            userParams.put("remember_me", true);
            Response response = givenApiV1()
                    .body(requestParams)
                    .contentType(ContentType.JSON)
                    .log().body()
                    .post(ApiV1Endpoints.USER_SESSIONS);
            cookies = response.getCookies();
            return response;
        }

        public static Response POST(UserData userData) {
            return POST(userData.getLogin(), userData.getPassword());
        }
    }

    public static class Tokens {
        /**
         * Получение токенов для админки шоппера
         */
        @Step("{method} /" + ApiV1Endpoints.TOKENS)
        public static Response GET() {
            Response response = givenApiV1()
                    .cookies(cookies)
                    .get(ApiV1Endpoints.TOKENS);
            ShoppersBackendV1 shoppersBackend = response.as(TokensV1Response.class).getShoppersBackend();
            //setToken("Token token=" + shoppersBackend.getClient_jwt() + ", id=" + shoppersBackend.getClient_id());
            return response;
        }
    }

    public static class Retailers {
        @Step("{method} /" + ApiV1Endpoints.RETAILERS)
        public static Response GET() {
            return givenApiV1().get(ApiV1Endpoints.RETAILERS);
        }

        @Step("{method} /" + ApiV1Endpoints.Retailers.ID)
        public static Response GET(int retailerId) {
            return givenApiV1().get(ApiV1Endpoints.Retailers.ID, retailerId);
        }

        public static class Stores {
            @Step("{method} /" + ApiV1Endpoints.Retailers.STORES)
            public static Response GET(int retailerId) {
                return givenApiV1().get(ApiV1Endpoints.Retailers.STORES, retailerId);
            }
        }
        public static class Eans {
            @Step("{method} /" + ApiV1Endpoints.Retailers.EANS)
            public static Response GET(int retailerId) {
                return givenApiV1().get(ApiV1Endpoints.Retailers.EANS, retailerId);
            }
        }
    }

    public static class Stores {
        @Step("{method} /" + ApiV1Endpoints.STORES)
        public static Response GET() {
            return givenApiV1().get(ApiV1Endpoints.STORES);
        }
        @Step("{method} /" + ApiV1Endpoints.Stores.UUID)
        public static Response GET(String storeUuid) {
            return givenApiV1().get(ApiV1Endpoints.Stores.UUID, storeUuid);
        }
        public static class Offers {
            @Step("{method} /" + ApiV1Endpoints.Stores.OFFERS)
            public static Response GET(String storeUuid, String offerName, String offerRetailerSku) {
                return givenApiV1().get(ApiV1Endpoints.Stores.OFFERS, storeUuid, offerName, offerRetailerSku);
            }
        }
    }

    public static class OperationalZones {
        @Step("{method} /" + ApiV1Endpoints.OPERATIONAL_ZONES)
        public static Response GET() {
            return givenApiV1().get(ApiV1Endpoints.OPERATIONAL_ZONES);
        }
        @Step("{method} /" + ApiV1Endpoints.OperationalZones.ID)
        public static Response GET(int operationalZoneId) {
            return givenApiV1().get(ApiV1Endpoints.OperationalZones.ID, operationalZoneId);
        }
    }

    public static class Shoppers {
        public static class MarketingSampleItems {
            @Step("{method} /" + ApiV1Endpoints.Shoppers.MARKETING_SAMPLE_ITEMS)
            public static Response GET(String shipmentUuid) {
                return givenApiV1()
                        .header("X-Spree-Token", UserManager.getDefaultAdmin().getToken())
                        .get(ApiV1Endpoints.Shoppers.MARKETING_SAMPLE_ITEMS, shipmentUuid);
            }
        }
        public static class OrderAvailablePaymentTools {
            @Step("{method} /" + ApiV1Endpoints.Shoppers.ORDER_AVAILABLE_PAYMENT_TOOLS)
            public static Response GET(String orderNumber) {
                return givenApiV1()
                        .header("X-Spree-Token", UserManager.getDefaultAdmin().getToken())
                        .get(ApiV1Endpoints.Shoppers.ORDER_AVAILABLE_PAYMENT_TOOLS, orderNumber);
            }
        }
    }

    public static class Offers {
        @Step("{method} /" + ApiV1Endpoints.Offers.UUID)
        public static Response GET(String offerUuid) {
            return givenApiV1().get(ApiV1Endpoints.Offers.UUID, offerUuid);
        }
    }

    public static class Orders {
        @Step("{method} /" + ApiV1Endpoints.ORDERS)
        public static Response GET() {
            return givenApiV1()
                    .cookies(cookies)
                    .get(ApiV1Endpoints.ORDERS);
        }
        @Step("{method} /" + ApiV1Endpoints.Orders.NUMBER)
        public static Response GET(String orderNumber) {
            return givenApiV1()
                    .cookies(cookies)
                    .get(ApiV1Endpoints.Orders.NUMBER, orderNumber);
        }
    }

    public static class Shipments {
        @Step("{method} /" + ApiV1Endpoints.Shipments.NUMBER)
        public static Response GET(String shipmentNumber) {
            return givenApiV1()
                    .cookies(cookies)
                    .get(ApiV1Endpoints.Shipments.NUMBER, shipmentNumber);
        }
        public static class Products {
            public static class Prereplacements {
                @Step("{method} /" + ApiV1Endpoints.Shipments.Products.PREREPLACEMENTS)
                public static Response GET(String shipmentNumber, long productSku) {
                    return givenApiV1().get(ApiV1Endpoints.Shipments.Products.PREREPLACEMENTS, shipmentNumber, productSku);
                }
            }
        }
        public static class Offers {
            @Step("{method} /" + ApiV1Endpoints.Shipments.OFFERS)
            public static Response GET(String shipmentNumber) {
                return givenApiV1().get(ApiV1Endpoints.Shipments.OFFERS, shipmentNumber);
            }
        }
    }

    public static class LineItems {
        @Step("{method} /" + ApiV1Endpoints.LINE_ITEMS)
        public static Response GET(String shipmentNumber) {
            return givenApiV1()
                    .cookies(cookies)
                    .get(ApiV1Endpoints.LINE_ITEMS, shipmentNumber);
        }
    }
}
