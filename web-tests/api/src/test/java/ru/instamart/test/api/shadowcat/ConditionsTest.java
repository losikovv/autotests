package ru.instamart.test.api.shadowcat;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
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
import ru.instamart.api.request.shadowcat.ConditionRequest;
import ru.instamart.api.request.shadowcat.ConditionRequest.AddTwoStepConditionRule;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode204or404;
import static ru.instamart.api.request.shadowcat.ConditionRequest.ConditionSimple.PUT;

@Epic("Shadowcat")
@Feature("Проверка добавления условий в промоакцию")
public class ConditionsTest extends ShadowcatRestBase {
    private static final int promoId = PromotionType.DISCOUNT.getId();

    @TmsLink("14")
    @Test(description = "Добавление простых condition",
            groups = {"api-shadowcat"}, dataProvider = "SimpleCondition",
            dataProviderClass = ShadowcatDataProvider.class)
    public void addSimpleCondition(SimpleCondition condition) {
        final Response response = PUT(promoId, condition);
        checkStatusCode204or404(response);
    }
    @TmsLink("15")
    @Test(description = "Добавление двухэтапных condition",
            groups = {"api-shadowcat"}, dataProvider = "TwoStepCondition",
            dataProviderClass = ShadowcatDataProvider.class)
    public void addTwoStepCondition(TwoStepCondition condition) {
        final Response response = AddTwoStepConditionRule.PUT(promoId, true, condition);
        checkStatusCode204or404(response);
        final Response second = AddTwoStepConditionRule.PUT(promoId, condition);
        checkStatusCode204or404(second);
    }

    @AfterMethod(alwaysRun = true)
    public void clearCondition(ITestResult result) {
        var parameter = result.getParameters()[0];
        ConditionRequest.DeleteCondition.DELETE(promoId, parameter);
    }
}
