package ru.instamart.reforged.admin.popover;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;

public final class Popover {

    private final Element popover = new Element(ByKraken.xpathExpression("//div[text()='%s']"), "окно поповера");
    private final Button ok = new Button(By.xpath("//button/span[text()='OK']"), "кнопка ОК");
    private final Button cancel = new Button(By.xpath("//button/span[text()='Отмена']"), "кнопка Отмена");

    @Step("Нажать на кнопку 'ОК'")
    public void clickToOk() {
        ok.click();
    }

    @Step("Нажать на кнопку 'Отмена'")
    public void clickToCancel() {
        cancel.click();
    }

    @Step("Поповер '{0}' отображается")
    public void checkPopoverVisible(final String popoverName) {
        popover.should().visible(popoverName);
    }
}
