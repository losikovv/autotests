package ru.instamart.autotests.appmanager;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.application.Environments;
import ru.instamart.autotests.application.Pages;

public class BrowseHelper extends HelperBase {

    private ApplicationManager kraken;

    BrowseHelper(WebDriver driver, Environments environment, ApplicationManager app) {
        super(driver, environment);
        kraken = app;
    }

    /**
     * Перейти на указанный URL
     */
    public void url(String url) {
        if (url.equals(fullBaseUrl)) printMessage("Переходим по базовому URL >>> " + url + "\n");
        try {
            driver.get(url);
        } catch (TimeoutException t) {
            printMessage("Истекло время перехода по URL " + url + "\n");
        }
    }

    /**
     * Перейти на базовый URL
     */
    public void baseUrl() {
        url(fullBaseUrl);
    }

    /**
     * Перейти на страницу
     */
    public void page(String page) {
        url(fullBaseUrl + page);
    }

    /**
     * Перейти на страницу из переданного объекта
     */
    public void page(Pages page) {
        String path = Pages.getPagePath();
        url(fullBaseUrl + path);
    }

    /**
     * Перейти на страницу чекаута
     */
    public void checkoutPage() {
        page("checkout/edit?");
        kraken.perform().waitingFor(1); // Ожидание загрузки чекаута
    }

    /**
     * Перейти на страницу профиля
     */
    public void profilePage() {
        page("user/edit");
    }

    /**
     * Перейти на страницу в админке
     */
    public void adminPage(String path) {
        url(baseUrl + "admin/" + path);
    }

    /**
     * Перейти на страницу заказа в админке
     */
    public void adminOrderDetailsPage(String orderNumber) {
        adminPage("orders/" + orderNumber + "/edit");
    }
}
