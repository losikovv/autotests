package ru.instamart.api.request.v1;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomUtils;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;
import ru.instamart.kraken.data.Generate;
import ru.sbermarket.common.Mapper;

import java.io.File;
import java.util.List;

public class RetailersV1Request extends ApiV1RequestBase {
    @Step("{method} /" + ApiV1Endpoints.RETAILERS)
    public static Response GET(RetailerParams retailerParams) {
        return givenWithSpec()
                .queryParams(Mapper.INSTANCE.objectToMap(retailerParams))
                .get(ApiV1Endpoints.RETAILERS);
    }

    @Step("{method} /" + ApiV1Endpoints.RETAILERS)
    public static Response GET(List<Long> retailerIds) {
        RequestSpecification req = givenWithSpec();
        retailerIds.forEach(id -> req.queryParam("q[id_in][]", id));
        return req.get(ApiV1Endpoints.RETAILERS);
    }

    @Step("{method} /" + ApiV1Endpoints.Retailers.ID)
    public static Response GET(int retailerId) {
        return givenWithSpec().get(ApiV1Endpoints.Retailers.ID, retailerId);
    }

    @Step("{method} /" + ApiV1Endpoints.RETAILERS)
    public static Response POST(Retailer retailer) {
        return givenWithAuth()
                .formParams(Mapper.INSTANCE.objectToMap(retailer))
                .multiPart("retailer[appearance_attributes][mini_logo_image]", new File("src/test/resources/data/logo.png"), "image/png")
                .multiPart("retailer[appearance_attributes][logo_image]", new File("src/test/resources/data/logo.png"), "image/png")
                .multiPart("retailer[icon_attributes][attachment]", new File("src/test/resources/data/logo.png"), "image/png")
                .multiPart("retailer[logo_attributes][attachment]", new File("src/test/resources/data/logo.png"), "image/png")
                .post(ApiV1Endpoints.RETAILERS);
    }

    @Step("{method} /" + ApiV1Endpoints.Retailers.SLUG)
    public static Response PUT(Retailer retailer, String slug) {
        return givenWithAuth()
                .formParams(Mapper.INSTANCE.objectToMap(retailer))
                .put(ApiV1Endpoints.Retailers.SLUG, slug);
    }

    public static class Stores {
        @Step("{method} /" + ApiV1Endpoints.Retailers.STORES)
        public static Response GET(int retailerId) {
            return givenWithSpec().get(ApiV1Endpoints.Retailers.STORES, retailerId);
        }
    }

    public static class Eans {
        @Step("{method} /" + ApiV1Endpoints.Retailers.EANS)
        public static Response GET(int retailerId) {
            return givenWithSpec().get(ApiV1Endpoints.Retailers.EANS, retailerId);
        }
    }

    public static class ShippingPolicies {
        @Step("{method} /" + ApiV1Endpoints.Retailers.SHIPPING_POLICIES)
        public static Response GET(String retailerSlug) {
            return givenWithAuth().get(ApiV1Endpoints.Retailers.SHIPPING_POLICIES, retailerSlug);
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class RetailerParams {
        private Integer page;
        @JsonProperty("per_page")
        private Integer perPage;
        @JsonProperty("q[sorts]")
        private String sorts;
        private Boolean active;
        @JsonProperty("q[stores_operational_zone_id_in]")
        private Long operationalZoneId;
        @JsonProperty("q[id_in]")
        private Long retailerId;
        @JsonProperty("q[name_cont]")
        private String nameCont;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class Retailer {
        @JsonProperty("retailer[appearance_attributes][image_color]")
        private String imageColor;
        @JsonProperty("retailer[appearance_attributes][background_color]")
        private String backgroundColor;
        @JsonProperty("retailer[logo_background_color]")
        private String logoBackgroundColor;
        @JsonProperty("retailer[appearance_attributes][black_theme]")
        private Boolean blackTheme;
        @JsonProperty("retailer[secondary_color]")
        private String secondaryColor;
        @JsonProperty("retailer[color]")
        private String color;
        @JsonProperty("retailer[key]")
        private Integer key;
        @JsonProperty("retailer[home_page_departments_depth]")
        private Integer homePageDepartmentsDepth;
        @JsonProperty("retailer[contract_type]")
        private String contactType;
        @JsonProperty("retailer[phone]")
        private String phone;
        @JsonProperty("retailer[legal_address]")
        private String legalAddress;
        @JsonProperty("retailer[slug]")
        private String slug;
        @JsonProperty("retailer[description]")
        private String description;
        @JsonProperty("retailer[short_name]")
        private String shortName;
        @JsonProperty("retailer[name]")
        private String name;
    }

    public static Retailer getRetailer() {
        return Retailer.builder()
                .imageColor("#DDDDDD")
                .backgroundColor("#FFFFFF")
                .logoBackgroundColor("#FFFFFF")
                .blackTheme(true)
                .secondaryColor("#EEEEEE")
                .color("#FFFFFF")
                .key(RandomUtils.nextInt(1, 1000000))
                .homePageDepartmentsDepth(22)
                .contactType("agent")
                .phone(Generate.phoneNumber())
                .legalAddress("Москва, ул Мира 1")
                .slug("autotest-" + Generate.literalString(6))
                .description("Autotest retailer")
                .shortName("autotest-" + Generate.literalString(6))
                .name("Autotest-" + Generate.literalString(6))
                .build();
    }
}
