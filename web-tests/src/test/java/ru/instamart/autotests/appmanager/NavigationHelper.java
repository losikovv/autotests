package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.models.RetailerData;

// хелпер глобальной навигации по сайту
public class NavigationHelper extends HelperBase {

    public NavigationHelper(WebDriver driver) {
        super(driver);
    }

    // переход по свободному URL
    public void getUrl(String url) {driver.get(url);}

    // переход на страницу Инстамарта
    public void getPage(String pagename) {driver.get(baseUrl + pagename);}


    // СТАТИЧЕСКИЕ СТРАНИЦЫ

    public void getPageAbout() {driver.get(baseUrl + "about");}
    public void getPageDelivery() {driver.get(baseUrl + "delivery");}
    public void getPageRules() {driver.get(baseUrl + "rules");}
    public void getPagePayment() {driver.get(baseUrl + "payment");}
    public void getPageFAQ() {driver.get(baseUrl + "faq");}
    public void getPageTerms() {driver.get(baseUrl + "terms");}
    public void getPageContacts() {driver.get(baseUrl + "contacts");}


    // ЛЕНДИНГИ

    public void getLandingPage() { driver.get(baseUrl); }
    public void getMnogoruLandingPage() {driver.get(baseUrl + "mnogoru"); }


    // переход на витрину ретейлера
    public void getRetailerPage(RetailerData retailerData) { driver.get(baseUrl + retailerData.getName()); }

    // переход в чекаут
    public void getPageCheckout() { driver.get(baseUrl + "checkout/edit?"); }


    // ПРОФИЛЬ

    public void getPageProfileAccount() { driver.get(baseUrl + "user/edit"); }
    public void getPageProfileOrders() { driver.get(baseUrl + "user/orders"); }
    public void getPageProfileAddresses() { driver.get(baseUrl + "user/addresses"); }

}
