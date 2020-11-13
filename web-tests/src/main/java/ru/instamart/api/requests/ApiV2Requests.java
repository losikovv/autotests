package instamart.api.requests;

import instamart.api.endpoints.ApiV2EndPoints;
import instamart.api.enums.v2.AuthProvider;
import instamart.api.enums.v2.OrderStatus;
import instamart.api.objects.v2.Address;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * МЕТОДЫ ЗАПРОСОВ REST API V2
 */
@SuppressWarnings("unchecked")
public class ApiV2Requests extends InstamartRequestsBase {

    public static class Users {
        /**
         * Регистрация
         */
        @Step("{method} /" + ApiV2EndPoints.USERS)
        public static Response POST(
                String email,
                String firstName,
                String lastName,
                String password) {
            Map<String, Object> data = new HashMap<>();
            data.put("user[email]", email);
            data.put("user[first_name]", firstName);
            data.put("user[last_name]", lastName);
            data.put("user[password]", password);

            System.out.println();

            return givenCatch()
                    .log()
                    .params()
                    .formParams(data)
                    .post(ApiV2EndPoints.USERS);
        }
    }
    public static class Sessions {
        /**
         * Авторизация
         */
        @Step("{method} /" + ApiV2EndPoints.SESSIONS)
        public static Response POST(String email, String password) {
            return givenCatch()
                    .auth()
                    .preemptive()
                    .basic(email, password)
                    .header("Client-Id",
                            "InstamartApp")
                    .post(ApiV2EndPoints.SESSIONS);
        }
    }
    public static class Retailers {
        /**
         * Получаем список всех доступных ритейлеров
         */
        @Step("{method} /" + ApiV2EndPoints.RETAILERS)
        public static Response GET() {
            return givenCatch().get(ApiV2EndPoints.RETAILERS);
        }
    }
    public static class Stores {
        /**
         * Получаем список доступных магазинов по координатам
         */
        @Step("{method} /" + ApiV2EndPoints.Stores.COORDINATES)
        public static Response GET(double lat, double lon) {
            return givenCatch().get(ApiV2EndPoints.Stores.COORDINATES, lat, lon);
        }

        /**
         * Получаем данные о конкретном магазине
         */
        @Step("{method} /" + ApiV2EndPoints.Stores.SID)
        public static Response GET(int sid) {
            return givenCatch().get(ApiV2EndPoints.Stores.SID, sid);
        }

        public static class PromotionCards {
            /**
             * Получаем промоакции в магазине
             */
            @Step
            public static Response GET(int sid) {
                return givenCatch().get(ApiV2EndPoints.Stores.PROMOTION_CARDS, sid);
            }
        }
    }
    public static class Searches {
        public static class Suggestions {
            /**
             * Получение поисковых подсказок
             */
            @Step("{method} /" + ApiV2EndPoints.Searches.SUGGESTIONS)
            public static Response GET(int sid, String query) {
                return givenCatch().get(ApiV2EndPoints.Searches.SUGGESTIONS, sid, query);
            }
        }
    }
    public static class Departments {
        /**
         * Получение продуктов в выбранном магазине
         */
        @Step("{method} /" + ApiV2EndPoints.DEPARTMENTS)
        public static Response GET(int sid, int numberOfProductsFromEachDepartment) {
            return givenCatch().get(ApiV2EndPoints.DEPARTMENTS, sid, numberOfProductsFromEachDepartment);
        }
    }
    public static class Taxons {
        /**
         * Получение таксонов в выбранном магазине
         */
        @Step("{method} /" + ApiV2EndPoints.TAXONS)
        public static Response GET(int sid) {
            return givenCatch().get(ApiV2EndPoints.TAXONS, sid);
        }

        /**
         * Получение конкретного таксона в выбранном магазине
         */
        @Step("{method} /" + ApiV2EndPoints.Taxons.ID)
        public static Response GET(int taxonId, int sid) {
            return givenCatch().get(ApiV2EndPoints.Taxons.ID, taxonId, sid);
        }
    }
    public static class Products {
        /**
         * Получить продукты
         */
        @Step("{method} /" + ApiV2EndPoints.PRODUCTS)
        public static Response GET(int sid, String query) {
            return givenCatch().get(ApiV2EndPoints.PRODUCTS, sid, query);
        }

