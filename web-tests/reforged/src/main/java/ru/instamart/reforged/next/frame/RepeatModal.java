package ru.instamart.reforged.next.frame;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;

import static ru.instamart.reforged.core.Kraken.waitAction;

public final class RepeatModal implements Check, Close {

    private final Element modal = new Element(By.xpath("//div[@data-qa='user-shipment-repeat-modal']"), "модальное окно повтора заказа");
    private final Button accept = new Button(By.xpath("//button[@data-qa='user-shipment-repeat-modal-btn-repeat']"), "кнопка Добавить товары");
    private final Button decline = new Button(By.xpath("//button[@data-qa='user-shipment-repeat-modal-btn-dismiss']"), "кнопка 'Не добавлять'");

    @Step("Проверка видимости модального окна Повторить заказ")
    public void checkModalWindowVisible() {
        waitAction().shouldBeVisible(modal);
        waitAction().shouldNotBeAnimated(modal);
    }

    @Step("Подтвердить действие")
    public void clickToAccept() {
        accept.click();
        checkRequestsWasLoad();
    }

    @Step("Отклонить действие")
    public void clickToDecline() {
        decline.click();
    }
}
