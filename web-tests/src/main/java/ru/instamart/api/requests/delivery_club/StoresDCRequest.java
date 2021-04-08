package ru.instamart.api.requests.delivery_club;

import ru.instamart.api.endpoints.DeliveryClubEndpoints;
import ru.instamart.api.helpers.InstamartApiHelper;
import ru.instamart.api.objects.delivery_club.*;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static ru.instamart.api.requests.InstamartRequestsBase.givenWithAuthDeliveryClub;

public class StoresDCRequest {

    public static class Stock {
        @Step("{method} /" + DeliveryClubEndpoints.Stores.STOCK)
        public static Response GET(final int sid) {
            return givenWithAuthDeliveryClub().get(DeliveryClubEndpoints.Stores.STOCK, sid);
        }
    }

    public static class Slots {
        @Step("{method} /" + DeliveryClubEndpoints.Stores.SLOTS)
        public static Response GET(final int sid) {
            return givenWithAuthDeliveryClub().get(DeliveryClubEndpoints.Stores.SLOTS, sid);
        }

        public static class Available {
            @Step("{method} /" + DeliveryClubEndpoints.Stores.Slots.AVAILABLE)
            public static Response GET(final int sid) {
                return givenWithAuthDeliveryClub().get(DeliveryClubEndpoints.Stores.Slots.AVAILABLE, sid);
            }
        }
    }

    public static class Notifications {
        @Step("{method} /" + DeliveryClubEndpoints.Stores.NOTIFICATIONS)
        public static Response POST(final int sid,
                                    final String orderNumber,
                                    final String notificationType) {
            JSONObject requestParams = new JSONObject();
            requestParams.put("type", notificationType);
            requestParams.put("orderId", orderNumber);
            return givenWithAuthDeliveryClub()
                    .contentType(ContentType.JSON)
                    .body(requestParams)
                    .post(DeliveryClubEndpoints.Stores.NOTIFICATIONS, sid);
        }
    }

    public static class Zones {
        @Step("{method} /" + DeliveryClubEndpoints.Stores.ZONES)
        public static Response GET(final int sid) {
            return givenWithAuthDeliveryClub().get(DeliveryClubEndpoints.Stores.ZONES, sid);
        }
    }

    public static class Orders {
        @Step("{method} /" + DeliveryClubEndpoints.Stores.ORDERS)
        public static Response POST(final int sid, final String slotId, final String productId) {
            OrderDC order = OrderDC
                    .builder()
                    .originalOrderId(UUID.randomUUID().toString())
                    .comment("Test")
                    .customer(CustomerDC
                            .builder()
                            .name("Test")
                            .phone("+79876543210")
                            .build())
                    .delivery(DeliveryDC
                            .builder()
                            .expectedDateTime(LocalDateTime
                                    .now()
                                    .plus(1, ChronoUnit.DAYS)
                                    .truncatedTo(ChronoUnit.SECONDS)
                                    .toString() + "+03:00")
                            .address(AddressDC
                                    .builder()
                                    .city(CityDC
                                            .builder()
                                            .name("Москва")
                                            .code("1")
                                            .build())
                                    .street(StreetDC
                                            .builder()
                                            .name("Проспект мира")
                                            .code("1")
                                            .build())
                                    .coordinates(CoordinatesDC
                                            .builder()
                                            .latitude("55.844041")
                                            .longitude("37.66265")
                                            .build())
                                    .region("1")
                                    .houseNumber("1")
                                    .flatNumber("1")
                                    .entrance("1")
                                    .intercom("1111")
                                    .floor("1")
                                    .comment("Test")
                                    .build())
                            .slotId(slotId)
                            .build())
                    .payment(PaymentDC
                            .builder()
                            .type("online")
                            .requiredMoneyChange("0")
                            .build())
                    .position(PositionDC
                            .builder()
                            .id(productId)
                            .quantity(5)
                            .price("123")
                            .discountPrice("100")
                            .build())
                    .total(TotalDC
                            .builder()
                            .totalPrice("3000")
                            .discountTotalPrice("2000")
                            .deliveryPrice("199")
                            .build())
                    .build();
            String body = InstamartApiHelper.getJSONFromPOJO(order);
            return givenWithAuthDeliveryClub()
                    .contentType(ContentType.JSON)
                    .body(body)
                    .post(DeliveryClubEndpoints.Stores.ORDERS, sid);
        }

        @Step("{method} /" + DeliveryClubEndpoints.Stores.Orders.BY_NUMBER)
        public static Response GET(final int sid, final String orderNumber) {
            return givenWithAuthDeliveryClub()
                    .get(DeliveryClubEndpoints.Stores.Orders.BY_NUMBER, sid, orderNumber);
        }

        public static class Status {
            @Step("{method} /" + DeliveryClubEndpoints.Stores.Orders.STATUS)
            public static Response PUT(final int sid,
                                       final String orderNumber,
                                       final String status) {
                JSONObject requestParams = new JSONObject();
                requestParams.put("status", status);
                return givenWithAuthDeliveryClub()
                        .contentType(ContentType.JSON)
                        .body(requestParams)
                        .put(DeliveryClubEndpoints.Stores.Orders.STATUS, sid, orderNumber);
            }
        }
    }

    public static class Catalog {
        public static class Categories {
            @Step("{method} /" + DeliveryClubEndpoints.Stores.Catalog.CATEGORIES)
            public static Response GET(final int sid) {
                return givenWithAuthDeliveryClub().get(DeliveryClubEndpoints.Stores.Catalog.CATEGORIES, sid);
            }
        }

        public static class Products {
            @Step("{method} /" + DeliveryClubEndpoints.Stores.Catalog.PRODUCTS)
            public static Response GET(final int sid) {
                return givenWithAuthDeliveryClub().get(DeliveryClubEndpoints.Stores.Catalog.PRODUCTS, sid);
            }
        }
    }
}
