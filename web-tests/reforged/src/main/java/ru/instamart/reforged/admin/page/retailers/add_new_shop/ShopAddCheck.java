package ru.instamart.reforged.admin.page.retailers.add_new_shop;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface ShopAddCheck extends Check, ShopAddElements {

    @Step("Проверяем, что инпут поиска региона отображается")
    default void checkRegionInputVisible() {
        waitAction().shouldBeVisible(regionsInput);
    }

    @Step("Проверяем, что лоадер не отображается")
    default void checkGlobalLoaderNotVisible() {
        waitAction().shouldNotBeVisible(globalLoader);
    }

    @Step("Проверяем, что добавленный регион находится через поиск")
    default void checkRegionInSearchDropdownVisible(String region) {
        waitAction().shouldBeVisible(regionsSearchElement, region);
    }
}
