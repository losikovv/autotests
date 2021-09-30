package ru.instamart.reforged.stf.drawer.category_menu;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.ElementCollection;

public class CategoryMenu implements CategoryMenuCheck {


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
