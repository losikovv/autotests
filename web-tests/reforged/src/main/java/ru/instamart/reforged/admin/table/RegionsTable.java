package ru.instamart.reforged.admin.table;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.instamart.reforged.core.component.Table;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Slf4j
public final class RegionsTable extends Table {

    private final By editRegion = By.xpath(".//button/span[text()='Настройка региона']");
    private final By editDispatching = By.xpath(".//button/span[text()='Настройка диспетчеризации']");
    private final By editWfs = By.xpath(".//button/span[text()='Настройка WFS']");
    private final By editCandidates = By.xpath(".//button/span[text()='Настройка кандидатов']");

    public void clickToEditRegion(final int line) {
        clickToEdit(line, editRegion);
    }

    public void clickToEditRegion(final String regionName) {
        clickToEdit(getIndexByColumnDataName(Column.REGION.label, regionName), editRegion);
    }

    public void clickToEditDispatching(final String regionName) {
        clickToEdit(getIndexByColumnDataName(Column.REGION.label, regionName), editDispatching);
    }

    public void clickToEditWfs(final String regionName) {
        clickToEdit(getIndexByColumnDataName(Column.REGION.label, regionName), editWfs);
    }

    public void clickToEditCandidates(final String regionName) {
        clickToEdit(getIndexByColumnDataName(Column.REGION.label, regionName), editCandidates);
    }

    public List<String> getAllRegionsId() {
        return getElementsFromColumn(Column.ID.label)
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public int getAllRegionsIdCount() {
        return getElementsFromColumn(Column.ID.label).size();
    }

    public List<String> getAllRegionsName() {
        return getElementsFromColumn(Column.REGION.label)
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public int getAllRegionsNameCount() {
        return getElementsFromColumn(Column.REGION.label).size();
    }

    private void clickToEdit(final int line, final By by) {
        final var cellElement = getCellElement(Column.ACTION.label, line);
        if (isNull(cellElement)) {
            log.error("Can't find cell element {} on line {}", Column.ACTION, line);
            return;
        }
        cellElement.findElement(by).click();
    }

    @RequiredArgsConstructor
    public enum Column {

        ID("#"),
        REGION("Регион"),
        ACTION("ACTION");

        @Getter
        private final String label;
    }
}
