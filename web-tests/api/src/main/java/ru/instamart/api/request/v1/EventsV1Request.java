package ru.instamart.api.request.v1;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;
import ru.instamart.kraken.common.Mapper;

import java.util.UUID;

import static ru.instamart.kraken.util.TimeUtil.getZonedDate;

public class EventsV1Request extends ApiV1RequestBase {

    public static class StoreHealthCheck {
        @Step("{method} /" + ApiV1Endpoints.Shoppers.Events.STORE_HEALTHCHECK)
        public static Response POST(String[] storeUuids) {
            JSONObject body = new JSONObject();
            body.put("stores_uuids", storeUuids);
            body.put("uuid", UUID.randomUUID());
            body.put("timestamp", getZonedDate());
            return givenWithAuthAndApiKey()
                    .contentType(ContentType.JSON)
                    .body(body)
                    .post(ApiV1Endpoints.Shoppers.Events.STORE_HEALTHCHECK);
        }
    }

    public static class OrderPaymentTool {
        @Step("{method} /" + ApiV1Endpoints.Shoppers.Events.ORDER_PAYMENT_TOOL_UPDATED)
        public static Response POST(String orderNumber, Long paymentToolId) {
            JSONObject body = new JSONObject();
            body.put("order_number", orderNumber);
            body.put("payment_tool_id", paymentToolId);
            body.put("uuid", UUID.randomUUID());
            body.put("timestamp", getZonedDate());
            return givenWithAuthAndApiKey()
                    .contentType(ContentType.JSON)
                    .body(body)
                    .post(ApiV1Endpoints.Shoppers.Events.ORDER_PAYMENT_TOOL_UPDATED);
        }
    }

    public static class ReceiptCreated {
        @Step("{method} /" + ApiV1Endpoints.Shoppers.Events.RECEIPT_CREATED)
        public static Response POST(String shipmentUuid) {
            JSONObject body = new JSONObject();
            body.put("shipment_uuid", shipmentUuid);
            body.put("uuid", UUID.randomUUID());
            body.put("timestamp", getZonedDate());
            body.put("total","100.00");
            body.put("paid_at", getZonedDate());
            body.put("fiscal_secret", "9400000000000000");
            body.put("fiscal_checksum", "10000");
            body.put("fiscal_document_number", "11");
            body.put("transaction_details", "t=20210622T120000");
            return givenWithAuthAndApiKey()
                    .contentType(ContentType.JSON)
                    .body(body)
                    .post(ApiV1Endpoints.Shoppers.Events.RECEIPT_CREATED);
        }
    }

    public static class LineItem {

        public static class Cancelled {
            @Step("{method} /" + ApiV1Endpoints.Shoppers.Events.LINE_ITEM_СANCELLED)
            public static Response POST(EventData eventData) {
                return givenWithAuthAndApiKey()
                        .contentType(ContentType.JSON)
                        .body(eventData)
                        .post(ApiV1Endpoints.Shoppers.Events.LINE_ITEM_СANCELLED);
            }
        }

        public static class Created {
            @Step("{method} /" + ApiV1Endpoints.Shoppers.Events.LINE_ITEM_СREATED)
            public static Response POST(EventData eventData) {
                return givenWithAuthAndApiKey()
                        .contentType(ContentType.JSON)
                        .body(eventData)
                        .post(ApiV1Endpoints.Shoppers.Events.LINE_ITEM_СREATED);
            }
        }

        public static class Restored {
            @Step("{method} /" + ApiV1Endpoints.Shoppers.Events.LINE_ITEM_RESTORED)
            public static Response POST(EventData eventData) {
                return givenWithAuthAndApiKey()
                        .contentType(ContentType.JSON)
                        .body(eventData)
                        .post(ApiV1Endpoints.Shoppers.Events.LINE_ITEM_RESTORED);
            }
        }

        public static class Returned {
            @Step("{method} /" + ApiV1Endpoints.Shoppers.Events.LINE_ITEM_RETURNED)
            public static Response POST(EventData eventData) {
                return givenWithAuthAndApiKey()
                        .contentType(ContentType.JSON)
                        .body(eventData)
                        .post(ApiV1Endpoints.Shoppers.Events.LINE_ITEM_RETURNED);
            }
        }

        public static class Updated {
            @Step("{method} /" + ApiV1Endpoints.Shoppers.Events.LINE_ITEM_UPDATED)
            public static Response POST(EventData eventData) {
                return givenWithAuthAndApiKey()
                        .contentType(ContentType.JSON)
                        .body(eventData)
                        .post(ApiV1Endpoints.Shoppers.Events.LINE_ITEM_UPDATED);
            }
        }
    }

    public static class Assembly {

        public static class Approved {
            @Step("{method} /" + ApiV1Endpoints.Shoppers.Events.ASSEMBLY_APPROVED)
            public static Response POST(String shipmentUuid) {
                JSONObject body = new JSONObject();
                body.put("uuid", UUID.randomUUID());
                body.put("timestamp", getZonedDate());
                body.put("assembly_updated_at", getZonedDate());
                body.put("shipment_uuid", shipmentUuid);
                return givenWithAuthAndApiKey()
                        .queryParams(Mapper.INSTANCE.objectToMap(body))
                        .post(ApiV1Endpoints.Shoppers.Events.ASSEMBLY_APPROVED);
            }
        }

