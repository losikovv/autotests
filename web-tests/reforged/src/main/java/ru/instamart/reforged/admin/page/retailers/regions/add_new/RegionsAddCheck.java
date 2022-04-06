package ru.instamart.reforged.admin.page.retailers.regions.add_new;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface RegionsAddCheck extends Check, RegionsAddElements {

    @Step("Проверяем, что кнопка добавления нового региона отображается и не анимирована")
    default void checkAddNewRegionModalNotAnimated() {
        waitAction().shouldNotBeAnimated(newRegionName);
    }

    @Step("Проверяем, что модальное окно добавления региона отображается и не анимировано")
    default void checkAddNewRegionModalVisible() {
        waitAction().shouldBeVisible(addNewRegionModal);
        waitAction().shouldNotBeAnimated(addNewRegionModal);
    }

    @Step("Проверяем, что модальное окно добавления региона не отображается")
    default void checkAddNewRegionModalNotVisible() {
        waitAction().shouldNotBeVisible(addNewRegionModal);
    }

}
