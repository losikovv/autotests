package instamart.core.provider;

import instamart.core.util.ProcessUtils;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;

import java.util.Optional;

import static instamart.core.settings.Config.DO_CLEANUP_BEFORE_TEST_RUN;

public final class IELocalProvider extends AbstractBrowserProvider {

    @Override
    public void createDriver(final String version) {
        if (DO_CLEANUP_BEFORE_TEST_RUN) {
            ProcessUtils.cleanProcessByName(BrowserType.IE);
        }
        createLocalIEDriver(Optional.empty());
    }
}
