package ru.instamart.reforged.stf.component;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.instamart.kraken.setting.Config;

@RequiredArgsConstructor
@ToString
@Slf4j
public abstract class Component {

    protected WebElement component;

    @Getter
    private final By by;
    @Getter
    private final long timeout;
    @Getter
    private final String description;
    @Getter
    private final String errorMsg;

    private final Action action;

    public Component(final By by) {
        this.by = by;
        this.timeout = Config.BASIC_TIMEOUT;
        this.description = "Вызвали " + this.getClass().getSimpleName() + " " + by.toString();
        this.errorMsg = "Элемент " + by + " не найден";
        this.action = new Action(this);
    }

    public Component(final By by, final String description) {
        this.by = by;
        this.timeout = Config.BASIC_TIMEOUT;
        this.description = description;
        this.errorMsg = "Элемент " + by.toString() + " не найден";
        this.action = new Action(this);
    }

    public Component(final By by, final String description, final String errorMsg) {
        this.by = by;
        this.timeout = Config.BASIC_TIMEOUT;
        this.description = description;
        this.errorMsg = errorMsg;
        this.action = new Action(this);
    }

    protected abstract WebElement getComponent();

    public void mouseOver() {
        log.info("Element {} hover", by);
        action.mouseOver();
    }
}
