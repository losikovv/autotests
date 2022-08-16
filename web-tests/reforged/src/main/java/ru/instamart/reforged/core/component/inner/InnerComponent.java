package ru.instamart.reforged.core.component.inner;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.component.AbstractComponent;
import ru.instamart.reforged.core.config.WaitProperties;

import static java.util.Objects.isNull;

@Slf4j
public abstract class InnerComponent extends AbstractComponent {

    @Getter
    private final WebElement webElement;
    @Getter
    private final long timeout;
    @Getter
    private final String description;
    @Getter
    private final String errorMsg;

    public InnerComponent(final WebElement webElement, final By by, final String description) {
        this(webElement, by, WaitProperties.BASIC_TIMEOUT, description, null);
    }

    public InnerComponent(final WebElement webElement, final By by, final long timeout, final String description, final String errorMsg) {
        super(by, timeout, description, errorMsg);
        this.webElement = webElement;
        this.timeout = timeout;
        this.description = isNull(description) ? this.getClass().getSimpleName() : description;
        this.errorMsg = isNull(errorMsg) ? "Элемент " + by + " не найден" : errorMsg;
    }

    public String getAttribute(final String attributeName) {
        return Kraken.waitAction().shouldExist(this, getWebElement()).getAttribute(attributeName);
    }
}
