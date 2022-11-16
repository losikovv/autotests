package ru.instamart.reforged.core.component;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.ByKraken;

import static java.util.Objects.nonNull;

public interface Component {

    By getBy();

    default By getBy(final Object... args) {
        if (nonNull(args) && (args.length > 0) && getBy() instanceof ByKraken) {
            final ByKraken byKraken = (ByKraken) getBy();
            return ByKraken.xpathExpression(byKraken.getDefaultXpathExpression(), args);
        }
        return getBy();
    }

    long getTimeout();
    String getDescription();
    String getErrorMsg();
}
