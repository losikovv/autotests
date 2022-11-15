package ru.instamart.reforged.stf.drawer.category_menu;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface CategoryMenuCheck extends CategoryMenuElement, Check {

    @Step("Шторка каталога открыта")
    default void checkCatalogMenuIsOpen() {
        waitAction().shouldBeVisible(categoryMenuDrawer);
    }

    @Step("Проверка видимости первого уровня каталога")
    default void checkFirstLevelElementsVisible() {
        waitAction().shouldBeVisible(firstLevelCategory);
    }

    @Step("Шторка каталога закрыта")
    default void checkCatalogMenuIsClosed() {
        Assert.assertTrue(categoryMenuDrawer.is().invisible());
    }
}
