package ru.instamart.api.request.v2;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.*;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.request.ApiV2RequestBase;
import ru.instamart.kraken.service.MapperService;

import java.util.List;

public class PersonalV2Request extends ApiV2RequestBase {

    @Step("{method} /" + ApiV2EndPoints.Recs.PERSONAL)
    public static Response POST(RecsV2 recs){
        return givenWithSpec()
                .body(MapperService.INSTANCE.objectToMap(recs))
                .post(ApiV2EndPoints.Recs.PERSONAL);
    }

    /**
     * Основной класс формирования запроса
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @Data
    public static class RecsV2 {
        private String reqId;
        private Context context;
        private List<PlacementsItem> placements;
    }


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @Data
    public static class App{
        private SiteAndAppExt ext;
        private String ver;
        private String domain;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @Data
    public static class Context{
        private App app;
        private Site site;
        private Device device;
        private User user;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @Data
    public static class Device{
        private Geo geo;
        private String osv;
        private String os;
        private String hwv;
        private int W;
        private int H;
        private String model;
        private String type;
        private String make;
    }


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @Data
    public static class PlacementsExt{
        private int componentId;
        @JsonProperty("display_condition")
        private String displayCondition;
        private int order;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @Data
    public static class SiteAndAppExt{
        @JsonProperty("category_id")
        private int categoryId;
        @JsonProperty("store_id")
        private String storeId;
        @JsonProperty("tenant_id")
        private int tenantId;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @Data
    public static class Geo{
        private int lon;
        private int lat;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @Data
    public static class PlacementsItem{
        private PlacementsExt ext;
        private String placementId;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @Data
    public static class Site{
        private SiteAndAppExt ext;
        private String domain;
        private String page;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @Data
    public static class User{
        private Geo geo;
        private SiteAndAppExt ext;
        private String data;
        private String id;
    }
}
