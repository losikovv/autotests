package ru.instamart.api.request.v2;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.*;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.request.ApiV2RequestBase;
import ru.instamart.utils.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.codehaus.groovy.runtime.InvokerHelper.asList;

public final class ShipmentsV2Request extends ApiV2RequestBase {

    @Step("{method} /" + ApiV2EndPoints.Shipments.SHIPMENTS)
    public static Response DELETE(String shipmentNumber) {
        return givenWithAuth()
                .delete(ApiV2EndPoints.Shipments.SHIPMENTS, shipmentNumber);
    }

    public static class DeliveryWindows {

        @Step("{method} /" + ApiV2EndPoints.Shipments.DELIVERY_WINDOWS)
        public static Response GET(String shipmentId, String date) {
            if (Objects.nonNull(date))
                date = "date=" + date;
            else
                date = "";
            return givenWithAuth()
                    .get(ApiV2EndPoints.Shipments.DELIVERY_WINDOWS, shipmentId, date);
        }
    }

    public static class LineItems {

        @Step("{method} /" + ApiV2EndPoints.Shipments.LINE_ITEMS)
        public static Response GET(String shipmentNumber) {
            return givenWithAuth()
                    .get(ApiV2EndPoints.Shipments.LINE_ITEMS, shipmentNumber);
        }
    }

    public static class LineItemCancellations {

        @Step("{method} /" + ApiV2EndPoints.Shipments.LINE_ITEM_CANCELLATIONS)
        public static Response GET(String shipmentNumber) {
            return givenWithAuth()
                    .get(ApiV2EndPoints.Shipments.LINE_ITEM_CANCELLATIONS, shipmentNumber);
        }
    }

    public static class LineItemReplacements {

        @Step("{method} /" + ApiV2EndPoints.Shipments.LINE_ITEM_REPLACEMENTS)
        public static Response GET(String shipmentNumber) {
            return givenWithAuth()
                    .get(ApiV2EndPoints.Shipments.LINE_ITEM_REPLACEMENTS, shipmentNumber);
        }
    }

    public static class ServiceRate {

        public static Response GET(String shipmentNumber, String deliveryWindowId) {
            if (Objects.nonNull(deliveryWindowId))
                deliveryWindowId = "delivery_window_id=" + deliveryWindowId;
            else
                deliveryWindowId = "";
            return givenWithAuth()
                    .get(ApiV2EndPoints.Shipments.SERVICE_RATE, shipmentNumber, deliveryWindowId);
        }
    }

    public static class ShippingRates {
        /**
         * Получаем доступные слоты
         */
        @Step("{method} /" + ApiV2EndPoints.Shipments.SHIPPING_RATES)
        public static Response GET(String shipmentNumber, String date) {
            if (Objects.nonNull(date))
                date = "date=" + date;
            else
                date = "";
            return givenWithAuth()
                    .get(ApiV2EndPoints.Shipments.SHIPPING_RATES, shipmentNumber, date);
        }
    }

    public static class State{

        @Step("{method} /" + ApiV2EndPoints.Shipments.STATE)
        public static Response GET(String shipmentNumber){
            return givenWithAuth()
                    .get(ApiV2EndPoints.Shipments.STATE, shipmentNumber);
        }
    }

    public static class ReviewIssues{

        @Step("{method} /" + ApiV2EndPoints.Shipments.REVIEW_ISSUES)
        public static Response GET(String shipmentNumber){
            return givenWithAuth()
                    .get(ApiV2EndPoints.Shipments.REVIEW_ISSUES,shipmentNumber);
        }
    }

    public static class Reviews{

        @Step("{method} /"+ApiV2EndPoints.Shipments.REVIEWS)
        public static Response POST(String shipmentNumber, Review review){
            return givenWithAuth()
                    .formParams(Mapper.INSTANCE.objectToMap(review))
                    .post(ApiV2EndPoints.Shipments.REVIEWS, shipmentNumber);
        }
    }

    public static class Clones{

        @Step("{method} /"+ApiV2EndPoints.Shipments.CLONES)
        public static Response POST(String shipmentNumber){
            return givenWithAuth()
                    .post(ApiV2EndPoints.Shipments.CLONES, shipmentNumber);
        }
    }

    public static class AssemblyItems{

        @Step("{method} /" + ApiV2EndPoints.Shipments.ASSEMBLY_ITEMS)
        public static Response GET(String shipmentNumber){
            return givenWithAuth()
                    .get(ApiV2EndPoints.Shipments.ASSEMBLY_ITEMS,shipmentNumber);
        }
    }

    public static class Cancellations {

        @Step("{method} /" + ApiV2EndPoints.Shipments.CANCELLATIONS)
        public static Response POST(String shipmentNumber, String reason) {
            return givenWithAuth()
                    .queryParam("reason", reason)
                    .post(ApiV2EndPoints.Shipments.CANCELLATIONS, shipmentNumber);
        }
    }

    /**
     * review[rate]	Да	Оценка
     * review[issue_ids]	-	Список выбранных вариантов ответа
     * review[images_attributes]	-	Прикрепленные изображения
     * review[comment]	-	Комментарий
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @Getter
    @EqualsAndHashCode
    @ToString
    public static final class Review{
        @JsonProperty(value = "review[rate]")
        private final Integer rate;
        @Singular
        @JsonProperty(value = "review[issue_ids][]")
        private final List<Integer> issueIds;
        @JsonProperty(value = "review[images_attributes][]")
        private final String imageAttributes;
        @JsonProperty(value = "review[comment]")
        private final String comment;
    }
}
