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
import ru.instamart.api.response.shadowcat.ErrorShadowcatResponse;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode204or404;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode422;
import static ru.instamart.api.helper.ShadowcatHelper.*;


@Epic("Shadowcat")
@Feature("Негативные тесты на калькуляцию скидки")
public class CalculateNegativeTest extends ShadowcatRestBase {

    private static final int promotion = PromotionType.DISCOUNT.getId();
    private static final String code = PromotionType.DISCOUNT.getCode();
    private static OrderSC order;

    @TmsLink("11")
    @Story("Негативные тесты")
    @Test(description = "Неудачный расчет скидки для сложных condition ",
            groups = {"api-shadowcat"}, dataProvider = "TwoStepCondition",
            dataProviderClass = ShadowcatDataProvider.class)
    public void calculateFailTwoStepCondition(TwoStepCondition condition) {
        order = createOrderSC(code);
        updateOrderToFail(order, condition);
        addTwoStepCondition(promotion, condition);
        final Response response = CalculateRequest.POST(order);
        checkStatusCode422(response);
        ErrorShadowcatResponse orderRes = response.as(ErrorShadowcatResponse.class);
        compareTwoObjects(orderRes.getDetail(), condition.getErrorMessage());
    }

    @TmsLink("12")
    @Story("Негативные тесты")
    @Test(description = "Неудачный расчет скидки по condition",
            groups = {"api-shadowcat"}, dataProvider = "SimpleCondition",
            dataProviderClass = ShadowcatDataProvider.class)
    public void calculateFailSimpleCondition(SimpleCondition condition) {
        order = createOrderSC(code);
        updateOrderToFail(order, condition);
        final Response res = ConditionRequest.ConditionSimple.PUT(promotion, condition);
        checkStatusCode204or404(res);
        final Response response = CalculateRequest.POST(order);
        checkStatusCode422(response);
        ErrorShadowcatResponse orderRes = response.as(ErrorShadowcatResponse.class);
        compareTwoObjects(orderRes.getDetail(), condition.getErrorMessage());
    }

    @AfterMethod(alwaysRun = true)
    public void clearCondition(ITestResult result) {
        var parameter = result.getParameters()[0];
        ConditionRequest.DeleteCondition.DELETE(promotion, parameter);
    }
}