        /**
         * Получить инфо о продукте
         */
        @Step("{method} /" + ApiV2EndPoints.Products.ID)
        public static Response GET(long productId) {
            return givenCatch().get(ApiV2EndPoints.Products.ID, productId);
        }
    }
    public static class Orders {
        /**
         * Получаем активные (принят, собирается, в пути) заказы
         */
        public static Response GET(OrderStatus status) {
            return GET(status, 1);
        }

        /**
         * Получаем активные (принят, собирается, в пути) заказы с указанием страницы
         */
        @Step("{method} /" + ApiV2EndPoints.Orders.STATUS)
        public static Response GET(OrderStatus status, int page) {
            return givenCatch()
                    .header("Authorization",
                            "Token token=" + token.get())
                    .get(ApiV2EndPoints.Orders.STATUS, status, page);
        }

        /**
         * Получение заказов
         */
        @Step("{method} /" + ApiV2EndPoints.ORDERS)
        public static Response GET() {
            return givenCatch()
                    .header("Authorization",
                            "Token token=" + token.get())
                    .get(ApiV2EndPoints.ORDERS);
        }

        /**
         * Получение заказа по номеру
         */
        @Step("{method} /" + ApiV2EndPoints.Orders.NUMBER)
        public static Response GET(String orderNumber) {
            return givenCatch()
                    .header("Authorization",
                            "Token token=" + token.get())
                    .get(ApiV2EndPoints.Orders.NUMBER, orderNumber);
        }

        /**
         * Создание заказа (если еще не создан)
         */
        @Step("{method} /" + ApiV2EndPoints.ORDERS)
        public static Response POST() {
            return givenCatch()
                    .header("Authorization",
                            "Token token=" + token.get())
                    .header("Client-Id",
                            "InstamartApp")
                    .post(ApiV2EndPoints.ORDERS);
        }

        /**
         * Применяем необходимые параметры к заказу
         */
        @Step("{method} /" + ApiV2EndPoints.Orders.NUMBER)
        public static Response PUT(//int addressId, //параметр ломает оформление заказа в некоторых магазинах
                                   int replacementPolicyId,
                                   String phoneNumber,
                                   String instructions,
                                   int paymentToolId,
                                   int shipmentId,
                                   int deliveryWindowId,
                                   int shipmentMethodId,
                                   String orderNumber) {
            Map<String, Object> data = new HashMap<>();
            //data.put("order[address_attributes][id]", addressId);
            data.put("order[replacement_policy_id]", replacementPolicyId);
            data.put("order[phone_attributes][value]", phoneNumber);
            data.put("order[address_attributes][instructions]", instructions);
            data.put("order[payment_attributes][payment_tool_id]", paymentToolId);
            data.put("order[shipments_attributes][][id]", shipmentId);
            data.put("order[shipments_attributes][][delivery_window_id]", deliveryWindowId);
            data.put("order[shipments_attributes][][shipping_method_id]", shipmentMethodId);

            return givenCatch()
                    .header("Authorization",
                            "Token token=" + token.get())
                    .log()
                    .params()
                    .formParams(data)
                    .put(ApiV2EndPoints.Orders.NUMBER, orderNumber);
        }

