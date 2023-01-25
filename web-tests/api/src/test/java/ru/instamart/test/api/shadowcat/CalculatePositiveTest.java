package ru.instamart.test.api.shadowcat;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import io.restassured.response.Response;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.instamart.api.common.ShadowcatRestBase;
import ru.instamart.api.dataprovider.ShadowcatDataProvider;
import ru.instamart.api.enums.shadowcat.PromotionType;
import ru.instamart.api.enums.shadowcat.SimpleCondition;
import ru.instamart.api.enums.shadowcat.TwoStepCondition;
import ru.instamart.api.model.shadowcat.OrderSC;
import ru.instamart.api.request.shadowcat.CalculateRequest;
import ru.instamart.api.request.shadowcat.ConditionRequest;
import ru.instamart.api.response.shadowcat.CalculateResponse;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode204or404;
import static ru.instamart.api.helper.ShadowcatHelper.*;


@Epic("Shadowcat")
@Feature("Позитивные тесты на калькуляцию скидки")
public class CalculatePositiveTest extends ShadowcatRestBase {

    private static final int promotion = PromotionType.DISCOUNT.getId();
    private static final String code = PromotionType.DISCOUNT.getCode();
    private static OrderSC order;

    @TmsLink("21")
    @Story("Позитивные тесты")
    @Test(description = "Успешный расчет скидки по condition",
            groups = {"api-shadowcat"}, dataProvider = "simpleCondition",
            dataProviderClass = ShadowcatDataProvider.class)
    public void testPositiveSimple(SimpleCondition condition) {
        order = createOrderSC(code);
        final Response res = ConditionRequest.ConditionSimple.PUT(promotion, condition);
        checkStatusCode204or404(res);
        updateOrderSimpleCondition(order, condition, true);
        final Response response = CalculateRequest.POST(order);
        checkStatusCode200(response);
        CalculateResponse orderRes = response.as(CalculateResponse.class);
        compareTwoObjects(orderRes.getPromotion().getAdjustmentAmount(), String.valueOf(PromotionType.DISCOUNT.getValue()));
    }

    @TmsLink("20")
    @Story("Позитивные тесты")
    @Test(description = "Успешный расчет скидки по condition",
            groups = {"api-shadowcat"}, dataProvider = "twoStepCondition",
            dataProviderClass = ShadowcatDataProvider.class)
    public void calculateTwoStepCondition(TwoStepCondition condition) {
        order = createOrderSC(code);
        addTwoStepCondition(promotion, condition);
        updateOrderTwoStepCondition(order, condition, true);
        final Response response = CalculateRequest.POST(order);
        checkStatusCode200(response);
        CalculateResponse orderRes = response.as(CalculateResponse.class);
        compareTwoObjects(orderRes.getPromotion().getAdjustmentAmount(), String.valueOf(PromotionType.DISCOUNT.getValue()));
    }

    @AfterMethod(alwaysRun = true)
    public void clearCondition(ITestResult result) {
        var parameter = result.getParameters()[0];
        ConditionRequest.DeleteCondition.DELETE(promotion, parameter);
    }
}
