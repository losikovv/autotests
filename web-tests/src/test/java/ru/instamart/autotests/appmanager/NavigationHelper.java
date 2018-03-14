package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.models.RetailerData;

public class NavigationHelper extends HelperBase {

    public NavigationHelper(WebDriver driver) {
        super(driver);
    }



    // хелпер навигации



    // САЙТ

    // переход на страницу сайта
    public void getPage(String pageName) {
        getUrl(baseUrl + pageName);
    }

    // переход на витрину ретейлера
    public void getRetailerPage(RetailerData retailerData) {
        getUrl(baseUrl + retailerData.getName());
    }

    // переход на страницу чекаута
    public void getCheckoutPage() {
        getUrl(baseUrl + "checkout/edit?");
    }

    // переходы на лендинги
    public void getLandingPage() {
        getUrl(baseUrl);
    }

    public void getMnogoruLandingPage() {
        getUrl(baseUrl + "mnogoru");
    }



    // АДМИНКА

    // переход на страницу админки
    public void getAdminPage(String pageName) {
        getUrl(baseUrl + "admin/" + pageName);
    }

    public  void getAdminOrderPage(String orderNumber){
        getUrl(baseUrl + "admin/orders/" + orderNumber + "/edit");
    }

}
