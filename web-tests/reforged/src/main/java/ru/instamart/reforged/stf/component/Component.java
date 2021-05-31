package ru.instamart.reforged.stf.component;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.instamart.kraken.setting.Config;

@RequiredArgsConstructor
@ToString
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
        this.description = "Вызвали " + this.getClass().getSimpleName();
        this.errorMsg = "Элемент " + by.toString() + " не найден";
        this.action = new Action(this);
    }

    protected abstract WebElement getComponent();

    public void mouseOver() {
        action.mouseOver();
    }
}
