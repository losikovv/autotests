package ru.instamart.ui.helper;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.TimeoutException;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.testdata.lib.Pages;
import ru.instamart.kraken.testdata.pagesdata.PageData;
import ru.instamart.ui.manager.AppManager;

@Slf4j
public final class BrowseHelper extends HelperBase {

    /** Перейти на указанный URL*/
    @Step("Перейти на указанный URL: {0}")
    public void url(String url) {
        if (url.equals(EnvironmentProperties.Env.FULL_SITE_URL)) {
            log.debug("Переходим по базовому URL >>> {}", url);
        }
        try {
            AppManager.getWebDriver().get(url);
            AppManager.setCookie();
            AppManager.getWebDriver().navigate().refresh();
        } catch (TimeoutException t) {
            log.debug("Истекло время перехода по URL {}", url);
        }
    }

    /** Перейти на базовый URL */
    public void baseUrl() {
        url(EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH);
    }

    /** Перейти на страницу */
    public void page(String page) {
        log.debug("> переходим на страницу: {}{}", EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH, page);
        url(EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + page);
    }

    public void page(PageData page) {
        url(EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + page.getPath());
    }

    /** Перейти на страницу чекаута */
    public void checkoutPage() {
        page(Pages.checkout());
    }

    /** Перейти на страницу в админке */
    public void adminPage(String path) {
        url(EnvironmentProperties.Env.FULL_ADMIN_URL_WITH_BASIC_AUTH + path);
    }

    /** Перейти на страницу заказа в админке */
    public void adminOrderDetailsPage(String orderNumber) {
        adminPage("ru.instamart.test.ui.orders/" + orderNumber + "/edit");
    }

    /** Перейти на страницу SEO-каталога */
    public void seoCatalogPage() {
        page(Pages.seo_catalog());
    }

    /** Перейти на страницу профиля */
    public void userProfilePage() {
        page(Pages.UserProfile.edit());
    }

    /** Перейти на страницу любимых товаров */
    @Step("Переходим на страницу Любимые продукты")
    public void userFavoritesPage() {
        page(Pages.UserProfile.favorites());
    }

    /** Перейти на страницу заказов юзера */
    public void userShipmentsPage(){
        page(Pages.UserProfile.shipments());
    }

    /** Перейти на страницу деталей заказа */
    public void userShipmentPage(String shipmentNumber){
        page(Pages.UserProfile.orderDetails(shipmentNumber));
    }

    // TODO сделать метод go принимающий массив элементов и кликающий их по очереди
    // TODO public void go(Elements[] elements){ }
}
