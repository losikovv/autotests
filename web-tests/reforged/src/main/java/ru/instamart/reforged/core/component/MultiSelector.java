package ru.instamart.reforged.core.component;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.Kraken;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Slf4j
public final class MultiSelector extends AbstractComponent {

    private final By input = By.xpath(".//input[@type='search']");
    private final ByKraken itemInDropDown = (ByKraken) ByKraken.xpathExpression("//div[contains(@class,'ant-select-item ')]/div[.='%s']");
    private final ByKraken unselectedItem = (ByKraken) ByKraken.xpathExpression("//div[@aria-selected='false']/div");
    private final By selected = By.xpath(".//div[@class='ant-select-selection-overflow-item']");
    private final ByKraken removeItem = (ByKraken) ByKraken.xpathExpression(".//span[contains(@class,'content')][.='%s']/following-sibling::span");
    private final By clearSelector = By.xpath(".//span[@class='ant-select-clear']//span");

    private final ByKraken scroll = (ByKraken) ByKraken.xpathExpression("//div[@id='%s']/following-sibling::div//div[contains(@class,'rc-virtual-list-scrollbar-thumb')]");

    public MultiSelector(By by, String description) {
        super(by, description);
    }

    public MultiSelector(By by, long timeout, String description) {
        super(by, timeout, description);
    }

    public MultiSelector(By by, String description, String errorMsg) {
        super(by, description, errorMsg);
    }

    public MultiSelector(By by, long timeout, String description, String errorMsg) {
        super(by, timeout, description, errorMsg);
    }

    @Override
    protected WebElement getComponent() {
        log.debug("Create {} with locator {}", getDescription(), getBy());
        if (isNull(component) || isCacheDisable) {
            component = shouldBe().clickable();
        }
        return component;
    }

    public void fill(final String name) {
        final var component = getComponent();
        component.findElement(input).sendKeys(name);
        component.findElement(ByKraken.xpathExpression(itemInDropDown.getDefaultXpathExpression(), name)).click();
    }

    public void selectUnselectedFirst() {
        final var component = getComponent();
        component.findElement(input).click();
        component.findElement(unselectedItem).click();
    }

    public void selectAll() {
        final var component = getComponent();
        component.findElement(input).click();
        final var scrollElement = component.findElement(ByKraken.xpathExpression(scroll.getDefaultXpathExpression(), component.findElement(input).getAttribute("aria-controls")));
        while (!component.findElements(unselectedItem).isEmpty()) {
            component.findElements(unselectedItem).forEach(s -> {
                if (scrollElement.isDisplayed()) {
                    Kraken.action().clickAndHold(scrollElement).moveByOffset(0, 15).release().build().perform();
                    s.click();
                } else {
                    s.click();
                }
            });
        }
    }

    public List<String> getAllSelectedName() {
       return getComponent().findElements(selected).stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public void removeItemByName(final String name) {
        getComponent().findElement(ByKraken.xpathExpression(removeItem.getDefaultXpathExpression(), name)).click();
    }

    public void removeAll() {
        getComponent().findElement(clearSelector).click();
    }
}
