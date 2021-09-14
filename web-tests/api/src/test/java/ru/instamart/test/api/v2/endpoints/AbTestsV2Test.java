package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.v2.AbTestsV2Request;
import ru.instamart.api.response.v2.AbTestsV2Response;

import java.util.UUID;

import static org.testng.Assert.assertNotNull;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode200;

@Epic("ApiV2")
@Feature("АБ тесты")
@Deprecated
public class AbTestsV2Test extends RestBase {

    @BeforeClass(alwaysRun = true)
    @Step("Создание сессии")
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2_FB);
    }

    @Deprecated
    @Story("Получить список всех АБ тестов для авторизованного устройства")
    @Test(groups = {},
            description = "Получить список всех АБ тестов для авторизованного устройства с существующим id")
    public void AbTest200() {
        response = AbTestsV2Request.GET(UUID.randomUUID().toString());
        checkStatusCode200(response);
        AbTestsV2Response abTestsV2Response = response.as(AbTestsV2Response.class);
        assertNotNull(abTestsV2Response.getAbTests(), "response вернул null");
    }

    @Deprecated
    @Story("Получить список всех АБ тестов для авторизованного устройства")
    @Test(groups = {},
            description = "Получить список всех АБ тестов для авторизованного устройства с несуществующим id")
    public void AbTest404() {
        response = AbTestsV2Request.GET();
        checkStatusCode200(response);
        AbTestsV2Response abTestsV2Response = response.as(AbTestsV2Response.class);
        assertNotNull(abTestsV2Response.getAbTests(), "response вернул null");
    }
}
