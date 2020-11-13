package instamart.core.helpers;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import instamart.ui.common.pagesdata.PageData;
import instamart.core.common.AppManager;
import instamart.ui.common.lib.Pages;
import instamart.ui.common.pagesdata.EnvironmentData;

public class BrowseHelper extends HelperBase {

    public BrowseHelper(WebDriver driver, EnvironmentData environment, AppManager app) {
        super(driver, environment, app);
    }

    /** Перейти на указанный URL*/
    public void url(String url) {
        if (url.equals(environment.getBasicUrl())) {
            verboseMessage("Переходим по базовому URL >>> " + url + "\n");
        }
        try {
            driver.get(url);
        } catch (TimeoutException t) {
            verboseMessage("Истекло время перехода по URL " + url + "\n");
        }
    }

    /** Перейти на базовый URL */
    public void baseUrl() {
        url(environment.getBasicUrlWithHttpAuth());
    }

    /** Перейти на страницу */
    public void page(String page) {
        url(environment.getBasicUrlWithHttpAuth() + page);
    }

    public void page(PageData page) {
        url(environment.getBasicUrlWithHttpAuth() + page.getPath());
    }

    /** Перейти на страницу чекаута */
    public void checkoutPage() {
        page(Pages.checkout());
    }

    /** Перейти на страницу в админке */
    public void adminPage(String path) {
        url(environment.getAdminUrlWithHttpAuth() + path);
    }

    /** Перейти на страницу заказа в админке */
    public void adminOrderDetailsPage(String orderNumber) {
        adminPage("orders/" + orderNumber + "/edit");
    }

    /** Перейти на страницу SEO-каталога */
    public void seoCatalogPage() {
        page(Pages.seo_catalog());
        kraken.await().implicitly(1); // Ожидание загрузки SEO-каталога
    }

    /** Перейти на страницу профиля */
    public void userProfilePage() {
        page(Pages.UserProfile.edit());
    }

    /** Перейти на страницу любимых товаров */
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
