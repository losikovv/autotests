package ru.instamart.api.request.delivery_club;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.DeliveryClubEndpoints;
import ru.instamart.api.model.delivery_club.*;
import ru.instamart.api.request.DeliveryClubRequestBase;
import ru.instamart.utils.Mapper;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class StoresDCRequest extends DeliveryClubRequestBase {

    public static class Stock {
        @Step("{method} /" + DeliveryClubEndpoints.Stores.STOCK)
        public static Response GET(final int sid) {
            return givenWithAuth().get(DeliveryClubEndpoints.Stores.STOCK, sid);
        }
    }

    public static class Slots {
        @Step("{method} /" + DeliveryClubEndpoints.Stores.SLOTS)
        public static Response GET(final int sid) {
            return givenWithAuth().get(DeliveryClubEndpoints.Stores.SLOTS, sid);
        }

        public static class Available {
            @Step("{method} /" + DeliveryClubEndpoints.Stores.Slots.AVAILABLE)
            public static Response GET(final int sid) {
                return givenWithAuth().get(DeliveryClubEndpoints.Stores.Slots.AVAILABLE, sid);
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
            return givenWithAuth()
                    .contentType(ContentType.JSON)
                    .body(requestParams)
                    .post(DeliveryClubEndpoints.Stores.NOTIFICATIONS, sid);
        }
    }

    public static class Zones {
        @Step("{method} /" + DeliveryClubEndpoints.Stores.ZONES)
        public static Response GET(final int sid) {
            return givenWithAuth().get(DeliveryClubEndpoints.Stores.ZONES, sid);
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
                                    .plusDays(1)
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
            final String body = Mapper.INSTANCE.objectToString(order);
            return givenWithAuth()
                    .contentType(ContentType.JSON)
                    .body(body)
                    .post(DeliveryClubEndpoints.Stores.ORDERS, sid);
        }

        @Step("{method} /" + DeliveryClubEndpoints.Stores.Orders.BY_NUMBER)
        public static Response GET(final int sid, final String orderNumber) {
            return givenWithAuth()
                    .get(DeliveryClubEndpoints.Stores.Orders.BY_NUMBER, sid, orderNumber);
        }

        public static class Status {
            @Step("{method} /" + DeliveryClubEndpoints.Stores.Orders.STATUS)
            public static Response PUT(final int sid,
                                       final String orderNumber,
                                       final String status) {
                JSONObject requestParams = new JSONObject();
                requestParams.put("status", status);
                return givenWithAuth()
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
                return givenWithAuth().get(DeliveryClubEndpoints.Stores.Catalog.CATEGORIES, sid);
            }
        }

        public static class Products {
            @Step("{method} /" + DeliveryClubEndpoints.Stores.Catalog.PRODUCTS)
            public static Response GET(final int sid) {
                return givenWithAuth().get(DeliveryClubEndpoints.Stores.Catalog.PRODUCTS, sid);
            }
        }
    }
}
