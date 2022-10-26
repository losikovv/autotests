package ru.instamart.reforged.admin.table;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.instamart.reforged.core.component.Table;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

//Партнеры -> Список партнеров
@Slf4j
public final class ShoppersTable extends Table {

    private final By addTag = By.xpath(".//span[@data-qa='shoppers_list_add_tag']");
    private final By expandTags = By.xpath(".//button[@data-qa='shoppers_list_collapse_tag_list']");
    private final By tag = By.xpath(".//span[contains(@class,'ant-tag ') and not(@data-qa)]");
    private final By tagDeleteButton = By.xpath(".//span[contains(@class,'ant-tag ') and not(@data-qa)]/span");

    public int getRowsCount() {
        return getTableDataLines().size();
    }

    public void clickOnAddTag(final int line) {
        final var cellElement = getCellElement(Column.TAGS.label, line);
        if (nonNull(cellElement)) {
            cellElement.findElement(addTag).click();
        }
    }

    public boolean checkAddTagButtonVisible(final int line) {
        final var cellElement = getCellElement(Column.TAGS.label, line);
        if (nonNull(cellElement)) {
            return !cellElement.findElements(addTag).isEmpty();
        }
        return false;
    }

    public boolean checkExpandButtonVisible(final int line) {
        final var cellElement = getCellElement(Column.TAGS.label, line);
        if (nonNull(cellElement)) {
            return !cellElement.findElements(expandTags).isEmpty();
        }
        return false;
    }

    public void clickOnExpandButton(final int line) {
        final var cellElement = getCellElement(Column.TAGS.label, line);
        if (nonNull(cellElement)) {
            cellElement.findElement(expandTags).click();
        }
    }

    public String getExpandButtonText(final int line) {
        final var cellElement = getCellElement(Column.TAGS.label, line);
        if (nonNull(cellElement)) {
            return cellElement.findElement(expandTags).getText();
        }
        return "empty";
    }

    public List<String> getPartnersNames() {
        return getDataFromColumn(Column.FULLNAME.label);
    }

    public List<String> getAllTagsTextFromLine(final int line) {
        return getAllTagsFromLine(line)
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public List<WebElement> getAllTagsDeleteButtons(final int line) {
        return getCellElement(Column.TAGS.label, line)
                .findElements(tagDeleteButton);
    }

    public List<WebElement> getAllTagsFromLine(final int line) {
        return getCellElement(Column.TAGS.label, line)
                .findElements(tag);
    }

    @RequiredArgsConstructor
    public enum Column {

        ID("#"),
        LOGIN("Логин"),
        FULLNAME("Имя и Фамилия"),
        PHONE("Телефон"),
        TAGS("Теги"),
        CURRENT_STORE("Текущий магазин"),
        ROLES("Роли"),
        STATUS("Статус");

        @Getter
        private final String label;
    }

}
