package ru.instamart.autotests.appmanager;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.application.Pages;
import ru.instamart.autotests.appmanager.models.EnvironmentData;
import ru.instamart.autotests.appmanager.models.PageData;

public class BrowseHelper extends HelperBase {

    BrowseHelper(WebDriver driver, EnvironmentData environment, ApplicationManager app) {
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

    public void adminURL() {
        url(adminUrl);
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

    /** Перейти на страницу в админке */
    public void adminPage(String path) {
        url(adminUrl + path);
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
