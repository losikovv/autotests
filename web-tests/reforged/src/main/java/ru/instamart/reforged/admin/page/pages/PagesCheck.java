package ru.instamart.reforged.admin.page.pages;

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

    @Step("Проверяем появление алерта об удалении страницы")
    default void checkDeleteAlertVisible() {
        Kraken.waitAction().shouldBeVisible(deleteAlert);
    }

    @Step("Проверяем отсутствие страницы с id {0} внутри таблицы страниц")
    default void checkSpecificEntryNotVisible(Long id) {
        Kraken.waitAction().shouldNotBeVisible(tableEntrySpecific, id);
    }

    @Step("Проверяем страницу с id {0} внутри таблицы страниц")
    default void checkSpecificEntryVisible(Long id) {
        Kraken.waitAction().shouldNotBeVisible(tableEntrySpecific, id);
    }
}
