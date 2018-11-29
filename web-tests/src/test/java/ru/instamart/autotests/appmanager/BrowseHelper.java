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
        if (url.equals(fullBaseUrl)) printMessage("Getting baseURL " + url + "\n");
        try {
            driver.get(url);
        } catch (TimeoutException t) {
            printMessage("Can't get " + url + " by timeout");
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
        waitingFor(1);
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
    public void adminOrderDetails(String orderNumber) {
        adminPage("orders/" + orderNumber + "/edit");
    }
}
