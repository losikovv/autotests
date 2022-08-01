package ru.instamart.api.response.v1.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class PromotionCodeV1Response extends BaseResponseObject {

    @JsonSchema(required = true)
    @JsonProperty("promotion_code")
    private PromotionCode promotionCode;

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class PromotionCode extends BaseResponseObject {

        @JsonSchema(required = true)
        @JsonProperty("usage_limit")
        private int usageLimit;

        @JsonSchema(required = true)
        @JsonProperty("usage_count")
        private Object usageCount;

        @JsonSchema(required = true)
        @JsonProperty("value")
        private String value;

        @JsonSchema(required = true)
        @JsonProperty("promotion")
        private Promotion promotion;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class Promotion extends BaseResponseObject {

        @JsonSchema(required = true)
        @JsonProperty("service_comment")
        private String serviceComment;

        @JsonSchema(required = true)
        @JsonProperty("starts_at")
        private Object startsAt;

        @JsonSchema(required = true)
        @JsonProperty("expires_at")
        private String expiresAt;

        @JsonSchema(required = true)
        @JsonProperty("description")
        private String description;

        @JsonSchema(required = true)
        @JsonProperty("sections")
        private List<SectionsItem> sections;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class SectionsItem extends BaseResponseObject {

        @JsonSchema(required = true)
        @JsonProperty("values")
        private List<Object> values;

        @JsonSchema(required = true)
        @JsonProperty("name")
        private String name;

        @JsonSchema(required = true)
        @JsonProperty("title")
        private String title;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class ValuesItem extends BaseResponseObject {

        @JsonSchema(required = true)
        @JsonProperty("values")
        private List<String> values;

        @JsonSchema(required = true)
        @JsonProperty("label")
        private String label;

        @JsonSchema(required = true)
        @JsonProperty("type")
        private String type;
    }
}