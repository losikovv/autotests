package ru.instamart.test.reforged.stf.shopping;

import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.stf.page.StfRouter.shop;

public final class StrekalovCartTests extends BaseTest {

    private final ApiHelper helper = new ApiHelper();

    @Test(description = "Мой эксперимент")
    public void testStrekalov() {
        var userData = UserManager.getQaUser();
        helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());
        helper.dropAndFillCart(userData, 1, 3);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        System.out.println("IsCartDisplayed: "+ shop().interactCartNew().isDisplayed());

        System.out.println("isCartEmpty: " + shop().interactCartNew().isCartEmpty());
        System.out.println("isAlertDisplayed: " + shop().interactCartNew().getRetailerByName("METRO").isAlertDisplayed());

        System.out.println("Retailers in cart: " + shop().interactCartNew().getAllRetailerNames());
        System.out.println("Retailer data: " + shop().interactCartNew().getRetailerByName("METRO").getRetailerData());

        shop().interactCartNew().getRetailerByName("METRO").getAllItems().get(1).deleteItem();
        shop().interactCartNew().getRetailerByName("METRO").getAllItems().get(0).deleteItem();

        shop().interactCartNew().getRetailerByName("METRO").getAllItems().get(0).openItemPopupInfo();
        System.out.println("Is advanced item info displayed: " + shop().interactCartNew().itemAdvancedInfoPopup.isDisplayed());

        System.out.println("Item data from advanced info: " + shop().interactCartNew().itemAdvancedInfoPopup.getItemData());
        shop().interactCartNew().itemAdvancedInfoPopup.closePopup();

        System.out.println("isAlertDisplayed: " + shop().interactCartNew().getRetailerByName("METRO").isAlertDisplayed());

        shop().interactCartNew().getRetailerByName("METRO").removeRetailer();
        shop().interactCartNew().removeRetailerConfirmPopup.clickConfirm();

        System.out.println("isCartEmpty: " + shop().interactCartNew().isCartEmpty());
    }

}
