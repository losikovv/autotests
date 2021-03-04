package ru.instamart.tests.api.v2.e2e;

import instamart.api.SessionFactory;
import instamart.api.common.RestBase;
import instamart.ui.common.pagesdata.EnvironmentData;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class OrderTests extends RestBase {

    @BeforeClass(alwaysRun = true,
                 description = "Регистрация")
    public void preconditions() {
        SessionFactory.makeSession();
    }

    @AfterClass(alwaysRun = true,
                description = "Отменяем заказ")
    public void cleanup() {
        apiV2.cancelCurrentOrder();
    }

    @CaseId(101)
    @Test(description = "Тест оформления заказа", groups = "api-instamart-regress")
    public void order() {
        apiV2.order(SessionFactory.getSession().getUserData(), EnvironmentData.INSTANCE.getDefaultSid());
    }
}
