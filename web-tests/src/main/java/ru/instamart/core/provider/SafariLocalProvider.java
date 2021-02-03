package instamart.core.provider;

import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.safari.SafariDriver;

import static instamart.core.settings.Config.DO_CLEANUP_BEFORE_TEST_RUN;

public final class SafariLocalProvider extends AbstractBrowserProvider {

    @Override
    public void createDriver() {
        if (DO_CLEANUP_BEFORE_TEST_RUN) {
            cleanProcessByName(BrowserType.SAFARI);
        }
        driver = new SafariDriver();
    }
}
