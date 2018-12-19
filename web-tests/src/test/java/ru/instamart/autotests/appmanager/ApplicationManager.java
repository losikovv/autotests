package ru.instamart.autotests.appmanager;

import org.openqa.selenium.*;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;

import ru.instamart.autotests.application.Environments;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.fail;

public class ApplicationManager {

    protected WebDriver driver;
    protected Environments environment = new Environments("staging"); // use "production" or "staging"
    private String browser;

    protected String environmentName = environment.getEnvironmentName();
    protected String host = environment.getHost();
    protected String baseUrl = environment.getBaseURL(true);

    // Helpers
    private BrowseHelper browseHelper;
    private NavigationHelper navigationHelper;
    private PerformHelper performHelper;
    private DetectionHelper detectionHelper;
    private GrabHelper grabHelper;
    private AddressHelper addressHelper;
    private SearchHelper searchHelper;
    private ShoppingHelper shoppingHelper;
    private CheckoutHelper checkoutHelper;
    private AdministrationHelper administrationHelper;
    private CleanupHelper cleanupHelper;

    private StringBuffer verificationErrors = new StringBuffer();

    public ApplicationManager(String browser) {
        this.browser = browser;
    }

    public void rise() throws IOException {

        switch (browser) {
            case BrowserType.FIREFOX:
                driver = new FirefoxDriver();
                break;
            case BrowserType.CHROME:
                driver = new ChromeDriver();
                break;
            case BrowserType.SAFARI:
                driver = new SafariDriver();
                break;
            // there is no IE driver for mac yet :(
            case BrowserType.IE:
                driver = new InternetExplorerDriver();
                break;
        }

        // Helpers init
        browseHelper = new BrowseHelper(driver, environment, this);
        navigationHelper = new NavigationHelper(driver, environment, this);
        performHelper = new PerformHelper(driver, environment, this);
        detectionHelper = new DetectionHelper(driver, environment, this);
        grabHelper = new GrabHelper(driver,environment,this);
        addressHelper = new AddressHelper(driver, environment, this);
        searchHelper = new SearchHelper(driver, environment, this);
        shoppingHelper = new ShoppingHelper(driver, environment, this);
        checkoutHelper = new CheckoutHelper(driver, environment, this);
        administrationHelper = new AdministrationHelper(driver, environment, this);
        cleanupHelper = new CleanupHelper(driver, environment, this);

        // Options
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS); // Basic timeout
        //driver.manage().window().fullscreen();                              // Open browser in fullscreen mode

        revealKraken();

        driver.get(baseUrl);
    }

    public void stop() {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private void revealKraken() throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("banner.txt"));
        String line = in.readLine();

        while(line !=null) {
            System.out.println(line);
            line = in.readLine();
        }
        in.close();
        System.out.println("\nENVIRONMENT: " + environmentName + " ( " + host + " ) \n");
    }

    // Getters
    public BrowseHelper get() { return browseHelper; }
    public NavigationHelper go() { return navigationHelper; }
    public PerformHelper perform() { return performHelper; }
    public DetectionHelper detect() { return detectionHelper; }
    public GrabHelper grab() { return grabHelper; }
    public AddressHelper shipAddress() { return addressHelper; }
    public SearchHelper search() { return searchHelper; }
    public ShoppingHelper shopping() { return shoppingHelper; }
    public CheckoutHelper checkout() { return checkoutHelper; }
    public AdministrationHelper admin() { return administrationHelper; }
    public CleanupHelper cleanup() { return cleanupHelper; }
}
