package ru.instamart.application.platform.helpers;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import ru.instamart.application.Servers;
import ru.instamart.application.models.PageData;
import ru.instamart.application.AppManager;
import ru.instamart.application.lib.Pages;
import ru.instamart.application.models.ServerData;

public class BrowseHelper extends HelperBase {

    public BrowseHelper(WebDriver driver, ServerData environment, AppManager app) {
        super(driver, environment, app);
    }

    /** Перейти на указанный URL*/
    public void url(String url) {
        if (url.equals(fullBaseUrl)) {
            debugMessage("Переходим по базовому URL >>> " + url + "\n");
        }
        try {
            driver.get(url);
        } catch (TimeoutException t) {
            debugMessage("Истекло время перехода по URL " + url + "\n");
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

    public void page(PageData page) {
        url(fullBaseUrl + page.getPath());
    }

    /** Перейти на страницу чекаута */
    public void checkoutPage() {
        page("checkout/edit?");
    }

    /** Перейти на страницу профиля */
    public void profilePage() {
        page("user/edit");
    }

    /** Перейти на страницу любимых товаров */
    public void favoritesPage() {
        page(Pages.Site.Profile.favorites());
        kraken.await().implicitly(2); // Ожидание загрузки Любимых товаров
    }

    public void adminURL() {
        adminPage("");
    }

    /** Перейти на страницу в админке */
    public void adminPage(String path) {
        switch(kraken.server.getEnvironment()) {
            case "production" :
                url( Servers.instamart_production().getBaseURL(true)+ "admin/" + path);
                break;
            case "preprod" :
                url( Servers.instamart_preprod().getBaseURL(true)+ "admin/" + path);
                break;
            case "staging" :
                url( Servers.instamart_staging().getBaseURL(true)+ "admin/" + path);
                break;

            default: throw new AssertionError("Неопределенное окружение");
        }

    }

    /** Перейти на страницу заказа в админке */
    public void adminOrderDetailsPage(String orderNumber) {
        adminPage("orders/" + orderNumber + "/edit");
    }

    /** Перейти на страницу SEO-каталога */
    public void seoCatalogPage() {
        page(Pages.Site.Catalog.seo());
        kraken.await().implicitly(1); // Ожидание загрузки SEO-каталога
    }

    /** Перейти на страницу заказов юзера */
    public void ordersPage(){
        page("user/orders");
    }

    /** Перейти на страницу адресов юзера */
    public void addressesPage(){
        page("user/addresses");
    }

    // TODO сделать метод go принимающий массив элементов и кликающий их по очереди
    // TODO public void go(Elements[] elements){ }
}
