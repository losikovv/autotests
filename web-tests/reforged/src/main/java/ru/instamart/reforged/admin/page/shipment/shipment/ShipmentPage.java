package ru.instamart.reforged.admin.page.shipment.shipment;

import io.qameta.allure.Step;
import ru.instamart.reforged.admin.AdminPage;

public final class ShipmentPage implements AdminPage, ShipmentCheck {

    @Step("Нажимаем на кнопку 'Магазин и время доставки'")
    public void clickDeliveryWindowLink() {
        deliveryWindowLink.click();
    }

    @Override
    public String pageUrl() {
        return "orders/%s/edit";
    }

    @Override
    public void goToPage() {
        throw new RuntimeException("Для перехода на страницу редактирования заказа необходимо использовать метод с параметрами");
    }

    @Override
    public void goToPage(final String orderNumber) {
        openAdminPage(String.format(pageUrl(), orderNumber));
    }
}
