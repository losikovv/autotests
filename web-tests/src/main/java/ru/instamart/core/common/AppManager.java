package instamart.core.common;

import instamart.api.helpers.InstamartApiHelper;
import instamart.core.service.WebDriverService;
import instamart.ui.common.pagesdata.EnvironmentData;
import instamart.ui.helpers.*;
import instamart.ui.modules.Administration;
import instamart.ui.modules.Checkout;
import instamart.ui.modules.Shop;
import instamart.ui.modules.User;
import org.openqa.selenium.WebDriver;

public final class AppManager {

    private final WebDriverService webDriverService = new WebDriverService();

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
        this.browseHelper = new BrowseHelper(this);
        this.performHelper = new PerformHelper(this);
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

    public void getBasicUrl() {
        webDriverService.createOrGetDriver().get(EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth());
    }

    public WebDriver getWebDriver() {
        return webDriverService.createOrGetDriver();
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
