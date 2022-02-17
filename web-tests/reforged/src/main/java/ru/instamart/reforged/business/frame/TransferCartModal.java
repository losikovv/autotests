package ru.instamart.reforged.business.frame;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.component.Button;

public class TransferCartModal implements Close {

    private final Button confirmSuccessTransfer = new Button(By.xpath("//button[contains(.,'Продолжить покупки')]"), "Кнопка 'Продолжить покупки'");
    private final Button confirmTransferNegative = new Button(By.xpath("//button[contains(.,'Собрать корзину ещё раз')]"), "Кнопка 'Собрать корзину ещё раз'");

    @Step("Проверяем, что окно подтверждения успешного переноса появилось")
    public void checkTransferSuccessDisplayed() {
        Kraken.waitAction().shouldBeVisible(confirmSuccessTransfer);
    }

    @Step("Проверяем, что окно подтверждения неуспешного переноса появилось")
    public void checkTransferNegativeDisplayed() {
        Kraken.waitAction().shouldBeVisible(confirmTransferNegative);
    }

    @Step("Нажимаем 'Перейти к покупкам'")
    public void confirm() {
        confirmSuccessTransfer.click();
    }
}
