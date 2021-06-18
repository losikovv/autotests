package ru.instamart.ui.helper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.instamart.ui.manager.AppManager;
import ru.instamart.kraken.testdata.pagesdata.PageData;
import ru.instamart.ui.module.Shop;
import ru.instamart.ui.module.User;

@RequiredArgsConstructor
@Slf4j
public final class ReachHelper extends HelperBase {

    private final AppManager kraken;

    public void checkout() {
        log.info("Переход на страницу чекаута по ссылке");
        kraken.get().checkoutPage();
        if(!kraken.detect().isOnCheckout()){
            log.info("> не выполнился переход на страницу чекаута, недостаточная сумма заказа. Собираем корзину до минимальной суммы");
            kraken.perform().refresh(); // Скипаем возможный алерт о минимальном заказе
            Shop.Cart.collectFirstTime();
            //Shop.Cart.collect();
            Shop.Cart.proceedToCheckout();
        }
    }
    private void adminPageOpen(String path){
        log.info("> пытаемся попасть на страницу {} админки...", path);
        kraken.get().adminPage(path);
    }

    public void admin() {
        admin("");
    }

    public void admin(PageData page) {
        admin(page.getPath());
    }

    public void admin(String path) {
        adminPageOpen(path);
        log.info("✓ Готово");
    }

    public void admin(String email,String password,String role){
        adminPageOpen("");
        User.Auth.withEmail(email,password,role);
    }

    public void seoCatalog() {
        User.Logout.quickly();
        AppManager.deleteAllCookie();
        kraken.get().seoCatalogPage();
    }

    /** Деавторизоваться, оставшись на текущей странице */
    public void logout() {
        if (kraken.detect().isUserAuthorised()) {
            String currentURL = kraken.grab().currentURL();
            User.Logout.quickly();
            kraken.get().url(currentURL);
        }
    }
}
