package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.models.RetailerData;

// хелпер глобальной навигации по сайту
public class SiteNavHelper extends HelperBase {

    public SiteNavHelper(WebDriver driver) {
        super(driver);
    }

    // переход на лендинг
    public void goToLandingPage() {
        driver.get(baseUrl);
    }

    // переход на витрину ретейлера
    public void goToRetailerPage(RetailerData retailerData) {
        driver.get(baseUrl + retailerData.getName());
    }

    // переход в чекаут
    public void goToCheckout() { driver.get(baseUrl + "checkout/edit?"); }

    // переход в Профиль > Аккаунт
    public void goToProfileAccount() { driver.get(baseUrl + "user/edit"); }

    // переход в Профиль > Заказы
    public void goToProfileOrders() { driver.get(baseUrl + "user/orders"); }

    // переход в Профиль > Адреса
    public void goToProfileAddresses() { driver.get(baseUrl + "user/addresses"); }

}
