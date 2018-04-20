package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.models.RetailerData;



    // Navigation helper
    // Handles navigation within system under test
    // 'get' methods navigate by getting URLs
    // 'go' methods navigate by making transitions through UI



public class NavigationHelper extends HelperBase {

    public NavigationHelper(WebDriver driver) {
        super(driver);
    }



    // ======= SITE =======

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

    // переход в профиль
    public void getProfilePage()  {
        getUrl(baseUrl + "user/edit");
    }

    // переходы на лендинги
    public void getLandingPage() {
        getUrl(baseUrl);
    }

    public void getMnogoruLandingPage() {
        getUrl(baseUrl + "mnogoru");
    }

    public void goToProfile() {
        // только для авторизованного
        // клик по кнопке Профиль
        click(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[5]/div/div[1]"));
        // клик по кнопке Профиль
        click(By.linkText("Профиль"));
    }

    public void goToHomepage() {
        // клик по кнопке Главная
        click(By.linkText("Главная"));
    }



    // ======= ADMIN =======

    // переход на страницу админки
    public void getAdminPage(String pageName) {
        getUrl(baseUrl + "admin/" + pageName);
    }

    public void getOrderAdminPage(String orderNumber){
        getUrl(baseUrl + "admin/orders/" + orderNumber + "/edit");
    }

    public void getTestUsersAdminPage(){
        getAdminPage("users?q%5Bemail_cont%5D=testuser%40example.com");
    }

    public void getTestOrdersAdminPage(){
        getAdminPage("shipments?search%5Bemail%5D=autotestuser%40instamart.ru&search%5Bonly_completed%5D=1&search%5Bstate%5D=ready");
    }

}
