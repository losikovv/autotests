package ru.instamart.reforged.stf.page.seo;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface SeoCatalogCheck extends Check, SeoCatalogElement {

    @Step("Отображается сетка товаров")
    default void checkProductGridVisible() {
        waitAction().shouldBeVisible(productGrid);
    }

    @Step("Отображается загаловок страницы каталога")
    default void checkCatalogTitleVisible() {
        waitAction().shouldBeVisible(catalogPageTitle);
    }

    @Step("Проверяем, не отображается спиннер")
    default void checkSpinnerIsNotVisible() {
        waitAction().shouldNotBeVisible(spinner);
    }
}
