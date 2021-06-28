package ru.instamart.reforged.core.component;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import ru.instamart.reforged.core.Kraken;

import static java.util.Objects.isNull;

@Slf4j
public final class Table extends Component {

    private ElementCollection lines = new ElementCollection(By.xpath("//tbody/tr"));

    public Table() {
        super(By.xpath("//table"));
    }

    public Table(final String description) {
        super(By.xpath("//table"), description);
    }

    public Table(final String description, final String errorMsg) {
        super(By.xpath("//table"), description, errorMsg);
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

    @Override
    protected WebElement getComponent() {
        log.info("Create {} with locator {}", getClass().getSimpleName(), getBy());
        if (isNull(component) || isCashDisable) {
            component = Kraken.waitAction().shouldBeVisible(this);
        }
        return component;
    }
}
