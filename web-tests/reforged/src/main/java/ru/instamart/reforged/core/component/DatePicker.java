package ru.instamart.reforged.core.component;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.instamart.reforged.core.ByKraken;

import static java.util.Objects.isNull;

//label[@title='Создание заказа']/parent::div/following-sibling::div//input[@placeholder='Начальная дата']
@Slf4j
public final class DatePicker extends AbstractComponent {

    private DateTable dateTable = new DateTable(By.xpath(".//table"));

    public DatePicker(By by, String description) {
        super(by, description);
    }

    public DatePicker(By by, long timeout, String description) {
        super(by, timeout, description);
    }

    @Override
    public WebElement getComponent() {
        log.debug("Create {} with locator {}", getDescription(), getBy());
        if (isNull(component) || isCacheDisable) {
            component = shouldBe().clickable();
        }
        return component;
    }

    public static final class DateTable extends Table {

        private final By superPrev = By.xpath("//button[@class='ant-picker-header-super-prev-btn']");
        private final By prev = By.xpath("//button[@class='ant-picker-header-prev-btn']");

        private final By month = By.xpath("//button[@class='ant-picker-month-btn']");
        private final By year = By.xpath("//button[@class='ant-picker-year-btn']");

        private final By next = By.xpath("//button[@class='ant-picker-header-next-btn']");
        private final By superNext = By.xpath("//button[@class='ant-picker-header-super-next-btn']");

        private final ByKraken hour = (ByKraken) ByKraken.xpathExpression("//ul[@class='ant-picker-time-panel-column'][1]/li/div[text()='%s']");
        private final ByKraken min = (ByKraken) ByKraken.xpathExpression("//ul[@class='ant-picker-time-panel-column'][2]/li/div[text()='%s']");

        private final By ok = By.xpath("//button/span[text()='ОК']");

        public DateTable(final By by) {
            super(by);
        }

        public void clickToDate(final Weekday weekday, final String date) {
            clickToDate(weekday, getIndexByColumnDataName(weekday.getLabel(), date));
        }

        private void clickToDate(final Weekday weekday, final int line) {
            final var cellElement = getCellElement(weekday.getLabel(), line);
            if (isNull(cellElement)) {
                log.error("Can't find cell element {} on line {}", weekday.getLabel(), line);
                return;
            }
            cellElement.click();
        }
    }

    @RequiredArgsConstructor
    public enum Weekday {

        MONDAY("пн"),
        TUESDAY("вт"),
        WEDNESDAY("ср"),
        THURSDAY("чт"),
        FRIDAY("пт"),
        SATURDAY("сб"),
        SUNDAY("вс");

        @Getter
        private final String label;
    }
}
