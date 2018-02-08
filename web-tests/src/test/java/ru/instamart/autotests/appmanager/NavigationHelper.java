package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.models.RetailerData;

// хелпер глобальной навигации по сайту
public class NavigationHelper extends HelperBase {

    public NavigationHelper(WebDriver driver) {
        super(driver);
    }

    // переход по свободному URL
    public void goToUrl(String url) {driver.get(url);}

    // переход на страницу Инстамарта
    public void goToPage(String pagename) {driver.get(baseUrl + pagename);}


    // СТАТИЧЕСКИЕ СТРАНИЦЫ

    public void goToAbout() {driver.get(baseUrl + "about");}
    public void goToDelivery() {driver.get(baseUrl + "delivery");}
    public void goToRules() {driver.get(baseUrl + "rules");}
    public void goToPayment() {driver.get(baseUrl + "payment");}
    public void goToFAQ() {driver.get(baseUrl + "faq");}
    public void goToTerms() {driver.get(baseUrl + "terms");}
    public void goToContacts() {driver.get(baseUrl + "contacts");}


    // ЛЕНДИНГИ

    public void goToLandingPage() { driver.get(baseUrl); }
    public void goToMnogoruLandingPage() {driver.get(baseUrl + "mnogoru"); }


    // переход на витрину ретейлера
    public void goToRetailerPage(RetailerData retailerData) { driver.get(baseUrl + retailerData.getName()); }

    // переход в чекаут
    public void goToCheckout() { driver.get(baseUrl + "checkout/edit?"); }


    // ПРОФИЛЬ

    public void goToProfileAccount() { driver.get(baseUrl + "user/edit"); }
    public void goToProfileOrders() { driver.get(baseUrl + "user/orders"); }
    public void goToProfileAddresses() { driver.get(baseUrl + "user/addresses"); }

}
