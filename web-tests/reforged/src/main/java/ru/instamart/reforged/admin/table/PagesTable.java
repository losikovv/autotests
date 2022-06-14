package ru.instamart.reforged.admin.table;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import ru.instamart.kraken.util.StringUtil;
import ru.instamart.reforged.core.component.TableComponent;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
public final class PagesTable extends TableComponent {

    private final By edit = By.xpath(".//button[@data-qa='pages_page_edit']");
    private final By remove = By.xpath(".//button[@data-qa='pages_page_delete']");

    public String getTitle(final int line) {
        return getDataFromCell(Column.TITLE.label, line);
    }

    public String getLink(final int line) {
        return getDataFromCell(Column.LINK.label, line);
    }

    public void clickToEdit(final int line) {
        final var cellElement = getCellElement(Column.ACTION.label, line);
        if (isNull(cellElement)) {
            log.error("Can't find cell element {} on line {}", Column.ACTION, line);
            return;
        }
        cellElement.findElement(edit).click();
    }

    public void clickToEdit(final String title) {
        final var columnData = getDataFromColumn(Column.TITLE.label);
        final var index = getIndexOf(title, columnData);
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

    public void clickToRemove(final String title) {
        final var columnData = getDataFromColumn(Column.TITLE.label);
        final var index = getIndexOf(title, columnData);
        clickToRemove(index);
    }

    public void clickToRemoveById(final long id) {
        final var dataLines = getTableDataLines();
        for (int i = 0; i < dataLines.size(); i++) {
            final var line = dataLines.get(i);
            final var tmp = line.getAttribute("id");
            if (nonNull(tmp) && !tmp.isEmpty()) {
                final var pageId = StringUtil.stringToLong(tmp);
                if (pageId == id) {
                    clickToRemove(i);
                    return;
                }
            }
        }
    }

    public long getPageId(final String pageName) {
        final var columnData = getDataFromColumn(Column.TITLE.label);
        final var index = getIndexOf(pageName, columnData);
        final var pageId = getLine(index).getAttribute("id");

        return nonNull(pageId) && !pageId.isEmpty() ? StringUtil.stringToLong(pageId) : 0;
    }

    @RequiredArgsConstructor
    public enum Column {

        TITLE("Заголовок"),
        LINK("Ссылка"),
        PUBLISH("Публиковать"),
        ACTION("")
        ;

        @Getter
        private final String label;
    }
}
