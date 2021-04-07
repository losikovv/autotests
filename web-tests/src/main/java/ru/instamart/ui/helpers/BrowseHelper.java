package instamart.ui.helpers;

import instamart.core.common.AppManager;
import instamart.core.helpers.HelperBase;
import instamart.ui.common.lib.Pages;
import instamart.ui.common.pagesdata.EnvironmentData;
import instamart.ui.common.pagesdata.PageData;
import io.qameta.allure.Step;
import org.openqa.selenium.TimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BrowseHelper extends HelperBase {

    private static final Logger log = LoggerFactory.getLogger(BrowseHelper.class);

    public BrowseHelper(final AppManager kraken) {
        super(kraken);
    }

    /** Перейти на указанный URL*/
    @Step("Перейти на указанный URL: {0}")
    public void url(String url) {
        if (url.equals(EnvironmentData.INSTANCE.getBasicUrl())) {
            log.info("Переходим по базовому URL >>> {}", url);
        }
        try {
            kraken.getWebDriver().get(url);
        } catch (TimeoutException t) {
            log.info("Истекло время перехода по URL {}", url);
        }
    }

    /** Перейти на базовый URL */
    public void baseUrl() {
        url(EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth());
    }

    /** Перейти на страницу */
    public void page(String page) {
        log.info("> переходим на страницу: {}{}", EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth(), page);
        url(EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth() + page);
    }

    public void page(PageData page) {
        url(EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth() + page.getPath());
    }

    /** Перейти на страницу чекаута */
    public void checkoutPage() {
        page(Pages.checkout());
    }

    /** Перейти на страницу в админке */
    public void adminPage(String path) {
        url(EnvironmentData.INSTANCE.getAdminUrlWithHttpAuth() + path);
    }

    /** Перейти на страницу заказа в админке */
    public void adminOrderDetailsPage(String orderNumber) {
        adminPage("orders/" + orderNumber + "/edit");
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
