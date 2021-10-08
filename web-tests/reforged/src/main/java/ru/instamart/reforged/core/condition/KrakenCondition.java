package ru.instamart.reforged.core.condition;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import ru.instamart.kraken.util.StringUtil;
import ru.instamart.reforged.core.Kraken;

import javax.annotation.Nullable;

import static java.util.Objects.nonNull;

@Slf4j
public final class KrakenCondition {

    public static ExpectedCondition<Boolean> keysSendCondition(final WebElement element, final String data, final boolean isPhone) {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(@Nullable WebDriver driver) {
                if (element.isDisplayed()) {
                    var value = element.getAttribute("value");

                    if (nonNull(value) && value.length() != 0) {
                        if (isPhone) {
                            value = StringUtil.getPhone(value);
                        }
                        element.sendKeys(Keys.COMMAND + "a");
                        element.sendKeys(Keys.CONTROL + "a");
                        element.sendKeys(Keys.DELETE);
                    }
                    element.sendKeys(data);
                    return value.equals(data);
                }
                return false;
            }

            @Override
            public String toString() {
                return String.format("element found by %s", element);
            }
        };
    }

    public static ExpectedCondition<Boolean> elementSelectCheckboxState(final By by, final boolean selected) {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    var element = findElement(by);
                    if (element.isSelected() != selected) {
                        element.click();
                        return false;
                    } else {
                        return true;
                    }
                } catch (StaleElementReferenceException e) {
                    log.error("Can't set state for checkbox '{}'", by);
                }

                return false;
            }

            @Override
            public String toString() {
                return String.format("element found by %s to %sbe selected", by, (selected ? "" : "not "));
            }
        };
    }

    private static WebElement findElement(final By by) {
        try {
            return Kraken.getWebDriver().findElements(by).stream().findFirst().orElseThrow(
                    () -> new NoSuchElementException("Cannot locate an element using " + by));
        } catch (NoSuchElementException e) {
            throw e;
        } catch (WebDriverException e) {
            log.warn(String.format("WebDriverException thrown by findElement(%s)", by), e);
            throw e;
        }
    }
}
