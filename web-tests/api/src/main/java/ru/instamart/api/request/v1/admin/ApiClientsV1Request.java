package ru.instamart.api.request.v1.admin;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;

import java.util.List;

public class ApiClientsV1Request extends ApiV1RequestBase {

    @Step("{method} /" + ApiV1Endpoints.API_CLIENTS)
    public static Response GET() {
        return GET(null, null);
    }

    @Step("{method} /" + ApiV1Endpoints.API_CLIENTS)
    public static Response GET(String client, Integer page) {
        RequestSpecification req = givenWithAuth();
        if(client != null) req.queryParam("q", client);
        if(client != null) req.queryParam("page", page);
        return req.get(ApiV1Endpoints.API_CLIENTS);
    }

    @Step("{method} /" + ApiV1Endpoints.API_CLIENTS)
    public static Response POST(ApiClientRequest apiClient) {
      return givenWithAuth()
              .contentType(ContentType.JSON)
              .body(apiClient)
              .post(ApiV1Endpoints.API_CLIENTS);
    }

    @Step("{method} /" + ApiV1Endpoints.ApiClients.BY_ID)
    public static Response PUT(ApiClientRequest apiClient, long clientId) {
        return givenWithAuth()
                .contentType(ContentType.JSON)
                .body(apiClient)
                .put(ApiV1Endpoints.ApiClients.BY_ID, clientId);
    }

    @Step("{method} /" + ApiV1Endpoints.ApiClients.BY_ID)
    public static Response GET(long clientId) {
        return givenWithAuth()
                .get(ApiV1Endpoints.ApiClients.BY_ID, clientId);
    }

    @Step("{method} /" + ApiV1Endpoints.ApiClients.BY_ID)
    public static Response DELETE(long clientId) {
        return givenWithAuth()
                .delete(ApiV1Endpoints.ApiClients.BY_ID, clientId);
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ApiClientRequest {
        @JsonProperty("api_client")
        private ApiClient apiClient;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ApiClient {
        @JsonProperty("basic_auth")
        private String basicAuth;
        @JsonProperty("card_payment_method")
        private String cardPaymentMethod;
        @JsonProperty("client_id")
        private String clientId;
        @JsonProperty("custom_prices")
        private Boolean customPrices;
        @JsonProperty("custom_promo")
        private Boolean customPromo;
        @JsonProperty("retailer_ids")
        private List<Long> retailerIds;
        private String secret;
        @JsonProperty("sku_kind")
        private String skuKind;
        @JsonProperty("tenant_id")
        private String tenantId;
        private Boolean verifiable;
        @JsonProperty("webhook_auth_token")
        private String webhookAuthToken;
    }
}
