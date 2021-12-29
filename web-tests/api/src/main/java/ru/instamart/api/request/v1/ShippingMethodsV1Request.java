package ru.instamart.api.request.v1;

import com.fasterxml.jackson.annotation.*;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.*;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;
import ru.sbermarket.common.Mapper;

public final class ShippingMethodsV1Request extends ApiV1RequestBase {

    @Step("{method} /" + ApiV1Endpoints.SHIPPING_METHODS)
    public static Response GET() {
        return givenWithAuth()
                .get(ApiV1Endpoints.SHIPPING_METHODS);
    }

    @JsonTypeName("rule")
    @JsonTypeInfo(include= JsonTypeInfo.As.WRAPPER_OBJECT, use= JsonTypeInfo.Id.NAME)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    @Builder
    public static final class Rules {

        private Preferences preferences;
        private RulesType type;

        @Step("{method} /" + ApiV1Endpoints.ShippingMethods.RULES)
        public static Response POST(final int ruleId, final Rules rules) {
            return givenWithAuth()
                    .contentType(ContentType.JSON)
                    .body(Mapper.INSTANCE.objectToMap(rules))
                    .post(ApiV1Endpoints.ShippingMethods.RULES, ruleId);
        }

        @Step("{method} /" + ApiV1Endpoints.ShippingMethods.Rules.RULE_ID)
        public static Response PUT(final int ruleId, final Rules rules) {
            return givenWithAuth()
                    .contentType(ContentType.JSON)
                    .body(Mapper.INSTANCE.objectToMap(rules))
                    .put(ApiV1Endpoints.ShippingMethods.Rules.RULE_ID, ruleId);
        }
    }

    @JsonTypeName("calculator")
    @JsonTypeInfo(include= JsonTypeInfo.As.WRAPPER_OBJECT, use= JsonTypeInfo.Id.NAME)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    @Builder
    public static final class Calculators {

        private Preferences preferences;
        private CalculatorType type;

        @Step("{method} /" + ApiV1Endpoints.ShippingMethods.CALCULATORS)
        public static Response POST(final int ruleId, final Calculators calculators) {
            return givenWithAuth()
                    .contentType(ContentType.JSON)
                    .body(Mapper.INSTANCE.objectToMap(calculators))
                    .post(ApiV1Endpoints.ShippingMethods.CALCULATORS, ruleId);
        }

        @Step("{method} /" + ApiV1Endpoints.ShippingMethods.Calculator.RULE_ID)
        public static Response PUT(final int ruleId, final Calculators calculator) {
            return givenWithAuth()
                    .contentType(ContentType.JSON)
                    .body(Mapper.INSTANCE.objectToString(calculator))
                    .put(ApiV1Endpoints.ShippingMethods.Calculator.RULE_ID, ruleId);
        }
    }

    public static final class MarketingPricers {

        @Step("{method} /" + ApiV1Endpoints.ShippingMethods.MarketingPricers.MARKETING_PRICERS)
        public static Response GET(final int methodId) {
            return givenWithAuth()
                    .get(ApiV1Endpoints.ShippingMethods.MarketingPricers.MARKETING_PRICERS, methodId);
        }

        @Step("{method} /" + ApiV1Endpoints.ShippingMethods.MarketingPricers.MARKETING_PRICERS)
        public static Response POST(final int methodId) {
            return givenWithAuth()
                    .post(ApiV1Endpoints.ShippingMethods.MarketingPricers.MARKETING_PRICERS, methodId);
        }

        @Step("{method} /" + ApiV1Endpoints.ShippingMethods.MarketingPricers.RULE_ID)
        public static Response DELETE(final int ruleId) {
            return givenWithAuth()
                    .delete(ApiV1Endpoints.ShippingMethods.MarketingPricers.RULE_ID, ruleId);
        }
    }

    public static final class NominalPricers {

        @Step("{method} /" + ApiV1Endpoints.ShippingMethods.NominalPricers.NOMINAL_COST_PRICERS)
        public static Response GET(final int methodId) {
            return givenWithAuth()
                    .get(ApiV1Endpoints.ShippingMethods.NominalPricers.NOMINAL_COST_PRICERS, methodId);
        }

        @Step("{method} /" + ApiV1Endpoints.ShippingMethods.NominalPricers.NOMINAL_COST_PRICERS)
        public static Response POST(final int methodId) {
            return givenWithAuth()
                    .post(ApiV1Endpoints.ShippingMethods.NominalPricers.NOMINAL_COST_PRICERS, methodId);
        }

        @Step("{method} /" + ApiV1Endpoints.ShippingMethods.NominalPricers.RULE_ID)
        public static Response DELETE(final int ruleId) {
            return givenWithAuth()
                    .delete(ApiV1Endpoints.ShippingMethods.NominalPricers.RULE_ID, ruleId);
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static final class Preferences {

        @JsonProperty(value = "assembly_fee")
        private int assemblyFee;
        @JsonProperty(value = "bags_fee")
        private int bagsFee;
        @JsonProperty(value = "base_price")
        private double basePrice;
        @JsonProperty(value = "base_weight")
        private double baseWeight;
        @JsonProperty(value = "base_items_count")
        private int baseItemsCount;
        @JsonProperty(value = "extra_items_count_batch")
        private int extraItemsCountBatch;
        @JsonProperty(value = "extra_weight_batch")
        private double extraWeightBatch;
        @JsonProperty(value = "fee_per_extra_items_count_batch")
        private int feePerExtraItemsCountBatch;
        @JsonProperty(value = "fee_per_extra_weight_batch")
        private double feePerExtraWeightBatch;
        private double price;

        @JsonProperty(value = "lower_bound")
        private double lower_bound;
        @JsonProperty(value = "upper_bound")
        private double upper_bound;
        @JsonProperty(value = "retailer_id")
        private int retailerId;
        @JsonProperty(value = "period_in_days")
        private int periodInDays;
        @JsonProperty(value = "item_cost_minimum")
        private double itemCostMinimum;
    }

    @RequiredArgsConstructor
    public enum RulesType {

        //"Первый заказ"
        FIRST_ORDER_RULE("ShippingMethod::PricerRules::FirstOrderRule"),
        //"Ритейлер"
        RETAILER_RULE("ShippingMethod::PricerRules::RetailerRule"),
        //"Периодический заказ"
        PERIODIC_ORDER_RULE("ShippingMethod::PricerRules::PeriodicOrderRule"),
        //"Заказ c 5-20 апреля 2021"
        DATE_ORDER_RULE("ShippingMethod::PricerRules::DateOrderRule"),
        //"Интервал стоимости заказа"
        ITEM_TOTAL_RULE("ShippingMethod::PricerRules::ItemTotalRule");

        @Getter(onMethod_={@JsonValue})
        private final String type;
    }

    @RequiredArgsConstructor
    public enum CalculatorType {

        //"Цена с учетом сложности"
        STANDARD_WITH_FEES_CALCULATOR("ShippingMethod::PricerCalculators::StandardWithFeesCalculator"),
        //"Фиксированная цена"
        FLAT_CALCULATOR("ShippingMethod::PricerCalculators::FlatCalculator");

        @Getter(onMethod_={@JsonValue})
        private final String type;
    }
}
