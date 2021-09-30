package ru.instamart.reforged.stf.drawer.category_menu;

import io.qameta.allure.Step;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface CategoryMenuCheck extends CategoryMenuElement {

    @Step("Шторка каталога открыта")
    default void checkCatalogMenuIsOpen() {
        waitAction().shouldBeVisible(categoryMenuDrawer);
    }

    @Step("Шторка каталога закрыта")
    default void checkCatalogMenuIsClosed() {
        waitAction().shouldNotBeVisible(categoryMenuDrawer);
    }
}
