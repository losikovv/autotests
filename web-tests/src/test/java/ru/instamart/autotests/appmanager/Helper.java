package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;



    // Orders helper for operations with test orders



public class OrdersHelper extends HelperBase {
    public OrdersHelper(WebDriver driver) {
        super(driver);
    }



    // ======= Admin panel  =======

    /**
     * Cancel all test orders from admin panel
     */
    public void cancelAllTestOrders() {

        //URL целевой страницы со списком тестовых заказов
        final String targetUrlForTestOrders = baseUrl + "admin/shipments?search%5Bemail%5D=%40example.com&search%5Bonly_completed%5D=1";

        //идем на целевую страницу
        driver.get(targetUrlForTestOrders);





    }

    /**
     * Find out if the order is canceled or not by checking the order page in admin panel
     */
    public boolean orderIsCanceled() {
        String XPATH = "//*[@id='content']/div/table/tbody/tr[3]/td/b";
        // checks status on the order page in admin panel
        if (isElementPresent(By.xpath(XPATH))){
            return getText(By.xpath(XPATH)).equals("ЗАКАЗ ОТМЕНЕН");
        } else {
            return false;
        }
    }

    /**
     * Cancel order on the order page in admin panel
     */
    public void cancelOrderFromAdmin(){
        printMessage("Canceling the test order from admin panel");
        // click cancel button
        click(By.xpath("//*[@id='content-header']/div/div/div/div[2]/ul/li[1]/form/button"));
        // accept order cancellation alert
        closeAlertAndGetItsText();
        // fill order cancellation reason form
        fillField(By.name("cancellation[reason]"),"Тестовый заказ");
        // click confirmation button
        click(By.xpath("//*[@id='new_cancellation']/fieldset/div[2]/button"));
        waitForIt();
    }

    /**
     * Resume canceled order on the order page in admin panel
     */
    public void resumeOrderFromAdmin(){
        printMessage("Resuming the test order from admin panel");
        // click resume button
        click(By.xpath("//*[@id='content-header']/div/div/div/div[2]/ul/li[1]/form/button"));
        // accept order resume alert
        closeAlertAndGetItsText();
        waitForIt();
    }

}
