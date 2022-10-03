package ru.instamart.reforged.admin.page.shipment.shipment;

import io.qameta.allure.Step;
import ru.instamart.reforged.admin.AdminPage;
import ru.instamart.reforged.admin.block.authored_header.AuthoredHeader;
import ru.instamart.reforged.core.page.Window;

public final class ShipmentPage implements AdminPage, ShipmentCheck, Window {

    public AuthoredHeader interactAuthoredHeader() {
        return authoredHeader;
    }

    @Step("Нажимаем на кнопку 'Магазин и время доставки'")
    public void clickDeliveryWindowLink() {
        deliveryWindowLink.click();
    }

    @Step("Нажимаем на кнопку 'Платежи'")
    public void clickOnPayments() {
        paymentsLink.click();
    }

    @Step("Нажимаем на кнопку 'Отправить промокод'")
    public void clickOnSendPromo() {
        sendPromo.click();
    }

    @Step("Нажимаем на кнопку 'Согласовать'")
    public void clickOnApprove() {
        compensationPromoApproveButton.click();
    }

    @Step("Нажимаем на кнопку 'Развернуть'")
    public void clickOnExpandButton() {
        compensationPromoExpandButton.click();
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
