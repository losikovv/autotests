package ru.instamart.reforged.next.frame;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.component.Button;

public class TransferCartModal implements Close {

    private final Button confirm = new Button(By.xpath("//a[contains(.,'Да, перенести')]"), "Кнопка 'Да, перенести'");
    private final Button cancel = new Button(By.xpath("//a[contains(.,'Не нужно')]"), "Кнопка 'Не нужно'");

    @Step("Проверяем, что окно подтверждения переноса корзины на бизнес-витрину появилось")
    public void checkTransferCartModalDisplayed() {
        Kraken.waitAction().shouldBeVisible(cancel);
    }

    @Step("Нажимаем 'Да, перенести'")
    public void confirm() {
        confirm.click();
    }

    @Step("Нажимаем 'Не нужно'")
    public void cancel() {
        cancel.click();
    }
}
