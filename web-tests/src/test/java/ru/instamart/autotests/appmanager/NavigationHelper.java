package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.models.RetailerData;

public class NavigationHelper extends HelperBase {

    public NavigationHelper(WebDriver driver) {
        super(driver);
    }



    // хелпер навигации
    // методы get* - совержают переход по заданному URL
    // методы go* - совершают цепочку переходов по UI



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

    // АДМИНКА

    // переход на страницу админки
    public void getPageAdmin(String pageName) {
        getUrl(baseUrl + "admin/" + pageName);
    }

    public  void getOrderPageAdmin(String orderNumber){
        getUrl(baseUrl + "admin/orders/" + orderNumber + "/edit");
    }

}
