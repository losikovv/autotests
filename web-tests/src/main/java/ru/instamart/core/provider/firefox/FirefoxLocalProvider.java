package instamart.core.provider.firefox;

import instamart.core.provider.AbstractBrowserProvider;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.BrowserType;

import static instamart.core.settings.Config.DO_CLEANUP_BEFORE_TEST_RUN;

public final class FirefoxLocalProvider extends AbstractBrowserProvider {

    @Override
    public void createDriver() {
        if (DO_CLEANUP_BEFORE_TEST_RUN) {
            cleanProcessByName(BrowserType.FIREFOX);
        }
        driver = new FirefoxDriver();
    }
}
