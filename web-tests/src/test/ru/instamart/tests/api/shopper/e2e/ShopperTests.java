package ru.instamart.tests.api.shopper.e2e;

import instamart.api.checkpoints.InstamartApiCheckpoints;
import instamart.api.common.RestBase;
import instamart.api.helpers.RegistrationHelper;
import instamart.api.objects.v2.Order;
import instamart.core.testdata.UserManager;
import instamart.ui.common.pagesdata.EnvironmentData;
import instamart.ui.common.pagesdata.UserData;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ShopperTests extends RestBase {
    String shipmentNumber;

    @BeforeMethod(alwaysRun = true,
            description = "Оформляем заказ")
    public void preconditions() {
        final UserData user = UserManager.getUser();
        RegistrationHelper.registration(user);
        Order order = apiV2.order(user, EnvironmentData.INSTANCE.getDefaultSid(), 4);
        shipmentNumber = order.getShipments().get(0).getNumber();
        InstamartApiCheckpoints.assertIsDeliveryToday(order);
    }

    @AfterMethod(alwaysRun = true,
            description = "Удаляем текущую сборку")
    public void cleanup() {
        shopper.deleteCurrentAssembly();
        apiV2.cancelCurrentOrder();
    }

    @CaseId(1)
    @Test(  description = "Собираем все позиции в заказе",
            groups = {"api-shopper-regress"})
    public void simpleCollect() {
        shopper.simpleCollect(shipmentNumber);
    }

    @CaseId(2)
    @Test(  description = "Собираем/отменяем/заменяем позиции в заказе",
            groups = {"api-shopper-regress"})
    public void complexCollect() {
        shopper.complexCollect(shipmentNumber);
    }
}
