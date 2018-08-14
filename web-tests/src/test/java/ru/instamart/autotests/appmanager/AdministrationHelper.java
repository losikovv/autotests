package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.configuration.Elements;
import ru.instamart.autotests.configuration.Environments;



    // Administration helper
    // Contains methods for operations within administration panel
    // TODO перенести сюда методы работы с заказами из SessionHelper



public class AdministrationHelper extends HelperBase {

    public AdministrationHelper(WebDriver driver, Environments environment) {
        super(driver, environment);
    }


    /** Возобновить заказ */

    public void resumeOrder(){
        click(Elements.OrderPageAdmin.resumeOrderButton());
        handleAlert();
        waitForIt(1);
    }


    /** Определить отменен ли заказ */

    public boolean isOrderCanceled() {
        return isElementDetected(Elements.OrderPageAdmin.canceledOrderAttribute());
    }



}
