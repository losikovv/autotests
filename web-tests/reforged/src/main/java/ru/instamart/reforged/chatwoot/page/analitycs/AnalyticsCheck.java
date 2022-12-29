package ru.instamart.reforged.chatwoot.page.analitycs;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

public interface AnalyticsCheck extends Check, AnalyticsElement {

    @Step("Проверяем, что открыта страница 'Аналитика'")
    default void checkAnalyticsPageOpened() {
        header.should().visible();
    }

    @Step("Проверяем, что отображается поле выбора групп")
    default void checkTeamsSelectorVisible() {
        team.should().visible();
    }

    @Step("Проверяем, что отображается выпадающий список селектора групп")
    default void checkTeamsListVisible() {
        teamsList.should().visible();
    }

    @Step("Проверяем, что отображается выпадающий список селектора статусов")
    default void checkStatusListVisible() {
        statusSelectorOptions.should().visible();
    }

    @Step("Проверяем, что отображается список операторов в таблице")
    default void checkOperatorsTableVisible() {
        operatorsInTable.should().visible();
    }

    @Step("Проверяем, что для оператора в {column}-й строке, в колонках не пустые значения")
    default void checkOperatorColumnValueNotEmpty(final int column) {
        krakenAssert.assertFalse(operatorsTable.getOperatorName(column).isEmpty(), "Указано пустое значение в колонке 'Исполнитель' " + column + "-й строки таблицы");
        krakenAssert.assertFalse(operatorsTable.getStatus(column).isEmpty(), "Указано пустое значение в колонке 'Статус' " + column + "-й строки таблицы");
        krakenAssert.assertFalse(operatorsTable.getStatusSpentTime(column).isEmpty(), "Указано пустое значение в колонке 'Время в статусе' " + column + "-й строки таблицы");
        krakenAssert.assertFalse(operatorsTable.getLastActivity(column).isEmpty(), "Указано пустое значение в колонке 'Время последней активности' " + column + "-й строки таблицы");
        krakenAssert.assertFalse(operatorsTable.getChatsAtWork(column).isEmpty(), "Указано пустое значение в колонке 'Чатов в работе' " + column + "-й строки таблицы");
        krakenAssert.assertAll();
    }

    @Step("Проверяем, что отображается заявка инициатора '{initiatorName}'")
    default void checkConversationVisible(final String initiatorName){
        conversationInTableByName.should().visible(initiatorName.toLowerCase());
    }
}
