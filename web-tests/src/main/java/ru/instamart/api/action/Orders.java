package instamart.api.action;

import instamart.api.endpoints.ApiV2EndPoints;
import instamart.api.enums.v2.OrderStatus;
import instamart.api.objects.v2.Address;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static instamart.api.requests.InstamartRequestsBase.givenWithAuth;

public final class Orders {

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
        return givenWithAuth()
                .get(ApiV2EndPoints.Orders.STATUS, status, page);
    }

    /**
     * Получение заказов
     */
    @Step("{method} /" + ApiV2EndPoints.ORDERS)
    public static Response GET() {
        return givenWithAuth()
                .get(ApiV2EndPoints.ORDERS);
    }

    /**
     * Получение заказа по номеру
     */
    @Step("{method} /" + ApiV2EndPoints.Orders.NUMBER)
    public static Response GET(String orderNumber) {
        return givenWithAuth()
                .get(ApiV2EndPoints.Orders.NUMBER, orderNumber);
    }

    /**
     * Создание заказа (если еще не создан)
     */
    @Step("{method} /" + ApiV2EndPoints.ORDERS)
    public static Response POST() {
        return givenWithAuth()
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

        return givenWithAuth()
                .formParams(data)
                .put(ApiV2EndPoints.Orders.NUMBER, orderNumber);
    }

    public static class Shipments {
        /**
         * Удаление всех шипментов у заказа
         */
        @Step("{method} /" + ApiV2EndPoints.Orders.SHIPMENTS)
        public static Response DELETE(String orderNumber) {
            return givenWithAuth()
                    .delete(ApiV2EndPoints.Orders.SHIPMENTS, orderNumber);
        }
    }
    public static class LineItems {
        /**
         * Получаем товары в заказе
         */
        @Step("{method} /" + ApiV2EndPoints.Orders.LINE_ITEMS)
        public static Response GET(String orderNumber) {
            return givenWithAuth()
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

            return givenWithAuth()
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
            return givenWithAuth()
                    .get(ApiV2EndPoints.Orders.CURRENT);
        }
    }
    public static class Completion {
        /**
         * Завершаем оформление заказа
         */
        @Step("{method} /" + ApiV2EndPoints.Orders.COMPLETION)
        public static Response POST(String orderNumber) {
            return givenWithAuth()
                    .post(ApiV2EndPoints.Orders.COMPLETION, orderNumber);
        }
    }
    public static class Cancellations {
        /**
         * Отменяем заказ по номеру
         */
        @Step("{method} /" + ApiV2EndPoints.Orders.CANCELLATIONS)
        public static Response POST(String orderNumber, String reason) {
            return givenWithAuth()
                    .post(ApiV2EndPoints.Orders.CANCELLATIONS, orderNumber, reason);
        }
    }
    public static class Unrated {
        /**
         * Получаем заказы для оценки
         */
        @Step("{method} /" + ApiV2EndPoints.Orders.UNRATED)
        public static Response GET() {
            return givenWithAuth()
                    .get(ApiV2EndPoints.Orders.UNRATED);
        }
    }
}
