package ru.instamart.reforged.admin.page.shipment.shipment.send_promo.confirm_modal;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Element;

public final class ConfirmModal {

    private final Element modal = new Element(By.xpath("//div[@role='dialog' and contains(@class,'modal-confirm')]"), "Модальное окно");
    private final Element sendButton = new Element(By.xpath("//div[@role='dialog' and contains(@class,'modal-confirm')]//button[contains(@class,'primary')]"), "Кнопка отправки промо на модальном окне");

    @Step("Нажимаем на кнопку 'Отправить'")
    public void clickOnSendPromo() {
        sendButton.click();
    }

    @Step("Проверяем, что отобразилось модальное окно")
    public void checkModalVisible() {
        modal.should().visible();
        modal.should().notAnimated();
    }
}