        public static class Shipments {
            /**
             * Удаление всех шипментов у заказа
             */
            @Step("{method} /" + ApiV2EndPoints.Orders.SHIPMENTS)
            public static Response DELETE(String orderNumber) {
                return givenCatch()
                        .header("Authorization",
                                "Token token=" + token.get())
                        .delete(ApiV2EndPoints.Orders.SHIPMENTS, orderNumber);
            }
        }
        public static class LineItems {
            /**
             * Получаем товары в заказе
             */
            @Step("{method} /" + ApiV2EndPoints.Orders.LINE_ITEMS)
            public static Response GET(String orderNumber) {
                return givenCatch()
                        .header("Authorization",
                                "Token token=" + token.get())
                        .get(ApiV2EndPoints.Orders.LINE_ITEMS, orderNumber);
            }
        }
        public static class ShipAddressChange {
            /**
             * Изменение/применение параметров адреса
             */
            @Step("{method} /" + ApiV2EndPoints.Orders.SHIP_ADDRESS_CHANGE)
            public static Response PUT(Address address, String orderNumber) {
                Map<String, Object> data = new HashMap<>();

                if (address.getCity() != null) data.put("ship_address[city]", address.getCity());
                if (address.getStreet() != null) data.put("ship_address[street]", address.getStreet());
                if (address.getBuilding() != null) data.put("ship_address[building]", address.getBuilding());
                if (address.getDoor_phone() != null) data.put("ship_address[door_phone]", address.getDoor_phone());
                if (address.getApartment() != null) data.put("ship_address[apartment]", address.getApartment());
                if (address.getComments() != null) data.put("ship_address[comments]", address.getComments());
                if (address.getFloor() != null) data.put("ship_address[floor]", address.getFloor());
                if (address.getEntrance() != null) data.put("ship_address[entrance]", address.getEntrance());
                if (address.getLat() != null) data.put("ship_address[lat]", address.getLat());
                if (address.getLon() != null) data.put("ship_address[lon]", address.getLon());
                if (address.getFirst_name() != null) data.put("ship_address[first_name]", address.getFirst_name());
                if (address.getLast_name() != null) data.put("ship_address[last_name]", address.getLast_name());
                if (address.getBlock() != null) data.put("ship_address[block]", address.getBlock());

                return givenCatch()
                        .header("Authorization",
                                "Token token=" + token.get())
                        .log()
                        .params()
                        .formParams(data)
                        .put(ApiV2EndPoints.Orders.SHIP_ADDRESS_CHANGE, orderNumber);
            }
        }
        public static class Current {
            /**
             * Получить информацию о текущем заказе
             */
            @Step("{method} /" + ApiV2EndPoints.Orders.CURRENT)
            public static Response GET() {
                return givenCatch()
                        .header("Authorization",
                                "Token token=" + token.get())
                        .get(ApiV2EndPoints.Orders.CURRENT);
            }
        }
        public static class Completion {
            /**
             * Завершаем оформление заказа
             */
            @Step("{method} /" + ApiV2EndPoints.Orders.COMPLETION)
            public static Response POST(String orderNumber) {
                return givenCatch()
                        .header("Authorization",
                                "Token token=" + token.get())
                        .post(ApiV2EndPoints.Orders.COMPLETION, orderNumber);
            }
        }
        public static class Cancellations {
            /**
             * Отменяем заказ по номеру
             */
            @Step("{method} /" + ApiV2EndPoints.Orders.CANCELLATIONS)
            public static Response POST(String orderNumber, String reason) {
                return givenCatch()
                        .header("Authorization",
                                "Token token=" + token.get())
                        .post(ApiV2EndPoints.Orders.CANCELLATIONS, orderNumber, reason);
            }
        }
        public static class Unrated {
            /**
             * Получаем заказы для оценки
             */
            @Step("{method} /" + ApiV2EndPoints.Orders.UNRATED)
            public static Response GET() {
                return givenCatch()
                        .header("Authorization",
                                "Token token=" + token.get())
                        .get(ApiV2EndPoints.Orders.UNRATED);
            }
        }
    }
    public static class Shipments {
        public static class ShippingRates {
            /**
             * Получаем доступные слоты
             */
            @Step("{method} /" + ApiV2EndPoints.Shipments.SHIPPING_RATES)
            public static Response GET(String shipmentNumber) {
                return givenCatch()
                        .header("Authorization",
                                "Token token=" + token.get())
                        .get(ApiV2EndPoints.Shipments.SHIPPING_RATES, shipmentNumber);
            }
        }
    }
    public static class LineItems {
        /**
         * Добавляем товар в корзину
         */
        @Step("{method} /" + ApiV2EndPoints.LINE_ITEMS)
        public static Response POST(long productId, int quantity, String orderNumber) {
            Map<String, Object> data = new HashMap<>();
            data.put("line_item[order_number]", orderNumber);
            data.put("line_item[product_id]", productId);
            data.put("line_item[quantity]", quantity);

            return givenCatch()
                    .header("Authorization",
                            "Token token=" + token.get())
                    .log()
                    .params()
                    .formParams(data)
                    .post(ApiV2EndPoints.LINE_ITEMS);
        }

