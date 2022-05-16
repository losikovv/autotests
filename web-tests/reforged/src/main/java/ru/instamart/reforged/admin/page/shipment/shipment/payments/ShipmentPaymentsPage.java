package ru.instamart.reforged.admin.page.shipment.shipment.payments;

import io.qameta.allure.Step;
import ru.instamart.reforged.admin.AdminPage;

public final class ShipmentPaymentsPage implements AdminPage, ShipmentPaymentsCheck {

    @Step("Нажать на 'Новый платеж'")
    public void clickOnNewPaymentLink() {
        newPayment.click();
    }

    @Override
    public String pageUrl() {
        return "orders/%s/payments";
    }

    @Override
    public void goToPage() {
        throw new RuntimeException("Для перехода на страницу платежей заказа необходимо использовать метод с параметрами");
    }

    @Override
    public void goToPage(final String orderNumber) {
        openAdminPage(String.format(pageUrl(), orderNumber));
    }
}
