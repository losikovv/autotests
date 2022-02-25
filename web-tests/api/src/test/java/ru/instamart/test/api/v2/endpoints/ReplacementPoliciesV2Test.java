package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import ru.sbermarket.qase.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.model.v2.ReplacementPolicyV2;
import ru.instamart.api.request.v2.ReplacementPoliciesV2Request;
import ru.instamart.api.response.v2.ReplacementPoliciesV2Response;

import java.util.List;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.enums.v2.ReplacementPolicyV2.*;

@Epic("ApiV2")
@Feature("Способы замены")
public class ReplacementPoliciesV2Test extends RestBase {

    @CaseId(811)
    @Test(groups = {"api-instamart-smoke", "api-instamart-prod"},
            description = "Получить список способов замены")
    public void getListReplacementMethods() {
        final Response response = ReplacementPoliciesV2Request.GET();
        checkStatusCode200(response);
        List<ReplacementPolicyV2> replacementPolicies = response.as(ReplacementPoliciesV2Response.class).getReplacementPolicies();
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(replacementPolicies.get(0).getDescription(), REPLACEMENT_1.getDescription(), "Ожидался иной способ замены");
        softAssert.assertEquals(replacementPolicies.get(1).getDescription(), REPLACEMENT_2.getDescription(), "Ожидался иной способ замены");
        softAssert.assertEquals(replacementPolicies.get(2).getDescription(), REPLACEMENT_3.getDescription(), "Ожидался иной способ замены");
        softAssert.assertEquals(replacementPolicies.get(3).getDescription(), REPLACEMENT_4.getDescription(), "Ожидался иной способ замены");
        softAssert.assertAll();
    }
}
