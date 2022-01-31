package ru.instamart.reforged.business.block.header;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface HeaderCheck extends HeaderElement, Check {

    @Step("Проверяем, что кнопка профиля видна")
    default void checkProfileButtonVisible() {
        waitAction().shouldBeVisible(profile);
    }
}

