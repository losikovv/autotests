package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.configuration.Elements;
import ru.instamart.autotests.configuration.Environments;



    // Profile helper
    // Contains methods for all operations within user profile section



public class ProfileHelper extends HelperBase {

    private ApplicationManager kraken;

    public ProfileHelper(WebDriver driver, Environments environment) {
        super(driver, environment);
    }



    // ======= Account =======

    /**
     * Get the Account page in the profile
     */
    public void getAccountPage(){
        kraken.get().url(baseUrl + "user/edit");
    }

    // TODO changePassword(String newPassword, String password) - изменение пароля

    // TODO changeEmail(String newEmail, String password) - изменение email

    // TODO changeFIO(String newName, String newFamily) - изменение имени и фамилии



    // ======= Orders =======

    /** Перейти в историю заказов */
    private void getOrdersPage(){
        kraken.get().url(baseUrl + "user/orders");
    }

    /** Перейти в детали крайнего заказа */
    public void goToLastOrderPage(){
        getOrdersPage();
        click(By.xpath("//*[@id='wrap']/div/div/div/div[2]/div[1]/div/div/div[1]/div/div/div[1]/div[2]/a"));
    }

    /** Повторить крайний заказ */
    public void repeatLastOrder(){
        printMessage("Repeating last order from profile...\n");
        getOrdersPage();
        if(isElementPresent(Elements.Site.OrdersPage.lastOrderActionButton(2))) {
            click(Elements.Site.OrdersPage.lastOrderActionButton(2));
        } else {
            click(Elements.Site.OrdersPage.lastOrderActionButton());
        }
        waitFor(2);
    }

    /** Отменить крайний заказ */
    public void cancelLastOrder (){
        printMessage("Canceling last order from profile...\n");
        getOrdersPage();
        if(isElementPresent(Elements.Site.OrdersPage.lastOrderActionButton(2))) {
            click(Elements.Site.OrdersPage.lastOrderActionButton(1));
        } else printMessage("> Skipped because order isn't active");
        waitFor(2);
    }

    /** Определить активен ли заказ */
    public boolean isOrderActive() {
        printMessage("Checking order page...");
        if (isElementDetected(Elements.Site.OrderDetailsPage.activeOrderAttribute())) {
            printMessage("✓ Order is active\n");
            return true;
        } else {
            printMessage("Experiencing performance troubles");
            waitFor(1);
            if (isElementDetected(Elements.Site.OrderDetailsPage.activeOrderAttribute())) {
                printMessage("✓ Order is active\n");
                return true;
            } else return false;
        }
    }

    /** Определить отменен ли заказ */
    public boolean isOrderCanceled(){
        printMessage("Checking order page...");
        if (isElementDetected(Elements.Site.OrderDetailsPage.canceledOrderAttribute())) {
            printMessage("Order is canceled!\n");
            return true;
        } else {
            return false;
        }
    }

    /** Проверка стоимости доставки заказа на странице деталей заказа */
    public boolean checkDeliveryPrice(int price) {
        int deliveryPrice = round(fetchText(Elements.Site.OrderDetailsPage.deliveryPrice()));
        if (deliveryPrice == price) {
            printMessage("✓ Delivery price " + deliveryPrice + "р\n");
            return true;
        } else return false;
    }



    // ======= Addresses =======

    /**
     * Get the Addresses page in the profile
     */
    public void getAddressesPage(){
        kraken.get().url(baseUrl + "user/addresses");
    }

}