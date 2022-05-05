package ru.instamart.reforged.admin.page.shipment.shipment.delivery_windows;

import io.qameta.allure.Step;
import ru.instamart.kraken.util.ThreadUtil;
import ru.instamart.reforged.admin.AdminPage;

import java.util.List;
import java.util.Set;

public final class ShipmentDeliveryWindowsPage implements AdminPage, ShipmentDeliveryWindowsCheck {

    @Step("Получаем первый доступный интервал доставки")
    public String getFirstAvailableDeliveryInterval() {
        return availableIntervals.getElementText(0);
    }

    @Step("Получаем информацию о доступных интервалах доставки")
    public Set<String> getAvailableIntervals() {
        return availableIntervals.getTextFromAllElements();
    }

    @Step("Выбираем первый доступный интервал доставки")
    public void selectFirstAvailableDeliveryInterval() {
        availableIntervals.clickOnFirst();
    }

    @Step("Кликаем в инпут выбора причины изменения интервала доставки")
    public void clickDeliveryChangeInputReason() {
        deliveryChangeReason.hoverAndClick();
        deliveryChangeReasonInput.click();
    }

    @Step("Первую доступную причину изменения интервала доставки в дропдауне")
    public void selectFirstDeliveryChangeReason() {
        ThreadUtil.simplyAwait(1);
        deliveryChangeReasons.selectFirst();
    }

    @Step("Нажимаем кнопку 'Обновить'")
    public void clickUpdateDelivery() {
        updateDeliveryInterval.click();
    }

    @Override
    public String pageUrl() {
        return "orders/%s/delivery_windows";
    }

    @Override
    public void goToPage() {
        throw new RuntimeException("Для перехода на страницу редактирования времени доставки заказа необходимо использовать метод с параметрами");
    }

    @Override
    public void goToPage(final String orderNumber) {
        openAdminPage(String.format(pageUrl(), orderNumber));
    }
}
