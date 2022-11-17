package ru.instamart.reforged.core.component.inner;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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
        this(webElement, by, WaitProperties.WAITING_TIMEOUT, description);
    }

    public InnerComponent(final WebElement webElement, final By by, final long timeout, final String description) {
        super(by, timeout, description);
        this.webElement = webElement;
        this.timeout = timeout;
        this.description = isNull(description) ? this.getClass().getSimpleName() : description;
        this.errorMsg = String.format("Для элемента '%s', '%s' не выполнено условие: ", description, by);
    }
}
