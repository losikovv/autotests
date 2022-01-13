package ru.instamart.reforged.core.component;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import ru.instamart.reforged.core.Kraken;

import static java.util.Objects.isNull;

@Slf4j
public final class Table extends AbstractComponent {

    private final ElementCollection lines;

    public Table() {
        super(By.xpath("//table"), "таблица");
        this.lines = new ElementCollection(By.xpath("//tbody/tr"), "вложенный элемент таблицы");
    }

    public Table(final String description) {
        super(By.xpath("//table"), description);
        this.lines = new ElementCollection(By.xpath("//tbody/tr"), description);
    }

    public Table(final String description, final String errorMsg) {
        super(By.xpath("//table"), description, errorMsg);
        this.lines = new ElementCollection(By.xpath("//tbody/tr"), description, errorMsg);
    }

    @Override
    protected WebElement getComponent() {
        log.debug("Create {} with locator {}", getClass().getSimpleName(), getBy());
        if (isNull(component) || isCacheDisable) {
            component = Kraken.waitAction().shouldBeVisible(this);
        }
        return component;
    }

    public WebElement getLine(final int index) {
        return lines.getComponents().get(index);
    }

    public WebElement getFirstLine() {
        return lines.getComponents().stream().findFirst().orElseThrow();
    }

    public WebElement getLastLine() {
        return lines.getComponents().stream().reduce((first, last) -> last).orElseThrow();
    }

    public WebElement getLine(final String name) {
        for (final WebElement column : lines.getComponents()) {
            if (column.getText().contains(name)) {
                return column;
            }
        }
        throw new NoSuchElementException("Строка " + name + " не найдена");
    }

    public void clickOnColumnElement(final String name) {
        for (final WebElement column : lines.getComponents()) {
            if (column.getText().contains(name)) {
                column.findElement(By.xpath("//td//a")).click();
                return;
            }
        }
        throw new NoSuchElementException("Строка " + name + " не найдена");
    }
}
