package ru.instamart.reforged.core.component.inner;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.instamart.kraken.util.StringUtil;
import ru.instamart.reforged.core.Kraken;

@Slf4j
public final class InnerInput extends InnerComponent {

    public InnerInput(WebElement webElement, By by, final String description) {
        super(webElement, by, description);
    }

    @Override
    protected WebElement getComponent() {
        return Kraken.waitAction().shouldBeClickable(this, getWebElement());
    }

    public void fill(final String data) {
        final var component = getComponent();
        component.clear();
        log.debug("Fill {} with locator {} and data {}", getDescription(), getBy(), data);
        component.sendKeys(data);
    }

    public void click() {
        log.debug("Click {} with locator {}", getDescription(), getBy());
        getComponent().click();
    }

    public int getNumericValue() {
        log.debug("Получить численное значение");
        var debutText = getComponent().getText();
        log.debug("Текст с числовым значением {}", debutText);
        return StringUtil.extractNumberFromString(debutText);
    }

    public String getValue() {
        return getAttribute("value");
    }

    public String getAttribute(final String attributeName) {
        log.debug("Get data by attribute {}", attributeName);
        return getComponent().getAttribute(attributeName);
    }
}
