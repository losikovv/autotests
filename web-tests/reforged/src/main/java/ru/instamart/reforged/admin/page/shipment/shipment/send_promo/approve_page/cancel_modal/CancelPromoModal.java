package ru.instamart.reforged.admin.page.shipment.shipment.send_promo.approve_page.cancel_modal;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Element;

public final class CancelPromoModal {

    private final Element modal = new Element(By.xpath("//div[@aria-label='Отмена промокода']"), "Модальное окно отмены промо");

    @Step("Проверяем, что отображается модальное окно отказа промо")
    public void checkCancelPromoModalVisible() {
        modal.should().visible();
    }
}
