package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.model.v2.ReplacementPoliciesItemV2;
import ru.instamart.api.request.v2.ReplacementPoliciesV2Request;
import ru.instamart.api.response.v2.ReplacementPoliciesV2Response;

import java.util.List;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.enums.v2.ReplacementPoliciesV2.*;

@Epic("ApiV2")
@Feature("Получить список способов замены")
public class ReplacementPoliciesV2Test extends RestBase {

    @CaseId(811)
    @Story("Получение списка всех банковских карт")
    @Test(groups = {"api-instamart-regress"},
            description = "Получить список способов замены")
    public void getListReplacementMethods() {
        final Response response = ReplacementPoliciesV2Request.GET();
        checkStatusCode200(response);
        List<ReplacementPoliciesItemV2> replacementPolicies = response.as(ReplacementPoliciesV2Response.class).getReplacementPolicies();
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(replacementPolicies.get(0).getDescription(), REPLACEMENT1.getDescription());
        softAssert.assertEquals(replacementPolicies.get(1).getDescription(), REPLACEMENT2.getDescription());
        softAssert.assertEquals(replacementPolicies.get(2).getDescription(), REPLACEMENT3.getDescription());
        softAssert.assertEquals(replacementPolicies.get(3).getDescription(), REPLACEMENT4.getDescription());
        softAssert.assertAll();
    }
}
