package ru.instamart.reforged.core.component;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.instamart.reforged.core.Kraken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
public class TableComponent extends AbstractComponent {

    private static final By table = By.xpath("//table");

    private final By headerLine = By.xpath("./thead/tr");
    private final By headerCell = By.xpath("./th");

    private final By dataLine = By.xpath("./tbody/child::tr[not(ancestor::tr)]");
    private final By dataCell = By.xpath("./child::td[not(ancestor::td)]");

    public TableComponent() {
        super(table, "таблица");
    }

    @Override
    protected WebElement getComponent() {
        log.debug("Create {} with locator {}", getClass().getSimpleName(), getBy());
        if (isNull(component) || isCacheDisable) {
            component = Kraken.waitAction().shouldBeVisible(this);
        }
        return component;
    }

    public List<String> getDataFromColumn(final String columnTitle) {
        return getElementsFromColumn(columnTitle)
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public List<WebElement> getElementsFromColumn(final String columnTitle) {
        final var columnElements = getColumn(columnTitle);
        if (isNull(columnElements) || columnElements.isEmpty()) {
            log.error("Column data empty or null for title={}", columnTitle);
            return Collections.emptyList();
        }
        return columnElements;
    }

    protected String getDataFromCell(final String columnTitle, final int line) {
        final var cell = getCellElement(columnTitle, line);
        if (isNull(cell)) {
            log.error("Can't find cell with title={} on line={}", columnTitle, line);
            return "empty";
        }
        return cell.getText();
    }

    protected void clickOnCell(final String columnTitle, final int line) {
        final var cell = getCellElement(columnTitle, line);
        if (isNull(cell)) {
            log.error("Can't find cell with title={} on line={}", columnTitle, line);
            return;
        }
        cell.click();
    }

    protected WebElement getCellElement(final String columnTitle, final int line) {
        final var headerTitles = getHeaderCellElements();
        final var dataLines = getTableDataLines();
        if (dataLines.isEmpty() || headerTitles.isEmpty()) {
            log.error("Header or data is empty. header size={}, data lines size={}", headerTitles.size(), dataLines.size());
            return null;
        }
        if (dataLines.size() < line) {
            log.error("Invalid line={} count lines in table equals {}", line, dataLines.size());
            return null;
        }
        final var cellInLine = getCellsFromLine(dataLines.get(line));
        final var index = columnTitle.equalsIgnoreCase("ACTION")
                ? headerTitles.size() - 1 : getColumnOrderByName(columnTitle, headerTitles);
        if (cellInLine.size() > index) {
            return cellInLine.get(index);
        }
        log.error("Invalid cell count");
        return null;
    }

    protected int getIndexOf(final String text, final List<String> data) {
        for (int i = 0; i < data.size(); i++) {
            if (text.equalsIgnoreCase(data.get(i))) {
                return i;
            }
        }
        return 0;
    }

    protected List<WebElement> getTableDataLines() {
        return getComponent().findElements(dataLine);
    }

    public int getRowsCount() {
        return getTableDataLines().size();
    }

    protected WebElement getLine(final int lineIndex) {
        final var dataLines = getTableDataLines();
        if (nonNull(dataLines) && dataLines.size() > lineIndex) {
            return dataLines.get(lineIndex);
        }
        log.error("Cant' get line with index={}", lineIndex);
        return null;
    }

    private List<WebElement> getCellsFromLine(final WebElement line) {
        return line.findElements(dataCell);
    }

    private List<WebElement> getColumn(final String columnTitle) {
        final var headerTitles = getHeaderCellElements();
        final var dataLines = getTableDataLines();
        if (dataLines.isEmpty() || headerTitles.isEmpty()) {
            log.error("Header or data is empty. header size={}, data size={}", headerTitles.size(), dataLines.size());
            return null;
        }

        final var index = getColumnOrderByName(columnTitle, headerTitles);
        final var column = new ArrayList<WebElement>();
        dataLines.forEach(line -> {
            final var cells = getCellsFromLine(line);
            if (!cells.isEmpty()) {
                column.add(cells.get(index));
            }
        });
        return column;
    }

    private int getColumnOrderByName(final String columnTitle, final List<WebElement> headerElements) {
        for (int i = 0; i < headerElements.size(); i++) {
            final var text = headerElements.get(i).getText();
            if (text.equalsIgnoreCase(columnTitle)) {
                return i;
            }
        }
        return 0;
    }

    private List<WebElement> getHeaderCellElements() {
        return getHeaderLine().findElements(headerCell);
    }

    private WebElement getHeaderLine() {
        return getComponent().findElement(headerLine);
    }
}
