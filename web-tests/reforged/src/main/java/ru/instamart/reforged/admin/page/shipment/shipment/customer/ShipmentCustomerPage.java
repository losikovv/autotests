package ru.instamart.reforged.admin.page.shipment.shipment.customer;

import ru.instamart.reforged.admin.AdminPage;

public final class ShipmentCustomerPage implements AdminPage, ShipmentCustomerCheck {

    @Override
    public String pageUrl() {
        return "orders/%s/customer";
    }

    @Override
    public void goToPage() {
        throw new RuntimeException("Для перехода на страницу платежей заказа необходимо использовать метод с параметрами");
    }

    public void goToPage(final String orderNumber) {
        goToPage(String.format(pageUrl(), orderNumber));
    }
}
