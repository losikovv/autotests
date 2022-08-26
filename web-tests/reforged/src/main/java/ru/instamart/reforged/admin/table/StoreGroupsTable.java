package ru.instamart.reforged.admin.table;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Table;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
public final class StoreGroupsTable extends Table {

    private final By edit = By.xpath(".//button[@data-qa='storeLabelListEdit']");
    private final By remove = By.xpath(".//button[@data-qa='storeLabelListDelete']");

    public String getGroupName(final int line) {
        final var cellElement = getCellElement(Column.NAME.label, line);
        if (nonNull(cellElement)) {
            return cellElement.getText();
        }
        return "empty";
    }

    public List<String> getGroupNames() {
        return getDataFromColumn(Column.NAME.label);
    }

    public void clickToEdit(final int line) {
        final var cellElement = getCellElement(Column.ACTION.label, line);
        if (isNull(cellElement)) {
            log.error("Can't find cell element {} on line {}", Column.ACTION, line);
            return;
        }
        cellElement.findElement(edit).click();
    }

    public void clickToEdit(final String groupName) {
        final var columnData = getDataFromColumn(Column.NAME.label);
        final var index = getIndexOf(groupName, columnData);
        clickToEdit(index);
    }

    public void clickToRemove(final int line) {
        final var cellElement = getCellElement(Column.ACTION.label, line);
        if (isNull(cellElement)) {
            log.error("Can't find cell element {} on line {}", Column.ACTION, line);
            return;
        }
        cellElement.findElement(remove).click();
    }

    public void clickToRemove(final String groupName) {
        final var columnData = getDataFromColumn(Column.NAME.label);
        final var index = getIndexOf(groupName, columnData);
        clickToRemove(index);
    }

    @RequiredArgsConstructor
    public enum Column {

        NAME("Название"),
        TAGLINE("Слоган"),
        CODE("Код"),
        ICON("Иконка"),
        POSITION("Позиция"),
        LEVEL("Уровень"),
        ACTION("ACTION");

        @Getter
        private final String label;
    }
}