        public static class Created {
            @Step("{method} /" + ApiV1Endpoints.Shoppers.Events.ASSEMBLY_CREATED)
            public static Response POST(EventData eventData) {
                return givenWithAuthAndApiKey()
                        .queryParams(Mapper.INSTANCE.objectToMap(eventData))
                        .post(ApiV1Endpoints.Shoppers.Events.ASSEMBLY_CREATED);
            }
        }

        public static class Destroyed {
            @Step("{method} /" + ApiV1Endpoints.Shoppers.Events.ASSEMBLY_DESTROYED)
            public static Response POST(EventData eventData) {
                return givenWithAuthAndApiKey()
                        .queryParams(Mapper.INSTANCE.objectToMap(eventData))
                        .post(ApiV1Endpoints.Shoppers.Events.ASSEMBLY_DESTROYED);
            }
        }

        public static class Purchased {
            @Step("{method} /" + ApiV1Endpoints.Shoppers.Events.ASSEMBLY_PURCHASED)
            public static Response POST(EventData eventData) {
                return givenWithAuthAndApiKey()
                        .queryParams(Mapper.INSTANCE.objectToMap(eventData))
                        .post(ApiV1Endpoints.Shoppers.Events.ASSEMBLY_PURCHASED);
            }
        }

        public static class Shipped {
            @Step("{method} /" + ApiV1Endpoints.Shoppers.Events.ASSEMBLY_SHIPPED)
            public static Response POST(EventData eventData) {
                return givenWithAuthAndApiKey()
                        .queryParams(Mapper.INSTANCE.objectToMap(eventData))
                        .post(ApiV1Endpoints.Shoppers.Events.ASSEMBLY_SHIPPED);
            }
        }

        public static class ShippingStarted {
            @Step("{method} /" + ApiV1Endpoints.Shoppers.Events.ASSEMBLY_SHIPPING_STARTED)
            public static Response POST(EventData eventData) {
                return givenWithAuthAndApiKey()
                        .queryParams(Mapper.INSTANCE.objectToMap(eventData))
                        .post(ApiV1Endpoints.Shoppers.Events.ASSEMBLY_SHIPPING_STARTED);
            }
        }

        public static class ShippingStopped {
            @Step("{method} /" + ApiV1Endpoints.Shoppers.Events.ASSEMBLY_SHIPPING_STOPPED)
            public static Response POST(EventData eventData) {
                return givenWithAuthAndApiKey()
                        .queryParams(Mapper.INSTANCE.objectToMap(eventData))
                        .post(ApiV1Endpoints.Shoppers.Events.ASSEMBLY_SHIPPING_STOPPED);
            }
        }

        public static class Updated {
            @Step("{method} /" + ApiV1Endpoints.Shoppers.Events.ASSEMBLY_UPDATED)
            public static Response POST(EventData eventData) {
                return givenWithAuthAndApiKey()
                        .queryParams(Mapper.INSTANCE.objectToMap(eventData))
                        .post(ApiV1Endpoints.Shoppers.Events.ASSEMBLY_UPDATED);
            }
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class EventData {
        private String uuid;
        private String timestamp;
        @JsonProperty("line_item_uuid")
        private String lineItemUuid;
        private String price;
        private Integer quantity;
        @JsonProperty("found_quantity")
        private Integer foundQuantity;
        private Boolean assembled;
        @JsonProperty("offer_uuid")
        private String offerUuid;
        @JsonProperty("items_per_pack")
        private Integer itemsPerPack;
        @JsonProperty("retailer_shelf_price")
        private String retailerShelfPrice;
        @JsonProperty("assembly_issue")
        private String assemblyIssue;
        @JsonProperty("shipment_uuid")
        private String shipmentUuid;
        @JsonProperty("to_uuid")
        private String toUuid;
        @JsonProperty("to_quantity")
        private Integer toQuantity;
        @JsonProperty("to_found_quantity")
        private Integer toFoundQuantity;
        @JsonProperty("to_assembly_issue")
        private String toAssemblyIssue;
        @JsonProperty("to_assembled")
        private Boolean toAssembled;
        @JsonProperty("return_quantity")
        private Integer returnQuantity;
        @JsonProperty("assembly_updated_at")
        private String assemblyUpdatedAt;
        @JsonProperty("assembly_shipped_at")
        private String assemblyShippedAt;
        @JsonProperty("assembly_invoice_number")
        private String assemblyInvoiceNumber;
        @JsonProperty("assembly_invoice_total")
        private Double assemblyInvoiceTotal;
    }

    public static EventData getAssemblyData(String shipmentUuid) {
        return  EventData.builder()
                .uuid(UUID.randomUUID().toString())
                .timestamp(getZonedDate())
                .assemblyUpdatedAt(getZonedDate())
                .shipmentUuid(shipmentUuid)
                .build();
    }
}
