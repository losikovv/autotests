package ru.instamart.reforged.admin.page.shipment.shipment.send_promo;

import io.qameta.allure.Step;
import ru.instamart.reforged.admin.AdminPage;
import ru.instamart.reforged.admin.page.shipment.shipment.send_promo.confirm_modal.ConfirmModal;

public final class SendPromoPage implements AdminPage, SendPromoCheck {

    public ConfirmModal interactConfirmModal() {
        return confirmModal;
    }

    @Step("Выбираем причину выдачи промокода")
    public void setReason(final String reasonBy) {
        reasonInList.click(reasonBy);
    }

    @Step("Открываем выпадающий список причин выдачи промокода")
    public void openReasonsList() {
        reason.click();
    }

    @Step("Вводим почту - {0}")
    public void fillEmail(final String email) {
        emailInput.fillField(email);
    }

    @Step("Открываем выпадающий список компенсаций")
    public void openCompensationsList() {
        compensation.click();
    }

    @Step("Выбираем компенсацию")
    public void setCompensation(final String compensationType) {
        compensationInList.click(compensationType);
    }

    @Step("Клик в выпадающий список возвратных товаров")
    public void clickOnReturnItemsList() {
        compensationItem.click();
    }

    @Step("Выбираем первый продукт")
    public void setFirstItem() {
        compensationItemInList.clickOnFirst();
    }

    @Step("Открываем выпадающий список суммы компенсации")
    public void openCompensationValueList() {
        compensationValue.click();
    }

    @Step("Выбираем cумму компенсации")
    public void setCompensationValue(final String compensationValue) {
        compensationValueInput.fill(compensationValue);
        compensationValueInList.click(compensationValue);
    }

    @Step("Нажимаем на кнопку 'Отправить промокод'")
    public void clickOnSendPromo() {
        sendPromoButton.click();
    }

    @Override
    public String pageUrl() {
        return "orders/%s/compensations/new";
    }

    @Override
    public void goToPage() {
        throw new RuntimeException("Для перехода на страницу отправки промо необходимо использовать метод с параметрами");
    }
}