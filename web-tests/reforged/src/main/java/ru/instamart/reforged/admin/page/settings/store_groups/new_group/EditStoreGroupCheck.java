package ru.instamart.reforged.admin.page.settings.store_groups.new_group;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface EditStoreGroupCheck extends Check, EditStoreGroupElements {

    @Step("Проверяем, что отображается поле ввода 'Название'")
    default void checkTitleInputVisible() {
        waitAction().shouldBeVisible(title);
    }
}
