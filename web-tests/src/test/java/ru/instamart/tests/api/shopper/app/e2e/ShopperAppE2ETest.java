package ru.instamart.tests.api.shopper.app.e2e;

import ru.instamart.api.checkpoint.InstamartApiCheckpoints;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.helper.RegistrationHelper;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.core.testdata.UserManager;
import ru.instamart.ui.common.pagesdata.EnvironmentData;
import ru.instamart.ui.common.pagesdata.UserData;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Shopper Mobile API")
@Feature("E2E тесты")
public class ShopperAppE2ETest extends RestBase {
    String shipmentNumber;

    @BeforeMethod(alwaysRun = true,
            description = "Оформляем заказ")
    public void preconditions() {
        final UserData user = UserManager.getUser();
        RegistrationHelper.registration(user);
        OrderV2 order = apiV2.order(user, EnvironmentData.INSTANCE.getDefaultSid(), 4);
        shipmentNumber = order.getShipments().get(0).getNumber();
        InstamartApiCheckpoints.checkIsDeliveryToday(order);
    }

    @AfterMethod(alwaysRun = true,
            description = "Удаляем текущую сборку")
    public void cleanup() {
    }

    @Story("Сборка заказа")
    @CaseId(1)
    @Test(  description = "Собираем все позиции в заказе",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void simpleCollect() {
        shopper.simpleCollect(shipmentNumber);
    }

    @Story("Сборка заказа")
    @CaseId(2)
    @Test(  description = "Собираем/отменяем/заменяем позиции в заказе",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void complexCollect() {
        shopper.complexCollect(shipmentNumber);
    }
}
