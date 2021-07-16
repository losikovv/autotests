package ru.instamart.reforged.stf.drawer;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.ElementCollection;

public class CategoryMenu {

    private final Button close = new Button(By.xpath("//div[@data-qa='category-menu']//button[@data-qa='close-button']"));
    private final ElementCollection category = new ElementCollection(By.xpath("//div[@data-qa='category-menu']//div[@class='category-menu-item__title']"));

    @Step("Закрыть каталог")
    public void clickToClose() {
        close.click();
    }

    @Step("Выбрать и кликуть категорию {0}")
    public void clickToCategoryByName(String data) {
        category.clickOnElementWithText(data);
    }

    @Step("Выбрать категорию {0}")
    public void moveOnCategory(String data) {
        category.moveOnElementWithText(data);
    }
}
