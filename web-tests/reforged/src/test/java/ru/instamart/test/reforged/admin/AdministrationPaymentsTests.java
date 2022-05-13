package ru.instamart.test.reforged.admin;

import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.*;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.test.reforged.BaseTest;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.helper.K8sHelper.changeToCancel;
import static ru.instamart.reforged.admin.AdminRout.*;

public class AdministrationPaymentsTests extends BaseTest {

    private final ApiHelper helper = new ApiHelper();
    private String orderNumber;

    @BeforeClass(alwaysRun = true, description = "Получаем оформленный заказ из подготовленных ранее")
    public void getOrder() {
        orderNumber = helper.getActiveOrderNumberWithComment("ORDER-FOR-SBERPAY");
        Assert.assertNotNull(orderNumber, "Не удалось получить заказ");
    }

    @AfterClass(alwaysRun = true)
    public void cancelOrder() {
        changeToCancel(orderNumber);
    }

    @CaseId(19)
    @Story("Тест создания платежа через SberPay")
    @Test(description = "Добавление нового платежа SberPay", groups = {"acceptance", "regression"})
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

        shipmentNewPayment().clickOnSave();

        shipmentPagePayments().checkPaymentSaveAlertVisible();
        shipmentPagePayments().checkPaymentByCardUnavailableVisible();

        shipmentPagePayments().checkPaymentBySberPayWaitingVisible();

        shipmentPagePayments().assertAll();
    }
}
