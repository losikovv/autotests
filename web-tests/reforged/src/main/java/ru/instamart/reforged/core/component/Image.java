package ru.instamart.reforged.core.component;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.Kraken;

import static java.util.Objects.isNull;

@Slf4j
public final class Image extends AbstractComponent {

    public Image(By by, String description) {
        super(by, description);
    }

    @Override
    protected WebElement getComponent() {
        log.debug("Create {} with locator {}", getDescription(), getBy());
        if (isNull(component) || isCacheDisable) {
            component = shouldBe().visible();
        }
        return component;
    }

    /**
     * ожидание загрузки элемента
     */
    public synchronized void waitImgLoad(final Object... args) {
        setBy(ByKraken.xpathExpression(((ByKraken) getBy()).getDefaultXpathExpression(), args));
        waitImgLoad();
    }

    /**
     * ожидание загрузки элемента
     */
    public void waitImgLoad() {
        log.debug("Get img '{}' with locator {}", getDescription(), getBy());
        Kraken.jsAction().waitImgLoad(getLocator());
    }
}
