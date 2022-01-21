package ru.instamart.reforged.stf.frame;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;

public final class RepeatModal implements Close {

    private final Element modal = new Element(By.xpath("//div[@data-qa='user-shipment-repeat-modal']"), "модальное окно повтора заказа");
    private final Button accept = new Button(By.xpath("//button[@data-qa='user-shipment-repeat-modal-btn-repeat']"), "кнопка Добавить товары");
    private final Button decline = new Button(By.xpath("//button[@data-qa='user-shipment-repeat-modal-btn-dismiss']"), "кнопка 'Не добавлять'");

    @Step("Проверка видимости модального окна Повторить заказ")
    public void checkModalWindowVisible() {
        Kraken.waitAction().shouldBeVisible(modal);
    }

    @Step("Подтвердить действие")
    public void clickToAccept() {
        accept.click();
    }

    @Step("Отклонить действие")
    public void clickToDecline() {
        decline.click();
    }
}
