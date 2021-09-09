package ru.instamart.reforged.core.component;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.config.BrowserProperties;

import java.util.regex.Matcher;

import static java.util.Objects.isNull;

@ToString(callSuper = true)
@Slf4j
public final class Input extends Component {

    public Input(final By by) {
        super(by);
    }

    public Input(final By by, final String description) {
        super(by, description);
    }

    public Input(final By by, final long timeout) {
        super(by, timeout);
    }

    public Input(final By by, final long timeout, final String description) {
        super(by, timeout, description);
    }

    public Input(final By by, final String description, final String errorMsg) {
        super(by, description, errorMsg);
    }

    public Input(final By by, final long timeout, final String description, final String errorMsg) {
        super(by, timeout, description, errorMsg);
    }

    @Override
    protected WebElement getComponent() {
        log.debug("Create {} with locator {}", getDescription(), getBy());
        if (isNull(component) || isCacheDisable) {
            component = Kraken.waitAction().shouldBeClickable(this);
        }
        return component;
    }

    public void fill(final String data) {
        clear();

        if (BrowserProperties.USE_JS_FILL) {
            jsFill(data);
        } else {
            log.info("Fill {} with locator {} and data {}", getDescription(), getBy(), data);
            getComponent().sendKeys(data);
        }
    }

    /**
     * Заполнение поле с очисткой и ожиданием, что введенный текст точно совпадает с ожидаемым
     * @param data - текст который необходимо ввести
     */
    public void fillField(final String data) {
        log.info("Fill with wait and check {} with locator {} and data {}", getDescription(), getBy(), data);
        Kraken.waitAction().fillField(getComponent(), data);
    }

    /**
     * Устанавливает текст в инпут через js код
     *
     * @param data - текст необходимый для вставки
     */
    public void jsFill(final String data) {
        log.info("JS Fill {} with locator {} and data {}", getDescription(), getBy(), data);
        Kraken.jsAction().setValue(getComponent(), data);
    }

    /**
     * Очистка инпута через js код
     */
    public void jsClear() {
        final Matcher matcher = LOCATOR.matcher(getBy().toString());
        while (matcher.find()) {
            log.info("Clear input field '{}' from js", getDescription());
            Kraken.jsAction().clearField(matcher.group());
        }
    }

    public void click() {
        log.info("Click into field {}", getDescription());
        getComponent().click();
    }

    public void clear() {
        log.info("Clear input {} field", getDescription());
        getComponent().clear();
    }

    public String getValue() {
        log.info("Get value");
        return getComponent().getAttribute("value");
    }
}
