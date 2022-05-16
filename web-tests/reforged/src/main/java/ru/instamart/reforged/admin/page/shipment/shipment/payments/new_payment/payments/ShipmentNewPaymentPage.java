package ru.instamart.reforged.admin.page.shipment.shipment.payments.new_payment.payments;

import io.qameta.allure.Step;
import ru.instamart.reforged.admin.AdminPage;

public final class ShipmentNewPaymentPage implements AdminPage, ShipmentNewPaymentCheck {

    @Step("Нажать на лейбл 'Указать привязанный номер'")
    public void clickOnSetPhoneLabel() {
        setPhoneLabel.click();
    }

    @Step("Нажать на кнопку 'Отправить уведомление'")
    public void clickOnSendNotificationButton() {
        sendNotification.click();
    }

    @Step("Вводим в поле 'Телефон'")
    public void fillPhone(final String phone) {
        phoneInput.hoverAndClick();
        phoneInput.fillField(phone, true);
    }

    @Step("Нажать на кнопку возврата на модельном окне")
    public void clickOnSendNotificationModalButton() {
        sendNotificationModalButton.click();
    }

    @Step("Нажать на кнопку 'Cохранить'")
    public void clickOnSave() {
        save.click();
    }

    @Override
    public String pageUrl() {
        return "orders/%s/payments/new";
    }

    @Override
    public void goToPage() {
        throw new RuntimeException("Для перехода на страницу нового платежа необходимо использовать метод с параметрами");
    }

    @Override
    public void goToPage(final String orderNumber) {
        openAdminPage(String.format(pageUrl(), orderNumber));
    }
}
