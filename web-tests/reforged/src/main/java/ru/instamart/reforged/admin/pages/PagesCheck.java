package ru.instamart.reforged.admin.pages;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.Kraken;

public interface PagesCheck extends Check, PagesElement {

    @Step("Проверяем присутствие элемента Таблица на странице")
    default void checkTable() {
        Kraken.waitAction().shouldBeVisible(tableElement);
    }

    @Step("Проверяем присутствие элемента Внутри Таблицы на странице")
    default void checkTableEntry() {
        Kraken.waitAction().shouldBeVisible(tableEntry);
    }
}
