package ru.instamart.reforged.stf.drawer;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;

public class StoreSelector {

    private final Button close = new Button(By.xpath("//button[@data-qa='close-button']"));
    private final Element storeCard = new Element(By.xpath("//a[@data-qa='store-card']"));

    @Step("Закрыть выбор магазинов")
    public void clickToCloseButton() {
        close.click();
    }

    @Step("Выбрать первый магазин в списке")
    public void clickToStoreCard() {
        storeCard.click();
    }
}
