package ru.instamart.reforged.core.component;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.ByKraken;

public interface Component {

    By getBy();

    default By getBy(final Object... args) {
        if (getBy() instanceof ByKraken) {
            final ByKraken byKraken = (ByKraken) getBy();
            return ByKraken.xpath(byKraken.getDefaultXpathExpression(), args);
        }
        return getBy();
    }

    long getTimeout();
    String getDescription();
    String getErrorMsg();
}
