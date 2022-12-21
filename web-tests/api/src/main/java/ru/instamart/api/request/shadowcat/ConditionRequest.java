package ru.instamart.api.request.shadowcat;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ShadowcatEndpoints;
import ru.instamart.api.enums.shadowcat.SimpleCondition;
import ru.instamart.api.enums.shadowcat.TwoStepCondition;
import ru.instamart.api.request.ShadowcatRequestBase;

import static ru.instamart.api.helper.ShadowcatHelper.*;

public class ConditionRequest extends ShadowcatRequestBase {
    public static class ConditionSimple {
        @Step("{method} /" + ShadowcatEndpoints.PromotionCondition.CONDITION)
        public static Response PUT(int promoId, SimpleCondition condition) {
            var body = returnBody(condition);
            return givenWithAuth()
                    .body(body)
                    .put(ShadowcatEndpoints.PromotionCondition.CONDITION, promoId, condition.getRoute());
        }
    }

        public static class AddTwoStepConditionRule {
            @Step("{method} /" + ShadowcatEndpoints.PromotionCondition.CONDITION)
            public static Response PUT(int promoId, boolean inclusion, TwoStepCondition condition) {
                JSONObject body = createBodyForFirstRequest(inclusion, condition);
                return givenWithAuth()
                        .body(body)
                        .put(ShadowcatEndpoints.PromotionCondition.CONDITION, promoId, condition.getRoute());
            }

            @Step("{method} /" + ShadowcatEndpoints.PromotionCondition.CONDITION_LIST)
            public static Response PUT(int promoId, TwoStepCondition condition) {
                return givenWithAuth()
                        .body(requestList(condition))
                        .put(ShadowcatEndpoints.PromotionCondition.CONDITION_LIST, promoId, condition.getRoute());
            }
        }

        public static class DeleteCondition {
            @Step("{method} /" + ShadowcatEndpoints.PromotionCondition.CONDITION)
            public static <T> Response DELETE(int promoId, T condition) {
                String route;
                if (condition instanceof SimpleCondition)
                    route = ((SimpleCondition) condition).getRoute();
                else
                    route = ((TwoStepCondition) condition).getRoute();
                return givenWithAuth()
                        .delete(ShadowcatEndpoints.PromotionCondition.CONDITION, promoId, route);
            }
        }
    }
