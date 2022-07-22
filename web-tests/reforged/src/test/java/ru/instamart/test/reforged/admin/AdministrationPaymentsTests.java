package ru.instamart.test.reforged.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.*;
import ru.instamart.api.enums.v2.StateV2;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.jdbc.dao.stf.SpreeOrdersDao;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.admin.AdminRout.*;

@Epic("Админка STF")
@Feature("Работа с платежами")
public final class AdministrationPaymentsTests {

    private final ApiHelper helper = new ApiHelper();
    private String orderNumber;

    @BeforeClass(alwaysRun = true, description = "Получаем оформленный заказ из подготовленных ранее")
    public void getOrder() {
        orderNumber = helper.getActiveOrderNumberWithComment("ORDER-FOR-SBERPAY");
        Assert.assertNotNull(orderNumber, "Не удалось получить заказ");
    }

    @AfterClass(alwaysRun = true)
    public void cancelOrder() {
        SpreeOrdersDao.INSTANCE.updateShipmentState(orderNumber, StateV2.CANCELED.getValue());
    }

    @CaseId(492)
    @Story("Тест создания платежа через SberPay")
    @Test(description = "Добавление нового платежа SberPay", groups = "regression")
    public void successPaymentViaSberPay() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        shipments().openAdminPageWithoutSpa(shipments().pageUrl());
        shipments().setShipmentOrOrderNumber(orderNumber);
        shipments().search();
        shipments().waitPageLoad();

        shipments().clickToShipmentNumber(0);

        shipmentPage().clickOnPayments();

        shipmentPagePayments().checkPaymentByCardPendingVisible();
        shipmentPagePayments().clickOnNewPaymentLink();

        shipmentNewPayment().clickOnSetPhoneLabel();

        shipmentNewPayment().fillPhone("9000000000");
        shipmentNewPayment().clickOnSendNotificationButton();

        shipmentNewPayment().checkSendNotificationModalVisisble();
        shipmentNewPayment().clickOnSendNotificationModalButton();
        shipmentNewPayment().checkSendNotificationModalNotVisisble();

        shipmentNewPayment().clickOnSave();

        shipmentPagePayments().checkPaymentSaveAlertVisible();
        shipmentPagePayments().checkPaymentByCardUnavailableVisible();

        shipmentPagePayments().checkPaymentBySberPayWaitingVisible();

        shipmentPagePayments().assertAll();
    }

    @CaseId(497)
    @Story("Тест создания платежа через SberPay")
    @Test(description = "Добавление нового платежа SberPay", groups = "regression")
    public void checkDebitBalanceVisibleTest() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        shipments().openAdminPageWithoutSpa(shipments().pageUrl());
        shipments().setShipmentOrOrderNumber(orderNumber);
        shipments().search();
        shipments().waitPageLoad();

        shipments().clickToShipmentNumber(0);

        shipmentPage().clickOnPayments();

        shipmentPagePayments().checkDebetBalanceVisible();
        shipmentPagePayments().checkDebetBalancePositive();
    }
}
