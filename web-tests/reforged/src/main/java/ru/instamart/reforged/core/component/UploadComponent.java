package ru.instamart.reforged.core.component;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.Kraken;

import java.nio.file.Paths;

import static java.util.Objects.isNull;

/**
 * Компонент для загрузки файлов на сервер. Input с типом file, локатор //input[@type='file']
 */
@ToString(callSuper = true)
@Slf4j
public final class UploadComponent extends AbstractComponent {

    public UploadComponent(final By by, final String description) {
        super(by, description);
    }

    public UploadComponent(final By by, final long timeout, final String description) {
        super(by, timeout, description);
    }

    public UploadComponent(final By by, final String description, final String errorMsg) {
        super(by, description, errorMsg);
    }

    public UploadComponent(final By by, final long timeout, final String description, final String errorMsg) {
        super(by, timeout, description, errorMsg);
    }

    @Override
    protected WebElement getComponent() {
        log.debug("Create {} with locator {}", getDescription(), getBy());
        if (isNull(component) || isCacheDisable) {
            component = Kraken.waitAction().shouldExist(this);
        }
        return component;
    }

    public void upload(final String filePath, final Object... args) {
        setBy(ByKraken.xpath(((ByKraken) getBy()).getDefaultXpathExpression(), args));
        upload(filePath);
    }

    public void upload(final String filePath) {
        var absoluteFilePath = getAbsoluteFilePath(filePath);
        log.debug("Fill {} with locator {} and data {}", getDescription(), getBy(), absoluteFilePath);
        getComponent().sendKeys(absoluteFilePath);
    }

    private String getAbsoluteFilePath(final String filePath) {
        if (!Paths.get(filePath).isAbsolute())
            return Paths.get(filePath).toAbsolutePath().toString();
        return filePath;
    }
}
