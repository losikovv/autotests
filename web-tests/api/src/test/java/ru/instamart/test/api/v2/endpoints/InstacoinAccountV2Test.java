package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import ru.sbermarket.qase.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.UserV2;
import ru.instamart.api.request.v2.InstacoinAccountV2Request;
import ru.instamart.api.response.v2.InstacoinAccountV2Response;
import ru.instamart.jdbc.dao.SpreeUsersDao;

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
            groups = {"api-instamart-regress"})
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
        UserV2 user = apiV2.getProfile().getUser();
        Long userId = SpreeUsersDao.INSTANCE.getIdByEmail(user.getEmail());
        execRakeTaskAddBonus(user.getEmail(), "100", userId.toString());
        final Response response = InstacoinAccountV2Request.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, InstacoinAccountV2Response.class);
        compareTwoObjects(100.0, response.as(InstacoinAccountV2Response.class).getInstacoinAccount().getAmount());
    }
}

