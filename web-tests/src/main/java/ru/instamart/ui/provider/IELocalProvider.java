package ru.instamart.ui.provider;

import ru.instamart.core.util.ProcessUtils;
import org.openqa.selenium.remote.BrowserType;

import java.util.Optional;

import static ru.instamart.core.settings.Config.DO_CLEANUP_BEFORE_TEST_RUN;

public final class IELocalProvider extends AbstractBrowserProvider {

    @Override
    public void createDriver(final String version) {
        if (DO_CLEANUP_BEFORE_TEST_RUN) {
            ProcessUtils.cleanProcessByName(BrowserType.IE);
        }
        createLocalIEDriver(Optional.empty());
    }
}
