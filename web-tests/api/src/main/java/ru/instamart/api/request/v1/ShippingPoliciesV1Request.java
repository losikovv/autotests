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
import org.apache.commons.lang3.RandomUtils;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.enums.v1.RulesAttributesTypeV1;
import ru.instamart.api.request.ApiV1RequestBase;
import ru.instamart.kraken.data.Generate;

import java.util.ArrayList;
import java.util.List;

public class ShippingPoliciesV1Request extends ApiV1RequestBase {

    @Step("{method} /" + ApiV1Endpoints.SHIPPING_POLICIES)
    public static Response POST(ShippingPolicies shippingPolicies) {
        return givenWithAuth()
                .contentType(ContentType.JSON)
                .body(shippingPolicies)
                .post(ApiV1Endpoints.SHIPPING_POLICIES);
    }

    @Step("{method} /" + ApiV1Endpoints.ShippingPolicies.ID)
    public static Response PUT(ShippingPolicies shippingPolicies, Long shippingPoliciesId) {
        return givenWithAuth()
                .contentType(ContentType.JSON)
                .body(shippingPolicies)
                .put(ApiV1Endpoints.ShippingPolicies.ID, shippingPoliciesId);
    }

    @Step("{method} /" + ApiV1Endpoints.ShippingPolicies.ID)
    public static Response GET(Long shippingPoliciesId) {
        return givenWithAuth()
                .get(ApiV1Endpoints.ShippingPolicies.ID, shippingPoliciesId);
    }

    @Step("{method} /" + ApiV1Endpoints.ShippingPolicies.ID)
    public static Response DELETE(Long shippingPoliciesId) {
        return givenWithAuth()
                .delete(ApiV1Endpoints.ShippingPolicies.ID, shippingPoliciesId);
    }

    public static class ShippingPoliciesRules {
        @Step("{method} /" + ApiV1Endpoints.ShippingPolicies.SHIPPING_POLICY_RULES)
        public static Response POST(Long shippingPoliciesId, ShippingPolicyRules shippingPolicyRule) {
            return givenWithAuth()
                    .contentType(ContentType.JSON)
                    .body(shippingPolicyRule)
                    .post(ApiV1Endpoints.ShippingPolicies.SHIPPING_POLICY_RULES, shippingPoliciesId);
        }

        @Step("{method} /" + ApiV1Endpoints.ShippingPolicies.ShippingPolicyRules.ID)
        public static Response DELETE(Long shippingPoliciesId, Long shippingPolicyRuleId) {
            return givenWithAuth()
                    .delete(ApiV1Endpoints.ShippingPolicies.ShippingPolicyRules.ID, shippingPoliciesId, shippingPolicyRuleId);
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class ShippingPolicies {
        @JsonProperty("shipping_policy")
        private ShippingPolicy shippingPolicy;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class RulesAttribute {
        private String type;
        @JsonProperty("preferred_days")
        private Integer preferredDays;
        @JsonProperty("preferred_time_gap")
        private Integer preferredTimeGap;
        @JsonProperty("preferred_time")
        private Integer preferredTime;
        @JsonProperty("preferred_number")
        private Integer preferredNumber;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class ShippingPolicy {
        @JsonProperty("retailer_id")
        private Integer retailerId;
        @JsonProperty("rules_attributes")
        private List<RulesAttribute> rulesAttributes;
        @JsonProperty
        private String title;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class ShippingPolicyRules {
        @JsonProperty("rule_type")
        private String ruleType;
        private RulesAttribute preferences;
    }

    public static ShippingPolicies getShippingPolicies(Integer retailerId) {
        List<RulesAttribute> rulesAttributes = new ArrayList<>();
        rulesAttributes.add(RulesAttribute.builder()
                .type(RulesAttributesTypeV1.DAYS_FROM_NOW.getValue())
                .preferredDays(RandomUtils.nextInt(1, 15))
                .build());
        rulesAttributes.add(RulesAttribute.builder()
                .type(RulesAttributesTypeV1.TIME_BEFORE_SHIPMENT.getValue())
                .preferredTimeGap(RandomUtils.nextInt(500, 1000))
                .build());
        rulesAttributes.add(RulesAttribute.builder()
                .type(RulesAttributesTypeV1.TIME_FROM_MIDNIGHT.getValue())
                .preferredTime(RandomUtils.nextInt(60000, 80000))
                .build());
        rulesAttributes.add(RulesAttribute.builder()
                .type(RulesAttributesTypeV1.DELIVERY_WINDOW_NUMBER.getValue())
                .preferredNumber(RandomUtils.nextInt(1, 10))
                .build());
        rulesAttributes.add(RulesAttribute.builder()
                .type(RulesAttributesTypeV1.TIME_BEFORE_EXPRESS_SHIPMENT.getValue())
                .preferredTimeGap(RandomUtils.nextInt(1000, 1500))
                .build());

        return ShippingPolicies.builder()
                .shippingPolicy(ShippingPolicy.builder()
                        .retailerId(retailerId)
                        .rulesAttributes(rulesAttributes)
                        .title("Autotest-" + Generate.literalString(6))
                        .build())
                .build();
    }
}
