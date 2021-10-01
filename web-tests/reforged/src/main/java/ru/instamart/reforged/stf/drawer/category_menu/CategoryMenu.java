package ru.instamart.reforged.stf.drawer.category_menu;

import io.qameta.allure.Step;


public class CategoryMenu implements CategoryMenuCheck {

    @Step("Закрыть каталог")
    public void clickToClose() {
        close.click();
    }

    @Step("Выбрать и кликуть категорию {0} первого уровня")
    public void clickToFirstLevelCategoryByName(String data) {
        firstLevelCategory.clickOnElementWithText(data);
    }

    @Step("Выбрать категорию {0} из первого уровня")
    public void moveOnFirstLevelCategory(String data) {
        firstLevelCategory.moveOnElementWithText(data);
    }

    @Step("Выбрать и кликуть категорию {0} второго уровня")
    public void clickToSecondLevelCategoryByName(String data) {
        secondLevelCategory.clickOnElementWithText(data);
    }

    @Step("Выбрать категорию {0} из второго уровня")
    public void moveOnSecondLevelCategory(String data) {
        secondLevelCategory.moveOnElementWithText(data);
    }
}
