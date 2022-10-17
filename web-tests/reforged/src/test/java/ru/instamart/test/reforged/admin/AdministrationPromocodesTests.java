package ru.instamart.test.reforged.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.Group.PHOENIX_SMOKE;
import static ru.instamart.reforged.Group.REGRESSION_ADMIN;
import static ru.instamart.reforged.admin.AdminRout.*;

@Epic("Админка STF")
@Feature("Работа с промокодами")
public final class AdministrationPromocodesTests {

    @CaseId(1535)
    @Story("Выдача промо под ролью call_center_dept")
    @Test(description = "Выдача промокода на бесплатную доставку", groups = {REGRESSION_ADMIN, PHOENIX_SMOKE})
    public void freeDeliveryPromoIssuing() {
        login().goToPage();
        login().auth(UserManager.getCallCenterDeptOperator());

        shipments().goToPageOld();
        shipments().waitPageLoad();

        shipments().clickToShipmentNumber(0);

        shipmentPage().clickOnSendPromo();

        var email = Generate.email();
        sendPromoPage().checkCompensationPromoPageVisible();
        sendPromoPage().fillEmail(email);

        var reason = "Опоздание доставки (плановая)";
        sendPromoPage().openReasonsList();
        sendPromoPage().setReason(reason);

        var compensation = "Бесплатная доставка";
        sendPromoPage().openCompensationsList();
        sendPromoPage().setCompensation(compensation);

        sendPromoPage().clickOnSendPromo();

        sendPromoPage().interactConfirmModal().checkModalVisible();
        sendPromoPage().interactConfirmModal().clickOnSendPromo();

        shipmentPage().checkCompensationPromoPending();
    }

    @CaseId(1530)
    @Story("Выдача промо под ролью call_center_dept")
    @Test(description = "Выдача промокода на сумму <= 1000 руб.", groups = {REGRESSION_ADMIN, PHOENIX_SMOKE})
    public void below1000PromoIssuing() {
        login().goToPage();
        login().auth(UserManager.getCallCenterDeptOperator());

        shipments().goToPageOld();
        shipments().waitPageLoad();

        shipments().clickToShipmentNumber(1);

        shipmentPage().clickOnSendPromo();

        var email = Generate.email();
        sendPromoPage().checkCompensationPromoPageVisible();
        sendPromoPage().fillEmail(email);

        var reason = "Возврат ненадлежащего товара";
        sendPromoPage().openReasonsList();
        sendPromoPage().setReason(reason);

        var compensation = "Промокод на скидку";
        sendPromoPage().openCompensationsList();
        sendPromoPage().setCompensation(compensation);

        sendPromoPage().clickOnReturnItemsList();
        sendPromoPage().checkPromoCompensationItemsListVisible();
        sendPromoPage().setFirstItem();
        sendPromoPage().clickOnReturnItemsList();
        sendPromoPage().checkPromoCompensationNotVisible();

        sendPromoPage().openCompensationValueList();
        sendPromoPage().setCompensationValue("800");

        sendPromoPage().clickOnSendPromo();

        sendPromoPage().interactConfirmModal().checkModalVisible();
        sendPromoPage().interactConfirmModal().clickOnSendPromo();

        shipmentPage().checkCompensationPromoPending();
    }

    @CaseIDs(value = {@CaseId(1531), @CaseId(1537)})
    @Story("Выдача промо под ролью call_center_dept")
    @Test(description = "Отправка промокода на сумму > 1000 руб. на утверждение", groups = {REGRESSION_ADMIN, PHOENIX_SMOKE})
    public void above1000PromoIssuingOnApprove() {
        login().goToPage();
        login().auth(UserManager.getCallCenterDeptOperator());

        shipments().goToPageOld();
        shipments().waitPageLoad();

        shipments().clickToShipmentNumber(2);

        shipmentPage().clickOnSendPromo();

        var email = Generate.email();
        sendPromoPage().checkCompensationPromoPageVisible();
        sendPromoPage().fillEmail(email);

        var reason = "Возврат ненадлежащего товара";
        sendPromoPage().openReasonsList();
        sendPromoPage().setReason(reason);

        var compensation = "Промокод на скидку";
        sendPromoPage().openCompensationsList();
        sendPromoPage().setCompensation(compensation);

        sendPromoPage().clickOnReturnItemsList();
        sendPromoPage().checkPromoCompensationItemsListVisible();
        sendPromoPage().setFirstItem();
        sendPromoPage().clickOnReturnItemsList();
        sendPromoPage().checkPromoCompensationNotVisible();

        sendPromoPage().openCompensationValueList();
        sendPromoPage().setCompensationValue("1200");

        sendPromoPage().clickOnSendPromo();

        sendPromoPage().interactConfirmModal().checkModalVisible();
        sendPromoPage().interactConfirmModal().clickOnSendPromo();

        shipmentPage().checkCompensationPromoOnApprove();
    }

