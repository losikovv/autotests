package ru.instamart.reforged.business.frame;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.Assert;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;

public class B2BTransferCartModal implements B2BClose {

    private final Button confirmSuccessTransfer = new Button(By.xpath("//button[contains(.,'Продолжить покупки')]"), "Кнопка 'Продолжить покупки'");
    private final Button confirmTransferNegative = new Button(By.xpath("//button[contains(.,'Собрать корзину ещё раз')]"), "Кнопка 'Собрать корзину ещё раз'");
    private final Element errorText = new Element(By.xpath("//button[contains(.,'Собрать корзину ещё раз')]/../p"), "Сообщение об ошибке при переносе");

    @Step("Проверяем, что окно подтверждения успешного переноса появилось")
    public void checkTransferSuccessDisplayed() {
        Kraken.waitAction().shouldBeVisible(confirmSuccessTransfer);
    }

    @Step("Проверяем, что окно подтверждения неуспешного переноса появилось")
    public void checkTransferNegativeDisplayed() {
        Kraken.waitAction().shouldBeVisible(confirmTransferNegative);
    }

    @Step("Проверяем, что сообщение об ошибке содержит текст")
    public void checkErrorTextContains(final String expectedErrorText) {
        Assert.assertTrue(errorText.getText().contains(expectedErrorText),
                String.format("В сообщении об ошибке: '%s' не найден искомый текст: '%s'", errorText.getText(), expectedErrorText));
    }

    @Step("Нажимаем 'Перейти к покупкам'")
    public void confirm() {
        confirmSuccessTransfer.click();
    }
}
