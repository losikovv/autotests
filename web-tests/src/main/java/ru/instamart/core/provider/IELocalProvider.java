package instamart.core.provider;

import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;

import static instamart.core.settings.Config.DO_CLEANUP_BEFORE_TEST_RUN;

public final class IELocalProvider extends AbstractBrowserProvider {

    @Override
    public void createDriver() {
        if (DO_CLEANUP_BEFORE_TEST_RUN) {
            cleanProcessByName(BrowserType.IE);
        }
        driver = new InternetExplorerDriver();
    }
}