    @CaseId(1572)
    @Story("Выдача промо под ролью call_center_dept_leader")
    @Test(description = "Выдача промокода на сумму > 1000 руб.", groups = {REGRESSION_ADMIN, PHOENIX_SMOKE})
    public void above1000PromoIssuing() {
        login().goToPage();
        login().auth(UserManager.getCallCenterDeptLeader());

        shipments().goToPageOld();
        shipments().waitPageLoad();

        shipments().clickToShipmentNumber(3);

        shipmentPage().clickOnSendPromo();

        var email = Generate.email();
        sendPromoPage().checkCompensationPromoPageVisible();
        sendPromoPage().fillEmail(email);

        var reason = "Возврат ненадлежащего товара";
        sendPromoPage().openReasonsList();
        sendPromoPage().setReason(reason);

        var compensation = "Промокод на скидку";
        sendPromoPage().openCompensationsList();
        sendPromoPage().setCompensation(compensation);

        sendPromoPage().clickOnReturnItemsList();
        sendPromoPage().checkPromoCompensationItemsListVisible();
        sendPromoPage().setFirstItem();
        sendPromoPage().clickOnReturnItemsList();
        sendPromoPage().checkPromoCompensationNotVisible();

        sendPromoPage().openCompensationValueList();
        sendPromoPage().setCompensationValue("1200");

        sendPromoPage().clickOnSendPromo();

        sendPromoPage().interactConfirmModal().checkModalVisible();
        sendPromoPage().interactConfirmModal().clickOnSendPromo();

        shipmentPage().checkCompensationPromoPending();
    }

    @CaseIDs(value = {@CaseId(1574), @CaseId(1635)})
    @Story("Выдача промо под ролью call_center_dept_leader")
    @Test(description = "Отображение данных о выданном промокоде на странице деталей заказа", groups = {REGRESSION_ADMIN, PHOENIX_SMOKE})
    public void checkCompensationPromoInfo() {
        login().goToPage();
        login().auth(UserManager.getCallCenterDeptLeader());

        shipments().goToPageOld();
        shipments().waitPageLoad();

        shipments().clickToShipmentNumber(4);

        shipmentPage().clickOnSendPromo();

        var email = Generate.email();
        sendPromoPage().checkCompensationPromoPageVisible();
        sendPromoPage().fillEmail(email);

        var reason = "Возврат ненадлежащего товара";
        sendPromoPage().openReasonsList();
        sendPromoPage().setReason(reason);

        var compensation = "Промокод на скидку";
        sendPromoPage().openCompensationsList();
        sendPromoPage().setCompensation(compensation);

        sendPromoPage().clickOnReturnItemsList();
        sendPromoPage().checkPromoCompensationItemsListVisible();
        sendPromoPage().setFirstItem();
        sendPromoPage().clickOnReturnItemsList();
        sendPromoPage().checkPromoCompensationNotVisible();

        sendPromoPage().openCompensationValueList();
        sendPromoPage().setCompensationValue("800");

        sendPromoPage().clickOnSendPromo();

        sendPromoPage().interactConfirmModal().checkModalVisible();
        sendPromoPage().interactConfirmModal().clickOnSendPromo();

        shipmentPage().checkCompensationPromoText();
        shipmentPage().checkCompensationPromoStatus();
        shipmentPage().checkCompensationPromoDeleteButton();
        shipmentPage().checkCompensationPromoExpandButton();
        shipmentPage().checkCompensationPromoExpirationDate();

        shipmentPage().clickOnExpandButton();
        shipmentPage().checkCompensationPromoReason();
        shipmentPage().checkCompensationPromoIssueDate();
        shipmentPage().checkCompensationPromoOperatorName();
        shipmentPage().checkCompensationPromoComment();
        shipmentPage().checkCompensationPromoReturnItems();

        shipmentPage().assertAll();
    }

    @CaseId(1803)
    @Story("Выдача промо под ролью call_center_dept_leader")
    @Test(description = "При клике на кнопку 'Отказать' появляется модальное окно отклонения заявки", groups = {REGRESSION_ADMIN, PHOENIX_SMOKE})
    public void checkCancelPromoModalDisplayed() {
        login().goToPage();
        login().auth(UserManager.getCallCenterDeptOperator());

        shipments().goToPageOld();
        shipments().waitPageLoad();

        shipments().clickToShipmentNumber(5);

        shipmentPage().clickOnSendPromo();

        var email = Generate.email();
        sendPromoPage().checkCompensationPromoPageVisible();
        sendPromoPage().fillEmail(email);

        var reason = "Возврат ненадлежащего товара";
        sendPromoPage().openReasonsList();
        sendPromoPage().setReason(reason);

        var compensation = "Промокод на скидку";
        sendPromoPage().openCompensationsList();
        sendPromoPage().setCompensation(compensation);

        sendPromoPage().clickOnReturnItemsList();
        sendPromoPage().checkPromoCompensationItemsListVisible();
        sendPromoPage().setFirstItem();
        sendPromoPage().clickOnReturnItemsList();
        sendPromoPage().checkPromoCompensationNotVisible();

        sendPromoPage().openCompensationValueList();
        sendPromoPage().setCompensationValue("1200");

        sendPromoPage().clickOnSendPromo();

        sendPromoPage().interactConfirmModal().checkModalVisible();
        sendPromoPage().interactConfirmModal().clickOnSendPromo();

        shipmentPage().checkCompensationPromoOnApprove();

        shipmentPage().interactAuthoredHeader().clickToDropDown();
        shipmentPage().interactAuthoredHeader().clickToLogout();

        login().checkTitle();
        login().auth(UserManager.getCallCenterDeptLeader());

        shipmentPage().clickOnApprove();

        approvePromoPage().checkApprovePromoPageVisible();
        approvePromoPage().clickOnCancel();
        approvePromoPage().interactCancelModal().checkCancelPromoModalVisible();
    }
}