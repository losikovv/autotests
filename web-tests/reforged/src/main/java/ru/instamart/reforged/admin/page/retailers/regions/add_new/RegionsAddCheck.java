package ru.instamart.reforged.admin.page.retailers.regions.add_new;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface RegionsAddCheck extends Check, RegionsAddElements {

    @Step("Проверяем, что кнопка добавления нового региона отображается")
    default void checkAddNewRegionModalNotAnimated() {
        waitAction().shouldNotBeAnimated(newRegionName);
    }

}
