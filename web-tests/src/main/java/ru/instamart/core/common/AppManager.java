package instamart.core.common;

import instamart.api.helpers.InstamartApiHelper;
import instamart.core.factory.BrowserFactory;
import instamart.core.testdata.UserManager;
import instamart.core.testdata.ui.Generate;
import instamart.ui.common.pagesdata.BrowserData;
import instamart.ui.common.pagesdata.EnvironmentData;
import instamart.ui.helpers.*;
import instamart.ui.modules.Administration;
import instamart.ui.modules.Checkout;
import instamart.ui.modules.Shop;
import instamart.ui.modules.User;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

import static instamart.core.settings.Config.*;

public class AppManager {

    private static final Logger log = LoggerFactory.getLogger(AppManager.class);

    public final static String testrunId = Generate.testRunId();

    public WebDriver driver;
    static public BrowserData browserData;

    private BrowseHelper browseHelper;
    private PerformHelper performHelper;
    private ReachHelper reachHelper;
    private DetectionHelper detectionHelper;
    private GrabHelper grabHelper;
    private Shop shopHelper;
    private User userHelper;
    private Checkout checkoutHelper;
    private Administration administrationHelper;
    private WaitingHelper waitingHelper;
    private InstamartApiHelper instamartApiHelper;

    public void rise() {
        initDriver();
        initHelpers();
        applyOptions();
        revealKraken();
        getBasicUrl();
    }

    public void getBasicUrl() {
        driver.get(EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth());
    }

    private void initDriver() {
        driver = BrowserFactory.createBrowserInstance(DEFAULT_BROWSER);
        setCapabilities(driver);
    }

    /**функция получает данные о браузере и тестовой машине */
    private void setCapabilities(final WebDriver driver){
        final Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
        browserData = new BrowserData(cap.getBrowserName(),
                cap.getPlatform().toString(),cap.getVersion());
    }

    private void initHelpers() {
        this.browseHelper = new BrowseHelper(driver,  this);
        this.performHelper = new PerformHelper(driver, this);
        this.reachHelper = new ReachHelper(driver, this);
        this.detectionHelper = new DetectionHelper(driver, this);
        this.grabHelper = new GrabHelper(driver,this);
        this.shopHelper = new Shop(driver, this);
        this.userHelper = new User(driver, this);
        this.checkoutHelper = new Checkout(driver, this);
        this.administrationHelper = new Administration(driver, this);
        this.waitingHelper = new WaitingHelper(driver, this);
        this.instamartApiHelper = new InstamartApiHelper();
    }

    private void applyOptions() {
        if (FULL_SCREEN_MODE) {driver.manage().window().fullscreen(); }
        if (BASIC_TIMEOUT > 0) {driver.manage().timeouts().implicitlyWait(BASIC_TIMEOUT, TimeUnit.SECONDS);}
    }

    private void revealKraken() {
        IS_KRAKEN_REVEALEN = true; // это вот как раз то самое место где она в true ставиться
        // на данный момент решил вопрос через группы при старте тест сьютов
        log.info("ENVIRONMENT: {} ({})", EnvironmentData.INSTANCE.getName(), EnvironmentData.INSTANCE.getBasicUrl());
        log.info("TEST RUN ID: {}", testrunId);
        log.info("ADMIN: {} / {}", UserManager.getDefaultAdmin().getLogin(), UserManager.getDefaultAdmin().getPassword());
        log.info("USER: {} / {}", UserManager.getDefaultUser().getLogin(), UserManager.getDefaultUser().getPassword());
    }

    public void stop() {
        if (driver != null) {
            driver.quit();
        }
    }

    public BrowseHelper get() { return browseHelper; }
    public PerformHelper perform() { return performHelper; }
    public ReachHelper reach() { return reachHelper; }
    public DetectionHelper detect() { return detectionHelper; }
    public GrabHelper grab() { return grabHelper; }
    public Shop shopping() { return shopHelper; }
    public User user() { return userHelper; }
    public Checkout checkout() { return checkoutHelper; }
    public Administration admin() { return administrationHelper; }
    public WaitingHelper await() { return waitingHelper; }
    public InstamartApiHelper apiV2() { return instamartApiHelper; }
}
