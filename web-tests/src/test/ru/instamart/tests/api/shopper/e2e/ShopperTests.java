package ru.instamart.tests.api.shopper.e2e;

import instamart.api.checkpoints.ApiV2Checkpoints;
import instamart.api.common.RestBase;
import instamart.api.objects.v2.Order;
import instamart.core.common.AppManager;
import instamart.ui.common.pagesdata.UserData;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ShopperTests extends RestBase {
    String shipmentNumber;

    @BeforeMethod(alwaysRun = true,
            description = "Оформляем заказ")
    public void preconditions() {
        UserData user = user();
        apiV2.registration(user);
        Order order = apiV2.order(user, AppManager.environment.getDefaultSid(), 4);
        shipmentNumber = order.getShipments().get(0).getNumber();
        ApiV2Checkpoints.assertIsDeliveryToday(order);
    }

    @AfterClass(alwaysRun = true,
            description = "Удаляем текущую сборку")
    public void cleanup() {
        if (shopper.authorized()) shopper.deleteCurrentAssembly();
    }

    @Test(  description = "Собираем все позиции в заказе",
            groups = {"api-shopper-regress"})
    public void simpleCollect() {
        shopper.simpleCollect(shipmentNumber);
    }

    @Test(  description = "Собираем/отменяем/заменяем позиции в заказе",
            groups = {"api-shopper-regress"})
    public void complexCollect() {
        shopper.complexCollect(shipmentNumber);
    }
}
