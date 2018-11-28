package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Environments;



    // Profile helper
    // Contains methods for all operations within user profile section



public class ProfileHelper extends HelperBase {

    private ApplicationManager kraken;

    ProfileHelper(WebDriver driver, Environments environment, ApplicationManager app) {
        super(driver, environment);
        kraken = app;
    }

    // ======= Profile > Orders ======= // TODO перенести все в perform

    /** Перейти в детали крайнего заказа */
    public void goToLastOrderPage(){ // TODO перенести в go
        kraken.get().url(baseUrl + "user/orders");
        kraken.perform().click(By.xpath("//*[@id='wrap']/div/div/div/div[2]/div[1]/div/div/div[1]/div/div/div[1]/div[2]/a"));
    }

    /** Повторить крайний заказ */
    public void repeatLastOrder(){
        printMessage("Repeating last order from profile...\n");
        kraken.get().url(baseUrl + "user/orders");
        if(isElementPresent(Elements.Site.OrdersPage.lastOrderActionButton(2))) {
            kraken.perform().click(Elements.Site.OrdersPage.lastOrderActionButton(2));
        } else {
            kraken.perform().click(Elements.Site.OrdersPage.lastOrderActionButton());
        }
        waitingFor(2);
    }

    /** Отменить крайний заказ */
    public void cancelLastOrder (){
        printMessage("Canceling last order from profile...\n");
        kraken.get().url(baseUrl + "user/orders");
        if(isElementPresent(Elements.Site.OrdersPage.lastOrderActionButton(2))) {
            kraken.perform().click(Elements.Site.OrdersPage.lastOrderActionButton(1));
        } else printMessage("> Skipped because order isn't active");
        waitingFor(2);
    }

}