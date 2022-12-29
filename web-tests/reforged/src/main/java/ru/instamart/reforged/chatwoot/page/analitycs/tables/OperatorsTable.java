package ru.instamart.reforged.chatwoot.page.analitycs.tables;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import ru.instamart.reforged.core.component.Table;

import static java.util.Objects.nonNull;

@Slf4j
public final class OperatorsTable extends Table {

    public String getOperatorName(final int line) {
        final var cellElement = getCellElement(Column.NAME.label, line);
        if (nonNull(cellElement)) {
            return cellElement.getText();
        }
        return "empty";
    }

    public String getStatus(final int line) {
        final var cellElement = getCellElement(Column.STATUS.label, line);
        if (nonNull(cellElement)) {
            return cellElement.getText();
        }
        return "empty";
    }

    public String getStatusSpentTime(final int line) {
        final var cellElement = getCellElement(Column.SPENT_TIME.label, line);
        if (nonNull(cellElement)) {
            return cellElement.getText();
        }
        return "empty";
    }

    public String getLastActivity(final int line) {
        final var cellElement = getCellElement(Column.LAST_ACTIVITY.label, line);
        if (nonNull(cellElement)) {
            return cellElement.getText();
        }
        return "empty";
    }

    public String getChatsAtWork(final int line) {
        final var cellElement = getCellElement(Column.CHATS_AT_WORK.label, line);
        if (nonNull(cellElement)) {
            return cellElement.getText();
        }
        return "empty";
    }


    @RequiredArgsConstructor
    public enum Column {

        NAME("Исполнитель"),
        STATUS("Статус"),
        SPENT_TIME("Время в статусе"),
        LAST_ACTIVITY("Время последней активности"),
        CHATS_AT_WORK("Чатов в работе");

        @Getter
        private final String label;
    }
}
