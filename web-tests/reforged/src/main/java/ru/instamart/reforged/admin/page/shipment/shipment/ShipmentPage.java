package ru.instamart.reforged.admin.page.shipment.shipment;

import io.qameta.allure.Step;
import ru.instamart.reforged.admin.AdminPage;
import ru.instamart.reforged.core.page.Window;

public final class ShipmentPage implements AdminPage, ShipmentCheck, Window {

    @Step("Нажимаем на кнопку 'Магазин и время доставки'")
    public void clickDeliveryWindowLink() {
        deliveryWindowLink.click();
    }

    @Step("Нажимаем на кнопку 'Платежи'")
    public void clickOnPayments() {
        paymentsLink.click();
    }

    @Override
    public String pageUrl() {
        return "orders/%s/edit";
    }

    @Override
    public void goToPage() {
        throw new RuntimeException("Для перехода на страницу редактирования заказа необходимо использовать метод с параметрами");
    }
}
