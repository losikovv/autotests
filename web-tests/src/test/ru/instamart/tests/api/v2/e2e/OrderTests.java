package ru.instamart.tests.api.v2.e2e;

import instamart.api.common.RestBase;
import instamart.core.common.AppManager;
import instamart.ui.common.pagesdata.UserData;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class OrderTests extends RestBase {
    private UserData user;

    @BeforeClass(alwaysRun = true,
                 description = "Регистрация")
    public void preconditions() {
        user = user();
        apiV2.registration(user);
    }

    @AfterClass(alwaysRun = true,
                description = "Отменяем заказ")
    public void cleanup() {
        apiV2.cancelCurrentOrder();
    }

    @CaseId(101)
    @Test(description = "Тест оформления заказа", groups = "api-v2-regress")
    public void order() {
        apiV2.order(user, AppManager.environment.getDefaultSid());
    }
}
