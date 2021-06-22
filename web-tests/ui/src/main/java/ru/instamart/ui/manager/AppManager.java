package ru.instamart.ui.manager;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.InvalidCookieDomainException;
import org.openqa.selenium.WebDriver;
import ru.instamart.api.helper.InstamartApiHelper;
import ru.instamart.kraken.testdata.pagesdata.EnvironmentData;
import ru.instamart.ui.helper.*;
import ru.instamart.ui.module.Administration;
import ru.instamart.ui.module.Shop;
import ru.instamart.ui.module.User;
import ru.instamart.ui.module.checkout.Checkout;
import ru.instamart.ui.service.WebDriverService;

import static java.util.Objects.isNull;

@Slf4j
public final class AppManager {

    private static final Cookie abCookie = new Cookie("external_analytics_anonymous_id",
            "c196a347-c3c9-421c-b3dc-b50eb8824f5f",
            "sbermarket.tech",
            "/",
            null);
    //cookies_consented = yes
    private static final Cookie cookieAlert = new Cookie("cookies_consented",
            "yes",
            "sbermarket.tech",
            "/",
            null);

    @Getter
    private static final WebDriverService webDriverService = new WebDriverService();

    private final BrowseHelper browseHelper;
    private final PerformHelper performHelper;
    private final ReachHelper reachHelper;
    private final DetectionHelper detectionHelper;
    private final GrabHelper grabHelper;
    private final Shop shopHelper;
    private final User userHelper;
    private final Checkout checkoutHelper;
    private final Administration administrationHelper;
    private final WaitingHelper waitingHelper;
    private final InstamartApiHelper instamartApiHelper;

    public AppManager() {
        this.browseHelper = new BrowseHelper();
        this.performHelper = new PerformHelper();
        this.reachHelper = new ReachHelper(this);
        this.detectionHelper = new DetectionHelper(this);
        this.grabHelper = new GrabHelper(this);
        this.shopHelper = new Shop(this);
        this.userHelper = new User(this);
        this.checkoutHelper = new Checkout(this);
        this.administrationHelper = new Administration(this);
        this.waitingHelper = new WaitingHelper(this);
        this.instamartApiHelper = new InstamartApiHelper();
    }

    public static void getBasicUrl() {
        webDriverService.createOrGetDriver().get(EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth());
    }

    public static WebDriver getWebDriver() {
        var webDriver = webDriverService.createOrGetDriver();
        /*if (webDriver != null && !webDriverService.isStillAlive(webDriver)) {
            webDriverService.closeDriver();
        } else */if (isNull(webDriver)) {
            webDriverService.closeDriver();
        }
        return webDriverService.createOrGetDriver();
    }

    public static void closeWebDriver() {
        webDriverService.closeDriver();
    }

    public static void setCookie() {
        try {
            setABCookie();
            setCookieAlert();
        } catch (InvalidCookieDomainException e) {
            log.error("FAILED: Add cookie ", e);
        }
    }

    private static void setCookieAlert() {
        getWebDriver().manage().addCookie(cookieAlert);
    }

    private static void setABCookie() {
        getWebDriver().manage().getCookies().removeIf(cookie -> cookie.getName().equals(abCookie.getName()));
        getWebDriver().manage().addCookie(abCookie);
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