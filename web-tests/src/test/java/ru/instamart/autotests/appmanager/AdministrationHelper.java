package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.models.EnvironmentData;



    // Administration helper
    // Contains methods for operations within administration panel
    // TODO перенести сюда методы работы с заказами из SessionHelper



public class AdministrationHelper extends HelperBase {

    public AdministrationHelper(WebDriver driver, EnvironmentData environment) {
        super(driver, environment);
    }

    /*
    public void getUrlAsAdmin(String targetUrl) {
        // trying to get target URL in admin panel
        getUrl(targetUrl);
        // if we don't have admin privileges then log-in as admin and try again
        if (!itsInAdmin()) {
            getBaseUrl();
            if (SessionHelper.isUserAuthorised()) {
                SessionHelper.doLogout();
            }
            SessionHelper.doLoginAsAdmin();
            getUrl(targetUrl);
        }
    }
    */

}
