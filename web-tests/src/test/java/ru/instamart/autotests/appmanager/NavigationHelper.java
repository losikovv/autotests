package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.models.RetailerData;

public class NavigationHelper extends HelperBase {

    public NavigationHelper(WebDriver driver) {
        super(driver);
    }



    // хелпер навигации



    // переход по свободному URL
    public void getUrl(String url) {driver.get(url);}



    // САЙТ

    // переход на страницу сайта
    public void getPage(String pageName) {driver.get(baseUrl + pageName);}

    // переход на витрину ретейлера
    public void getRetailerPage(RetailerData retailerData) { driver.get(baseUrl + retailerData.getName()); }

    // переход на страницу чекаута
    public void getCheckoutPage() { driver.get(baseUrl + "checkout/edit?"); }

    // переходы на лендинги
    public void getLandingPage() { driver.get(baseUrl); }
    public void getMnogoruLandingPage() {driver.get(baseUrl + "mnogoru"); }



    // АДМИНКА

    // переход на страницу админки
    public void getAdminPage(String pageName) {driver.get(baseUrl + "admin/" + pageName);}

}
