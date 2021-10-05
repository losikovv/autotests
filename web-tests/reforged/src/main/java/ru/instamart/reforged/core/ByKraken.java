package ru.instamart.reforged.core;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.FindsByXPath;

import java.io.Serializable;
import java.util.List;

import static java.util.Objects.isNull;

public final class ByKraken extends By implements Serializable {

    private static final long serialVersionUID = 1L;

    @Getter
    private final String defaultXpathExpression;
    private final String xpathExpression;

    public ByKraken(final String xpathExpression, final Object... args) {
        this.defaultXpathExpression = xpathExpression;
        //TODO: Заменить медленный format на свою реализацию
        this.xpathExpression = String.format(defaultXpathExpression, args);
    }

    @Override
    public List<WebElement> findElements(SearchContext context) {
        return ((FindsByXPath) context).findElementsByXPath(xpathExpression);
    }

    @Override
    public WebElement findElement(SearchContext context) {
        return ((FindsByXPath) context).findElementByXPath(xpathExpression);
    }

    public static By xpath(final String name) {
        return xpath(name, "");
    }

    public static By xpath(final String name, final Object... args) {
        if (isNull(name))
            throw new IllegalArgumentException(
                    "Cannot find elements when name text is null.");
        return new ByKraken(name, args);
    }

    @Override
    public String toString() {
        return "By.xpath: " + xpathExpression;
    }
}
