package ru.instamart.reforged.core.component;

import io.qameta.allure.Step;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.instamart.kraken.util.StringUtil;
import ru.instamart.kraken.util.ThreadUtil;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.component.inner.InnerButton;
import ru.instamart.reforged.core.component.inner.InnerCollectionComponent;
import ru.instamart.reforged.core.component.inner.InnerInput;

import java.util.Set;

import static java.util.Objects.isNull;

@ToString(callSuper = true)
@Slf4j
public final class SelectorMulti extends AbstractComponent {
    // Локатор поля селектора имеет класс "ant-select", например //бла-бла-бла//div[contains(@class,'ant-select ')]
    // Локатор выпадающего списка (в DOM лежит отдельно от селектора) имеет класс "ant-select-dropdown", например //бла-бла-бла//div[contains(@class,'ant-select-dropdown ')]
    // Они связаны по id (см. getControlsId())

    private final InnerInput input = new InnerInput(getComponent(), By.xpath(".//input"), "Инпут селектора");
    private final InnerCollectionComponent selectedItemsInInput = new InnerCollectionComponent(getComponent(), By.xpath(".//div[@class='ant-select-selection-overflow-item']"), "Выбранные пункты в селекторе");
    private final InnerButton removeItemByName = new InnerButton(getComponent(), ByKraken.xpathExpression(".//span[contains(@class,'content')][.='%s']/following-sibling::span"), "Кнопка 'X'(удалить) выбранного пункта селектора по имени");
    private final InnerButton clearSelected = new InnerButton(getComponent(), By.xpath(".//span[@class='ant-select-clear']//span"), "Кнопка 'Х' (очистить)");

    private final Element dropdownList = new Element(ByKraken.xpathExpression("//div[contains(@class,'ant-select-dropdown')][.//div[@id='%s']]"), "Выпадающий список элементов селектора");
    private final ElementCollection visibleDropdownItems = new ElementCollection(ByKraken.xpathExpression("//div[@id='%s']/following-sibling::div//div[contains(@class,'ant-select-item ')]/div"), "Отображаемые (присутствующие в DOM) элементы выпадающего списка");
    private final Element dropdownItemByName = new Element(ByKraken.xpathExpression("//div[contains(@class,'ant-select-item ')]/div[contains(.,'%s')]"), "Элемент выпадающего списка по имени");
    private final Element dropdownItemByNameExactly = new Element(ByKraken.xpathExpression("//div[contains(@class,'ant-select-item ')]/div[.='%s']"), "Элемент выпадающего списка по имени (точное совпадение)");
    private final Element scrollBarIndicator = new Element(ByKraken.xpathExpression("//div[@id='%s']/following-sibling::div//div[contains(@class,'rc-virtual-list-scrollbar-thumb')]"), "Индикатор полосы прокрутки выпадающего списка");

    private final String controlsId;

    public SelectorMulti(final By by, final String description) {
        super(by, description);
        controlsId = getControlsId();
    }

    public SelectorMulti(final By by, final long timeout, final String description) {
        super(by, timeout, description);
        controlsId = getControlsId();
    }

    public SelectorMulti(final By by, final String description, final String errorMsg) {
        super(by, description, errorMsg);
        controlsId = getControlsId();
    }

    public SelectorMulti(final By by, final long timeout, final String description, final String errorMsg) {
        super(by, timeout, description, errorMsg);
        controlsId = getControlsId();
    }

    private String getControlsId() {
        return input.getAttribute("aria-controls");
    }

    @Override
    protected WebElement getComponent() {
        log.debug("Create {} with locator {}", getDescription(), getBy());
        if (isNull(component) || isCacheDisable) {
            component = Kraken.waitAction().shouldExist(this);
        }
        return component;
    }

    public void checkSelectedItemsInInputNotVisible() {
        Kraken.waitAction().shouldNotBeVisible(selectedItemsInInput, getComponent());
    }

    public void checkDropdownVisible() {
        Kraken.waitAction().shouldBeVisible(dropdownList, controlsId);
    }

    public void checkDropdownNotVisible() {
        Kraken.waitAction().shouldNotBeVisible(dropdownList, controlsId);
    }

    public void click() {
        getComponent().click();
    }

    public void clickItemInDropdownByName(final String itemName) {
        ThreadUtil.simplyAwait(1);
        input.fill(itemName);
        dropdownItemByName.click(itemName);
    }

    //Точное совпадение по имени
    public void clickItemInDropdownByNameExactly(final String itemName) {
        ThreadUtil.simplyAwait(1);
        input.fill(itemName);
        dropdownItemByNameExactly.click(itemName);
    }

    public void removeSelectedItemFromInputByName(final String itemName) {
        removeItemByName.click(itemName);
    }

    @Step("Кликаем 'x' (очистить)")
    public void clearSelectedInInput() {
        this.getActions().mouseOver();
        clearSelected.click();
    }

    @Step("Получаем все значения из выпадающего списка селектора")
    public Set<String> getAllValuesFromDropdown() {
        Set<String> dropdownValues = visibleDropdownItems.getTextFromAllElements(controlsId);
        var currentPosition = 0.0;
        var previousPosition = 0.0;
        if (scrollBarIndicator.isDisplayed(controlsId))
            do {
                previousPosition = StringUtil.stringToDouble(scrollBarIndicator.getElement(controlsId).getCssValue("top"));
                Kraken.action().clickAndHold(scrollBarIndicator.getElement(controlsId)).moveByOffset(0, 12).release().build().perform();
                currentPosition = StringUtil.stringToDouble(scrollBarIndicator.getElement(controlsId).getCssValue("top"));

                dropdownValues.addAll(visibleDropdownItems.getTextFromAllElements(controlsId));
            } while (scrollBarIndicator.isDisplayed(controlsId) && currentPosition > previousPosition);
        dropdownValues.remove("");
        return dropdownValues;
    }

    @Step("Получаем значение выбранных значений из поля ввода")
    public Set<String> getAllSelectedItemsInInput() {
        return selectedItemsInInput.getTextFromAllElements();
    }
}
