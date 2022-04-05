package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import ru.instamart.kraken.data.user.UserData;
import ru.sbermarket.qase.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.v2.InstacoinAccountV2Request;
import ru.instamart.api.response.v2.InstacoinAccountV2Response;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.helper.K8sHelper.execRakeTaskAddBonus;

@Epic("ApiV2")
@Feature("Бонусный счет")
public class InstacoinAccountV2Test extends RestBase {

    @BeforeMethod(alwaysRun = true)
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2);
    }

    @CaseId(555)
    @Story("Бонусный счет пользователя")
    @Test(description = "У пользователя нет бонусов",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void getNonExistingInstacoinAccount() {
        final Response response = InstacoinAccountV2Request.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, InstacoinAccountV2Response.class);
        compareTwoObjects(0.0, response.as(InstacoinAccountV2Response.class).getInstacoinAccount().getAmount());
    }

    @CaseId(557)
    @Story("Бонусный счет пользователя")
    @Test(description = "У пользователя есть бонусы",
            groups = {"api-instamart-regress"})
    public void getInstacoinAccount() {
        UserData user = SessionFactory.getSession(SessionType.API_V2).getUserData();
        execRakeTaskAddBonus(user.getEmail(), "100", user.getId());
        final Response response = InstacoinAccountV2Request.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, InstacoinAccountV2Response.class);
        compareTwoObjects(100.0, response.as(InstacoinAccountV2Response.class).getInstacoinAccount().getAmount());
    }
}

