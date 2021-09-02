package ru.instamart.reforged.stf.page.seo;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface SeoCatalogCheck extends Check, SeoCatalogElement {

    @Step("Отображается сетка товаров")
    default void checkProductGridVisible() {
        waitAction().shouldBeVisible(productGrid);
    }
}