        /**
         * Удаляем товар из корзины
         */
        @Step("{method} /" + ApiV2EndPoints.LineItems.ID)
        public static Response DELETE(long productId) {
            return givenCatch()
                    .header("Authorization",
                            "Token token=" + token.get())
                    .delete(ApiV2EndPoints.LineItems.ID, productId);
        }
    }
    public static class PaymentTools {
        /**
         * Получаем список способов оплаты для юзера
         */
        @Step("{method} /" + ApiV2EndPoints.PAYMENT_TOOLS)
        public static Response GET() {
            return givenCatch()
                    .header("Authorization",
                            "Token token=" + token.get())
                    .header("Client-Id",
                            "InstamartApp")
                    .header("Client-Ver",
                            "5.0")
                    .get(ApiV2EndPoints.PAYMENT_TOOLS);
        }
    }
    public static class PaymentToolTypes {
    }
    public static class ShippingMethods {
        /**
         * Получаем доступные способы доставки
         */
        @Step("{method} /" + ApiV2EndPoints.SHIPPING_METHODS)
        public static Response GET(int sid) {
            return givenCatch().get(ApiV2EndPoints.SHIPPING_METHODS, sid);
        }
    }
    public static class OnboardingPages {
        /**
         * Получаем экраны онбординга
         */
        @Step("{method} /" + ApiV2EndPoints.ONBOARDING_PAGES)
        public static Response GET() {
            return givenCatch().get(ApiV2EndPoints.ONBOARDING_PAGES);
        }
    }
    public static class FavoritesList {
        public static class Items {
            /**
             * Получаем любимые товары
             */
            @Step("{method} /" + ApiV2EndPoints.FavoritesList.ITEMS)
            public static Response GET(int sid) {
                return givenCatch()
                        .header("Authorization",
                                "Token token=" + token.get())
                        .get(ApiV2EndPoints.FavoritesList.ITEMS, sid);
            }
        }
    }
    public static class Promotions {
        public static class ReferralProgram {
            /**
             * Получаем инфу о реферальной программе
             */
            @Step("{method} /" + ApiV2EndPoints.Promotions.REFERRAL_PROGRAM)
            public static Response GET() {
                return givenCatch().get(ApiV2EndPoints.Promotions.REFERRAL_PROGRAM);
            }
        }
    }
    public static class AuthProviders {
        public static class Sessions {
            /**
             * Авторизация через стороннего провайдера
             */
            @Step("{method} /" + ApiV2EndPoints.AuthProviders.SESSIONS)
            public static Response POST(AuthProvider provider) {
                JSONObject sessionParams = new JSONObject();
                JSONObject requestParams = new JSONObject();
                requestParams.put("session", sessionParams);
                switch (provider) {
                    case METRO:
                    case SBERAPP:
                        sessionParams.put("uid", provider.getSessionUid());
                        sessionParams.put("digest", provider.getSessionDigest());
                        return givenCatch()
                                .body(requestParams)
                                .contentType(ContentType.JSON)
                                .log().body()
                                .post(ApiV2EndPoints.AuthProviders.SESSIONS, provider.getId());
                    case VKONTAKTE:
                    case FACEBOOK:
                        return givenCatch()
                                .param("session[uid]", provider.getSessionUid())
                                .log().params()
                                .post(ApiV2EndPoints.AuthProviders.SESSIONS, provider.getId());
                    default:
                        throw new IllegalStateException("Unexpected value: " + provider.getId());
                }
            }
        }
    }
}
