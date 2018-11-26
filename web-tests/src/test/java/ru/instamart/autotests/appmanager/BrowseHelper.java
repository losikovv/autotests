package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.configuration.Elements;
import ru.instamart.autotests.configuration.Environments;
import ru.instamart.autotests.configuration.Pages;

public class BrowseHelper extends HelperBase {

    private ApplicationManager kraken;

    BrowseHelper(WebDriver driver, Environments environment, ApplicationManager app){
        super(driver, environment);
        kraken = app;
    }

    /** Перейти на указанный URL */
    public void url(String url) {
        if (url.equals(fullBaseUrl)) printMessage("Getting baseURL " + url + "\n");
        try {
            driver.get(url);
        } catch (TimeoutException t) {
            printMessage("Can't get " + url + " by timeout");
        }
    }

    /** Перейти на базовый URL */
    public void baseUrl() {
        url(fullBaseUrl);
    }

    /** Перейти на страницу */
    public void page(String page) {
        url(fullBaseUrl + page);
    }

    /** Перейти на страницу из переданного объекта */
    public void page(Pages page) {
        String path = Pages.getPagePath();
        url(fullBaseUrl + path);
    }

    /** Перейти на страницу ретейлера */
    public void retailerPage(String retailerName) {
        url(baseUrl + retailerName);
    }

    /** Перейти на страницу чекаута */
    public void checkoutPage() {
        url(baseUrl + "checkout/edit?");
        waitFor(1);
    }



    /** Навигация переходами по элементам на страницах */
    // TODO вынести в отдельный хелпер
    public void go(Elements element){
        kraken.perform().click(Elements.getLocator());
    }

    // TODO сделать метод go принимающий массив элементов и кликающий их по очереди
    // TODO public void go(Elements[] elements){ }


    // ======= ADMIN =======

    /**
     * Get page in admin panel
     */
    public void getAdminPage(String pageName) {
        url(baseUrl + "admin/" + pageName);
    }

    /**
     * Get order page in admin panel
     */
    public void getOrderAdminPage(String orderNumber){
        url(baseUrl + "admin/orders/" + orderNumber + "/edit");
    }

    /**
     * Get page with the list of test users in admin panel
     */
    public void getTestUsersAdminPage(){
        getAdminPage("users?q%5Bemail_cont%5D=testuser%40example.com");
    }

    /**
     * Get page with the list of test orders in admin panel
     */
    public void getTestOrdersAdminPage(){
        getAdminPage("shipments?search%5Bemail%5D=autotestuser%40instamart.ru&search%5Bonly_completed%5D=1&search%5Bstate%5D%5B%5D=ready");
    }
}
