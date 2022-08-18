package ru.instamart.test.reforged.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.api.model.shopper.app.ShipmentSHP;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.util.StringUtil;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.admin.AdminRout.*;

@Epic("Админка STF")
@Feature("Заказы")
@Story("Страница заказа /orders/:order_id/edit")
public final class AdministrationOrdersEditTests {

    private final ApiHelper helper = new ApiHelper();
    private ShipmentSHP.Data shipment;

    @BeforeClass(alwaysRun = true, description = "Получаем оформленный заказ из подготовленных ранее")
    public void getOrder() {
        shipment = helper.getShipmentByComment("UI-TEST-SINGLE");
        Assert.assertNotNull(shipment, "Не удалось получить заказ");
    }

    @CaseId(133)
    @Test(description = "В шапке указан выбранный юзером слот доставки", groups = "regression")
    public void checkDeliverySlotVisible() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        shipments().goToPageOld();
        shipments().setShipmentOrOrderNumber(shipment.getAttributes().getNumber());
        shipments().search();
        shipments().waitPageLoad();

        shipments().clickToShipmentNumber(0);

        shipmentPage().checkOrderInfoVisible();
        shipmentPage().clickDeliveryWindowLink();

        shipmentPageDelivery().checkSavedDeliveryIntervalVisible();
    }

    @CaseId(135)
    @Test(description = "Проверка успешного изменения времени доставки", groups = "regression")
    public void checkEditDeliverySlot() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        shipments().goToPageOld();
        shipments().setShipmentOrOrderNumber(shipment.getAttributes().getNumber());
        shipments().search();
        shipments().waitPageLoad();

        shipments().clickToShipmentNumber(0);

        shipmentPage().checkOrderInfoVisible();
        shipmentPage().clickDeliveryWindowLink();

        shipmentPageDelivery().checkSavedDeliveryIntervalVisible();
        shipmentPageDelivery().checkSavedDeliveryIntervalTextContains(shipment.getAttributes().getDeliveryInterval());

        final String newDeliveryInterval = StringUtil.getTimeFromDeliveryText(shipmentPageDelivery().getFirstAvailableDeliveryInterval());
        shipmentPageDelivery().selectFirstAvailableDeliveryInterval();

        shipmentPageDelivery().clickDeliveryChangeInputReason();
        shipmentPageDelivery().checkReasonsDisplayed();
        shipmentPageDelivery().selectFirstDeliveryChangeReason();
        shipmentPageDelivery().clickUpdateDelivery();
        shipmentPageDelivery().checkNoticePopupDisplayed();
        shipmentPageDelivery().checkNoticeTextEquals("Успешно обновлено");
        shipmentPageDelivery().checkSavedDeliveryIntervalTextContains(newDeliveryInterval);
    }

    @CaseId(137)
    @Test(description = "В интервале указан лимит доступных слотов - занятые/общее кол-во слотов", groups = "regression")
    public void checkSlotsLimitDisplayedInInterval() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        shipments().goToPageOld();
        shipments().setShipmentOrOrderNumber(shipment.getAttributes().getNumber());
        shipments().search();
        shipments().waitPageLoad();

        shipments().clickToShipmentNumber(0);

        shipmentPage().checkOrderInfoVisible();
        shipmentPage().clickDeliveryWindowLink();

        shipmentPageDelivery().checkSavedDeliveryIntervalVisible();
        shipmentPageDelivery().checkSlotsInfoInAvailableIntervals(shipmentPageDelivery().getAvailableIntervals());
    }
}
