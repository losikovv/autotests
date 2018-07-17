package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.models.EnvironmentData;



    // Profile helper
    // Contains methods for all operations within user profile section



public class ProfileHelper extends HelperBase {

    public ProfileHelper(WebDriver driver, EnvironmentData environment) {
        super(driver, environment);
    }



    // ======= Account =======

    /**
     * Get the Account page in the profile
     */
    public void getAccountPage(){
        getUrl(baseUrl + "user/edit");
    }

    // TODO changePassword(String newPassword, String password) - изменение пароля

    // TODO changeEmail(String newEmail, String password) - изменение email

    // TODO changeFIO(String newName, String newFamily) - изменение имени и фамилии



    // ======= Orders =======

    /**
     * Get the Orders page in the profile
     */
    public void getOrdersPage(){
        getUrl(baseUrl + "user/orders");
    }

    // перейти в детали крайнего заказа
    public void goToLastOrderPage(){
        getOrdersPage();
        click(By.xpath("//*[@id='wrap']/div/div/div/div[2]/div[1]/div/div/div[1]/div/div/div[1]/div[2]/a"));
    }

    // перейти в детали заказа по позиции в списке
    public void goToOrderPage(int orderPosition){
        getOrdersPage();
        click(By.xpath("//*[@id='wrap']/div/div/div/div[2]/div[1]/div/div/div[" + orderPosition + "]/div/div/div[1]/div[2]/a/button"));
    }

    // повторить крайний заказ
    public void repeatLastOrder(){
        printMessage("Repeating last order from profile\n");
        getOrdersPage();
        click(By.xpath("//*[@id='wrap']/div/div/div/div[2]/div[1]/div/div/div[1]/div/div/div[1]/div[2]/button"));
        waitForIt(3);
    }

    // повторить заказ по позиции в списке
    public void repeatOrder (int orderPosition){
        printMessage("Repeating order by position " + orderPosition + " from profile\n");
        getOrdersPage();
        click(By.xpath("//*[@id='wrap']/div/div/div/div[2]/div[1]/div/div/div[" + orderPosition + "]/div/div/div[1]/div[2]/button"));
        waitForIt(3);
    }

    // отменить крайний заказ
    public void cancelLastOrder (){
        printMessage("Canceling last order from profile\n");
        getOrdersPage();
        click(By.xpath("//*[@id='wrap']/div/div/div/div[2]/div[1]/div/div/div[1]/div/div/div[1]/div[2]/button[1]"));
        waitForIt(2);
    }

    // отменить заказ по позиции в списке
    public void cancelOrder (int orderPosition){
        printMessage("Canceling order by position " + orderPosition + " from profile\n");
        getOrdersPage();
        click(By.xpath("//*[@id='wrap']/div/div/div/div[2]/div[1]/div/div/div[" + orderPosition + "]/div/div/div[1]/div[2]/button[1]"));
        waitForIt(2);
    }

    public boolean isLastOrderActive(){
        goToLastOrderPage();
        return isOrderActive();
    }

    public boolean isOrderActive() {
        printMessage("Checking order page...");
        String XPATH = "//*[@id='wrap']/div/div/div/div/div[2]/div/div[1]/div/div/div/div[2]/div[2]";
        if (isElementPresent(By.xpath(XPATH)) && getText(By.xpath(XPATH)).equals("Благодарим за использование Instamart!")){
            printMessage("✓ Order is active\n");
            return true;
        } else {
            return false;
        }
    }

    // TODO
    public boolean isLastOrderCanceled(){
        goToLastOrderPage();
        return isOrderCanceled();
    }

    // TODO
    public boolean isOrderCanceled(){
        if(isElementPresent(By.xpath("// TODO "))){
            return true;
        } else {
            return false;
        }
    }

    // TODO
    public boolean isLastOrderDelivered(){
        goToLastOrderPage();
        return isOrderDelivered();
    }

    // TODO
    public boolean isOrderDelivered(){
        if(isElementPresent(By.xpath("// TODO "))){
            return true;
        } else {
            return false;
        }
    }



    // ======= Addresses =======

    /**
     * Get the Addresses page in the profile
     */
    public void getAddressesPage(){
        getUrl(baseUrl + "user/addresses");
    }

}