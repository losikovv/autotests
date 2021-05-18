package ru.instamart.ui.data;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.openqa.selenium.By;
import ru.instamart.kraken.setting.Config;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public final class ElementData {

    private final By locator;
    private final String description;
    private final int timeout;

    public ElementData(final By locator, final String description) {
        this(locator, description, Config.BASIC_TIMEOUT);
    }
}
