package ru.instamart.reforged.core.component;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.instamart.kraken.util.StringUtil;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.Kraken;

import static java.util.Objects.isNull;

@ToString(callSuper = true)
@Slf4j
public final class Input extends AbstractComponent {

    public Input(final By by, final String description) {
        super(by, description);
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

    public void fill(final String data, final Object... args) {
        setBy(ByKraken.xpath(((ByKraken) getBy()).getDefaultXpathExpression(), args));
        fill(data);
    }

    /**
     * Очистка может не работать с полями у которых атрибут autocomplete
     * @param data - заполняемое значение
     */
    public void fill(final String data) {
        final var component = getComponent();
        component.clear();
        log.debug("Fill {} with locator {} and data {}", getDescription(), getBy(), data);
        component.sendKeys(data);
    }

    /**
     * Чаще всего используется там где есть атрибут autocomplete или происходит форматирование введенного текста js'ом
     * под капотом через ожидания заполняет и сверяет, что введенный текст соответствует,
     * тому что введено, если ввод оказывается ошибочным, то стирает все в инпуте и пытается заполнить снова
     * @param data - заполняемое значение
     * @param isPhone - из-за того что в форме авторизации js форматируется инпут, нужно передавать этот флаг в true,
     *                для всего остального false
     */
    public void fillField(final String data, final boolean isPhone) {
        log.debug("Fill with wait and check {} with locator {} and data {}", getDescription(), getBy(), data);
        Kraken.waitAction().fillField(getComponent(), data, isPhone);
    }

    /**
     * Заполнение поле с очисткой и ожиданием, что введенный текст точно совпадает с ожидаемым
     *
     * @param data - текст который необходимо ввести
     */
    public void fillField(final String data) {
        fillField(data, false);
    }

    /**
     * Устанавливает текст в инпут через js код
     *
     * @param data - текст необходимый для вставки
     */
    public void jsFill(final String data) {
        log.debug("JS Fill {} with locator {} and data {}", getDescription(), getBy(), data);
        Kraken.jsAction().setValue(getComponent(), data);
    }

    /**
     * Очистка инпута через js код
     */
    public void jsClear() {
        Kraken.jsAction().clearField(getLocator());
    }

    public void click() {
        log.debug("Click into field {}", getDescription());
        getComponent().click();
    }

    public void clear() {
        log.debug("Clear input {} field", getDescription());
        getComponent().clear();
    }

    public String getValue() {
        log.debug("Get value");
        return getComponent().getAttribute("value");
    }

    public String getText() {
        log.debug("Get text");
        return getComponent().getText();
    }

    public int getNumericValue() {
        log.debug("Получить численное значение");
        return StringUtil.extractNumberFromString(getComponent().getText());
    }
}
