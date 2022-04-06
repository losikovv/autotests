package ru.instamart.test.api.admin_api;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.v1.MobileConfigsV1Request;
import ru.instamart.api.response.v1.MobileConfigsV1Response;
import ru.instamart.kraken.data.Generate;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkMobileConfig;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV1")
@Feature("Конфигурация")
public class MobileConfigsV1Test extends RestBase {

    private int mobileExtendId;
    String property;

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        admin.authApi();
    }

    @CaseId(2544)
    @Story("Конфигурация системы")
    @Test(groups = {"api-instamart-regress"},
            description = "Редактирование информации о настройках смс")
    public void editMobileConfigs() {
        mobileExtendId = RandomUtils.nextInt(1, 100);
        property = "Autotest" + Generate.literalString(6);
        final Response response = MobileConfigsV1Request.PATCH(mobileExtendId, property);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, MobileConfigsV1Response.class);
        checkMobileConfig(response, mobileExtendId, property);
    }

    @CaseId(2544)
    @Story("Конфигурация системы")
    @Test(groups = {"api-instamart-regress"},
            description = "Редактирование информации о настройках смс",
            dependsOnMethods = "editMobileConfigs")
    public void getMobileConfigs() {
        final Response response = MobileConfigsV1Request.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, MobileConfigsV1Response.class);
        checkMobileConfig(response, mobileExtendId, property);
    }
}
