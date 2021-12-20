package ru.instamart.reforged.admin.block.flash_alert;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.component.Element;

public final class FlashAlert {

    private final Element successFlash = new Element(By.xpath("//div[@id='flashWrapper']//div[@class = 'flash success']"), "алерт после успешного редактирования");
    private final Element errorFlash = new Element(By.xpath("//div[@id='flashWrapper']//div[@class = 'flash error']"), "алерт с ошибкой редактирования");

    @Step("Проверяем что появилась нотификация об успешном сохранении")
    public void checkSuccessFlash() {
        Kraken.waitAction().shouldBeVisible(successFlash);
    }

    @Step("Проверяем что появилась нотификация об неуспешном сохранении")
    public void checkErrorFlash() {
        Kraken.waitAction().shouldBeVisible(errorFlash);
    }
}
