package ru.instamart.api.request.v2;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.enums.v2.OrderStatusV2;
import ru.instamart.api.model.v2.AddressV2;
import ru.instamart.api.request.ApiV2RequestBase;
import ru.instamart.kraken.common.Mapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class OrdersV2Request extends ApiV2RequestBase {

    /**
     * Получаем активные (принят, собирается, в пути) заказы
     */
    public static Response GET(OrderStatusV2 status) {
        return GET(status, 1);
    }

    /**
     * Получаем активные (принят, собирается, в пути) заказы с указанием страницы
     */
    @Step("{method} /" + ApiV2EndPoints.ORDERS)
    public static Response GET(OrderStatusV2 status, int page) {
        return givenWithAuth()
                .queryParam("status", status.getStatus())
                .queryParam("page", page)
                .get(ApiV2EndPoints.ORDERS);
    }

    @Step("{method} /" + ApiV2EndPoints.ORDERS)
    public static Response GET(int page) {
        return givenWithAuth()
                .queryParam("page", page)
                .get(ApiV2EndPoints.ORDERS);
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
    @Step("{method} /" + ApiV2EndPoints.Orders.BY_NUMBER)
    public static Response GET(String orderNumber) {
        return givenWithAuth()
                .get(ApiV2EndPoints.Orders.BY_NUMBER, orderNumber);
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
    @Step("{method} /" + ApiV2EndPoints.Orders.BY_NUMBER)
    public static Response PUT(//int addressId, //параметр ломает оформление заказа в некоторых магазинах
                               int replacementPolicyId,
                               String phoneNumber,
                               String instructions,
                               int paymentToolId,
                               int shipmentId,
                               int deliveryWindowId,
                               int shipmentMethodId,
                               String orderNumber) {
        return PUT(replacementPolicyId, phoneNumber, instructions, paymentToolId, shipmentId, deliveryWindowId, null, shipmentMethodId, orderNumber);
    }

    @Step("{method} /" + ApiV2EndPoints.Orders.BY_NUMBER)
    public static Response PUT(//int addressId, //параметр ломает оформление заказа в некоторых магазинах
                               int replacementPolicyId,
                               String phoneNumber,
                               String instructions,
                               int paymentToolId,
                               int shipmentId,
                               Integer deliveryWindowId,
                               String deliveryWindowKind,
                               int shipmentMethodId,
                               String orderNumber) {
        Map<String, Object> data = new HashMap<>();
        //data.put("order[address_attributes][id]", addressId);
        data.put("order[replacement_policy_id]", replacementPolicyId);
        data.put("order[phone_attributes][value]", phoneNumber);
        data.put("order[address_attributes][instructions]", instructions);
        data.put("order[address_attributes][apartment]", "1");
        data.put("order[payment_attributes][payment_tool_id]", paymentToolId);
        data.put("order[shipments_attributes][][id]", shipmentId);
        data.put("order[shipments_attributes][][delivery_window_id]", deliveryWindowId);
        if(Objects.nonNull(deliveryWindowKind)) {
            data.put("order[shipments_attributes][][delivery_window_kind]", deliveryWindowKind);
        }
        data.put("order[shipments_attributes][][shipping_method_id]", shipmentMethodId);

        return givenWithAuth()
                .formParams(data)
                .put(ApiV2EndPoints.Orders.BY_NUMBER, orderNumber);
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
        public static Response PUT(AddressV2 address, String orderNumber) {
            Map<String, Object> data = new HashMap<>();

            if (address.getCity() != null) data.put("ship_address[city]", address.getCity());
            if (address.getStreet() != null) data.put("ship_address[street]", address.getStreet());
            if (address.getBuilding() != null) data.put("ship_address[building]", address.getBuilding());
            if (address.getDoorPhone() != null) data.put("ship_address[door_phone]", address.getDoorPhone());
            if (address.getApartment() != null) data.put("ship_address[apartment]", address.getApartment());
            if (address.getComments() != null) data.put("ship_address[comments]", address.getComments());
            if (address.getFloor() != null) data.put("ship_address[floor]", address.getFloor());
            if (address.getEntrance() != null) data.put("ship_address[entrance]", address.getEntrance());
            if (address.getLat() != null) data.put("ship_address[lat]", address.getLat());
            if (address.getLon() != null) data.put("ship_address[lon]", address.getLon());
            if (address.getFirstName() != null) data.put("ship_address[first_name]", address.getFirstName());
            if (address.getLastName() != null) data.put("ship_address[last_name]", address.getLastName());
            if (address.getBlock() != null) data.put("ship_address[block]", address.getBlock());

            return givenWithAuth()
                    .formParams(data)
                    .put(ApiV2EndPoints.Orders.SHIP_ADDRESS_CHANGE, orderNumber);
        }

        @Step("{method} /" + ApiV2EndPoints.Orders.SHIP_ADDRESS_CHANGE)
        public static Response PUT(final String token, final AddressV2 address, final String orderNumber) {
            Map<String, Object> data = new HashMap<>();

            if (address.getCity() != null) data.put("ship_address[city]", address.getCity());
            if (address.getStreet() != null) data.put("ship_address[street]", address.getStreet());
            if (address.getBuilding() != null) data.put("ship_address[building]", address.getBuilding());
            if (address.getDoorPhone() != null) data.put("ship_address[door_phone]", address.getDoorPhone());
            if (address.getApartment() != null) data.put("ship_address[apartment]", address.getApartment());
            if (address.getComments() != null) data.put("ship_address[comments]", address.getComments());
            if (address.getFloor() != null) data.put("ship_address[floor]", address.getFloor());
            if (address.getEntrance() != null) data.put("ship_address[entrance]", address.getEntrance());
            if (address.getLat() != null) data.put("ship_address[lat]", address.getLat());
            if (address.getLon() != null) data.put("ship_address[lon]", address.getLon());
            if (address.getFirstName() != null) data.put("ship_address[first_name]", address.getFirstName());
            if (address.getLastName() != null) data.put("ship_address[last_name]", address.getLastName());
            if (address.getBlock() != null) data.put("ship_address[block]", address.getBlock());

            return givenCustomToken(token)
                    .formParams(data)
                    .put(ApiV2EndPoints.Orders.SHIP_ADDRESS_CHANGE, orderNumber);
        }

        @Step("{method} /" + ApiV2EndPoints.Orders.SHIP_ADDRESS_CHANGE)
        public static Response GET(final String orderNumber) {
            return givenWithAuth()
                    .get(ApiV2EndPoints.Orders.SHIP_ADDRESS_CHANGE, orderNumber);
        }

        @Step("{method} /" + ApiV2EndPoints.Orders.SHIP_ADDRESS_CHANGE)
        public static Response GET(final String authToken, final String orderNumber) {
            return givenCustomToken(authToken)
                    .get(ApiV2EndPoints.Orders.SHIP_ADDRESS_CHANGE, orderNumber);
        }
    }

    public static class ShipAddress {

        @Step("{method} /" + ApiV2EndPoints.Orders.SHIP_ADDRESS)
        public static Response GET(final String authToken, final String orderNumber) {
            return givenCustomToken(authToken)
                    .get(ApiV2EndPoints.Orders.SHIP_ADDRESS, orderNumber);
        }

        @Step("{method} /" + ApiV2EndPoints.Orders.SHIP_ADDRESS)
        public static Response GET(final String orderNumber) {
            return givenWithAuth()
                    .get(ApiV2EndPoints.Orders.SHIP_ADDRESS, orderNumber);
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

        /**
         * Заменить текущий заказ на другой
         */
        @Step("{method} /" + ApiV2EndPoints.Orders.CURRENT)
        public static Response PUT(String uuid) {
            return givenWithAuth()
                    .queryParam("order_uuid", uuid)
                    .put(ApiV2EndPoints.Orders.CURRENT);
        }
    }

    public static class Company {

        @Step("{method} /" + ApiV2EndPoints.Orders.COMPANY)
        public static Response PATCH(final String orderNumber, final int companyId) {
            return givenWithAuth()
                    .queryParam("company_id", companyId)
                    .patch(ApiV2EndPoints.Orders.COMPANY, orderNumber);
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
                    .queryParam("reason", reason)
                    .post(ApiV2EndPoints.Orders.CANCELLATIONS, orderNumber);
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

    public static class Previous {

        @Step("{method} /" + ApiV2EndPoints.Orders.PREVIOUS)
        public static Response GET() {
            return givenWithAuth()
                    .get(ApiV2EndPoints.Orders.PREVIOUS);
        }
    }

    public static class Promotions {

        @Step("{method} /" + ApiV2EndPoints.Orders.PROMOTIONS)
        public static Response POST(final String orderNumber, final String promoCode) {
            return givenWithAuth()
                    .formParam("promotion_code", promoCode)
                    .post(ApiV2EndPoints.Orders.PROMOTIONS, orderNumber);
        }

        @Step("{method} /" + ApiV2EndPoints.Orders.PROMOTION_CODE)
        public static Response DELETE(final String orderNumber, final String promoCode) {
            return givenWithAuth()
                    .delete(ApiV2EndPoints.Orders.PROMOTION_CODE, orderNumber, promoCode);
        }
    }

    public static class Instacoins {

        @Step("{method} /" + ApiV2EndPoints.Orders.PROMOTIONS)
        public static Response POST(final String orderNumber, final String instacoinsValue) {
            return givenWithAuth()
                    .formParam("instacoins_value", instacoinsValue)
                    .post(ApiV2EndPoints.Orders.PROMOTIONS, orderNumber);
        }
    }

    public static class PromotionLimit {
        @Step("{method} /" + ApiV2EndPoints.Orders.PROMOTION_LIMITS)
        public static Response GET(String orderNumber) {
            return givenWithAuth()
                    .get(ApiV2EndPoints.Orders.PROMOTION_LIMITS, orderNumber);
        }
    }

    public static class LineItemCancellations {
        @Step("{method} /" + ApiV2EndPoints.Orders.LINE_ITEM_CANCELLATIONS)
        public static Response GET(String orderNumber) {
            return givenWithAuth()
                    .get(ApiV2EndPoints.Orders.LINE_ITEM_CANCELLATIONS, orderNumber);
        }
    }

    public static class LineItemReplacements {
        @Step("{method} /" + ApiV2EndPoints.Orders.LINE_ITEM_REPLACEMENTS)
        public static Response GET(String orderNumber) {
            return givenWithAuth()
                    .get(ApiV2EndPoints.Orders.LINE_ITEM_REPLACEMENTS, orderNumber);
        }
    }

    public static class PaymentToolsWithTypes {
        @Step("{method} /" + ApiV2EndPoints.Orders.PAYMENT_TOOLS_WITH_TYPES)
        public static Response GET(String orderNumber) {
            return givenWithAuth()
                    .get(ApiV2EndPoints.Orders.PAYMENT_TOOLS_WITH_TYPES, orderNumber);
        }
    }

    public static class TransferMethod {

        @Step("{method} /" + ApiV2EndPoints.Orders.TRANSFER_METHOD)
        public static Response PUT(TransferMethodParams params, String orderNumber) {
            return givenWithAuth()
                    .queryParams(Mapper.INSTANCE.objectToMap(params))
                    .put(ApiV2EndPoints.Orders.TRANSFER_METHOD, orderNumber);
        }

        public static class Analyze {
            @Step("{method} /" + ApiV2EndPoints.Orders.TransferMethod.ANALYZE)
            public static Response GET(TransferMethodParams params, String orderNumber) {
                return givenWithAuth()
                        .queryParams(Mapper.INSTANCE.objectToMap(params))
                        .get(ApiV2EndPoints.Orders.TransferMethod.ANALYZE, orderNumber);
            }
        }

        public static class Losses {
            @Step("{method} /" + ApiV2EndPoints.Orders.TransferMethod.LOSSES)
            public static Response GET(TransferMethodParams params, String orderNumber) {
                return givenWithAuth()
                        .queryParams(Mapper.INSTANCE.objectToMap(params))
                        .get(ApiV2EndPoints.Orders.TransferMethod.LOSSES, orderNumber);
            }
        }
    }

    public static class SpasiboInfo {
        @Step("{method} /" + ApiV2EndPoints.Orders.SPASIBO_INFO)
        public static Response GET(String orderNumber) {
            return givenWithAuth()
                    .get(ApiV2EndPoints.Orders.SPASIBO_INFO, orderNumber);
        }
    }

    public static class MergeStatus {
        @Step("{method} /" + ApiV2EndPoints.Orders.MERGE_STATUS)
        public static Response GET(String orderNumber) {
            return givenWithAuth()
                    .get(ApiV2EndPoints.Orders.MERGE_STATUS, orderNumber);
        }
    }

    public static class ReviewIssues {

        @Step("{method} /" + ApiV2EndPoints.Orders.REVIEW_ISSUES)
        public static Response GET(String orderNumber) {
            return givenWithAuth()
                    .get(ApiV2EndPoints.Orders.REVIEW_ISSUES, orderNumber);
        }
    }


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class TransferMethodParams {
        @JsonProperty("order_id")
        Integer orderId;
        @JsonProperty("shipping_method_kind")
        String shippingMethod;
        @JsonProperty("pickup_store_id")
        Integer pickupStoreId;
        @JsonProperty("address_params[address_id]")
        Integer addressId;
        @JsonProperty("address_params[first_name]")
        String firstName;
        @JsonProperty("address_params[last_name]")
        String lastName;
        @JsonProperty("address_params[lat]")
        Double lat;
        @JsonProperty("address_params[lon]")
        Double lon;
        @JsonProperty("address_params[city]")
        String city;
        @JsonProperty("address_params[street]")
        String street;
        @JsonProperty("address_params[floor]")
        String floor;
        @JsonProperty("address_params[business_center]")
        String businessCenter;
        @JsonProperty("address_params[elevator]")
        Boolean elevator;
        @JsonProperty("address_params[region]")
        String region;
        @JsonProperty("address_params[pass]")
        String pass;
        @JsonProperty("address_params[shipment_comment]")
        String shipmentComment;
        @JsonProperty("address_params[building]")
        String building;
        @JsonProperty("address_params[block]")
        String block;
        @JsonProperty("address_params[settlement]")
        String settlement;
        @JsonProperty("address_params[area]")
        String area;
        @JsonProperty("address_params[apartment]")
        String apartment;
        @JsonProperty("address_params[comments]")
        String comments;
        @JsonProperty("address_params[city_kladr_id]")
        String cityKladrId;
        @JsonProperty("address_params[street_kladr_id]")
        String streetKladrId;
        @JsonProperty("address_params[entrance]")
        String entrance;
        @JsonProperty("address_params[door_phone]")
        String doorPhone;
    }
}
