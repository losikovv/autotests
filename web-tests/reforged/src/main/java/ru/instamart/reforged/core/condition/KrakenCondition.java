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

    /**
     * Проверяет состояние при котором значение инпута не начнет совпадать с ожидаемым текстом
     * @param element - элемент для поиска
     * @param data - текст для ввода
     * @param isPhone - если в true то форматирует введенный текст под номер телефона
     */
    public static ExpectedCondition<Boolean> keysSendCondition(final WebElement element, final String data, final boolean isPhone) {
        return new ExpectedCondition<>() {
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

    /**
     * Проверяет состояние до тех пор, пока значение чекбокса не будет соответствовать ожиданию
     * @param element - элемент для поиска
     * @param selected - ожидаемое значение
     */
    public static ExpectedCondition<Boolean> elementSelectCheckboxState(final WebElement element, final boolean selected) {
        return new ExpectedCondition<>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    if (element.isSelected() != selected) {
                        element.click();
                        return false;
                    } else {
                        return true;
                    }
                } catch (StaleElementReferenceException e) {
                    log.error("Can't set state for checkbox '{}'", element);
                }

                return false;
            }

            @Override
            public String toString() {
                return String.format("element found by %s to %sbe selected", element, (selected ? "" : "not "));
            }
        };
    }

    /**
     * Проверяет позицию элемента, до тех пор, пока он не прекратит изменяться
     * @param by - локатор для поиска
     */
    public static ExpectedCondition<Boolean> steadinessOfElementLocated(final By by) {
        return new ExpectedCondition<>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    var element = findElement(by);
                    var pointX1 = element.getLocation().x;
                    var pointY1 = element.getLocation().y;

                    var pointX2 = element.getLocation().x;
                    var pointY2 = element.getLocation().y;

                    return pointX1 == pointX2 || pointY1 == pointY2;
                } catch (StaleElementReferenceException e) {
                    log.error("Can't get element steadiness '{}'", by);
                }

                return false;
            }

            @Override
            public String toString() {
                return String.format("element found by %s not stop animating", by);
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
