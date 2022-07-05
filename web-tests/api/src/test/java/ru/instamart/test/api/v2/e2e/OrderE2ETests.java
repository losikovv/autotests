package ru.instamart.test.api.v2.e2e;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.sbermarket.qase.annotation.CaseId;

@Epic("ApiV2")
@Feature("E2E тесты старого приложения")
public class OrderE2ETests extends RestBase {

    @BeforeClass(alwaysRun = true,
                 description = "Регистрация")
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2);
    }

    @AfterClass(alwaysRun = true,
                description = "Отменяем заказ")
    public void cleanup() {
        //apiV2.cancelCurrentOrder(); //todo починить 404
    }

    @CaseId(101)
    @Test(description = "Тест оформления заказа", groups = {"api-instamart-regress", "api-instamart-prod"})
    public void order() {
        apiV2.order(SessionFactory.getSession(SessionType.API_V2).getUserData(), EnvironmentProperties.DEFAULT_SID);
    }
}
