package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.models.RetailerData;

public class NavigationHelper extends HelperBase {

    public NavigationHelper(WebDriver driver) {
        super(driver);
    }



    // хелпер навигации
    // методы getPage просто переходят по базовому URL + название страницы
    // методы goTo для попадания на целевую страницу совершают цепочку фвктических переходов по страницам



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
    public void getPageAdmin(String pageName) {
        getUrl(baseUrl + "admin/" + pageName);
    }

    public  void getOrderPageAdmin(String orderNumber){
        getUrl(baseUrl + "admin/orders/" + orderNumber + "/edit");
    }

}
