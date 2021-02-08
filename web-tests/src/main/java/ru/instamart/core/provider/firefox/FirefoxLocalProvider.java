package instamart.core.provider.firefox;

import instamart.core.provider.AbstractBrowserProvider;
import instamart.core.util.ProcessUtils;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.BrowserType;

import java.util.Optional;

import static instamart.core.settings.Config.DO_CLEANUP_BEFORE_TEST_RUN;

public final class FirefoxLocalProvider extends AbstractBrowserProvider {

    @Override
    public void createDriver(final String version) {
        if (DO_CLEANUP_BEFORE_TEST_RUN) {
            ProcessUtils.cleanProcessByName(BrowserType.FIREFOX);
        }
        createLocalFirefoxDriver(Optional.empty());
    }
}
