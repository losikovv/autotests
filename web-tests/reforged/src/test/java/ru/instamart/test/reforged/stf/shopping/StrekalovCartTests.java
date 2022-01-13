package ru.instamart.test.reforged.stf.shopping;

import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.stf.page.StfRouter.shop;

public final class StrekalovCartTests extends BaseTest {

    private final ApiHelper helper = new ApiHelper();

    @Test(description = "Мой эксперимент")
    public void testStrekalov() {
        var userData = UserManager.getQaUser();
        helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());
        helper.dropAndFillCartMultiple(userData, RestAddresses.Moscow.defaultAddress(), EnvironmentProperties.DEFAULT_SECOND_SID, EnvironmentProperties.DEFAULT_AUCHAN_SID);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactCartNew().checkCartOpen();

        shop().interactCartNew().checkCartNotEmpty();

        shop().interactCartNew().getRetailerByName("METRO").isAlertNotDisplayed();

        System.out.println("Retailers in cart: " + shop().interactCartNew().getAllRetailerNames());
        System.out.println("Retailer data: " + shop().interactCartNew().getRetailerByName("METRO").getRetailerData());

        shop().interactCartNew().getRetailerByName("METRO").getItem(0).openProductCart();
        shop().interactCartNew().interactProductCart().checkProductCardVisible();
        shop().interactProductCard().close();

        shop().interactCartNew().getRetailerByName("Ашан").getItem(0).deleteItem();

        shop().interactCartNew().getRetailerByName("METRO").removeRetailer();
        shop().interactCartNew().interactClearCart().confirm();

        shop().interactCartNew().checkCartEmpty();
        shop().interactCartNew().closeCart();
    }
}
